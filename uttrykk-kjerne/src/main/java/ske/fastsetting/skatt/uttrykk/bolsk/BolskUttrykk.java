package ske.fastsetting.skatt.uttrykk.bolsk;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;

public abstract class BolskUttrykk extends AbstractUttrykk<Boolean, BolskUttrykk> {
    public BolskUttrykk og(BolskUttrykk uttrykk) {
        return new OgUttrykk(this, uttrykk);
    }

    public BolskUttrykk eller(BolskUttrykk uttrykk) {
        return new EllerUttrykk(this, uttrykk);
    }

}
