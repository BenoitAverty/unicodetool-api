package org.unicodetool.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unicodetool.application.exceptions.CodepointFormatException;
import org.unicodetool.application.exceptions.ValueOutsideRangeException;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.Properties;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Application service that handles "codepoint" queries from the graphql interface (find  single codepoint)
 */
@Service
public class CodepointQueryHandler {

    private final UnicodeCharacterDatabaseFinder unicodeCharacterDatabaseFinder;

    @Autowired
    public CodepointQueryHandler(UnicodeCharacterDatabaseFinder finder) {
        this.unicodeCharacterDatabaseFinder = finder;
    }

    /**
     * Find a codepoint by its integer value.
     *
     * @param value The integer value of the codepoint.
     * @return The codepoint represented by a graphql object.
     * @throws ValueOutsideRangeException if the value is negative or greater than the maximum codepoint.
     */
    public Optional<Codepoint> findCodepoint(int value) {

        if(value > 0x10FFFF || value < 0) {
            throw new ValueOutsideRangeException("The value of a codepoint must be between 0 and 0x10FFFF (1114111) inclusive");
        }

        return unicodeCharacterDatabaseFinder.findCodepoint(value)
                .map(xmlCodepoint -> new Codepoint(
                        Integer.parseInt(xmlCodepoint.getCp(), 16),
                        xmlCodepoint.getNa(),
                        new Properties(
                                xmlCodepoint.getNa(),
                                xmlCodepoint.getBlk()
                        )
                ));
    }

    /**
     * Find a codepoint by its string value.
     *
     * @param valueStr The string value of the codepoint.
     * @return The codepoint represented by a graphql object.
     * @throws ValueOutsideRangeException if the value is negative or greater than the maximum codepoint.
     * @throws CodepointFormatException if the given string has an incorrect format.
     */
    public Optional<Codepoint> findCodepoint(String valueStr) {



        try {
            Integer value = Optional.ofNullable(valueStr)
                    .map(s -> s.startsWith("U+") ? s.substring(2) : s)
                    .map(s -> s.startsWith("0x") ? s.substring(2) : s)
                    .map(s -> Integer.valueOf(s, 16))
                    .orElseThrow(() -> new CodepointFormatException(valueStr));
            return this.findCodepoint(value);
        }
        catch (NumberFormatException e) {
            throw new CodepointFormatException(valueStr, e);
        }
    }
}
