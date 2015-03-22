package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.MultisatsUttrykk;

import java.util.Collection;

import static ske.fastsetting.skatt.uttrykk.belop.BelopDiffUttrykk.differanseMellom;
import static ske.fastsetting.skatt.uttrykk.belop.TilBelopUttrykk.tilBelopUttrykk;
import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.KonstantUttrykk.tall;

public class BelopMultisatsFunksjon extends MultisatsUttrykk<Belop,Belop,Tall,Belop,BelopMultisatsFunksjon> implements BelopUttrykk {
    public BelopMultisatsFunksjon(Uttrykk<Belop> grunnlag) {
        super(grunnlag);
    }

    public static BelopMultisatsFunksjon multisatsFunksjonAv(BelopUttrykk grunnlag) {
        return new BelopMultisatsFunksjon(grunnlag);
    }

    @Override
    protected SatsStegUttrykk<Belop,Belop,Tall,Belop> lagSteg() {
        final SatsStegUttrykk<Belop,Belop,Tall,Belop> satsStegUttrykk = new SatsStegUttrykk<Belop,Belop,Tall,Belop>() {

            @Override
            public Belop eval(UttrykkContext ctx) {
                GrenseUttrykk grenseUttrykk = begrens(differanseMellom(grunnlag, nedreGrense).multiplisertMed(sats)).nedad(kr(0));
                if (oevreGrense != null) {
                    grenseUttrykk.oppad(differanseMellom(oevreGrense, nedreGrense).multiplisertMed(sats));
                }

                return ctx.eval(grenseUttrykk);
            }
        };


        return satsStegUttrykk.medNedreGrense(kr(0)).medSats(tall(0));
    }

    @Override
    protected Uttrykk<Belop> sum(Collection<Uttrykk<Belop>> satsSteg) {
        return BelopSumUttrykk.sum(tilBelopUttrykk(satsSteg));
    }


}
