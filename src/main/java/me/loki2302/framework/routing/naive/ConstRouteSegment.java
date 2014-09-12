package me.loki2302.framework.routing.naive;

import java.util.Map;

public class ConstRouteSegment implements RouteSegment {
    private final String value;

    public ConstRouteSegment(String value) {
        this.value = value;
    }

    @Override
    public SegmentMatchResult match(String urlSegment) {
        if(!urlSegment.equals(value)) {
            return SegmentMatchResult.noMatch(this);
        }

        return SegmentMatchResult.matchNoContext(this);
    }

    @Override
    public String getValue(Map<String, Object> context) {
        return value;
    }
}
