package org.unicodetool.application;

import org.unicodetool.graphql.schema.*;
import org.unicodetool.graphql.schema.Character;
import org.unicodetool.ucd.schema.Boolean;
import org.unicodetool.ucd.schema.CodePoint;

import java.util.function.Function;

/**
 * Converts a Codepoint in the XML representation of the UCD to the GraphQL schema object.
 *
 * This function needs to know the formatted value of the codepoint, because the xml node doesn't always contain it
 * (for example when the xml node represents a range of codepoints).
 *
 * Use the {@link CodepointConverter#withFormattedValue} function to create a converter.
 */
public class CodepointConverter implements Function<CodePoint, Codepoint> {

    private final String formattedValue;
    private CodepointConverter(String formattedValue) {
        this.formattedValue = formattedValue;
    }

    @Override
    public Codepoint apply(CodePoint xmlCodePoint) {

        if("Cn".equals(xmlCodePoint.getGc())) {
            if(Boolean.Y.equals(xmlCodePoint.getNChar())) {
                return new NonCharacter(
                        CodepointValue.of(formattedValue),
                        xmlCodePoint.getNa(),
                        new Properties(
                                xmlCodePoint.getNa(),
                                xmlCodePoint.getBlk()
                        )
                );
            }
            else {
                return new Reserved(
                        CodepointValue.of(formattedValue),
                        xmlCodePoint.getNa(),
                        new Properties(
                                xmlCodePoint.getNa(),
                                xmlCodePoint.getBlk()
                        )
                );
            }
        }

        if("Cs".equals(xmlCodePoint.getGc())) {
            return new Surrogate(
                    CodepointValue.of(formattedValue),
                    xmlCodePoint.getNa(),
                    new Properties(
                            xmlCodePoint.getNa(),
                            xmlCodePoint.getBlk()
                    )
            );
        }
        return new Character(
                CodepointValue.of(formattedValue),
                xmlCodePoint.getNa(),
                new Properties(
                        xmlCodePoint.getNa(),
                        xmlCodePoint.getBlk()
                )
        );
    }

    /**
     * Create a CodepointConverter with the given formatted value. The formatted value will be the value of the
     * returned codepoint object. It is necessary because the xml node doesn't always contain the value.
     */
    public static CodepointConverter withFormattedValue(String formattedValue) {
        return new CodepointConverter(formattedValue);
    }
}
