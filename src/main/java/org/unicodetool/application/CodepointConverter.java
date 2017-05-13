package org.unicodetool.application;

import org.unicodetool.graphql.schema.*;
import org.unicodetool.graphql.schema.Character;
import org.unicodetool.ucd.schema.Boolean;
import org.unicodetool.ucd.schema.CodePoint;

import java.util.function.Function;
import java.util.stream.Collectors;

import static org.unicodetool.ucd.schema.Boolean.Y;

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

    /**
     * Create a CodepointConverter with the given formatted value. The formatted value will be the value of the
     * returned codepoint object. It is necessary because the xml node doesn't always contain the value.
     */
    public static CodepointConverter withFormattedValue(String formattedValue) {
        return new CodepointConverter(formattedValue);
    }

    @Override
    public Codepoint apply(CodePoint xmlCodePoint) {

        if("Cn".equals(xmlCodePoint.getGc())) {
            if(Boolean.Y.equals(xmlCodePoint.getNChar())) {
                return new NonCharacter(
                        CodepointValue.of(formattedValue),
                        xmlCodePoint.getNa(),
                        this.buildProperties(xmlCodePoint)
                );
            }
            else {
                return new Reserved(
                        CodepointValue.of(formattedValue),
                        xmlCodePoint.getNa(),
                        this.buildProperties(xmlCodePoint)
                );
            }
        }

        if("Cs".equals(xmlCodePoint.getGc())) {
            return new Surrogate(
                    CodepointValue.of(formattedValue),
                    xmlCodePoint.getNa(),
                    this.buildProperties(xmlCodePoint)
            );
        }
        return new Character(
                CodepointValue.of(formattedValue),
                xmlCodePoint.getNa(),
                this.buildProperties(xmlCodePoint)
        );
    }


    /**
     * Builds the properties object from an xml codepoint
     */
    private Properties buildProperties(CodePoint xmlCodepoint) {
        return new Properties(
                xmlCodepoint.getNa(),
                xmlCodepoint.getNameAlias().stream()
                        .map(na -> new NameAlias(na.getAlias(), na.getType())).collect(Collectors.toList()),
                xmlCodepoint.getBlk(),
                xmlCodepoint.getAge(),
                xmlCodepoint.getGc(),
                xmlCodepoint.getSc().value(),
                xmlCodepoint.getScx().stream().map(s -> s.value()).collect(Collectors.toList()),
                xmlCodepoint.getWSpace() == Y,
                xmlCodepoint.getAlpha() == Y,
                xmlCodepoint.getHst(),
                xmlCodepoint.getNChar() == Y,
                xmlCodepoint.getDI() == Y,
                xmlCodepoint.getDep() == Y,
                xmlCodepoint.getLOE() == Y,
                xmlCodepoint.getVS() == Y,
                xmlCodepoint.getUpper() == Y,
                xmlCodepoint.getLower() == Y,
                xmlCodepoint.getLc(),
                xmlCodepoint.getTc(),
                xmlCodepoint.getUc(),
                xmlCodepoint.getCf(),
                xmlCodepoint.getSlc(),
                xmlCodepoint.getStc(),
                xmlCodepoint.getSuc(),
                xmlCodepoint.getScf(),
                xmlCodepoint.getSD() == Y,
                xmlCodepoint.getCased() == Y,
                xmlCodepoint.getCI() == Y,
                xmlCodepoint.getCWL() == Y,
                xmlCodepoint.getCWU() == Y,
                xmlCodepoint.getCWT() == Y,
                xmlCodepoint.getCWCF() == Y,
                xmlCodepoint.getCWCM() == Y,
                xmlCodepoint.getNv(),
                xmlCodepoint.getNt(),
                xmlCodepoint.getHex() == Y,
                xmlCodepoint.getAHex() == Y,
                xmlCodepoint.getCcc(),
                xmlCodepoint.getDm(),
                xmlCodepoint.getCE() == Y,
                xmlCodepoint.getCompEx() == Y,
                xmlCodepoint.getDt(),
                xmlCodepoint.getFCNFKC(),
                xmlCodepoint.getNFCQC(),
                xmlCodepoint.getNFKCQC(),
                xmlCodepoint.getNFDQC(),
                xmlCodepoint.getNFKDQC(),
                xmlCodepoint.getXONFC() == Y,
                xmlCodepoint.getXONFKC() == Y,
                xmlCodepoint.getXONFD() == Y,
                xmlCodepoint.getXONFKD() == Y,
                xmlCodepoint.getNFKCCF(),
                xmlCodepoint.getCWKCF() == Y,
                xmlCodepoint.getJoinC() == Y,
                xmlCodepoint.getJg(),
                xmlCodepoint.getJt(),
                xmlCodepoint.getLb(),
                xmlCodepoint.getGCB(),
                xmlCodepoint.getSB(),
                xmlCodepoint.getWB(),
                xmlCodepoint.getEa(),
                xmlCodepoint.getPCM() == Y,
                xmlCodepoint.getBc(),
                xmlCodepoint.getBidiC() == Y,
                xmlCodepoint.getBidiM() == Y,
                xmlCodepoint.getBmg(),
                CodepointValue.of(xmlCodepoint.getBpb()),
                xmlCodepoint.getBpt(),
                xmlCodepoint.getIDC() == Y,
                xmlCodepoint.getIDS() == Y,
                xmlCodepoint.getXIDC() == Y,
                xmlCodepoint.getXIDS() == Y,
                xmlCodepoint.getPatSyn() == Y,
                xmlCodepoint.getPatWS() == Y,
                xmlCodepoint.getIdeo() == Y,
                xmlCodepoint.getUIdeo() == Y,
                xmlCodepoint.getRadical() == Y,
                xmlCodepoint.getIDSB() == Y,
                xmlCodepoint.getIDST() == Y,
                xmlCodepoint.getKRSUnicode(),
                xmlCodepoint.getMath() == Y,
                xmlCodepoint.getQMark() == Y,
                xmlCodepoint.getDash() == Y,
                xmlCodepoint.getHyphen() == Y,
                xmlCodepoint.getSTerm() == Y,
                xmlCodepoint.getTerm() == Y,
                xmlCodepoint.getDia() == Y,
                xmlCodepoint.getExt() == Y,
                xmlCodepoint.getGrBase() == Y,
                xmlCodepoint.getGrExt() == Y,
                xmlCodepoint.getGrLink() == Y,
                xmlCodepoint.getNa1(),
                xmlCodepoint.getIsc(),
                xmlCodepoint.getInPC(),
                xmlCodepoint.getInSC(),
                xmlCodepoint.getOAlpha() == Y,
                xmlCodepoint.getODI() == Y,
                xmlCodepoint.getOGrExt() == Y,
                xmlCodepoint.getOIDS() == Y,
                xmlCodepoint.getOIDC() == Y,
                xmlCodepoint.getOLower() == Y,
                xmlCodepoint.getOMath() == Y,
                xmlCodepoint.getOUpper() == Y,
                xmlCodepoint.getJSN()
        );
    }
}
