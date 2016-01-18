package ske.fastsetting.skatt.uttrykk.multitall;

import ske.fastsetting.skatt.domene.MultiTall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class MultiTallKonstantUttrykk<T> extends AbstractUttrykk<MultiTall<T>,MultiTallKonstantUttrykk<T>> implements MultiTallUttrykk<T> {

    private final MultiTall<T> multitall;

    public MultiTallKonstantUttrykk(MultiTall<T> multiTall) {
        this.multitall = multiTall;
    }

    public static <T> MultiTallKonstantUttrykk<T> heltall(int tall, T nokkel) {
        return new MultiTallKonstantUttrykk<>(MultiTall.heltall(tall, nokkel));
    }

    @Override
    public MultiTall<T> eval(UttrykkContext ctx) {
        return multitall;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return multitall.toString();
    }
}
