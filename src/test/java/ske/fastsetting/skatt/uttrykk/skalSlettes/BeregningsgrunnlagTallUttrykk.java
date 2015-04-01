package ske.fastsetting.skatt.uttrykk.skalSlettes;

import java.util.function.Function;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class BeregningsgrunnlagTallUttrykk extends Beregningsgrunnlag.BeregningsgrunnlagUttrykk<Tall, TallUttrykk,
  BeregningsgrunnlagTallUttrykk> implements TallUttrykk {

    public static BeregningsgrunnlagTallUttrykk beregningsgrunnlag(Function<Beregningsgrunnlag, TallUttrykk> function) {
        return new BeregningsgrunnlagTallUttrykk(function);
    }

    public <T> BeregningsgrunnlagTallUttrykk(Function<Beregningsgrunnlag, TallUttrykk> function) {
        super(function);
    }
}
