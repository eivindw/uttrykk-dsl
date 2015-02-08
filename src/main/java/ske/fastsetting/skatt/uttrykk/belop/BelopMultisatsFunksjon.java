package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

/* TODO: UFULLSTENDIG */
public class BelopMultisatsFunksjon {

    private BelopUttrykk xVerdi;

    public BelopMultisatsFunksjon(BelopUttrykk x) {

        this.xVerdi = x;
    }

    public static BelopMultisatsFunksjon multisatsFunksjonAv(BelopUttrykk x) {
        return new BelopMultisatsFunksjon(x);
    }

    public SatsStegUttrykk medSats(TallUttrykk sats) {

        return new SatsStegUttrykk(null,kr(0),sats);
    }


    public class SatsStegUttrykk extends AbstractUttrykk<Belop,SatsStegUttrykk> implements BelopUttrykk {
        private final TallUttrykk sats;
        private BelopUttrykk oevreGrense;
        private final BelopUttrykk nedreGrense;
        private final GrenseUttrykk grenseUttrykk;
        private final BelopUttrykk resultatUttrykk;


        public SatsStegUttrykk(SatsStegUttrykk forrigeSatsSteg, BelopUttrykk nedreGrense, TallUttrykk sats) {
            this.sats = sats;
            this.nedreGrense = nedreGrense;
            this.grenseUttrykk = begrens(xVerdi.minus(nedreGrense).multiplisertMed(sats)).nedad(kr(0));
            this.resultatUttrykk = forrigeSatsSteg==null ? grenseUttrykk : grenseUttrykk.pluss(forrigeSatsSteg);
        }

        public TilSatsStegUttrykk til(BelopUttrykk til) {
            oevreGrense = til;
            this.grenseUttrykk.oppad(til.minus(nedreGrense).multiplisertMed(sats));
            return new TilSatsStegUttrykk(this);
        }


        @Override
        public Belop eval(UttrykkContext ctx) {
            return ctx.eval(resultatUttrykk);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(resultatUttrykk);
        }

        public class TilSatsStegUttrykk extends AbstractUttrykk<Belop,SatsStegUttrykk> implements BelopUttrykk   {

            private final SatsStegUttrykk satsSteg;

            public TilSatsStegUttrykk(SatsStegUttrykk satsStegUttrykk) {
                 this.satsSteg = satsStegUttrykk;
            }

            public SatsStegUttrykk deretterMedSats(TallUttrykk sats) {
                return new SatsStegUttrykk(satsSteg, oevreGrense,sats);
            }

            @Override
            public Belop eval(UttrykkContext ctx) {
                return ctx.eval(satsSteg);
            }

            @Override
            public String beskriv(UttrykkContext ctx) {
                return ctx.beskriv(satsSteg);
            }
        }
    }
}
