package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StebundetHverGrenseUttrykk.begrensHvertSted;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;

/**
 * Created by jorn ola birkeland on 13.03.15.
 */
public class StedbundetHverGrenseUttrykkTest {
    @Test
    public void test()  {
        StedbundetBelopUttrykk<String> uttrykk = kr(34,"Asker").pluss(kr(-12,"Askim"));
        StedbundetBelopUttrykk<String> begrenset = begrensHvertSted(uttrykk)
                .nedad(KroneUttrykk.KR_0)
                .oppad(KroneUttrykk.kr(20));

        final StedbundetBelop<String> faktisk = UttrykkContextImpl.beregne(begrenset).verdi();

        System.out.println(faktisk);

        assertEquals(Belop.NULL,faktisk.get("Askim"));
        assertEquals(Belop.kr(20),faktisk.get("Asker"));

    }
}
