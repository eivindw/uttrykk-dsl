package ske.fastsetting.skatt.uttrykk.ektefelle;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.Skattegrunnlag;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConfluenceUttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel.ExcelUttrykkBeskriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by jorn ola birkeland on 15.01.15.
 */
public class JordbruksfradragTest {

    private EktefelleUttrykkContext<Belop> skattyterKontekst1;
    private EktefelleUttrykkContext<Belop> skattyterKontekst2;

    @Before
    public void init() {
        Skattegrunnlag sg1 = new Skattegrunnlag().post(Skattegrunnlag.SKATTEGRUNNLAGOBJEKT_TYPE__INNTEKT_JORDBRUK,new Belop(300_000));
        Skattegrunnlag sg2 = new Skattegrunnlag().post(Skattegrunnlag.SKATTEGRUNNLAGOBJEKT_TYPE__INNTEKT_JORDBRUK,new Belop(400_000));

        skattyterKontekst1 = EktefelleUttrykkContext.ny(sg1);
        skattyterKontekst2 = skattyterKontekst1.medEktefelle(sg2);
    }


    @Test
    public void testJordbruksFradrag() {
        BelopUttrykk jordbruksfradrag = SkatteberegningHelper.jordbruksfradrag();

        System.out.println(skattyterKontekst1.eval(jordbruksfradrag));
        System.out.println(skattyterKontekst2.eval(jordbruksfradrag));
    }

    @Test
    public void testJordbruksfradragUtenEktefelle() {
        Skattegrunnlag sg1 = new Skattegrunnlag().post(Skattegrunnlag.SKATTEGRUNNLAGOBJEKT_TYPE__INNTEKT_JORDBRUK,new Belop(300_000));

        skattyterKontekst1 = EktefelleUttrykkContext.ny(sg1);

        System.out.println(skattyterKontekst1.eval(SkatteberegningHelper.jordbruksfradrag()));
    }

    @Test
    public void skrivWiki() {
        BelopUttrykk jordbruksfradrag = SkatteberegningHelper.jordbruksfradrag();

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
    public void skrivConsole() {
        BelopUttrykk jordbruksfradrag = SkatteberegningHelper.jordbruksfradrag();

        final ConsoleUttrykkBeskriver beskriver = new ConsoleUttrykkBeskriver();

        String res = beskriver.beskriv(skattyterKontekst2.beskrivResultat(jordbruksfradrag));

        System.out.println(res);
    }

    @Test
    public void skrivExcel() throws IOException {
        BelopUttrykk jordbruksfradrag = SkatteberegningHelper.jordbruksfradrag();

        final ExcelUttrykkBeskriver beskriver = new ExcelUttrykkBeskriver();

        beskriver.beskriv(skattyterKontekst1.beskrivResultat(jordbruksfradrag));
        final Workbook wb = beskriver.beskriv(skattyterKontekst2.beskrivResultat(jordbruksfradrag));

        FileOutputStream out = new FileOutputStream("jordbruksfradrag.xlsx");
        wb.write(out);
        out.close();
    }


}
