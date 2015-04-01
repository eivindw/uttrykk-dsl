package ske.fastsetting.skatt.uttrykk.tekst;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class TekstKonstantUttrykk extends AbstractUttrykk<String, TekstKonstantUttrykk> implements TekstUttrykk {
    private final String tekst;

    public static TekstKonstantUttrykk tekst(String tekst) {
        return new TekstKonstantUttrykk(tekst);
    }

    private TekstKonstantUttrykk(String tabellnummer) {
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

