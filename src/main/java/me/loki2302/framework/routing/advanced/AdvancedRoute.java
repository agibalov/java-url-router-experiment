package me.loki2302.framework.routing.advanced;

import me.loki2302.framework.routing.Route;
import me.loki2302.framework.routing.RouteMatchResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdvancedRoute implements Route {
    private final PartMatcher[] matchers;

    public AdvancedRoute(PartMatcher... matchers) {
        this.matchers = matchers;
    }

    @Override
    public RouteMatchResult match(String url) {
        if(url.startsWith("/")) {
            url = url.substring(1);
        }

        String[] segments = url.split("/");
        Map<String, Object> context = new HashMap<String, Object>();
        String[] remainingSegments = segments;
        int segmentsConsumed = 0;
        for(PartMatcher matcher : matchers) {
            PartMatchResult result = matcher.match(remainingSegments);
            if (!result.match) {
                return RouteMatchResult.noMatch(this);
            }

            context.putAll(result.context);
            remainingSegments = Arrays.copyOfRange(
                    remainingSegments,
                    result.segmentsConsumed,
                    remainingSegments.length);

            segmentsConsumed += result.segmentsConsumed;
        }

        if(segmentsConsumed != segments.length) {
            return RouteMatchResult.noMatch(this);
        }

        return RouteMatchResult.match(this, context);
    }

    @Override
    public String build(Map<String, Object> context) {
        throw new RuntimeException("Not implemented");
    }
}
