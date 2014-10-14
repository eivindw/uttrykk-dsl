package ske.fastsetting.skatt.beregn;

import org.junit.Test;
import ske.fastsetting.skatt.beregn.util.IdUtil;
import ske.fastsetting.skatt.domene.Belop;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.beregn.HvisUttrykk.hvis;
import static ske.fastsetting.skatt.beregn.KrUttrykk.kr;
import static ske.fastsetting.skatt.beregn.MultiplikasjonsUttrykk.mult;
import static ske.fastsetting.skatt.beregn.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.beregn.SumUttrykk.sum;
import static ske.fastsetting.skatt.beregn.UttrykkContextImpl.*;

public class UttrykkTest {

    @Test
    public void tallUttrykk() {
        Uttrykk<Belop> en = kr(1);

        assertEquals(new Belop(1), en.eval(null));
    }

    @Test
    public void prosentUttrykk() {
        MultiplikasjonsUttrykk ti = mult(kr(100), prosent(10));
        SumUttrykk tjue = sum(ti, ti);

        assertEquals(new Belop(20), beregne(tjue).verdi());

        print(beregne(tjue));
        System.out.println("\n###");
        print(beskrive(tjue));
        System.out.println("\n###");
        print(beregneOgBeskrive(tjue));
    }

    @Test
    public void boolskUttrykk() {
        Uttrykk svar = hvis(new BoolskUttrykk(true))
            .saa(kr(10))
            .ellers(kr(20));

        assertEquals(new Belop(10), beregne(svar).verdi());
    }

    @Test
    public void sumUttrykk() {
        Uttrykk<Belop> lonn = kr(6).navn("lønn");
        Uttrykk sum = sum(
            kr(2).navn("utbytte"),
            sum(
                lonn,
                kr(2).navn("bonus")
            ).navn("inntekt"),
            kr(4).navn("renter"),
            mult(
                lonn,
                prosent(50).navn("superprosent")
            ).navn("superskatt")
        ).navn("skatt");

        UttrykkResultat ctx = beregneOgBeskrive(sum);

        assertEquals(new Belop(17), ctx.verdi());

        print(beregneOgBeskrive(sum));
    }

    private void print(UttrykkResultat<?> resultat) {
        print(resultat.start(), resultat, new HashSet<>());
    }

    private void print(String id, UttrykkResultat<?> resultat, Set<String> behandledeIder) {
        Map map = resultat.uttrykk().get(id);
        Set<String> subIder = null;
        StringBuilder output = new StringBuilder(IdUtil.idLink(id));

        if (map.containsKey(UttrykkResultat.KEY_NAVN)) {
            output.append(", navn=").append(map.get(UttrykkResultat.KEY_NAVN));
        }

        if (map.containsKey(UttrykkResultat.KEY_VERDI)) {
            output.append(", verdi=").append(map.get(UttrykkResultat.KEY_VERDI));
        }

        if (map.containsKey(UttrykkResultat.KEY_UTTRYKK)) {
            String uttrykk = (String) map.get(UttrykkResultat.KEY_UTTRYKK);
            output.append(", uttrykk=").append(uttrykk);
            subIder = IdUtil.parseIder(uttrykk);
            subIder.removeAll(behandledeIder);
        }

        System.out.println(output.toString());

        if(subIder != null) {
            behandledeIder.addAll(subIder);
            subIder.forEach(subId -> print(subId, resultat, behandledeIder));
        }
    }
}
