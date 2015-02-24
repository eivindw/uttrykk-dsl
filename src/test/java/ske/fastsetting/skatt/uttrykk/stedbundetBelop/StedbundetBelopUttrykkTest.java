package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

/**
 * Created by jorn ola birkeland on 23.02.15.
 */
public class StedbundetBelopUttrykkTest {
    StedbundetBelopUttrykk b = kr(45,"Lørenskog").pluss(kr(45,"Asker"));
    StedbundetBelopUttrykk filtrert = b.filtrer(s->s.equals("Lørenskog"));

    StedbundetBelopUttrykk produkt = b.multiplisertMed(prosent(37));
    StedbundetBelopUttrykk div = b.dividertMed(prosent(37));

    StedbundetBelopUttrykk fordelt = b.fordelProporsjonalt(KroneUttrykk.kr(45));

    BelopUttrykk a = KroneUttrykk.kr(45).pluss(b.steduavhengig());
}
