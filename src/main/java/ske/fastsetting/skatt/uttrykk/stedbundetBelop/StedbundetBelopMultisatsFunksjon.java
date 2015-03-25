package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultisatsUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopDiffUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk;

import java.util.Collection;

import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StebundetGrenseUttrykk.begrensFordholdmessig;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopWrapperUttrykk.tilStedbundetBelopUttrykk;

public class StedbundetBelopMultisatsFunksjon<K>
  extends MultisatsUttrykk<StedbundetBelop<K>, StedbundetBelop<K>, Tall, Belop, StedbundetBelopMultisatsFunksjon<K>>
  implements StedbundetBelopUttrykk<K> {


    public StedbundetBelopMultisatsFunksjon(StedbundetBelopUttrykk<K> grunnlag) {
        super(grunnlag);
    }

    public static <K> StedbundetBelopMultisatsFunksjon<K> multisatsFunksjonAv(StedbundetBelopUttrykk<K> grunnlag) {
        return new StedbundetBelopMultisatsFunksjon<>(grunnlag);
    }

    @Override
    protected SatsStegUttrykk<StedbundetBelop<K>, StedbundetBelop<K>, Tall, Belop> lagSteg() {
        final SatsStegUttrykk<StedbundetBelop<K>, StedbundetBelop<K>, Tall, Belop> satsStegUttrykk = new
          SatsStegUttrykk<StedbundetBelop<K>, StedbundetBelop<K>, Tall, Belop>() {
            @Override
            public StedbundetBelop<K> eval(UttrykkContext ctx) {
                StebundetGrenseUttrykk<K> grenseUttrykk = begrensFordholdmessig(new
                  ProporsjonalFordelingDiffUttrykk<>(grunnlag, nedreGrense).multiplisertMed(sats))
                  .nedad(kr(0));
                if (oevreGrense != null) {
                    GrenseUttrykk grenseDiff = begrens(new BelopDiffUttrykk(oevreGrense, nedreGrense)).nedad(kr(0));
                    grenseUttrykk.oppad(grenseDiff.multiplisertMed(sats));
                }

                return ctx.eval(grenseUttrykk);
            }
        };

        return satsStegUttrykk.medNedreGrense(kr(0));
    }

    @Override
    protected Uttrykk<StedbundetBelop<K>> sum(Collection<Uttrykk<StedbundetBelop<K>>> satsSteg) {
        return StedbundetBelopSumUttrykk.sum(tilStedbundetBelopUttrykk(satsSteg));
    }
}
