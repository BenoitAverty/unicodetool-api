package org.unicodetool.ucd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class UcdConfiguration {
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setPackagesToScan("org.unicodetool.ucd.schema");

        return marshaller;
    }
}
