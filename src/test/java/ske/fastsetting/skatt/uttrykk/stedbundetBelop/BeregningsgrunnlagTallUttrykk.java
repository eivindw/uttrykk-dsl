package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.function.Function;

/**
 * Created by jorn ola birkeland on 27.02.15.
 */
public class BeregningsgrunnlagTallUttrykk extends Beregningsgrunnlag.BeregningsgrunnlagUttrykk<Tall,TallUttrykk,BeregningsgrunnlagTallUttrykk> implements TallUttrykk {

    public static BeregningsgrunnlagTallUttrykk beregningsgrunnlag(Function<Beregningsgrunnlag,TallUttrykk> function) {
        return new BeregningsgrunnlagTallUttrykk(function);
    }

    public <T> BeregningsgrunnlagTallUttrykk(Function<Beregningsgrunnlag, TallUttrykk> function) {
        super(function);
    }
}
