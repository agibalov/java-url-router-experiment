package me.loki2302.routing.advanced;

import java.util.Map;

public class PartMatchResult {
    public boolean match;
    public int segmentsConsumed;
    public Map<String, Object> context;

    public static PartMatchResult noMatch() {
        PartMatchResult result = new PartMatchResult();
        result.match = false;
        return result;
    }

    public static PartMatchResult match(int segmentsConsumed, Map<String, Object> context) {
        PartMatchResult result = new PartMatchResult();
        result.match = true;
        result.segmentsConsumed = segmentsConsumed;
        result.context = context;
        return result;
    }
}
