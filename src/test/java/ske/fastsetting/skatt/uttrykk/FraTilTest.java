package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.beregneOgBeskrive;
import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver.print;

public class FraTilTest {

    @Test
    public void testTil() {
        BelopUttrykk bu = hvis(kr(5).erInntil(kr(5))).brukDa(kr(100)).ellersBruk(kr(50));

        UttrykkResultat<Belop> resultat = beregneOgBeskrive(bu);

        assertEquals(Belop.kr(50), resultat.verdi());

        print(resultat);
    }

    @Test
    public void testTilOgMed() {
        BelopUttrykk bu = hvis(kr(5).erTilOgMed(kr(5))).brukDa(kr(100)).ellersBruk(kr(50));

        UttrykkResultat<Belop> resultat = beregneOgBeskrive(bu);

        assertEquals(Belop.kr(100), resultat.verdi());
        print(resultat);
    }

    @Test
    public void testFra() {
        BelopUttrykk bu = hvis(kr(5).erFra(kr(5))).brukDa(kr(100)).ellersBruk(kr(50));

        UttrykkResultat<Belop> resultat = beregneOgBeskrive(bu);

        assertEquals(Belop.kr(50), resultat.verdi());
        print(resultat);
    }

    @Test
    public void testFraOgMed() {
        BelopUttrykk bu = hvis(kr(5).erFraOgMed(kr(5))).brukDa(kr(100)).ellersBruk(kr(50));

        UttrykkResultat<Belop> resultat = beregneOgBeskrive(bu);

        assertEquals(Belop.kr(100), resultat.verdi());
        print(resultat);
    }

    @Test
    public void testTilFra() {
        BelopUttrykk bu = hvis(kr(5).erFra(kr(4)).ogTil(kr(6))).brukDa(kr(100)).ellersBruk(kr(50));

        UttrykkResultat<Belop> resultat = beregneOgBeskrive(bu);

        assertEquals(Belop.kr(100), resultat.verdi());
        print(resultat);
    }

    @Test
    public void testTilOgMedFraOgMed() {
        BelopUttrykk bu = hvis(kr(4).erFraOgMed(kr(4)).ogTilOgMed(kr(4))).brukDa(kr(100)).ellersBruk(kr(50));

        UttrykkResultat<Belop> resultat = beregneOgBeskrive(bu);

        assertEquals(Belop.kr(100), resultat.verdi());
        print(resultat);
    }

    @Test
    public void testUgyldigTilFra() {
        BelopUttrykk bu = hvis(kr(5).erFra(kr(6)).ogTil(kr(4))).brukDa(kr(100)).ellersBruk(kr(50));

        UttrykkResultat<Belop> resultat = beregneOgBeskrive(bu);

        assertEquals(Belop.kr(50), resultat.verdi());
        print(resultat);
    }
}
