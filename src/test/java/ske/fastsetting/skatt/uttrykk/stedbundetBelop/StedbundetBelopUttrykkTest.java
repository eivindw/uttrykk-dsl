package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import org.junit.Ignore;
import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.confluence.ConfluenceUttrykkBeskriver;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.Debug.debug;

public class StedbundetBelopUttrykkTest {

    @Test
    public void skalFordeleProporsjonaltVedMinus() {
        StedbundetBelopUttrykk<String> b = kr(30, "A").pluss(kr(60, "B"));

        StedbundetBelopUttrykk<String> diff = b.minusProporsjonalt(KroneUttrykk.kr(15));

        StedbundetBelop<String> belop = UttrykkContextImpl.beregne(diff).verdi();

        assertEquals(Belop.kr(25), belop.get("A").rundAvTilHeleKroner());
        assertEquals(Belop.kr(50), belop.get("B").rundAvTilHeleKroner());
    }

    @Test
    public void skalPlusseOgMinuse() {
        StedbundetBelopUttrykk<String> almFF = kr(320_000, "L")
          .pluss(kr(210_000, "E"))
          .pluss(kr(420_000, "R"))
          .pluss(kr(6_000, "L"))
          .minus(kr(3_850, "L"))
          .minus(kr(89_050, "L"))
          .minus(kr(55_467, "E"))
          .minus(kr(110_933, "R"));

        StedbundetBelop<String> belop = UttrykkContextImpl.beregne(almFF).verdi();

        assertEquals(Belop.kr(309_067), belop.get("R").rundAvTilHeleKroner());
        assertEquals(Belop.kr(154_533), belop.get("E").rundAvTilHeleKroner());
        assertEquals(Belop.kr(233_100), belop.get("L").rundAvTilHeleKroner());
    }


    @Test
    public void skalTrekkeFraSteder() {
        StedbundetBelopUttrykk<String> a = kr(30, "A").pluss(kr(60, "B"));
        StedbundetBelopUttrykk<String> b = kr(45, "B");

        StedbundetBelop<String> belop = UttrykkContextImpl.beregne(a.minusSted(b)).verdi();

        assertEquals(1, belop.steder().size());
        assertTrue(belop.harSted("A"));
        assertEquals(Belop.kr(30), belop.get("A"));
    }

    @Test
    public void skalTrekkeFraNull() {
        StedbundetBelopUttrykk<String> a = kr(30, "A").pluss(kr(60, "B"));
        StedbundetBelopUttrykk<String> b = StedbundetKroneUttrykk.kr0();

        StedbundetBelop belop = UttrykkContextImpl.beregne(a.minusSted(b)).verdi();

        assertEquals(2, belop.steder().size());
    }

    @Test
    public void skalTrekkeStederFraNull() {
        StedbundetBelopUttrykk<String> a = kr(30, "A").pluss(kr(60, "B"));
        StedbundetBelopUttrykk<String> b = StedbundetKroneUttrykk.kr0();

        StedbundetBelop<String> belop = UttrykkContextImpl.beregne(b.minusSted(a)).verdi();

        assertEquals(0, belop.steder().size());
    }

