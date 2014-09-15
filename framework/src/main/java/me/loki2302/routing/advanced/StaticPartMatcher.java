package me.loki2302.routing.advanced;

import java.util.HashMap;

public class StaticPartMatcher implements PartMatcher {
    private final String segmentTemplate;

    public StaticPartMatcher(String segmentTemplate) {
        this.segmentTemplate = segmentTemplate;
    }

    @Override
    public PartMatchResult match(String[] segments) {
        if(segments.length < 1) {
            return PartMatchResult.noMatch();
        }

        String firstSegment = segments[0];
        if(!firstSegment.equals(segmentTemplate)) {
            return PartMatchResult.noMatch();
        }

        HashMap<String, Object> context = new HashMap<String, Object>();
        return PartMatchResult.match(1, context);
    }
}
