package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;

public class BasisTest {

//    @Test
//    public void prosentUttrykk() {
//        final ProsentUttrykk<String> satsTrygdeavgift = prosent(8.2).navn("sdadad");
//
//        final BelopUttrykk<?,String> sumLonn = sum(
//                kr(60_000).navn("Lønn"),
//                kr(40_000).navn("Bonus")
//        ).navn("Sum lønn");
//
//        final BelopUttrykk<?,String> trygdeavgift = sumLonn.multiplisertMed(satsTrygdeavgift);
//
//        final UttrykkResultat<Belop> resultat = UttrykkContextImpl.beregneOgBeskrive(trygdeavgift);
//
//        assertEquals(new Belop(8_200), resultat.verdi());
//
//        ConsoleUttrykkBeskriver.print(resultat);
//    }

    public static class StringHelper {
        public static <String> ProsentUttrykk<String> prosent(double prosent) {
            return new ProsentUttrykk<String>(Tall.prosent(prosent));
        }

    }
}
