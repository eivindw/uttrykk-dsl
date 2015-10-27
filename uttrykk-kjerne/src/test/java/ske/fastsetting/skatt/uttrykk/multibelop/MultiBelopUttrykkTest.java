package ske.fastsetting.skatt.uttrykk.multibelop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ske.fastsetting.skatt.uttrykk.belop.BelopGrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.uttrykk.test.Debug.debug;

import org.junit.Ignore;
import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;

public class MultiBelopUttrykkTest {

    @Test
    public void skalFordeleProporsjonaltVedMinus() {
        MultiBelopUttrykk<String> b = kr(30, "A").pluss(kr(60, "B"));

        MultiBelopUttrykk<String> diff = b.minusProporsjonalt(KroneUttrykk.kr(15));

        MultiBelop<String> belop = TestUttrykkContext.beregne(diff).verdi();

        assertEquals(Belop.kr(25), belop.get("A").rundAvTilHeleKroner());
        assertEquals(Belop.kr(50), belop.get("B").rundAvTilHeleKroner());
    }

    @Test
    public void skalPlusseOgMinuse() {
        MultiBelopUttrykk<String> almFF = kr(320_000, "L")
          .pluss(kr(210_000, "E"))
          .pluss(kr(420_000, "R"))
          .pluss(kr(6_000, "L"))
          .minus(kr(3_850, "L"))
          .minus(kr(89_050, "L"))
          .minus(kr(55_467, "E"))
          .minus(kr(110_933, "R"));

        MultiBelop<String> belop = TestUttrykkContext.beregne(almFF).verdi();

        assertEquals(Belop.kr(309_067), belop.get("R").rundAvTilHeleKroner());
        assertEquals(Belop.kr(154_533), belop.get("E").rundAvTilHeleKroner());
        assertEquals(Belop.kr(233_100), belop.get("L").rundAvTilHeleKroner());
    }


    @Test
    public void skalTrekkeFraSteder() {
        MultiBelopUttrykk<String> a = kr(30, "A").pluss(kr(60, "B"));
        MultiBelopUttrykk<String> b = kr(45, "B");

        MultiBelop<String> belop = TestUttrykkContext.beregne(a.minusSted(b)).verdi();

        assertEquals(1, belop.steder().size());
        assertTrue(belop.harSted("A"));
        assertEquals(Belop.kr(30), belop.get("A"));
    }

    @Test
    public void skalTrekkeFraNull() {
        MultiBelopUttrykk<String> a = kr(30, "A").pluss(kr(60, "B"));
        MultiBelopUttrykk<String> b = MultiKroneUttrykk.kr0();

        MultiBelop belop = TestUttrykkContext.beregne(a.minusSted(b)).verdi();

        assertEquals(2, belop.steder().size());
    }

    @Test
    public void skalTrekkeStederFraNull() {
        MultiBelopUttrykk<String> a = kr(30, "A").pluss(kr(60, "B"));
        MultiBelopUttrykk<String> b = MultiKroneUttrykk.kr0();

        MultiBelop<String> belop = TestUttrykkContext.beregne(b.minusSted(a)).verdi();

        assertEquals(0, belop.steder().size());
    }

