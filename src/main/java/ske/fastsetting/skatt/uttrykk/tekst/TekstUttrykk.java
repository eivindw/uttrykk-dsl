package ske.fastsetting.skatt.uttrykk.tekst;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class TekstUttrykk extends AbstractUttrykk<String, TekstUttrykk> {
    private final String tekst;

    public static TekstUttrykk tekst(String tekst) {
        return new TekstUttrykk(tekst);
    }

    private TekstUttrykk(String tabellnummer) {
        this.tekst = tabellnummer;
    }

    @Override
    public String eval(UttrykkContext ctx) {
        return tekst;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return tekst;
    }
}