    @Test
    @Ignore
    public void formueIFlereKommuner() {
        final ProsentUttrykk satsKommuneskatt = prosent(0.7).navn("sats kommuneskatt");
        final BelopUttrykk fribelop = KroneUttrykk.kr(1_200_000).navn("fribeløp formue kl 1");

        StedbundetBelopUttrykk<Kommune> bankinnskudd = kr(30_800, Kommune.Lorenskog).navn("bankinnskudd");
        StedbundetBelopUttrykk<Kommune> bil = kr(99_000, Kommune.Lorenskog).navn("bil");
        StedbundetBelopUttrykk<Kommune> egenBolig = kr(4_025_000, Kommune.Lorenskog).navn("primærbolig");

        StedbundetBelopUttrykk<Kommune> tomt = kr(100_000, Kommune.Asker).navn("tomt");

        StedbundetBelopUttrykk<Kommune> fritid1 = kr(316_250, Kommune.Hvaler);
        StedbundetBelopUttrykk<Kommune> fritid2 = kr(660_000, Kommune.Hamar);
        StedbundetBelopUttrykk<Kommune> fritid3 = kr(260_000, Kommune.Lorenskog);

        BelopUttrykk gjeld = KroneUttrykk.kr(1_700_000).navn("gjeld");

        StedbundetBelopUttrykk<Kommune> bruttoformueFritidsbolig = sum(fritid1, fritid2, fritid3).navn("bruttoformue "
          + "fritidsbolig");
        StedbundetBelopUttrykk<Kommune> bruttoformueUtenFritidsbolig = sum(egenBolig, tomt, bankinnskudd, bil).navn
          ("bruttoformue uten fritidsbolig");
        StedbundetBelopUttrykk<Kommune> bruttoformueHytte = bruttoformueFritidsbolig.minusSted
          (bruttoformueUtenFritidsbolig).navn("bruttoformue hytte");
        StedbundetBelopUttrykk<Kommune> oevrigStedbundenBruttoformue = bruttoformueUtenFritidsbolig.pluss
          (bruttoformueFritidsbolig.minusSted(bruttoformueHytte)).navn("øvrig stedbunden formue");

        BelopUttrykk maksGjeldsfradragFormueUtenHytte = begrens(gjeld).oppad(oevrigStedbundenBruttoformue).navn("maks"
          + " gjeldsfradrag formue uten hytte");

        StedbundetBelopUttrykk<Kommune> nettoformueUtenHytte = oevrigStedbundenBruttoformue.minusProporsjonalt
          (maksGjeldsfradragFormueUtenHytte).navn("nettoformue uten hytte");

        BelopUttrykk maksFribelopFormueUtenHytte = begrens(fribelop).oppad(nettoformueUtenHytte).navn("maks fribeløp "
          + "formue uten hytte");

        StedbundetBelopUttrykk<Kommune> grunnlagKommuneskattUtenHytte = nettoformueUtenHytte.minusProporsjonalt
          (maksFribelopFormueUtenHytte).navn("grunnlag kommuneskatt skattekommune");

        BelopUttrykk restGjeldTilFordelingHytte = begrens(gjeld.minus(maksGjeldsfradragFormueUtenHytte)).oppad
          (bruttoformueHytte).navn("gjeldsfradrag til fordeling utenbygds");

        StedbundetBelopUttrykk<Kommune> nettoFormueHytte = bruttoformueHytte.minusProporsjonalt
          (restGjeldTilFordelingHytte).navn("nettoformue utenbygds fritidsbolig");

        BelopUttrykk begrensetRestFribelop = begrens(fribelop.minus(maksFribelopFormueUtenHytte)).oppad
          (nettoFormueHytte).navn("fribeløp til fordeling utenbygds");

        StedbundetBelopUttrykk<Kommune> grunnlagKommuneskattHytte = nettoFormueHytte.minusProporsjonalt
          (begrensetRestFribelop).navn("grunnlag kommuneskatt utenbygds fritidsbolig");

        StedbundetBelopUttrykk<Kommune> grunnlagKommuneskatt = grunnlagKommuneskattHytte.pluss
          (grunnlagKommuneskattUtenHytte).navn("grunnlag kommuneskatt");

        StedbundetBelopUttrykk<Kommune> kommuneskatt = grunnlagKommuneskatt.multiplisertMed(satsKommuneskatt).navn
          ("kommuneskatt");

        //skrivtUtVerdi(kommuneskatt);
        debug(kommuneskatt);

        //skrivUtWiki(kommuneskatt);
    }

    private void skrivtUtVerdi(Uttrykk<?> uttrykk) {
        System.out.println(UttrykkContextImpl.beregne(uttrykk).verdi());
    }

    private void skrivUtWiki(Uttrykk<?> uttrykk) {
        ConfluenceUttrykkBeskriver confluenceUttrykkBeskriver = new ConfluenceUttrykkBeskriver("stedbundet");
        Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> sider = confluenceUttrykkBeskriver.beskriv
          (UttrykkContextImpl.beskrive(uttrykk));
        sider.values().stream().map(cs -> cs.getInnhold()).forEach(s -> System.out.println(s));
    }

    public enum Kommune {
        Asker,
        Lorenskog,
        Hvaler,
        Hamar
    }
}
