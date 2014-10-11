package ske.fastsetting.skatt.beregn;

public class KrUttrykk extends AbstractUttrykk<Integer> {

    private final Integer tall;

    public KrUttrykk(Integer tall) {
        this.tall = tall;
    }

    @Override
    public Integer eval(UttrykkContext ctx) {
        return tall;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return tall + " kr";
    }

    public static KrUttrykk kr(Integer tall) {
        return new KrUttrykk(tall);
    }
}
