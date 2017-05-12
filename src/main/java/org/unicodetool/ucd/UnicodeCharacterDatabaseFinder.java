package org.unicodetool.ucd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.unicodetool.ucd.schema.CodePoint;
import org.unicodetool.ucd.schema.Repertoire;
import org.unicodetool.ucd.schema.UcdContent;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class UnicodeCharacterDatabaseFinder {

    private final Jaxb2Marshaller marshaller;
    private final InputStream ucdStream;
    private UcdContent ucd;

    /**
     * Construct the Unicode Character Database Finder.
     *
     * @param marshaller the JAXB Marshaller that will be used to decode the xml stream
     * @param ucdStream An InputStream which contains the XML data of the unicode character database
     */
    @Autowired
    public UnicodeCharacterDatabaseFinder(
            Jaxb2Marshaller marshaller,
            InputStream ucdStream
    ) {
        this.marshaller = marshaller;
        this.ucdStream = ucdStream;
    }

    /**
     * Initialize the Unicode Character Database Finder.
     *
     * The initialization consist in the unmarshalling of the ucd into memory.
     */
    @PostConstruct
    public void init() throws IOException {
        log.info("Starting unmarshalling the Unicode Character Database");
        ucd =(UcdContent) ((JAXBElement) marshaller.unmarshal(
                new StreamSource(ucdStream)
        )).getValue();
        log.info("Successfully unmarshalled the Unicode Character Database");
    }

    /**
     * Return a ucd Codepoint based on its value.
     *
     * @param formattedValue the integer value of the codepoint
     * @return The codepoint as a UCD XML Object.
     */
    @Cacheable("codepoint")
    public Optional<CodePoint> findCodepoint(String formattedValue) {
        log.debug(String.format("Cache miss for codepoint %s", formattedValue));

        // Return true if the strValue above is equal to the given codepoint value,
        // or in the given codepoint range
        Predicate<CodePoint> codepointMatches =
                codepoint -> (codepoint.getCp() != null)
                        ? formattedValue.equals(codepoint.getCp())
                        : (
                                formattedValue.compareTo(codepoint.getFirstCp())>=0 &&
                                formattedValue.compareTo(codepoint.getLastCp())<=0
                        )
                ;

        return ucd.getDescriptionOrRepertoireOrBlocks().parallelStream()
                .filter(obj -> Repertoire.class.isAssignableFrom(obj.getClass()))
                .flatMap(obj -> ((Repertoire)obj).getCodePointOrGroup().parallelStream())
                .map(obj -> ((JAXBElement)obj).getValue())
                .filter(obj -> CodePoint.class.isAssignableFrom(obj.getClass()))
                .map(obj -> (CodePoint) obj)
                .filter(codepointMatches)
                .findAny();
    }
}
