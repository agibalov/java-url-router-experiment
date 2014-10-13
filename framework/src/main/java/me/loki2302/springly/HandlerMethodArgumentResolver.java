package me.loki2302.springly;

public interface HandlerMethodArgumentResolver<TParameterContext, TResolutionContext> {
    boolean canResolve(TParameterContext parameterContext, TResolutionContext resolutionContext);
    Object resolve(TParameterContext parameterContext, TResolutionContext resolutionContext);
}
