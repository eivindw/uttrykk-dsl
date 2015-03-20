package ske.fastsetting.skatt.uttrykk.belop.multisats;

import ske.fastsetting.skatt.uttrykk.Uttrykk;

/**
* Created by jorn ola birkeland on 20.03.15.
*/
public class DeretterMedSatsStegBuilderUttrykk<K ,S> extends SatsStegBuilderUttrykk<K,S,DeretterMedSatsStegBuilderUttrykk<K,S>> {

    private final Uttrykk<K> nedreGrense;

    public DeretterMedSatsStegBuilderUttrykk(BelopMultisatsUttrykk<K, S> funksjon, Uttrykk<K> nedreGrense) {
        super(funksjon);
        this.nedreGrense = nedreGrense;
    }

    public OppTilGrenseStegBuilderUttrykk<K,S> deretterMedSats(Uttrykk<S> sats) {
        funksjon.nyttSteg(nedreGrense,sats);
        return new OppTilGrenseStegBuilderUttrykk<>(funksjon);
    }

}
