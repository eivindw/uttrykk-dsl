package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import org.junit.Ignore;
import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopUttrykk.StedbundetBelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

/**
 * Created by jorn ola birkeland on 23.02.15.
 */
public class StedbundetBelopUttrykkTest {

    @Test
    @Ignore
    public void test() {
        StedbundetBelopUttrykk b = kr(45,"Lørenskog").pluss(kr(45,"Asker"));
        StedbundetBelopUttrykk filtrert = b.filtrer(s->s.equals("Lørenskog"));

        StedbundetBelopUttrykk produkt = b.multiplisertMed(prosent(37));
        StedbundetBelopUttrykk div = b.dividertMed(prosent(37));

        StedbundetBelopUttrykk fordelt = b.fordelProporsjonalt(KroneUttrykk.kr(45));

        BelopUttrykk a = KroneUttrykk.kr(45).pluss(b.steduavhengig());

    }


    @Test
    @Ignore
    public void formueIFlereKommuner() {
        StedbundetBelopUttrykk bankinnskudd = kr(30_000, "Lørenskog");
        StedbundetBelopUttrykk bil = kr(70_000, "Lørenskog");
        StedbundetBelopUttrykk egenBolig = kr(4_000_000, "Lørenskog");

        StedbundetBelopUttrykk tomt = kr(100_000,"Asker");

        StedbundetBelopUttrykk fritid1 = kr(300_000, "Hvaler");
        StedbundetBelopUttrykk fritid2 = kr(600_000,"Hamar");


        BelopUttrykk gjeld = KroneUttrykk.kr(4_800_000);
        BelopUttrykk fribelop = KroneUttrykk.kr(1_200_000);


        StedbundetBelopUttrykk bruttoformueEkslFritidsbolig = sum(egenBolig,tomt,bankinnskudd,bil);
        StedbundetBelopUttrykk bruttoformueFritidsbolig = fritid1.pluss(fritid2);


        BelopUttrykk maksGjeldsfradragHjemstedkommune = begrens(gjeld).oppad(bruttoformueEkslFritidsbolig.steduavhengig());

        StedbundetBelopUttrykk nettoformueEksklFritidsbolig = bruttoformueEkslFritidsbolig.minusProporsjonalt(maksGjeldsfradragHjemstedkommune);

        System.out.println(UttrykkContextImpl.beregne(nettoformueEksklFritidsbolig).verdi());

        BelopUttrykk maksFribelopHjemstedkommune = begrens(fribelop).oppad(nettoformueEksklFritidsbolig.steduavhengig());

        StedbundetBelopUttrykk grunnlagKommuneskattEkslFritidsbolig = nettoformueEksklFritidsbolig.minusProporsjonalt(maksFribelopHjemstedkommune);

        StedbundetBelopUttrykk skattFromueEkslFritidsbolig = grunnlagKommuneskattEkslFritidsbolig.multiplisertMed(prosent(0.7));


        BelopUttrykk restGjeld = gjeld.minus(maksGjeldsfradragHjemstedkommune);

        StedbundetBelopUttrykk nettoFormueFritidsbolig = bruttoformueFritidsbolig.fordelProporsjonalt(restGjeld);

        BelopUttrykk begrensetRestFribelop = fribelop.minus(maksFribelopHjemstedkommune);

        StedbundetBelopUttrykk grunnlagKommuneskattFritidsbolig = nettoFormueFritidsbolig.fordelProporsjonalt(begrensetRestFribelop);

        StedbundetBelopUttrykk skattFritidsbolig = grunnlagKommuneskattFritidsbolig.multiplisertMed(prosent(0.7));

        StedbundetBelopUttrykk kommuneskatt = skattFromueEkslFritidsbolig.pluss(skattFritidsbolig);

        System.out.println(UttrykkContextImpl.beregne(kommuneskatt).verdi());
    }
}
