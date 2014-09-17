package me.loki2302.routing.advanced;

import java.util.ArrayList;
import java.util.List;

public class AdvancedRouteParser {
    public List<PartMatcher> parse(String template) {
        String[] parts = template.split("/");
        List<PartMatcher> matchers = new ArrayList<PartMatcher>();
        for(String part : parts) {
            if(part.startsWith(":")) {
                String name = part.substring(1);
                VariablePartMatcher matcher = new VariablePartMatcher(name);
                matchers.add(matcher);
            } else if(part.startsWith("*")) {
                String name = part.substring(1);
                AnyPartMatcher matcher = new AnyPartMatcher(name);
                matchers.add(matcher);
            } else {
                StaticPartMatcher matcher = new StaticPartMatcher(part);
                matchers.add(matcher);
            }
        }
        return matchers;
    }
}
