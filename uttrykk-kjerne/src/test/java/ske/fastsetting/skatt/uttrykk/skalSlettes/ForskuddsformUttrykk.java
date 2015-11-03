package ske.fastsetting.skatt.uttrykk.skalSlettes;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

/**
 * Created by jorn ola birkeland on 27.10.15.
 */
public class ForskuddsformUttrykk extends ForskuddstrekkUttrykk<String> {
    public ForskuddsformUttrykk(Uttrykk<String> uttrykk) {
        super(uttrykk,ForskuddXml::getForskuddsform,"");
    }

    public static ForskuddsformUttrykk forskuddsform(Uttrykk<String> beregnetForskuddsform) {
        return new ForskuddsformUttrykk(beregnetForskuddsform);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "Forskuddsform";
    }
}
