package ske.fastsetting.skatt.uttrykk.ektefelle;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.Skattegrunnlag;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConfluenceUttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel.ExcelUttrykkBeskriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static ske.fastsetting.skatt.uttrykk.UttrykkTest.PostUttrykk.post;
import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.ektefelle.EktefelleUttrykk.ektefelles;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

/**
 * Created by jorn ola birkeland on 15.01.15.
 */
public class JordbruksfradragTest {

    public static final String POST_INNTEKT_JORDBRUK = "inntekt_jordbruk";
    private EktefelleUttrykkContext<Belop> skattyterKontekst1;
    private EktefelleUttrykkContext<Belop> skattyterKontekst2;

    @Before
    public void init() {
        Skattegrunnlag sg1 = new Skattegrunnlag().post(POST_INNTEKT_JORDBRUK,new Belop(300_000));
        Skattegrunnlag sg2 = new Skattegrunnlag().post(POST_INNTEKT_JORDBRUK,new Belop(400_000));

        skattyterKontekst1 = EktefelleUttrykkContext.ny(sg1);
        skattyterKontekst2 = skattyterKontekst1.medEktefelle(sg2);
    }


    @Test
    public void testJordbruksFradrag() {
        BelopUttrykk jordbruksfradrag = jordbruksfradrag();

        System.out.println(skattyterKontekst1.eval(jordbruksfradrag));
        System.out.println(skattyterKontekst2.eval(jordbruksfradrag));
    }

    @Test
    public void testJordbruksfradragUtenEktefelle() {
        Skattegrunnlag sg1 = new Skattegrunnlag().post(POST_INNTEKT_JORDBRUK,new Belop(300_000));

        skattyterKontekst1 = EktefelleUttrykkContext.ny(sg1);

        System.out.println(skattyterKontekst1.eval(jordbruksfradrag()));
    }

    @Test
    public void skrivWiki() {
        BelopUttrykk jordbruksfradrag = jordbruksfradrag();

        final ConfluenceUttrykkBeskriver beskriver = new ConfluenceUttrykkBeskriver("Hovedside");

        beskriver.beskriv(skattyterKontekst1.beskrivResultat(jordbruksfradrag));
        final Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> sider = beskriver.beskriv(skattyterKontekst2.beskrivResultat(jordbruksfradrag));

        assertNotNull("Sider er null", sider);

        sider.forEach((tittel, side) -> {
            System.out.println("### " + tittel + " ###");
            System.out.println(side);
        });
    }

    @Test
    public void skrivExcel() throws IOException {
        BelopUttrykk jordbruksfradrag = jordbruksfradrag();

        final ExcelUttrykkBeskriver beskriver = new ExcelUttrykkBeskriver();

        beskriver.beskriv(skattyterKontekst1.beskrivResultat(jordbruksfradrag));
        final Workbook wb = beskriver.beskriv(skattyterKontekst2.beskrivResultat(jordbruksfradrag));

        FileOutputStream out = new FileOutputStream("jordbruksfradrag.xlsx");
        wb.write(out);
        out.close();
    }


    private BelopUttrykk jordbruksfradrag() {
        BelopUttrykk minsteJordbruksfradrag = kr(63500).tags("sats").navn("minste jordbruksfradrag");
        BelopUttrykk maksJordbruksfradrag = kr(166400).tags("sats").navn("maks jorbruksfradrag");
        TallUttrykk jordbruksFradragSats = prosent(38).tags("sats").navn("prosentsats jordbruksfradrag");

        BelopUttrykk jordbruksinntekt = post(POST_INNTEKT_JORDBRUK).navn("Inntekt jordbruk").tags("skattegrunnlag");

        BelopUttrykk totalJordbruksinntekt = jordbruksinntekt.pluss(ektefelles(jordbruksinntekt));

        BelopUttrykk ubegrensetTotalfradrag =  minsteJordbruksfradrag
                .pluss(((totalJordbruksinntekt
                        .minus(minsteJordbruksfradrag)).multiplisertMed(jordbruksFradragSats)));

        BelopUttrykk totalfradrag = begrens(ubegrensetTotalfradrag).nedad(kr(0)).oppad(maksJordbruksfradrag);

        TallUttrykk andelAvTotaltfradrag = jordbruksinntekt.dividertMed(totalJordbruksinntekt);

        return totalfradrag.multiplisertMed(andelAvTotaltfradrag).navn("Jordbruksfradrag");
    }

}
