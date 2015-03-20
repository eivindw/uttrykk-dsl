package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.BelopPerKvantitet;
import ske.fastsetting.skatt.domene.Kvantitet;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.multisats.BelopMultisatsUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.multisats.SatsStegUttrykk;

import static ske.fastsetting.skatt.uttrykk.belop.BelopPerKvantitetUttrykk.NULL;


public class BelopMultisatsKvantitetUttrykk<K extends Kvantitet> extends BelopMultisatsUttrykk<K,BelopPerKvantitet<K>> {

    public BelopMultisatsKvantitetUttrykk(Uttrykk<K> grunnlag) {
        super(grunnlag);
    }

    public static <K extends Kvantitet> BelopMultisatsKvantitetUttrykk<K> multisats(Uttrykk<K> grunnlag) {
        return new BelopMultisatsKvantitetUttrykk<>(grunnlag);
    }

    @Override
    protected SatsStegUttrykk<K, BelopPerKvantitet<K>> lagSteg() {
        final SatsStegUttrykk<K, BelopPerKvantitet<K>> satsStegUttrykk = new SatsStegUttrykk<K, BelopPerKvantitet<K>>() {
            @Override
            public Belop eval(UttrykkContext ctx) {
                final BelopPerKvantitet<K> satsEval = ctx.eval(sats);
                Belop oevre = oevreGrense != null ? satsEval.multiplisertMed(ctx.eval(oevreGrense)) : null;
                Belop nedre = nedreGrense != null ? satsEval.multiplisertMed(ctx.eval(nedreGrense)) : Belop.NULL;
                Belop verdi = satsEval.multiplisertMed(ctx.eval(grunnlag));

                if (verdi.erMindreEnn(nedre)) {
                    return Belop.NULL;
                } else if (oevre != null && verdi.erStorreEnn(oevre)) {
                    return oevre.minus(nedre);
                } else {
                    return verdi.minus(nedre);
                }

            }
        };

        return satsStegUttrykk.medSats(NULL());
    }
}
