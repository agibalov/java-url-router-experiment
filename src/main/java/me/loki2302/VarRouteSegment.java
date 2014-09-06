package me.loki2302;

import java.util.Map;

public class VarRouteSegment implements RouteSegment {
    private final String name;

    public VarRouteSegment(String name) {
        this.name = name;
    }

    @Override
    public SegmentMatchResult match(String urlSegment) {
        if(urlSegment.isEmpty()) {
            return SegmentMatchResult.noMatch(this);
        }

        return SegmentMatchResult.matchSingletonContext(this, name, urlSegment);
    }

    @Override
    public String getValue(Map<String, Object> context) {
        return String.valueOf(context.get(name));
    }
}
