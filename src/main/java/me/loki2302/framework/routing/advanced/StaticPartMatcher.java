package me.loki2302.framework.routing.advanced;

public class StaticPartMatcher implements PartMatcher {
    private final String[] templateSegments;

    public StaticPartMatcher(String[] templateSegments) {
        this.templateSegments = templateSegments;
    }

    @Override
    public PartMatchResult match(String[] segments) {
        if(segments.length < templateSegments.length) {
            return PartMatchResult.noMatch();
        }

        int numberOfSegments = templateSegments.length;
        for(int i = 0; i < numberOfSegments; ++i) {
            String expectedSegment = templateSegments[i];
            String actualSegment = segments[i];
            if(!actualSegment.equals(expectedSegment)) {
                return PartMatchResult.noMatch();
            }
        }

        return PartMatchResult.match(numberOfSegments);
    }
}
