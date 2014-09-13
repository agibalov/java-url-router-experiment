package me.loki2302.framework.routing.advanced;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StaticPartMatcherTest {
    @Test
    public void canMatch() {
        StaticPartMatcher m = new StaticPartMatcher("hello");
        PartMatchResult result = m.match(new String[]{ "hello" });
        assertTrue(result.match);
        assertEquals(1, result.segmentsConsumed);
        assertTrue(result.context.isEmpty());
    }

    @Test
    public void canNotMatch() {
        StaticPartMatcher m = new StaticPartMatcher("hello");
        PartMatchResult result = m.match(new String[]{ "bye" });
        assertFalse(result.match);
    }
}
