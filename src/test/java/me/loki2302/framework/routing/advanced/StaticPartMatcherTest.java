package me.loki2302.framework.routing.advanced;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StaticPartMatcherTest {
    @Test
    public void canMatchSingleSegment() {
        StaticPartMatcher m = new StaticPartMatcher(new String[]{ "hello" });
        PartMatchResult result = m.match(new String[]{ "hello" });
        assertTrue(result.match);
        assertEquals(1, result.segmentsConsumed);
    }

    @Test
    public void canMatchMultipleSegments() {
        StaticPartMatcher m = new StaticPartMatcher(new String[]{ "hello", "there" });
        PartMatchResult result = m.match(new String[]{ "hello", "there" });
        assertTrue(result.match);
        assertEquals(2, result.segmentsConsumed);
    }

    @Test
    public void noMatchWhenSingleSegmentDoesNotMatch() {
        StaticPartMatcher m = new StaticPartMatcher(new String[]{ "hello" });
        PartMatchResult result = m.match(new String[]{ "bye" });
        assertFalse(result.match);
    }

    @Test
    public void noMatchWhenLastSegmentDoesNotMatch() {
        StaticPartMatcher m = new StaticPartMatcher(new String[]{ "hello", "there" });
        PartMatchResult result = m.match(new String[]{ "hello", "ther" });
        assertFalse(result.match);
    }
}
