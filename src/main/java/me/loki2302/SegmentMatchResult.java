package me.loki2302;

import java.util.HashMap;
import java.util.Map;

public class SegmentMatchResult {
    public boolean match;
    public RouteSegment segment;
    public Map<String, Object> context;

    public static SegmentMatchResult noMatch(RouteSegment segment) {
        SegmentMatchResult result = new SegmentMatchResult();
        result.match = false;
        result.segment = segment;
        return result;
    }

    public static SegmentMatchResult matchNoContext(RouteSegment segment) {
        SegmentMatchResult result = new SegmentMatchResult();
        result.match = true;
        result.segment = segment;
        result.context = new HashMap<String, Object>();
        return result;
    }

    public static SegmentMatchResult matchSingletonContext(RouteSegment segment, String name, Object value) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(name, value);

        SegmentMatchResult result = new SegmentMatchResult();
        result.match = true;
        result.segment = segment;
        result.context = context;

        return result;
    }
}
