package me.loki2302.routing.advanced;

import java.util.HashMap;
import java.util.Map;

public class VariablePartMatcher implements PartMatcher {
    private final String name;

    public VariablePartMatcher(String name) {
        this.name = name;
    }

    @Override
    public PartMatchResult match(String[] segments) {
        if(segments.length < 1) {
            return PartMatchResult.noMatch();
        }

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(name, segments[0]);

        return PartMatchResult.match(1, context);
    }
}
