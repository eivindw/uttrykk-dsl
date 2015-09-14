package ske.fastsetting.skatt.uttrykk;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.FeilUttrykk.feil;
import static ske.fastsetting.skatt.uttrykk.SkattegrunnlagHelper.kr;
import static ske.fastsetting.skatt.uttrykk.TestUttrykkContext.beregne;
import static ske.fastsetting.skatt.uttrykk.TestUttrykkContext.beregneOgBeskrive;
import static ske.fastsetting.skatt.uttrykk.TestUttrykkContext.beskrive;
import static ske.fastsetting.skatt.uttrykk.SkattegrunnlagobjektUttrykk.skattegrunnlagobjekt;
import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver.print;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public class UttrykkTest {

    @Test
    public void tallUttrykk() {
        Uttrykk<Belop> en = kr(1);

        assertEquals(Belop.kr(1), en.eval(null));
    }

    @Test
    public void prosentUttrykk() {
        final BelopUttrykk ti = kr(100).multiplisertMed(prosent(10)).navn("asdad");
        final BelopUttrykk tjue = sum(ti, ti, skattegrunnlagobjekt("2.1.1")).navn("asdasd");

        Skattegrunnlag sg = new Skattegrunnlag();

        assertEquals(Belop.kr(65), beregne(tjue, sg).verdi());

        print(beregne(tjue, sg));
        System.out.println("\n###");
        print(beskrive(tjue, sg));
        System.out.println("\n###");
        print(beregneOgBeskrive(tjue, sg));
    }


    @Test
    public void sumUttrykk() {
        final BelopUttrykk lonn = kr(6);
        final BelopUttrykk sum = sum(
          kr(2),
          sum(
            lonn,
            kr(2).navn("Test"),
            skattegrunnlagobjekt("4.3.3").navn("dasdad")
          ).regler(Regel.skatteloven("323")),
          kr(4),
          lonn.multiplisertMed(prosent(50))
        ).navn("Hallo");

        Skattegrunnlag sg = new Skattegrunnlag();
        UttrykkResultat ctx = beregneOgBeskrive(sum, sg);

        assertEquals(Belop.kr(62), ctx.verdi());

        print(ctx);
    }

    @Test(expected = RuntimeException.class)
    public void feilHvisNavnSettesIgjen() {
        kr(6).navn("test").navn("test igjen");
    }

    @Test
    public void testException() {
        BelopUttrykk a = hvis(kr(5).erMindreEnn(kr(4))).brukDa(kr(8)).ellersBruk(feil("En feil")).navn("a");
        BelopUttrykk b = sum(a, kr(4)).navn("b");
        BelopUttrykk c = b.multiplisertMed(SkattegrunnlagHelper.prosent(45)).navn("c");
        BelopUttrykk d = c.pluss(kr(45));
        BelopUttrykk e = d.dividertMed(prosent(12)).navn("e");

        try {
            beregne(e);
        } catch(Throwable t) {
            assertEquals("'e'->''->'c'->'b'->'a'->'En feil'",t.getMessage());
        }
    }

}
