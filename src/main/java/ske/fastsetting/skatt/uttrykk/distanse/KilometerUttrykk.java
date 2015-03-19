package ske.fastsetting.skatt.uttrykk.distanse;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;


public class KilometerUttrykk extends AbstractUttrykk<Distanse,KilometerUttrykk> implements DistanseUttrykk {


    private final Distanse distanse;

    public KilometerUttrykk(double km) {
        this.distanse = new Distanse(km);
    }

    public static KilometerUttrykk km(double km) {
        return new KilometerUttrykk(km);
    }

    public static KilometerUttrykk km() {
        return new KilometerUttrykk(1d);
    }

    @Override
    public Distanse eval(UttrykkContext ctx) {
        return distanse;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return distanse.verdi() + " km";
    }
}
