package me.loki2302.routing.advanced;

import me.loki2302.routing.RouteMatchResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Route {
    private final MethodMatcher methodMatcher;
    private final List<PartMatcher> partMatchers;

    public Route(MethodMatcher methodMatcher, List<PartMatcher> partMatchers) {
        this.methodMatcher = methodMatcher;
        this.partMatchers = partMatchers;
    }

    public RouteMatchResult match(RequestMethod requestMethod, String url) {
        if(!methodMatcher.match(requestMethod)) {
            return RouteMatchResult.noMatch(this);
        }

        if(url.startsWith("/")) {
            url = url.substring(1);
        }

        String[] segments = url.split("/");
        Map<String, Object> context = new HashMap<String, Object>();
        String[] remainingSegments = segments;
        int segmentsConsumed = 0;
        for(PartMatcher matcher : partMatchers) {
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

    public String build(Map<String, Object> context) {
        throw new RuntimeException("Not implemented");
    }
}
