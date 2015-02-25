package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

/**
 * Created by jorn ola birkeland on 23.02.15.
 */
public class StedbundetKroneUttrykk extends AbstractUttrykk<StedbundetBelop,StedbundetKroneUttrykk> implements StedbundetBelopUttrykk
{

    private final Belop belop;
    private final String sted;

    private StedbundetKroneUttrykk(Belop belop, String sted) {

        this.belop = belop;
        this.sted = sted;
    }

    public static StedbundetKroneUttrykk kr(int belop, String sted) {
        return new StedbundetKroneUttrykk(Belop.kr(belop),sted);
    }

    @Override
    public StedbundetBelop eval(UttrykkContext ctx) {
        return new StedbundetBelop(sted, belop);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return belop.toString() + " ("+sted+")";
    }
}
