package ske.fastsetting.skatt.uttrykk.skalSlettes;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.skalSlettes.FinnmarkBeregning.erFinnmarkberegning;
import static ske.fastsetting.skatt.uttrykk.skalSlettes.FinnmarkBeregning.finnmarkberegningAv;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

/**
 * Created by jorn ola birkeland on 30.06.15.
 */
public class FinmmarkberegningTest {
    @Test
    public void test() {
        BelopUttrykk grunnlagInntektsskatt = kr(45_000);

        BelopUttrykk fellesskatt = hvis(erFinnmarkberegning())
                .brukDa(grunnlagInntektsskatt.multiplisertMed(prosent(8.2)))
                .ellersBruk(grunnlagInntektsskatt.multiplisertMed(prosent(13.1)));


        System.out.println(TestUttrykkContext.beregne(finnmarkberegningAv(fellesskatt)).verdi());
        System.out.println(TestUttrykkContext.beregne(fellesskatt).verdi());
    }
}
