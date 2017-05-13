package org.unicodetool.graphql.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class Properties {
    public Properties(String name, String block) {
        this.name = name;
        this.block = block;
    }

    // general properties
    private String name;
    private List<String> nameAlias;
    private String block;
    private String age;
    private String generalCategory;
    private String script;
    private String scriptExtensions;
    private Boolean whiteSpace;
    private Boolean alphabetic;
    private String hangulSyllabeType;
    private Boolean nonCharacterCodePoint;
    private Boolean defaultIgnorableCodePoint;
    private Boolean deprecated;
    private Boolean logicalOrderException;
    private Boolean variationSelector;

    // case properties
    private Boolean uppercase;
    private Boolean lowercase;
    private String lowercaseMapping;
    private String titlecaseMapping;
    private String uppercaseMapping;
    private String caseFolding;
    private String simpleLowercaseMapping;
    private String simpleTitlecaseMapping;
    private String simpleUppercaseMapping;
    private String simpleCaseFolding;
    private Boolean softDotted;
    private Boolean cased;
    private Boolean caseIgnorable;
    private Boolean changesWhenLowercased;
    private Boolean changesWhenUppercased;
    private Boolean changesWhenTitlecased;
    private Boolean changesWhenCasefolded;
    private Boolean changesWhenCasemapped;

    // numeric properties
    private String numericValue;
    private String numericType;
    private Boolean hexDigit;
    private Boolean asciiHexDigit;

    // normalization properties
    private String canonicalCombiningClass;
    private String decompositionMapping;
    private Boolean compositionExclusion;
    private Boolean fullCompositionExclusion;
    private String decompositionType;
    private String fcNfkcClosure;
    private String nfcQuickCkeck;
    private String nfkcQuickCheck;
    private String nfdQuickCheck;
    private String nfkdQuickCheck;
    private Boolean expandsOnNfc;
    private Boolean expandsOnNfd;
    private Boolean expandsOnNfkd;
    private String nfkcCasefold;
    private Boolean changesWhenNfkcCaseFolded;

    // shaping and rendering properties
    private String joinControl;
    private String joiningGroup;
    private String joiningType;
    private String lineBreak;
    private String graphemeClusterBreak;
    private String sentenceBreak;
    private String wordBreak;
    private String eastAsianWidth;
    private Boolean prependConcatenationMark;

    // Bidirectional properties
    private String bidiClass;
    private String bidiControl;
    private Boolean bidiMirrored;
    private String bidiMirroringGlyph;
    private CodepointValue bidiPairedBracket;
    private String bidiPairedBracketType;

    // identifiers
    private Boolean idContinue;
    private Boolean idStart;
    private Boolean xidContinue;
    private Boolean xidStart;
    private Boolean patternSyntax;
    private Boolean patternWhiteSpace;

    // CJK properties
    private Boolean ideographic;
    private Boolean unifiedIdeograph;
    private Boolean radical;
    private Boolean idsBinaryOperator;
    private Boolean idsTrinaryOperator;
    private String unicodeRadicalStroke;

    // Misc properties
    private Boolean math;
    private Boolean quotationMark;
    private Boolean dash;
    private Boolean hyphen;
    private Boolean sentenceTerminal;
    private Boolean terminalPunctuation;
    private Boolean diacritic;
    private Boolean extender;
    private Boolean graphemeBase;
    private Boolean graphemeExtend;
    private Boolean graphemeLink;
    private String unicode1Name;
    private String isoComment;
    private String indicPositionalCategory;
    private String indicSyllabicCategory;

    // Contributory properties
    private Boolean otherAlphabetic;
    private Boolean otherDefaultIgnorableCodePoint;
    private Boolean otherGraphemeExtend;
    private Boolean otherIdStart;
    private Boolean otherIdContinue;
    private Boolean otherLowercase;
    private Boolean otherMath;
    private Boolean otherUppercase;
    private String jamoShortName;
}
