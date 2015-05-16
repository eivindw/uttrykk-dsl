package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

/**
 * Created by jorn ola birkeland on 16.05.15.
 */
public class UttrykkContextTest {

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneHaInputAvUlikType() {
        TestUttrykkContext.beregne(kr(5),"1",3,new A());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneHaInputAvSammeType() {
        TestUttrykkContext.beregne(kr(5),"1","2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneHaInputSomImplementererSammeInterface() {
        TestUttrykkContext.beregne(kr(5),new A(),new B());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneHaInputDerEnErEnSubklasseAvDenAndre() {
        TestUttrykkContext.beregne(kr(5),new A(),new C());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneHaInputDerEnImplementerEtInterfaceSomArverFraEtInterfaceAlleredeBrukt() {
        TestUttrykkContext.beregne(kr(5),new A(),new D());
    }

    static interface I {}
    static interface J extends I {}
    static class A implements I {}
    static class B implements I {}
    static class C extends A {}
    static class D implements J {}
}
