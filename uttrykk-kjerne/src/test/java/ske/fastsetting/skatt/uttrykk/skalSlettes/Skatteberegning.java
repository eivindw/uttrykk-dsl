package ske.fastsetting.skatt.uttrykk.skalSlettes;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class Skatteberegning {

    static final TallUttrykk FELLESSKATT_SATS = prosent(25);
    static final TallUttrykk KOMMUNESKATT_SATS = prosent(8);

    static final MultiBelopUttrykk<String> lonnsinntekt = kr(78_100, "Asker");
    static final MultiBelopUttrykk<String> renteinntekt = kr(270, "Asker");
    static final MultiBelopUttrykk<String> naeringsinntekt = kr(56_000, "Tønsberg");

    static final BelopUttrykk renteutgift = kr(3_800);
    static final BelopUttrykk fagforeningskontingent = kr(3_400);

    static final MultiBelopUttrykk<String> inntekt =
            lonnsinntekt
                    .pluss(renteinntekt)
                    .pluss(naeringsinntekt);

    static final BelopUttrykk fordelingsfradrag =
            renteutgift
                    .pluss(fagforeningskontingent);

    static final MultiBelopUttrykk<String> alminneligInntekt =
            inntekt.minusProporsjonalt(fordelingsfradrag);

    static final BelopUttrykk fellesskatt =
            alminneligInntekt.tilBelop().multiplisertMed(FELLESSKATT_SATS);

    static final MultiBelopUttrykk<String> kommuneskatt =
            alminneligInntekt.multiplisertMed(KOMMUNESKATT_SATS);

    public static void main(String[] args) {

        SkattyterKontekst kontekst = SkattyterKontekst.ny();

        System.out.println(kontekst.verdiAv(fellesskatt));
        System.out.println(kontekst.verdiAv(kommuneskatt));
    }
}
