package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

import java.util.HashSet;
import java.util.Set;

import static ske.fastsetting.skatt.domene.MultiBelop.kr0;

/**
 * Created by x00jen on 04.11.15.
 */
public class MulitBelopStorsteAvHverUttrykk<K>
    extends AbstractUttrykk<MultiBelop<K>,MulitBelopStorsteAvHverUttrykk<K>>
    implements MultiBelopUttrykk<K>{

    private final MultiBelopUttrykk<K> uttrykk1;
    private final MultiBelopUttrykk<K> uttrykk2;

    public MulitBelopStorsteAvHverUttrykk(MultiBelopUttrykk<K> uttrykk1, MultiBelopUttrykk<K> uttrykk2) {

        this.uttrykk1 = uttrykk1;
        this.uttrykk2 = uttrykk2;
    }

    public static <K> MulitBelopStorsteAvHverUttrykk<K> storsteAvHver(MultiBelopUttrykk<K> uttrykk1, MultiBelopUttrykk<K> uttrykk2) {
        return new MulitBelopStorsteAvHverUttrykk<>(uttrykk1, uttrykk2);
    }


    @Override
    public MultiBelop<K> eval(UttrykkContext ctx) {
        MultiBelop<K> mb1 = ctx.eval(uttrykk1);
        MultiBelop<K> mb2 = ctx.eval(uttrykk2);

        Set<K> noekler = new HashSet<>(mb1.steder());
        noekler.addAll(mb2.steder());

        return noekler.stream()
            .map(n->MultiBelop.kr(storsteAv(n, mb1,mb2),n))
            .reduce(kr0(), MultiBelop::pluss);
    }

    private Belop storsteAv(K nokkel, MultiBelop<K> mb1,MultiBelop<K> mb2) {
        if(mb1.harSted(nokkel) && mb2.harSted(nokkel)) {
            return mb1.get(nokkel).erStorreEnn(mb2.get(nokkel)) ? mb1.get(nokkel) : mb2.get(nokkel);
        } else if (mb1.harSted(nokkel)) {
            return mb1.get(nokkel);
        } else {
            return mb2.get(nokkel);
        }
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "største av hver i "+ctx.beskriv(uttrykk1)+" og "+ctx.beskriv(uttrykk2);
    }
}
