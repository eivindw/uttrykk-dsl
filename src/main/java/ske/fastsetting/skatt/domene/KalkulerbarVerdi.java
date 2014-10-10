package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;

public interface KalkulerbarVerdi<T> {
    T minus(T ledd);
    T pluss(T ledd);
    T multiplisertMed(BigDecimal faktor);
    T dividertMed(BigDecimal divisor);
}
