package me.loki2302.springly.web;

import me.loki2302.context.RequestContext;
import me.loki2302.springly.HandlerMethodArgumentResolver;

public class QueryParamArgumentResolver implements HandlerMethodArgumentResolver<ControllerParameterMeta, RequestContext> {
    @Override
    public boolean canResolve(
            ControllerParameterMeta controllerParameterMeta,
            RequestContext requestContext) {

        System.out.printf("class: %s\n", controllerParameterMeta.getParameterClass());
        System.out.printf("annotation: %s\n", controllerParameterMeta.getAnnotation(QueryParam.class));

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
