package ske.fastsetting.skatt.uttrykk.distanse;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.BelopPerKvantitet;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopPerKvantitetUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jorn ola birkeland on 19.03.15.
 */
public interface DistanseUttrykk extends CompareableUttrykk<Distanse> {

    default DistanseSumUttrykk pluss(DistanseUttrykk ledd) {
        return DistanseSumUttrykk.sum(this, ledd);
    }

    default BelopUttrykk multiplisertMed(BelopPerKvantitetUttrykk<Distanse> belopPerDistanse) {
        return new DistanseMultiplisertMedBelopPerDistanseUttrykk(this, belopPerDistanse);
    }

    public class DistanseSumUttrykk extends SumUttrykk<Distanse, DistanseUttrykk, DistanseSumUttrykk> implements
      DistanseUttrykk {

        @SafeVarargs
        public static DistanseSumUttrykk sum(DistanseUttrykk... uttrykk) {
            return new DistanseSumUttrykk(Stream.of(uttrykk).collect(Collectors.toList()));
        }

        protected DistanseSumUttrykk(Collection<DistanseUttrykk> uttrykk) {
            super(uttrykk);
        }

        @Override
        protected Distanse nullVerdi() {
            return new Distanse(0d);
        }
    }

    class DistanseMultiplisertMedBelopPerDistanseUttrykk extends AbstractUttrykk<Belop,
      DistanseMultiplisertMedBelopPerDistanseUttrykk> implements BelopUttrykk {
        private final DistanseUttrykk distanseUttrykk;
        private final BelopPerKvantitetUttrykk<Distanse> belopPerDistanseUttrykk;

        public DistanseMultiplisertMedBelopPerDistanseUttrykk(DistanseUttrykk distanseUttrykk,
          BelopPerKvantitetUttrykk<Distanse> belopPerDistanseUttrykk) {
            this.distanseUttrykk = distanseUttrykk;
            this.belopPerDistanseUttrykk = belopPerDistanseUttrykk;
        }

        @Override
        public Belop eval(UttrykkContext ctx) {

            Distanse distanse = ctx.eval(distanseUttrykk);
            BelopPerKvantitet<Distanse> belopPerDistanse = ctx.eval(belopPerDistanseUttrykk);

            return belopPerDistanse.multiplisertMed(distanse);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return null;
        }
    }
}
