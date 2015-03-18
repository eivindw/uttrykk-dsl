package ske.fastsetting.skatt.uttrykk.util;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IdUtilTest {

    @Test
    public void testIdLink() {
        assertEquals("<id>", IdUtil.idLink("id"));
    }

    @Test
    public void parseId() {
        final Set<String> ider = IdUtil.parseIder("test <id1> og <id2>.");

        assertEquals(2, ider.size());
        assertTrue(ider.contains("id1"));
        assertTrue(ider.contains("id2"));
    }

    @Test
    public void parseNullEllerTomStringGirEmptySet() {
        assertTrue(IdUtil.parseIder(null).isEmpty());
        assertTrue(IdUtil.parseIder("").isEmpty());
    }

    @Test
    public void droppeWhitespace() {
        final Set<String> ider = IdUtil.parseIder("test <id1> < <id2>.");

        assertEquals(2, ider.size());
        assertTrue("Mangler id1", ider.contains("id1"));
        assertTrue("Mangler id2", ider.contains("id2"));
    }

}