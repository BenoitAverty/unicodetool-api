package org.unicodetool.ucd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

@Component
@Slf4j
public class TestUnicodeData {

    private final Jaxb2Marshaller marshaller;
    private final File unicodeCharacterDatabaseXml;

    @Autowired
    public TestUnicodeData(Jaxb2Marshaller marshaller,
                           @Value("classpath:ucd/ucd.all.flat.xml") File ucdFile) {
        this.marshaller = marshaller;
        this.unicodeCharacterDatabaseXml = ucdFile;
    }

    @PostConstruct
    public void init() {
        Object plouf = marshaller.unmarshal(new StreamSource(unicodeCharacterDatabaseXml));

        log.debug(plouf.toString());
    }
}
