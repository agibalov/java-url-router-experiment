package me.loki2302.framework.routing.advanced;

import java.util.HashMap;
import java.util.Map;

public class AnyPartMatcher implements PartMatcher {
    private final String name;

    public AnyPartMatcher(String name) {
        this.name = name;
    }

    @Override
    public PartMatchResult match(String[] segments) {
        StringBuilder sb = new StringBuilder();
        for(String segment : segments) {
            sb.append("/");
            sb.append(segment);
        }

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(name, sb.toString());

        return PartMatchResult.match(segments.length, context);
    }
}
