package me.loki2302.springly.web;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import me.loki2302.context.RequestContext;
import me.loki2302.handling.RouteHandler;
import me.loki2302.springly.HandlerMethodArgumentResolverRegistry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ControllerMethodHandler implements RouteHandler {
    private final Class<?> controllerClass;
    private final List<ControllerParameterMeta> parameters;
    private final Method method;
    private final String path;

    public ControllerMethodHandler(
            Class<?> controllerClass,
            List<ControllerParameterMeta> parameters,
            Method method,
            String path) {

        this.controllerClass = controllerClass;
        this.parameters = parameters;
        this.method = method;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Class<?> getHandlerClass() {
        return controllerClass;
    }

    public List<ControllerParameterMeta> getParameters() {
        return parameters;
    }

    @Override
    public Object handle(RequestContext requestContext) {
        Injector injector = requestContext.injector;

        Object instance = injector.getInstance(Key.get(controllerClass));

        HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext> handlerMethodArgumentResolverRegistry =
                injector.getInstance(Key.get(new TypeLiteral<HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext>>() {}));

        List<Object> arguments = new ArrayList<Object>();
        for(ControllerParameterMeta parameter : parameters) {
            Object argument = handlerMethodArgumentResolverRegistry
                    .resolve(parameter, requestContext);
            arguments.add(argument);
        }

        try {
            return method.invoke(instance, arguments.toArray());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
