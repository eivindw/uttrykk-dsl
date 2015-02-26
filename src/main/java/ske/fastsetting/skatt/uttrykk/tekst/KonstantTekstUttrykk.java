package ske.fastsetting.skatt.uttrykk.tekst;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class KonstantTekstUttrykk extends AbstractUttrykk<String, KonstantTekstUttrykk> implements TekstUttrykk {
    private final String tekst;

    public static KonstantTekstUttrykk tekst(String tekst) {
        return new KonstantTekstUttrykk(tekst);
    }

    private KonstantTekstUttrykk(String tabellnummer) {
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

