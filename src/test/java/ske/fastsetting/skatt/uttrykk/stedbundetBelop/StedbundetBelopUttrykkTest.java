package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import org.junit.Ignore;
import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConfluenceUttrykkBeskriver;

import java.util.Map;
import java.util.function.Predicate;

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

        StedbundetBelopUttrykk bankinnskudd = kr(30_000, "Lørenskog").navn("bankinnskudd");
        StedbundetBelopUttrykk bil = kr(70_000, "Lørenskog").navn("bil");
        StedbundetBelopUttrykk egenBolig = kr(4_000_000, "Lørenskog").navn("primærbolig");

        StedbundetBelopUttrykk tomt = kr(100_000,"Asker").navn("tomt");

        StedbundetBelopUttrykk fritid1 = kr(300_000, "Hvaler");
        StedbundetBelopUttrykk fritid2 = kr(600_000, "Hamar");
        StedbundetBelopUttrykk fritid3 = kr(150_000, "Lørenskog");

        BelopUttrykk gjeld = KroneUttrykk.kr(3_140_000).navn("gjeld");
        BelopUttrykk fribelop = KroneUttrykk.kr(1_200_000).navn("fribeløp formue kl 1");

        Predicate<String> erHjemsted = s->s.equals("Lørenskog");
        Predicate<String> erIkkeHjemsted = s->!s.equals("Lørenskog");

        StedbundetBelopUttrykk bruttoformueFritidsbolig = sum(fritid1,fritid2,fritid3).navn("bruttoformue fritidsbolig");
        StedbundetBelopUttrykk bruttoformueHjemsted = sum(egenBolig,tomt,bankinnskudd,bil,bruttoformueFritidsbolig.filtrer(erHjemsted)).navn("bruttoformue hjemsted");
        StedbundetBelopUttrykk bruttoformueUtenbygdsFritidsbolig = bruttoformueFritidsbolig.filtrer(erIkkeHjemsted).navn("bruttoformue utenbygds fritidsbolig");

        BelopUttrykk maksGjeldsfradragHjemsted = begrens(gjeld).oppad(bruttoformueHjemsted).navn("maks gjeldsfradrag hjemsted");

        StedbundetBelopUttrykk nettoformueHjemsted = bruttoformueHjemsted.minusProporsjonalt(maksGjeldsfradragHjemsted).navn("nettoformue hjemsted");

        BelopUttrykk maksFribelopHjemsted = begrens(fribelop).oppad(nettoformueHjemsted).navn("maks fribeløp hjemsted");

        StedbundetBelopUttrykk grunnlagKommuneskattHjemsted = nettoformueHjemsted.minusProporsjonalt(maksFribelopHjemsted).navn("grunnlag kommuneskatt hjemsted");

        BelopUttrykk restGjeld = gjeld.minus(maksGjeldsfradragHjemsted).navn("gjeldsfradrag til fordeling utenbygds");

        StedbundetBelopUttrykk nettoFormueFritidsbolig = bruttoformueUtenbygdsFritidsbolig.minusProporsjonalt(restGjeld).navn("nettoformue utenbygds fritidsbolig");

        BelopUttrykk begrensetRestFribelop = begrens(fribelop.minus(maksFribelopHjemsted)).oppad(nettoFormueFritidsbolig).navn("fribeløp til fordeling utenbygds");

        StedbundetBelopUttrykk grunnlagKommuneskattFritidsbolig = nettoFormueFritidsbolig.minusProporsjonalt(begrensetRestFribelop).navn("grunnlag kommuneskatt utenbygds fritidsbolig");

        StedbundetBelopUttrykk grunnlagKommuneskatt = grunnlagKommuneskattFritidsbolig.pluss(grunnlagKommuneskattHjemsted).navn("grunnlag kommuneskatt");

        StedbundetBelopUttrykk kommuneskatt = grunnlagKommuneskatt.multiplisertMed(satsKommuneskatt).navn("kommuneskatt");

        skrivtUtVerdi(kommuneskatt);

        skrivUtWiki(kommuneskatt);
    }

    private void skrivtUtVerdi(StedbundetBelopUttrykk kommuneskatt) {
        System.out.println(UttrykkContextImpl.beregne(kommuneskatt).verdi());
    }

    private void skrivUtWiki(StedbundetBelopUttrykk kommuneskatt) {
        ConfluenceUttrykkBeskriver confluenceUttrykkBeskriver = new ConfluenceUttrykkBeskriver("stedbundet");
        Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> sider = confluenceUttrykkBeskriver.beskriv(UttrykkContextImpl.beskrive(kommuneskatt));
        sider.values().stream().map(cs->cs.getInnhold()).forEach(s->System.out.println(s));
    }
}
