package me.loki2302.routing.advanced;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SequencePartMatcher implements PartMatcher {
    private final PartMatcher[] matchers;

    public SequencePartMatcher(PartMatcher[] matchers) {
        this.matchers = matchers;
    }

    @Override
    public PartMatchResult match(String[] segments) {
        Map<String, Object> context = new HashMap<String, Object>();

        String[] remainingSegments = segments;
        int segmentsConsumed = 0;
        for(PartMatcher matcher : matchers) {
            PartMatchResult result = matcher.match(remainingSegments);
            if (!result.match) {
                return PartMatchResult.noMatch();
            }

            context.putAll(result.context);
            remainingSegments = Arrays.copyOfRange(
                    remainingSegments,
                    result.segmentsConsumed,
                    remainingSegments.length);

            segmentsConsumed += result.segmentsConsumed;
        }

        return PartMatchResult.match(segmentsConsumed, context);
    }
}
