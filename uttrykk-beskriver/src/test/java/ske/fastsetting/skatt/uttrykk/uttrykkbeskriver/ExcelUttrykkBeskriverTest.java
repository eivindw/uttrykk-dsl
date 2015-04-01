package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;


import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel.ExcelFormel;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel.ExcelUttrykkBeskriver;

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

        Assert.assertEquals("if(A,B)", ExcelFormel.HvisParser.parse("hvis A bruk da B"));
        Assert.assertEquals("if(A,B,C)", ExcelFormel.HvisParser.parse("hvis A bruk da B ellers bruk C"));
        Assert.assertEquals("if(A,B,if(C,D))", ExcelFormel.HvisParser.parse("hvis A bruk da B ellers hvis C bruk da "
          + "D"));
        Assert.assertEquals("if(A,B,if(C,D,E))", ExcelFormel.HvisParser.parse("hvis A bruk da B ellers hvis C bruk da"
          + " D ellers bruk E"));
        Assert.assertEquals("if(A,B,if(C,D,if(E,F)))", ExcelFormel.HvisParser.parse("hvis A bruk da B ellers hvis C "
          + "bruk da D ellers hvis E bruk da F"));
    }

}

