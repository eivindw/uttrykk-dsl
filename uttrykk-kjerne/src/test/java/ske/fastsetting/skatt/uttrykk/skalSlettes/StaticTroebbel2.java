package ske.fastsetting.skatt.uttrykk.skalSlettes;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public class StaticTroebbel2 {

    public static BelopUttrykk ut1 = kr(45);
    public static BelopUttrykk ut3 = ut1.pluss(StaticTroebbel1.ut2);
}
