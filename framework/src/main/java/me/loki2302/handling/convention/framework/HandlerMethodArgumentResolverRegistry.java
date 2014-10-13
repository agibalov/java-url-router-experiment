package me.loki2302.handling.convention.framework;

import java.util.ArrayList;
import java.util.List;

public class HandlerMethodArgumentResolverRegistry<TParameterContext, TResolutionContext> {
    private final List<HandlerMethodArgumentResolver<TParameterContext, TResolutionContext>> resolvers =
            new ArrayList<HandlerMethodArgumentResolver<TParameterContext, TResolutionContext>>();

    public void register(HandlerMethodArgumentResolver<TParameterContext, TResolutionContext> resolver) {
        resolvers.add(resolver);
    }

    public Object resolve(TParameterContext parameterContext, TResolutionContext resolutionContext) {
        for(HandlerMethodArgumentResolver<TParameterContext, TResolutionContext> resolver : resolvers) {
            if(!resolver.canResolve(parameterContext, resolutionContext)) {
                continue;
            }

            return resolver.resolve(parameterContext, resolutionContext);
        }

        String message = String.format("Can't resolve %s, checked %d resolvers", parameterContext, resolvers.size());
        throw new RuntimeException(message);
    }
}
