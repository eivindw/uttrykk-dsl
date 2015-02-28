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
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConfluenceUttrykkBeskriver;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

/**
 * Created by jorn ola birkeland on 23.02.15.
 */
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

        StedbundetBelopUttrykk<Kommune> tomt = kr(100_000,Kommune.Asker).navn("tomt");

        StedbundetBelopUttrykk<Kommune> fritid1 = kr(316_250, Kommune.Hvaler);
        StedbundetBelopUttrykk<Kommune> fritid2 = kr(660_000, Kommune.Hamar);

        BelopUttrykk gjeld = KroneUttrykk.kr(1_700_000).navn("gjeld");

        StedbundetBelopUttrykk<Kommune>  bruttoformueFritidsbolig = sum(fritid1, fritid2).navn("bruttoformue fritidsbolig");
        StedbundetBelopUttrykk<Kommune>  bruttoformueUtenFritidsbolig = sum(egenBolig,tomt,bankinnskudd,bil).navn("bruttoformue uten fritidsbolig");

        BelopUttrykk            maksGjeldsfradragFormueUtenFritidsbolig = begrens(gjeld).oppad(bruttoformueUtenFritidsbolig).navn("maks gjeldsfradrag formue uten fritidsbolig");

        StedbundetBelopUttrykk<Kommune>  nettoformueUtenFritidsbolig = bruttoformueUtenFritidsbolig.minusProporsjonalt(maksGjeldsfradragFormueUtenFritidsbolig).navn("nettoformue uten fritidsbolig");

        BelopUttrykk            maksFribelopFormueUtenFritidsbolig = begrens(fribelop).oppad(nettoformueUtenFritidsbolig).navn("maks fribeløp formue uten fritidsbolig");

        StedbundetBelopUttrykk<Kommune>  grunnlagKommuneskattUtenFritidsbolig = nettoformueUtenFritidsbolig.minusProporsjonalt(maksFribelopFormueUtenFritidsbolig).navn("grunnlag kommuneskatt hjemsted");

        BelopUttrykk            restGjeldTilFordelingFritidsbolig = begrens(gjeld.minus(maksGjeldsfradragFormueUtenFritidsbolig)).oppad(bruttoformueFritidsbolig).navn("gjeldsfradrag til fordeling utenbygds");

        StedbundetBelopUttrykk<Kommune>  nettoFormueFritidsbolig = bruttoformueFritidsbolig.minusProporsjonalt(restGjeldTilFordelingFritidsbolig).navn("nettoformue utenbygds fritidsbolig");

        BelopUttrykk            begrensetRestFribelop = begrens(fribelop.minus(maksFribelopFormueUtenFritidsbolig)).oppad(nettoFormueFritidsbolig).navn("fribeløp til fordeling utenbygds");

        StedbundetBelopUttrykk<Kommune>  grunnlagKommuneskattFritidsbolig = nettoFormueFritidsbolig.minusProporsjonalt(begrensetRestFribelop).navn("grunnlag kommuneskatt utenbygds fritidsbolig");

        StedbundetBelopUttrykk<Kommune>  grunnlagKommuneskatt = grunnlagKommuneskattFritidsbolig.pluss(grunnlagKommuneskattUtenFritidsbolig).navn("grunnlag kommuneskatt");

        StedbundetBelopUttrykk<Kommune>  kommuneskatt = grunnlagKommuneskatt.multiplisertMed(satsKommuneskatt).navn("kommuneskatt");

        skrivtUtVerdi(kommuneskatt);

        //skrivUtWiki(kommuneskatt);
    }

    private void skrivtUtVerdi(Uttrykk<?> uttrykk) {
        System.out.println(UttrykkContextImpl.beregne(uttrykk).verdi());
    }

    private void skrivUtWiki(Uttrykk<?> uttrykk) {
        ConfluenceUttrykkBeskriver confluenceUttrykkBeskriver = new ConfluenceUttrykkBeskriver("stedbundet");
        Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> sider = confluenceUttrykkBeskriver.beskriv(UttrykkContextImpl.beskrive(uttrykk));
        sider.values().stream().map(cs->cs.getInnhold()).forEach(s->System.out.println(s));
    }

    public enum Kommune {
        Asker,
        Lorenskog,
        Hvaler,
        Hamar
    }
}
