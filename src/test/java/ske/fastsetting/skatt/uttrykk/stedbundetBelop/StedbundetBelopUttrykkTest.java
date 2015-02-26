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
        StedbundetBelopUttrykk b = kr(30, "A").pluss(kr(60, "B"));

        StedbundetBelopUttrykk diff = b.minusProporsjonalt(KroneUttrykk.kr(15));

        StedbundetBelop belop = UttrykkContextImpl.beregne(diff).verdi();

        assertEquals(Belop.kr(25), belop.get("A").rundAvTilHeleKroner());
        assertEquals(Belop.kr(50), belop.get("B").rundAvTilHeleKroner());
    }

    @Test
    @Ignore
    public void test() {
        StedbundetBelopUttrykk b = kr(45,"Lørenskog").pluss(kr(45,"Asker"));
        StedbundetBelopUttrykk filtrert = b.filtrer(s->s.equals("Lørenskog"));

        StedbundetBelopUttrykk produkt = b.multiplisertMed(prosent(37));
        StedbundetBelopUttrykk div = b.dividertMed(prosent(37));

        StedbundetBelopUttrykk fordelt = b.plussProporsjonalt(KroneUttrykk.kr(45));

        BelopUttrykk a = KroneUttrykk.kr(45).pluss(b.steduavhengig());

    }


    @Test
    @Ignore
    public void formueIFlereKommuner() {
        final ProsentUttrykk satsKommuneskatt = prosent(0.7).navn("sats kommuneskatt");
        final BelopUttrykk fribelop = KroneUttrykk.kr(1_200_000).navn("fribeløp formue kl 1");

        StedbundetBelopUttrykk bankinnskudd = kr(30_800, "Lørenskog").navn("bankinnskudd");
        StedbundetBelopUttrykk bil = kr(99_000, "Lørenskog").navn("bil");
        StedbundetBelopUttrykk egenBolig = kr(4_025_000, "Lørenskog").navn("primærbolig");

        StedbundetBelopUttrykk tomt = kr(100_000,"Asker").navn("tomt");

        StedbundetBelopUttrykk fritid1 = kr(316_250, "Hvaler");
        StedbundetBelopUttrykk fritid2 = kr(660_000, "Hamar");

        BelopUttrykk gjeld = KroneUttrykk.kr(1_700_000).navn("gjeld");

        StedbundetBelopUttrykk  bruttoformueFritidsbolig = sum(fritid1,fritid2).navn("bruttoformue fritidsbolig");
        StedbundetBelopUttrykk  bruttoformueUtenFritidsbolig = sum(egenBolig,tomt,bankinnskudd,bil).navn("bruttoformue uten fritidsbolig");

        BelopUttrykk            maksGjeldsfradragFormueUtenFritidsbolig = begrens(gjeld).oppad(bruttoformueUtenFritidsbolig).navn("maks gjeldsfradrag formue uten fritidsbolig");

        StedbundetBelopUttrykk  nettoformueUtenFritidsbolig = bruttoformueUtenFritidsbolig.minusProporsjonalt(maksGjeldsfradragFormueUtenFritidsbolig).navn("nettoformue uten fritidsbolig");

        BelopUttrykk            maksFribelopFormueUtenFritidsbolig = begrens(fribelop).oppad(nettoformueUtenFritidsbolig).navn("maks fribeløp formue uten fritidsbolig");

        StedbundetBelopUttrykk  grunnlagKommuneskattUtenFritidsbolig = nettoformueUtenFritidsbolig.minusProporsjonalt(maksFribelopFormueUtenFritidsbolig).navn("grunnlag kommuneskatt hjemsted");

        BelopUttrykk            restGjeldTilFordelingFritidsbolig = begrens(gjeld.minus(maksGjeldsfradragFormueUtenFritidsbolig)).oppad(bruttoformueFritidsbolig).navn("gjeldsfradrag til fordeling utenbygds");

        StedbundetBelopUttrykk  nettoFormueFritidsbolig = bruttoformueFritidsbolig.minusProporsjonalt(restGjeldTilFordelingFritidsbolig).navn("nettoformue utenbygds fritidsbolig");

        BelopUttrykk            begrensetRestFribelop = begrens(fribelop.minus(maksFribelopFormueUtenFritidsbolig)).oppad(nettoFormueFritidsbolig).navn("fribeløp til fordeling utenbygds");

        StedbundetBelopUttrykk  grunnlagKommuneskattFritidsbolig = nettoFormueFritidsbolig.minusProporsjonalt(begrensetRestFribelop).navn("grunnlag kommuneskatt utenbygds fritidsbolig");

        StedbundetBelopUttrykk  grunnlagKommuneskatt = grunnlagKommuneskattFritidsbolig.pluss(grunnlagKommuneskattUtenFritidsbolig).navn("grunnlag kommuneskatt");

        StedbundetBelopUttrykk  kommuneskatt = grunnlagKommuneskatt.multiplisertMed(satsKommuneskatt).navn("kommuneskatt");

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
}
