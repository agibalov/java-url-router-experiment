package me.loki2302.handling.convention.framework;

public interface HandlerMethodArgumentResolver<TParameterContext, TResolutionContext> {
    boolean canResolve(TParameterContext parameterContext, TResolutionContext resolutionContext);
    Object resolve(TParameterContext parameterContext, TResolutionContext resolutionContext);
}
