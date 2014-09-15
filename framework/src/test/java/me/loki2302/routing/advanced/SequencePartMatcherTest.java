package me.loki2302.routing.advanced;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SequencePartMatcherTest {
    @Test
    public void canMatch() {
        SequencePartMatcher m = new SequencePartMatcher(new PartMatcher[] {
                new StaticPartMatcher("hello"),
                new StaticPartMatcher("there")
        });
        PartMatchResult result = m.match(new String[] { "hello", "there" });
        assertTrue(result.match);
        assertEquals(2, result.segmentsConsumed);
        assertTrue(result.context.isEmpty());
    }

    @Test
    public void canNotMatch() {
        SequencePartMatcher m = new SequencePartMatcher(new PartMatcher[] {
                new StaticPartMatcher("hello"),
                new StaticPartMatcher("there")
        });
        PartMatchResult result = m.match(new String[] { "hello", "bye" });
        assertFalse(result.match);
    }
}
