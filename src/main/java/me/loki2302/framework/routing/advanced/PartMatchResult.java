package me.loki2302.framework.routing.advanced;

public class PartMatchResult {
    public boolean match;
    public int segmentsConsumed;

    public static PartMatchResult noMatch() {
        PartMatchResult result = new PartMatchResult();
        result.match = false;
        return result;
    }

    public static PartMatchResult match(int segmentsConsumed) {
        PartMatchResult result = new PartMatchResult();
        result.match = true;
        result.segmentsConsumed = segmentsConsumed;
        return result;
    }
}
