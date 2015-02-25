package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* Created by jorn ola birkeland on 24.02.15.
*/
public class StedbundetBelopSumUttrykk extends SumUttrykk<StedbundetBelop,StedbundetBelopUttrykk,StedbundetBelopSumUttrykk> implements StedbundetBelopUttrykk{

    protected StedbundetBelopSumUttrykk(Collection<StedbundetBelopUttrykk> uttrykk) {
        super(uttrykk);
    }

    @Override
    protected StedbundetBelop nullVerdi() {
        return StedbundetBelop.NULL;
    }

    public static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopSumUttrykk sum(StedbundetBelopUttrykk... stedbundetBelopUttrykk) {
        return new ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopSumUttrykk(Stream.of(stedbundetBelopUttrykk).collect(Collectors.toList()));
    }
}
