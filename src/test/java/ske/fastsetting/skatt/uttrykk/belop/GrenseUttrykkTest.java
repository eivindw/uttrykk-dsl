package ske.fastsetting.skatt.uttrykk.belop;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.beregne;
import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.beregneOgBeskrive;
import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.nedre0;
import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.KR_0;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.belop.StorsteAvUttrykk.storsteAv;
import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver.print;

public class GrenseUttrykkTest {

    @Test
    public void negativBlirNull() {
        assert0(-1_000_000_000);
        assert0(-1_000);
        assert0(-1);
        assert0(0);

        assertUendret(0);
        assertUendret(1);
        assertUendret(1_000);
        assertUendret(1_000_000_000);
    }

    @Test
    public void fornuftigBeskrivelse() {
        print(beregneOgBeskrive(nedre0(kr(200))));
    }

    @Test
    public void alternativer() {
        final KroneUttrykk hundre = kr(100);

        System.out.println("Nedre0");
        print(beregneOgBeskrive(nedre0(hundre)));

        System.out.println("Hvis");
        print(beregneOgBeskrive(hvis(hundre.erStorreEnn(KR_0)).brukDa(hundre).ellersBruk(KR_0)));

        System.out.println("Størst av");
        print(beregneOgBeskrive(storsteAv(hundre, KR_0)));

        System.out.println("Grense");
        print(beregneOgBeskrive(begrens(kr(-1_000)).nedad(KR_0)));
    }

    private void assert0(int belop) {
        assertGrense(Belop.NULL, belop);
    }

    private void assertUendret(int belop) {
        assertGrense(Belop.kr(belop), belop);
    }

    private void assertGrense(Belop forventet, int belop) {
        assertEquals(forventet, beregne(nedre0(kr(belop))).verdi());
    }
}
