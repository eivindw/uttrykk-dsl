package ske.fastsetting.skatt.uttrykk.bolsk;

import ske.fastsetting.skatt.uttrykk.Uttrykk;

public interface BolskUttrykk extends Uttrykk<Boolean> {
    default AbstractBolskUttrykk og(BolskUttrykk uttrykk) {
        return new OgUttrykk(this, uttrykk);
    }

    default AbstractBolskUttrykk eller(BolskUttrykk uttrykk) {
        return new EllerUttrykk(this, uttrykk);
    }

}
