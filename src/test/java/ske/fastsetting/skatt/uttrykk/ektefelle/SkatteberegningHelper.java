package ske.fastsetting.skatt.uttrykk.ektefelle;

import ske.fastsetting.skatt.uttrykk.Skattegrunnlag;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import static ske.fastsetting.skatt.uttrykk.UttrykkTest.SkattegrunnlagobjektUttrykk.skattegrunnlagobjekt;
import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.ektefelle.EktefelleUttrykk.ektefelles;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class SkatteberegningHelper {

    public static final BelopUttrykk MINSTE_JORDBRUKSFRADRAG = kr(63500).tags("sats").navn("Minste jordbruksfradrag");
    public static final BelopUttrykk MAKS_JORDBRUKSFRADRAG = kr(166400).tags("sats").navn("Maks jordbruksfradrag");
    public static final TallUttrykk JORDBRUKS_FRADRAG_SATS = prosent(38).tags("sats").navn("Sats jordbruksfradrag");

    static BelopUttrykk jordbruksfradrag() {

        BelopUttrykk jordbruksinntekt = skattegrunnlagobjekt(Skattegrunnlag.SKATTEGRUNNLAGOBJEKT_TYPE__INNTEKT_JORDBRUK).navn("Inntekt jordbruk").tags("skattegrunnlag");

        BelopUttrykk totalJordbruksinntekt = jordbruksinntekt.pluss(ektefelles(jordbruksinntekt));

        BelopUttrykk ubegrensetTotalfradrag =  MINSTE_JORDBRUKSFRADRAG
                .pluss(((totalJordbruksinntekt
                        .minus(MINSTE_JORDBRUKSFRADRAG)).multiplisertMed(JORDBRUKS_FRADRAG_SATS)));

        BelopUttrykk totalfradrag = begrens(ubegrensetTotalfradrag).nedad(kr(0)).oppad(MAKS_JORDBRUKSFRADRAG);

        TallUttrykk andelAvTotaltfradrag = jordbruksinntekt.dividertMed(totalJordbruksinntekt);

        return totalfradrag.multiplisertMed(andelAvTotaltfradrag).navn("Jordbruksfradrag");
    }
}
