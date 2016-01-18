package ske.fastsetting.skatt.domene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ske.fastsetting.skatt.domene.MultiBelop.kr;
import static ske.fastsetting.skatt.domene.MultiBelop.kr0;

import org.junit.Test;

public class MultiBelopTest {
    @Test
    public void tommeBelopSkalVaereLike() {
        assertTrue(kr0().equals(kr0()));
    }

    @Test
    public void toLikeBeloMedSammeStedSkalVaereLike() {
        MultiBelop<String> belop1 = kr(45, "Oslo");
        MultiBelop<String> belop2 = kr(45, "Oslo");

        assertEquals(belop1.hashCode(),belop2.hashCode());
        assertTrue(belop1.equals(belop2));
    }

    @Test
    public void toUlikeBeloMedSammeStedSkalVaereUlike() {
        assertFalse(kr(44, "Oslo").equals(kr(45, "Oslo")));
    }

    @Test
    public void toLikeBeloMedForskjelligStedSkalVaereUlike() {
        assertFalse(kr(45, "Mysen").equals(kr(45, "Oslo")));
    }

    @Test
    public void toSummerteBeloMedSammeStedSkalVaereLike1() {
        MultiBelop<String> belop1 = kr(45, "Mysen").pluss(kr(55, "Mysen"));
        MultiBelop<String> belop2 = kr(11, "Mysen").pluss(kr(89, "Mysen"));

        assertEquals(belop1,belop2);
    }

    @Test
    public void toSummerteBeloMedSammeStedSkalVaereLike2() {
        MultiBelop<String> belop1 = kr(45, "Mysen").pluss(kr(55, "Oslo"));
        MultiBelop<String> belop2 = kr(55, "Oslo").pluss(kr(45, "Mysen"));

        assertEquals(belop1,belop2);
    }

}
