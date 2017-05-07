package org.unicodetool.ucd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Configuration
@Slf4j
public class UcdConfiguration {
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setPackagesToScan("org.unicodetool.ucd.schema");

        return marshaller;
    }

    @Bean("ucdStream")
    public InputStream ucdStream(@Value("${ucd.location}")URL ucdFileUrl) throws IOException {
        ZipInputStream input = new ZipInputStream(ucdFileUrl.openStream());
        ZipEntry entry = input.getNextEntry();
        log.info("Downloaded " +
                ucdFileUrl.toString() +
                " and found " + entry.getName() +
                " ("+entry.getSize()+" bytes)");

        return input;
    }
}
