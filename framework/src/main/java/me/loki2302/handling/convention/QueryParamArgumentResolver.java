package me.loki2302.handling.convention;

import me.loki2302.context.RequestContext;
import me.loki2302.handling.convention.framework.HandlerMethodArgumentResolver;

public class QueryParamArgumentResolver implements HandlerMethodArgumentResolver<ControllerParameterMeta, RequestContext> {
    @Override
    public boolean canResolve(
            ControllerParameterMeta controllerParameterMeta,
            RequestContext requestContext) {

        return controllerParameterMeta.getParameterClass() == String.class &&
                controllerParameterMeta.getAnnotation(QueryParam.class) != null;
    }

    @Override
    public Object resolve(
            ControllerParameterMeta controllerParameterMeta,
            RequestContext requestContext) {

        QueryParam queryParam = controllerParameterMeta.getAnnotation(QueryParam.class);
        String queryParamName = queryParam.value();
        return requestContext.queryParams.get(queryParamName);
    }
}
