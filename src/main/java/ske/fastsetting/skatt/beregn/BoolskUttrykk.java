package ske.fastsetting.skatt.beregn;

public class BoolskUttrykk extends AbstractUttrykk<Boolean> {

    private final boolean verdi;

    public BoolskUttrykk(boolean verdi) {
        this.verdi = verdi;
    }

    @Override
    public Boolean eval(UttrykkContext ctx) {
        return verdi;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return Boolean.toString(verdi);
    }
}
