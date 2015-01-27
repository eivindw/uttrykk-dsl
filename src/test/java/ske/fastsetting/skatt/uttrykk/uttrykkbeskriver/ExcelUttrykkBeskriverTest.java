package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;


import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.BasisTest;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel.ExcelUttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel.ExcelUttrykkKonverterer;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jorn ola birkeland on 10.01.15.
 */
public class ExcelUttrykkBeskriverTest {

    @Test
    public void testSkrivUt() throws IOException {

        final ExcelUttrykkBeskriver beskriver = new ExcelUttrykkBeskriver();
        final UttrykkResultat<Belop> resultat = BasisTest.lagEnkeltUttrykkResultat();

        final Workbook wb = beskriver.beskriv(resultat);

        FileOutputStream out = new FileOutputStream("workbook.xlsx");
        wb.write(out);
        out.close();
    }

    @Test
    public void testHvisParser() {

        Assert.assertEquals("if(A,B)", ExcelUttrykkKonverterer.HvisParser.parse("hvis A bruk da B"));
        Assert.assertEquals("if(A,B,C)", ExcelUttrykkKonverterer.HvisParser.parse("hvis A bruk da B ellers bruk C"));
        Assert.assertEquals("if(A,B,if(C,D))", ExcelUttrykkKonverterer.HvisParser.parse("hvis A bruk da B ellers hvis C bruk da D"));
        Assert.assertEquals("if(A,B,if(C,D,E))", ExcelUttrykkKonverterer.HvisParser.parse("hvis A bruk da B ellers hvis C bruk da D ellers bruk E"));
        Assert.assertEquals("if(A,B,if(C,D,if(E,F)))", ExcelUttrykkKonverterer.HvisParser.parse("hvis A bruk da B ellers hvis C bruk da D ellers hvis E bruk da F"));
    }

}

