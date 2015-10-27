package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public enum Avrunding {
    Opp(RoundingMode.UP),
    Ned(RoundingMode.DOWN),
    Naermeste(RoundingMode.HALF_UP);
    private RoundingMode roundingMode;

    Avrunding(RoundingMode roundingMode) {

        this.roundingMode = roundingMode;
    }

    public BigDecimal rundAv(BigDecimal verdi, int presisjon) {
        return verdi.round(new MathContext(presisjon, roundingMode));
    }
}
