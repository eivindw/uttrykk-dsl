package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.BasisTest;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

import java.io.FileInputStream;
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
    public void test() throws IOException {
        Workbook[] wbs = new Workbook[]{new XSSFWorkbook()};
        for (int i = 0; i < wbs.length; i++) {
            Workbook wb = wbs[i];
            CreationHelper createHelper = wb.getCreationHelper();

            // create a new sheet
            Sheet s = wb.createSheet("sheet");
            // declare a row object reference
            Row r = null;
            // declare a cell object reference
            Cell c = null;
            // create 2 cell styles
            CellStyle cs = wb.createCellStyle();
            CellStyle cs2 = wb.createCellStyle();
            DataFormat df = wb.createDataFormat();

            // create 2 fonts objects
            Font f = wb.createFont();
            Font f2 = wb.createFont();

            // Set font 1 to 12 point type, blue and bold
            f.setFontHeightInPoints((short) 12);
            f.setColor(IndexedColors.RED.getIndex());
            f.setBoldweight(Font.BOLDWEIGHT_BOLD);

            // Set font 2 to 10 point type, red and bold
            f2.setFontHeightInPoints((short) 10);
            f2.setColor(IndexedColors.RED.getIndex());
            f2.setBoldweight(Font.BOLDWEIGHT_BOLD);

            // Set cell style and formatting
            cs.setFont(f);
            cs.setDataFormat(df.getFormat("#,##0.0"));

            // Set the other cell style and formatting
            cs2.setBorderBottom(cs2.BORDER_THIN);
            cs2.setDataFormat(df.getFormat("text"));
            cs2.setFont(f2);


            // Define a few rows
            for (int rownum = 0; rownum < 30; rownum++) {
                r = s.createRow(rownum);
                for (int cellnum = 0; cellnum < 10; cellnum += 2) {

                    c = r.createCell(cellnum);



                    CellReference cellReference = new CellReference(s.getSheetName(), rownum, cellnum, true, true);
                    AreaReference areaReference = new AreaReference(cellReference, cellReference);

                    Name name = wb.createName();
                    name.setNameName("Jobi" + rownum + cellnum);

                    name.setRefersToFormula(areaReference.formatAsString());

                    Cell c2 = r.createCell(cellnum + 1);

                    c.setCellValue((double) rownum + (cellnum / 10));
                    c2.setCellFormula(name.getNameName() + "+2");
                }
            }

            s.createRow(0).createCell(10).setCellFormula("SUM(Jobi02,Jobi12)");
            s.createRow(1).createCell(10).setCellFormula("IF(Jobi02>Jobi12,Jobi24,Jobi46)");

            // Save
            String filename = "workbook.xlsx";

            FileOutputStream out = new FileOutputStream(filename);
            wb.write(out);
            out.close();
        }

    }

    @Test
    public void test2() throws IOException {
        String sname = "TestSheet", cname = "TestName", cvalue = "TestVal";
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(sname);
        sheet.createRow(0).createCell((short) 0).setCellValue(cvalue);


        // 1. create named range for a single cell using areareference
        Name namedCell = wb.createName();
        namedCell.setNameName(cname);
        String reference = sname + "!A1"; // area reference
        namedCell.setRefersToFormula(reference);

        // Save
        String filename = "workbook.xlsx";
        FileOutputStream out = new FileOutputStream(filename);
        wb.write(out);
        out.close();

    }

    @Test
    public void test3() throws IOException {
        String filename = "workbook.xlsx";

        FileInputStream in = new FileInputStream(filename);

        Workbook wb = new XSSFWorkbook(in);

        in.close();

        for (int i = 0; i < wb.getNumberOfNames(); i++) {
            Name name = wb.getNameAt(i);

            System.out.println(name.getRefersToFormula());
        }

    }

    @Test
    public void test4() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sh = wb.createSheet("Sheet1");

        //applies to the entire workbook
        XSSFName name1 = wb.createName();
        name1.setNameName("FMLA");
        name1.setRefersToFormula("Sheet1!$B$3");

        // Save
        String filename = "workbook.xlsx";
        FileOutputStream out = new FileOutputStream(filename);
        wb.write(out);
        out.close();


    }
}

