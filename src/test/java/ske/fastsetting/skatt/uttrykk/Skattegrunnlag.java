package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Belop;

public class Skattegrunnlag {
    public Belop getPostBelop(String postnummer) {
        return new Belop(45);
    }
}
