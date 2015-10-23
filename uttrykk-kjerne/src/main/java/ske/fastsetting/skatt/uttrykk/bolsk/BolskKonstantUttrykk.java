package ske.fastsetting.skatt.uttrykk.bolsk;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class BolskKonstantUttrykk extends BolskUttrykk {

    public static final BolskKonstantUttrykk SANN = new BolskKonstantUttrykk(true);
    public static final BolskKonstantUttrykk USANN = new BolskKonstantUttrykk(false);


    private final boolean verdi;

    private BolskKonstantUttrykk(boolean verdi) {
        this.verdi = verdi;
    }

    public static BolskKonstantUttrykk erSann(boolean verdi) {
        return new BolskKonstantUttrykk(verdi);
    }

    public static BolskKonstantUttrykk erUsann(boolean verdi) {
        return new BolskKonstantUttrykk(!verdi);
    }

    @Override
    public Boolean eval(UttrykkContext ctx) {
        return verdi;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return String.valueOf(verdi);
    }
}
