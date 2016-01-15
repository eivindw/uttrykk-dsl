package ske.fastsetting.skatt.uttrykk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UttrykkContextTest {

    @Test
    public void skalLeggeTilSupertyper() {
        UttrykkContext uc = TestUttrykkContext.ny(new C(11));

        assertTrue(uc.harInput(C.class));
        assertTrue(uc.harInput(A.class));
        assertTrue(uc.harInput(I.class));
        assertEquals(11,uc.hentInput(I.class).verdi());
    }


    @Test
    public void skalKunneHaInputAvUlikTypeSomIkkeDelerSuperklasserEllerInterfacer() {
        TestUttrykkContext.ny(new A(), new O());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneHaInputAvSammeType() {
        TestUttrykkContext.ny("1", "2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneHaInputSomImplementererSammeInterface() {
        TestUttrykkContext.ny(new A(), new B());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneHaInputDerEnErEnSubklasseAvDenAndre() {
        TestUttrykkContext.ny(new A(), new C());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneHaInputDerEnImplementerEtInterfaceSomArverFraEtInterfaceAlleredeBrukt() {
        TestUttrykkContext.ny(new A(), new D());
    }

    @Test
    public void skaFjerneInputOgSupertyper() {
        C c = new C();
        UttrykkContext uc = TestUttrykkContext.ny(c);

        uc.fjernInput(c);

        assertFalse(uc.harInput(C.class));
        assertFalse(uc.harInput(A.class));
        assertFalse(uc.harInput(I.class));
    }

    @Test
    public void skaFjerneIndirekte() {

        UttrykkContext uc = TestUttrykkContext.ny(new C());
        I input = uc.hentInput(I.class);
        uc.fjernInput(input);

        assertFalse(uc.harInput(C.class));
        assertFalse(uc.harInput(A.class));
        assertFalse(uc.harInput(I.class));
    }


    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneFjerneTypeSomIkkeFinnes() {
        UttrykkContext uc = TestUttrykkContext.ny(new C());
        uc.fjernInput(new O());
    }

    @Test
    public void skalKunneOverstyreInputDirekte() {
        UttrykkContext uc = TestUttrykkContext.ny(new C());
        uc.settInput(new C());

        assertTrue(uc.harInput(C.class));
        assertTrue(uc.harInput(A.class));
        assertTrue(uc.harInput(I.class));
    }

    @Test
    public void skalKunneOverstyreInputIndirekte() {
        I input1 = new C(5);
        A input2 = new C(6);

        UttrykkContext uc = TestUttrykkContext.ny(input1);
        uc.settInput(input2);

        assertEquals(6, uc.hentInput(C.class).verdi());
        assertEquals(6, uc.hentInput(A.class).verdi());
        assertEquals(6,uc.hentInput(I.class).verdi());
    }


    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneOverstyreInputFraSupertype() {
        UttrykkContext uc = TestUttrykkContext.ny(new C());
        uc.settInput(new A()); // Supertype av C
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalIkkeKunneOverstyreInputSomDelerSupertype() {
        UttrykkContext uc = TestUttrykkContext.ny(new A());
        uc.settInput(new B()); // Implementer I, som også A gjør
    }


    static interface I { int verdi();}
    static interface J extends I {}
    static class O {};

    static class A implements I {
        private final int verdi;
        A() { this.verdi = 1; }
        A(int verdi) { this.verdi = verdi; }
        public int verdi() { return verdi; }
    }
    static class B implements I {
        private final int verdi;
        B() { this.verdi = 2; }
        public int verdi() { return verdi; }
    }

    static class C extends A {
        public C() {super(3);}
        public C(int v) {super(v);}
    }

    static class D implements J {
        public int verdi() { return 4; }
    }
}
