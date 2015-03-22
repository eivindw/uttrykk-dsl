package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.BelopPerKvantitet;
import ske.fastsetting.skatt.domene.Kvantitet;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.MultisatsUttrykk;

import java.util.Collection;

import static ske.fastsetting.skatt.uttrykk.belop.TilBelopUttrykk.tilBelopUttrykk;
import static ske.fastsetting.skatt.uttrykk.belop.BelopPerKvantitetUttrykk.NULL;


public class BelopMultisatsKvantitetUttrykk<K extends Kvantitet>
        extends MultisatsUttrykk<Belop,K,BelopPerKvantitet<K>,K,BelopMultisatsKvantitetUttrykk<K>>
        implements BelopUttrykk {

    public BelopMultisatsKvantitetUttrykk(Uttrykk<K> grunnlag) {
        super(grunnlag);
    }

    public static <K extends Kvantitet> BelopMultisatsKvantitetUttrykk<K> multisats(Uttrykk<K> grunnlag) {
        return new BelopMultisatsKvantitetUttrykk<>(grunnlag);
    }

    @Override
    protected SatsStegUttrykk<Belop,K, BelopPerKvantitet<K>,K> lagSteg() {
        final SatsStegUttrykk<Belop, K, BelopPerKvantitet<K>,K> satsStegUttrykk = new SatsStegUttrykk<Belop,K, BelopPerKvantitet<K>,K>() {
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

    @Override
    protected Uttrykk<Belop> sum(Collection<Uttrykk<Belop>> satsSteg) {
        return BelopSumUttrykk.sum(tilBelopUttrykk(satsSteg));
    }
}
