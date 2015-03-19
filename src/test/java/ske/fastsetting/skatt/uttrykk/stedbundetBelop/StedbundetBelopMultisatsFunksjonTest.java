package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StebundetHverGrenseUttrykk.begrensHvertSted;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopMultisatsFunksjon.multisatsFunksjonAv;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class StedbundetBelopMultisatsFunksjonTest {
    @Test
    public void test() {
        StedbundetBelopUttrykk<String> uttrykk = begrensHvertSted(kr(34,"Asker").pluss(kr(-12,"Askim"))).nedad(KroneUttrykk.KR_0);

        StedbundetBelopUttrykk<String> jordfradrag = multisatsFunksjonAv(uttrykk)
                .medSats(prosent(100)).til(KroneUttrykk.kr(20)).deretterMedSats(prosent(70))
                .til(KroneUttrykk.kr(50)).navn("jordfradrag");

        System.out.println(UttrykkContextImpl.beregne(jordfradrag).verdi());
    }
}