    @Test
    @Ignore
    public void formueIFlereKommuner() {
        final ProsentUttrykk satsKommuneskatt = prosent(0.7).navn("sats kommuneskatt");
        final BelopUttrykk fribelop = KroneUttrykk.kr(1_200_000).navn("fribeløp formue kl 1");

        MultiBelopUttrykk<Kommune> bankinnskudd = kr(30_800, Kommune.Lorenskog).navn("bankinnskudd");
        MultiBelopUttrykk<Kommune> bil = kr(99_000, Kommune.Lorenskog).navn("bil");
        MultiBelopUttrykk<Kommune> egenBolig = kr(4_025_000, Kommune.Lorenskog).navn("primærbolig");

        MultiBelopUttrykk<Kommune> tomt = kr(100_000, Kommune.Asker).navn("tomt");

        MultiBelopUttrykk<Kommune> fritid1 = kr(316_250, Kommune.Hvaler);
        MultiBelopUttrykk<Kommune> fritid2 = kr(660_000, Kommune.Hamar);
        MultiBelopUttrykk<Kommune> fritid3 = kr(260_000, Kommune.Lorenskog);

        BelopUttrykk gjeld = KroneUttrykk.kr(1_700_000).navn("gjeld");

        MultiBelopUttrykk<Kommune> bruttoformueFritidsbolig = sum(fritid1, fritid2, fritid3).navn("bruttoformue "
          + "fritidsbolig");
        MultiBelopUttrykk<Kommune> bruttoformueUtenFritidsbolig = sum(egenBolig, tomt, bankinnskudd, bil).navn
          ("bruttoformue uten fritidsbolig");
        MultiBelopUttrykk<Kommune> bruttoformueHytte = bruttoformueFritidsbolig.minusSted
          (bruttoformueUtenFritidsbolig).navn("bruttoformue hytte");
        MultiBelopUttrykk<Kommune> oevrigStedbundenBruttoformue = bruttoformueUtenFritidsbolig.pluss
          (bruttoformueFritidsbolig.minusSted(bruttoformueHytte)).navn("øvrig stedbunden formue");

        BelopUttrykk maksGjeldsfradragFormueUtenHytte = begrens(gjeld).oppad(oevrigStedbundenBruttoformue).navn("maks"
          + " gjeldsfradrag formue uten hytte");

        MultiBelopUttrykk<Kommune> nettoformueUtenHytte = oevrigStedbundenBruttoformue.minusProporsjonalt
          (maksGjeldsfradragFormueUtenHytte).navn("nettoformue uten hytte");

        BelopUttrykk maksFribelopFormueUtenHytte = begrens(fribelop).oppad(nettoformueUtenHytte).navn("maks fribeløp "
          + "formue uten hytte");

        MultiBelopUttrykk<Kommune> grunnlagKommuneskattUtenHytte = nettoformueUtenHytte.minusProporsjonalt
          (maksFribelopFormueUtenHytte).navn("grunnlag kommuneskatt skattekommune");

        BelopUttrykk restGjeldTilFordelingHytte = begrens(gjeld.minus(maksGjeldsfradragFormueUtenHytte)).oppad
          (bruttoformueHytte).navn("gjeldsfradrag til fordeling utenbygds");

        MultiBelopUttrykk<Kommune> nettoFormueHytte = bruttoformueHytte.minusProporsjonalt
          (restGjeldTilFordelingHytte).navn("nettoformue utenbygds fritidsbolig");

        BelopUttrykk begrensetRestFribelop = begrens(fribelop.minus(maksFribelopFormueUtenHytte)).oppad
          (nettoFormueHytte).navn("fribeløp til fordeling utenbygds");

        MultiBelopUttrykk<Kommune> grunnlagKommuneskattHytte = nettoFormueHytte.minusProporsjonalt
          (begrensetRestFribelop).navn("grunnlag kommuneskatt utenbygds fritidsbolig");

        MultiBelopUttrykk<Kommune> grunnlagKommuneskatt = grunnlagKommuneskattHytte.pluss
          (grunnlagKommuneskattUtenHytte).navn("grunnlag kommuneskatt");

        MultiBelopUttrykk<Kommune> kommuneskatt = grunnlagKommuneskatt.multiplisertMed(satsKommuneskatt).navn
          ("kommuneskatt");

        debug(kommuneskatt);

    }

    private void skrivtUtVerdi(Uttrykk<?> uttrykk) {
        System.out.println(TestUttrykkContext.beregne(uttrykk).verdi());
    }


    public enum Kommune {
        Asker,
        Lorenskog,
        Hvaler,
        Hamar
    }
}
