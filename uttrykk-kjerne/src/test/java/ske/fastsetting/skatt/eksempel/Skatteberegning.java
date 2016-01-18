package ske.fastsetting.skatt.eksempel;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

import ske.fastsetting.skatt.eksempel.beregning.SkattyterKontekst;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class Skatteberegning {

    static final TallUttrykk FELLESSKATT_SATS = prosent(33);

    static final BelopUttrykk lonnsinntekt =          kr(78_100);
    static final BelopUttrykk renteinntekt =             kr(270);
    static final BelopUttrykk renteutgift =            kr(3_800);
    static final BelopUttrykk fagforeningskontingent = kr(3_400);

    static final BelopUttrykk alminneligInntekt = lonnsinntekt
      .pluss(renteinntekt)
      .minus(renteutgift)
      .minus(fagforeningskontingent);

    static final BelopUttrykk fellesskatt =
      alminneligInntekt.multiplisertMed(FELLESSKATT_SATS);

    public static void main(String[] args) {
        SkattyterKontekst kontekst = SkattyterKontekst.ny();

        System.out.println(kontekst.verdiAv(fellesskatt));
        System.out.println(kontekst.verdiAv(alminneligInntekt));
        System.out.println(kontekst.verdiAv(fagforeningskontingent));
    }
}