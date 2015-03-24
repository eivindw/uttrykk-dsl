package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.BelopPerKvantitet;
import ske.fastsetting.skatt.domene.Kvantitet;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

/**
 * Created by jorn ola birkeland on 19.03.15.
 */
public class BelopPerKvantitetUttrykk<K extends Kvantitet> extends AbstractUttrykk<BelopPerKvantitet<K>,
  BelopPerKvantitetUttrykk<K>> {

    private final BelopUttrykk belopUttrykk;
    private final Uttrykk<K> kvantitetUttrykk;

    public static <K extends Kvantitet> BelopPerKvantitetUttrykk<K> NULL() {
        return new BelopPerKvantitetUttrykk<>(null, null);
    }

    BelopPerKvantitetUttrykk(BelopUttrykk belopUttrykk, Uttrykk<K> kvantitetUttrykk) {

        this.belopUttrykk = belopUttrykk;
        this.kvantitetUttrykk = kvantitetUttrykk;
    }

    @Override
    public BelopPerKvantitet<K> eval(UttrykkContext ctx) {
        BelopPerKvantitet<K> resultat;

        if (belopUttrykk == null || kvantitetUttrykk == null) {
            resultat = new BelopPerKvantitet<>(Belop.NULL);
        } else {
            K kvantitet = ctx.eval(kvantitetUttrykk);
            Belop belop = ctx.eval(belopUttrykk);

            resultat = new BelopPerKvantitet<>(belop.dividertMed(kvantitet.toBigDecimal()));
        }

        return resultat;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(belopUttrykk) + "/" + ctx.beskriv(kvantitetUttrykk);
    }
}
