package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.*;
import static ske.fastsetting.skatt.uttrykk.UttrykkTest.PostUttrykk.post;
import static ske.fastsetting.skatt.uttrykk.SkattegrunnlagHelper.kr;
import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver.print;

public class UttrykkTest {

    @Test
    public void tallUttrykk() {
        Uttrykk<Belop, Skattegrunnlag> en = kr(1);

        assertEquals(new Belop(1), en.eval(null));
    }

    @Test
    public void prosentUttrykk() {
        final BelopUttrykk<Skattegrunnlag> ti = kr(100).multiplisertMed(prosent(10)).navn("asdad");
        final BelopUttrykk<Skattegrunnlag> tjue = sum(ti, ti, post("2.1.1")).navn("asdasd");

        Skattegrunnlag sg = new Skattegrunnlag();

        assertEquals(new Belop(65), beregne(tjue, sg).verdi());

        print(beregne(tjue, sg));
        System.out.println("\n###");
        print(beskrive(tjue, sg));
        System.out.println("\n###");
        print(beregneOgBeskrive(tjue, sg));
    }

    @Test
    public void sumUttrykk() {
        final BelopUttrykk<Skattegrunnlag> lonn = kr(6);
        final BelopUttrykk<Skattegrunnlag> sum = sum(
            kr(2),
            sum(
                lonn,
                kr(2).navn("Test"),
                post("4.3.3").navn("dasdad")
            ).regler(Regel.skatteloven("323", "2323")),
            kr(4),
            lonn.multiplisertMed(prosent(50))
        ).navn("Hallo");

        Skattegrunnlag sg = new Skattegrunnlag();
        UttrykkResultat ctx = beregneOgBeskrive(sum, sg);

        assertEquals(new Belop(62), ctx.verdi());

        print(ctx);
    }

    public static class PostUttrykk extends AbstractUttrykk<Belop, PostUttrykk, Skattegrunnlag> implements BelopUttrykk<Skattegrunnlag> {

        private String postnummer;

        public PostUttrykk(String postnr) {
            this.postnummer = postnr;
        }

        public static PostUttrykk post(String postnummer) {
            return new PostUttrykk(postnummer);
        }

        @Override
        public Belop eval(UttrykkContext<Skattegrunnlag> ctx) {
            return ctx.input().getPostBelop(postnummer);
        }

        @Override
        public String beskriv(UttrykkContext<Skattegrunnlag> ctx) {
            return "Post " + postnummer;
        }
    }
}
