package ske.fastsetting.skatt.uttrykk.belop.multisats;

import ske.fastsetting.skatt.uttrykk.Uttrykk;

/**
* Created by jorn ola birkeland on 20.03.15.
*/
public class OppTilGrenseStegBuilderUttrykk<K,S> extends SatsStegBuilderUttrykk<K,S,OppTilGrenseStegBuilderUttrykk<K,S>> {

    public OppTilGrenseStegBuilderUttrykk(BelopMultisatsUttrykk<K, S> funksjon) {
        super(funksjon);
    }

    public DeretterMedSatsStegBuilderUttrykk<K,S> til(Uttrykk<K> til) {
        funksjon.settGjeldendeOevreGrense(til);
        return new DeretterMedSatsStegBuilderUttrykk<>(funksjon,til);
    }

}
