package me.loki2302;

import java.util.HashMap;
import java.util.Map;

public class SegmentMatchResult {
    public boolean match;
    public Map<String, Object> context;

    public static SegmentMatchResult noMatch() {
        SegmentMatchResult result = new SegmentMatchResult();
        result.match = false;
        return result;
    }

    public static SegmentMatchResult matchNoContext() {
        SegmentMatchResult result = new SegmentMatchResult();
        result.match = true;
        result.context = new HashMap<String, Object>();
        return result;
    }

    public static SegmentMatchResult matchSingletonContext(String name, Object value) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(name, value);

        SegmentMatchResult result = new SegmentMatchResult();
        result.match = true;
        result.context = context;

        return result;
    }
}
