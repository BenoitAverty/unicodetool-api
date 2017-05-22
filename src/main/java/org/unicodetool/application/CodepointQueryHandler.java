package org.unicodetool.application;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unicodetool.application.exceptions.CodepointFormatException;
import org.unicodetool.application.exceptions.ValueOutsideRangeException;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.CodepointValue;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import java.util.Optional;

/**
 * Application service that handles "codepoint" queries from the graphql interface (find  single codepoint)
 */
@Service
public class CodepointQueryHandler {

    private final UnicodeCharacterDatabaseFinder unicodeCharacterDatabaseFinder;
    private final CodepointConverter codepointConverter;

    @Autowired
    public CodepointQueryHandler(UnicodeCharacterDatabaseFinder finder, CodepointConverter converter) {
        this.unicodeCharacterDatabaseFinder = finder;
        codepointConverter = converter;
    }

    /**
     * Find a codepoint by its string value.
     *
     * @param value The value of the codepoint.
     * @return The codepoint represented by a graphql object.
     * @throws ValueOutsideRangeException if the value is negative or greater than the maximum codepoint.
     * @throws CodepointFormatException if the given string has an incorrect format.
     */
    public Optional<Codepoint> findCodepoint(CodepointValue value) {
        String formattedValue = Optional.ofNullable(value)
                .map(cv -> cv.getValue())
                .map(s -> s.startsWith("U+") ? s.substring(2) : s)
                .map(s -> s.startsWith("0x") ? s.substring(2) : s)
                .filter(StringUtils::isNotBlank)
                .map(s -> StringUtils.leftPad(s, 4, '0'))
                .map(s -> s.toUpperCase())
                .orElseThrow(() -> new CodepointFormatException(value.getValue()));

        try {
            Integer decimalValue = Integer.valueOf(formattedValue, 16);
            if(decimalValue < 0 || decimalValue > 0x10FFFF) {
                throw new ValueOutsideRangeException("The value " + formattedValue + " is outside the unicode range");
            }
        }
        catch (NumberFormatException e) {
            throw new CodepointFormatException(value.getValue(), e);
        }

        return unicodeCharacterDatabaseFinder.findCodepoint(formattedValue)
                .map(codepointConverter::convert)
                .map(cp -> cp.withValue(value));
    }
}
