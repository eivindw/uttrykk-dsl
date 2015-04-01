package ske.fastsetting.skatt.uttrykk.skalSlettes;

import java.util.function.Function;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public class BeregningsgrunnlagBelopUttrykk extends Beregningsgrunnlag.BeregningsgrunnlagUttrykk<Belop, BelopUttrykk,
  BeregningsgrunnlagBelopUttrykk> implements BelopUttrykk {

    public static BelopUttrykk beregningsgrunnlag(Function<Beregningsgrunnlag, BelopUttrykk> function) {
        return new BeregningsgrunnlagBelopUttrykk(function);
    }

    public BeregningsgrunnlagBelopUttrykk(Function<Beregningsgrunnlag, BelopUttrykk> function) {
        super(function);
    }
}



