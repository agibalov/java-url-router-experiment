package me.loki2302.springly.web;

import me.loki2302.context.RequestContext;
import me.loki2302.springly.HandlerMethodArgumentResolver;

public class PathParamArgumentResolver implements HandlerMethodArgumentResolver<ControllerParameterMeta, RequestContext> {
    @Override
    public boolean canResolve(
            ControllerParameterMeta controllerParameterMeta,
            RequestContext requestContext) {

        return controllerParameterMeta.getParameterClass() == String.class &&
                controllerParameterMeta.getAnnotation(PathParam.class) != null;
    }

    @Override
    public Object resolve(
            ControllerParameterMeta controllerParameterMeta,
            RequestContext requestContext) {

        PathParam pathParam = controllerParameterMeta.getAnnotation(PathParam.class);
        String pathParamName = pathParam.value();
        return requestContext.pathParams.get(pathParamName);
    }
}
