package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Ignore;
import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.confluence.ConfluenceResultatKonverterer;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.confluence.ConfluenceUttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel.ExcelUttrykkBeskriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static ske.fastsetting.skatt.uttrykk.belop.BelopMultisatsFunksjon.multisatsFunksjonAv;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class BelopMultisatsFunksjonTest {

    @Test
    @Ignore
    public void excelSkriveTest() throws IOException {
        final KroneUttrykk kr = kr(20).navn("grunnlag");
        BelopUttrykk multisats = multisatsFunksjonAv(kr).medSats(prosent(10), kr(50)).medSats(prosent(20), kr(100))
          .medSats(prosent(7)).navn("multisats");
        ExcelUttrykkBeskriver beskriver = new ExcelUttrykkBeskriver();

        Workbook wb = beskriver.beskriv(TestUttrykkContext.beskrive(multisats));

        FileOutputStream out = new FileOutputStream("workbook.xlsx");
        wb.write(out);
        out.close();
    }


    @Test
    @Ignore
    public void confluenceSkriveTest() throws IOException {
        BelopUttrykk multisats = multisatsFunksjonAv(kr(200)).medSats(prosent(10), kr(50)).medSats(prosent(20), kr
          (100)).medSats(prosent(7)).navn("multisats");

        ConfluenceUttrykkBeskriver beskriver = new ConfluenceUttrykkBeskriver("Tittel");

        UttrykkResultat<Belop> resultat = TestUttrykkContext.beskrive(multisats);

        UttrykkResultat<Belop> endretResultat = ConfluenceResultatKonverterer.konverterResultat(resultat);

        Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> sider = beskriver.beskriv(endretResultat);

        sider.entrySet().stream().map(e -> e.getValue()).map(ConfluenceUttrykkBeskriver.ConfluenceSide::getInnhold)
          .forEach(System.out::println);
    }

//    String s = "multisats(satsTil(kr(200),%(5),kr(50)),satsFraTil(kr(200),%(20),kr(50),kr(100)),satsFra(kr(200),%
// (7),kr(100)))";
//    String s4 = "multisats(satsTil(kr(200);%(5);kr(50));satsFraTil(kr(200);%(20);kr(50);kr(100));satsFra(kr(200),%
// (7),kr(100)))";
//    String s2 = "begrensNedreØvre(kr(200),kr(0),kr(500))";
//    String s3 = "hvisEllers(>(a;b);kr(50);hvis(>=(c;a);))";
//
//    String wiki = "5% av 200 kr inntil kr 50, 20% av 200 kr over kr 50 inntil kr 100, 7% av 200 kr over kr 100";
//    String wiki2 = "5% av grunnlag inntil kr 50, 20% av grunnlag over kr 50 inntil kr 100, 7% av grunnlag over kr
// 100";
//    String excel = "=((MAX(((200 - 100) * 0,07);0)) + (((MIN(MAX(((200 - 50) * 0,2);0);((100 - 50) * 0,2))) + ((MIN
// (MAX(((200 - 0) * 0,1);0);((50 - 0) * 0,1)))))))";


}
