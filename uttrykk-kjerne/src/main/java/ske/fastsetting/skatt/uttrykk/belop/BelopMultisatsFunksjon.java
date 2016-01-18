package ske.fastsetting.skatt.uttrykk.belop;

import static ske.fastsetting.skatt.uttrykk.belop.BelopDiffUttrykk.differanseMellom;
import static ske.fastsetting.skatt.uttrykk.belop.BelopGrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr0;
import static ske.fastsetting.skatt.uttrykk.belop.TilBelopUttrykk.tilBelopUttrykk;
import static ske.fastsetting.skatt.uttrykk.tall.TallKonstantUttrykk.tall;

import java.util.Collection;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultisatsUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class BelopMultisatsFunksjon extends MultisatsUttrykk<Belop, Belop, Tall, Belop, BelopMultisatsFunksjon>
  implements BelopUttrykk {
    public BelopMultisatsFunksjon(Uttrykk<Belop> grunnlag) {
        super(grunnlag);
    }

    public static BelopMultisatsFunksjon multisatsFunksjonAv(BelopUttrykk grunnlag) {
        return new BelopMultisatsFunksjon(grunnlag);
    }

    @Override
    protected SatsStegUttrykk<Belop, Belop, Tall, Belop> lagSteg() {
        final SatsStegUttrykk<Belop, Belop, Tall, Belop> satsStegUttrykk = new BelopSatsStegUttrykk();

        return satsStegUttrykk.medNedreGrense(kr0()).medSats(tall(0));
    }

    @Override
    protected Uttrykk<Belop> sum(Collection<Uttrykk<Belop>> satsSteg) {
        return BelopSumUttrykk.sum(tilBelopUttrykk(satsSteg));
    }

    private static class BelopSatsStegUttrykk extends SatsStegUttrykk<Belop,Belop,Tall,Belop> {

        private BelopUttrykk grenseUttrykk;

        @Override
        public Belop eval(UttrykkContext ctx) {
            if(grenseUttrykk==null) {
                grenseUttrykk = lagGrenseuttrykk();
            }

            return ctx.eval(grenseUttrykk);
        }

        private BelopUttrykk lagGrenseuttrykk() {

            BelopGrenseUttrykk grenseUttrykk = begrens(differanseMellom(grunnlag, nedreGrense).multiplisertMed(sats))
              .nedad(kr0());
            if (oevreGrense != null) {
                grenseUttrykk.oppad(differanseMellom(oevreGrense, nedreGrense).multiplisertMed(sats));
            }

            return grenseUttrykk;
        }
    }


}
