package ske.fastsetting.skatt.uttrykk.multibelop;

import static ske.fastsetting.skatt.uttrykk.belop.BelopGrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.BelopPlussMinusUttrykk.diff;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr0;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopHverDiffUttrykk.diffHver;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopHverGrenseUttrykk.begrensHvertSted;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopWrapperUttrykk.tilStedbundetBelopUttrykk;

import java.util.Collection;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultisatsUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopGrenseUttrykk;

public class MultiBelopHverMultisatsUttrykk<K>
    extends MultisatsUttrykk<MultiBelop<K>, MultiBelop<K>, Tall, Belop, MultiBelopHverMultisatsUttrykk<K>>
    implements MultiBelopUttrykk<K> {


        public MultiBelopHverMultisatsUttrykk(MultiBelopUttrykk<K> grunnlag) {
            super(grunnlag);
        }

        public static <K> MultiBelopHverMultisatsUttrykk<K> multisatsHverAv(MultiBelopUttrykk<K> grunnlag) {
            return new MultiBelopHverMultisatsUttrykk<>(grunnlag);
        }

        @Override
        protected MultisatsUttrykk.SatsStegUttrykk<MultiBelop<K>, MultiBelop<K>, Tall, Belop> lagSteg() {
            final MultisatsUttrykk.SatsStegUttrykk<MultiBelop<K>, MultiBelop<K>, Tall, Belop> satsStegUttrykk = new
              MultisatsUttrykk.SatsStegUttrykk<MultiBelop<K>, MultiBelop<K>, Tall, Belop>() {
                  @Override
                  public MultiBelop<K> eval(UttrykkContext ctx) {
                      MultiBelopHverGrenseUttrykk<K> grenseUttrykk = begrensHvertSted(
                        diffHver(grunnlag, nedreGrense).multiplisertMed(sats))
                        .nedad(kr0());
                      if (oevreGrense != null) {
                          BelopGrenseUttrykk grenseDiff = begrens(diff(oevreGrense, nedreGrense)).nedad(kr0());
                          grenseUttrykk.oppad(grenseDiff.multiplisertMed(sats));
                      }

                      return ctx.eval(grenseUttrykk);
                  }
              };

            return satsStegUttrykk.medNedreGrense(kr(0));
        }

        @Override
        protected Uttrykk<MultiBelop<K>> sum(Collection<Uttrykk<MultiBelop<K>>> satsSteg) {
            return MultiBelopPlussMinusUttrykk.sum(tilStedbundetBelopUttrykk(satsSteg));
        }
    }
