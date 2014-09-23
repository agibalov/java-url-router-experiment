package me.loki2302.springly;

import me.loki2302.context.RequestContext;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Dummy {

    private static void dummy() {
        ControllerMetadataReader controllerMetadataReader = new ControllerMetadataReader();
        ControllerHandlerFactory controllerHandlerFactory = new ControllerHandlerFactory();
        HandlerReader<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta, RouteHandlerHandler> handlerReader =
                new HandlerReader<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta, RouteHandlerHandler>(
                        controllerMetadataReader,
                        controllerHandlerFactory);

        HandlerRegistry<RouteHandlerHandler, RequestContext> handlerRegistry = new HandlerRegistry<RouteHandlerHandler, RequestContext>(new HandlerPredicate<RouteHandlerHandler, RequestContext>() {
            @Override
            public boolean match(RouteHandlerHandler routeHandlerHandler, RequestContext requestContext) {
                return false;
            }
        });

        // List<RouteHandlerHandler> handlers = handlerReader.readClass(null);
        // handlerRegistry.register(handlers);

        HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext> handlerMethodArgumentResolverRegistry =
                new HandlerMethodArgumentResolverRegistry<ControllerParameterMeta, RequestContext>();
        handlerMethodArgumentResolverRegistry.register(new PathParamArgumentResolver());
        handlerMethodArgumentResolverRegistry.register(new QueryParamArgumentResolver());

        RequestProcessor<RouteHandlerHandler, ControllerParameterMeta, RequestContext> requestProcessor =
                new RequestProcessor<RouteHandlerHandler, ControllerParameterMeta, RequestContext>(
                        handlerRegistry,
                        new HandlerInstanceResolver() {
                            @Override
                            public Object resolveInstance(Class<?> handlerClass) {
                                return null;
                            }
                        },
                handlerMethodArgumentResolverRegistry);

        // requestProcessor.processRequest(null);
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface RequestMapping {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface PathParam {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface QueryParam {
        String value();
    }

    public static class ControllerClassMeta {
        private final Class<?> controllerClass;
        private final String path;

        public ControllerClassMeta(Class<?> controllerClass, String path) {
            this.controllerClass = controllerClass;
            this.path = path;
        }

        public Class<?> getControllerClass() {
            return controllerClass;
        }

        public String getPath() {
            return path;
        }
    }

    public static class ControllerMethodMeta {
        private final Method method;
        private final String path;

        public ControllerMethodMeta(
                Method method,
                String path) {

            this.method = method;
            this.path = path;
        }

        public Method getMethod() {
            return method;
        }

        public String getPath() {
            return path;
        }
    }

    public static class ControllerParameterMeta {
        private final Class<?> parameterClass;
        private final Annotation[] annotations;

        private ControllerParameterMeta(
                Class<?> parameterClass,
                Annotation[] annotations) {

            this.parameterClass = parameterClass;
            this.annotations = annotations;
        }

        public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> annotationClass) {
            for(Annotation annotation : annotations) {
                if(annotation.getClass() == annotationClass) {
                    return (TAnnotation)annotation;
                }
            }

            return null;
        }

        public Class<?> getParameterClass() {
            return parameterClass;
        }
    }

    public static class PathParamArgumentResolver implements HandlerMethodArgumentResolver<ControllerParameterMeta, RequestContext> {
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

    public static class QueryParamArgumentResolver implements HandlerMethodArgumentResolver<ControllerParameterMeta, RequestContext> {
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

    public static class RouteHandlerHandler implements Handler<ControllerParameterMeta> {
        private final Class<?> controllerClass;
        private final List<ControllerParameterMeta> parameters;
        private final Method method;
        private final String path;

        public RouteHandlerHandler(
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

        @Override
        public Class<?> getHandlerClass() {
            return controllerClass;
        }

        @Override
        public List<ControllerParameterMeta> getParameters() {
            return parameters;
        }

        @Override
        public Object handle(Object instance, List<Object> arguments) {
            try {
                return method.invoke(instance, arguments.toArray());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class ControllerHandlerFactory implements HandlerFactory<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta, RouteHandlerHandler> {
        @Override
        public RouteHandlerHandler makeHandler(
                ControllerClassMeta controllerClassMeta,
                ControllerMethodMeta controllerMethodMeta,
                List<ControllerParameterMeta> controllerParameterMetas) {

            String controllerPath = controllerClassMeta.getPath();
            if(controllerPath == null) {
                controllerPath = "";
            }

            String methodPath = controllerMethodMeta.getPath();
            if(methodPath == null) {
                methodPath = "";
            }

            // HEY! DIRTY HACK!!!
            String path = new File(controllerPath, methodPath).getPath();

            Class<?> controllerClass = controllerClassMeta.getControllerClass();
            Method controllerMethod = controllerMethodMeta.getMethod();

            RouteHandlerHandler routeHandlerHandler = new RouteHandlerHandler(
                    controllerClass,
                    controllerParameterMetas,
                    controllerMethod,
                    path);

            return routeHandlerHandler;
        }
    }

    public static class ControllerMetadataReader implements MetadataReader<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta> {
        @Override
        public ControllerClassMeta readClass(
                Class<?> clazz,
                ClassHelper classHelper) {

            String path = null;
            RequestMapping requestMapping = classHelper.getAnnotation(RequestMapping.class);
            if(requestMapping != null) {
                path = requestMapping.value();
            }

            ControllerClassMeta controllerClassMeta = new ControllerClassMeta(
                    clazz,
                    path);
            return controllerClassMeta;
        }

        @Override
        public ControllerMethodMeta readMethod(
                ControllerClassMeta classContext,
                Method method,
                MethodHelper methodHelper) {

            String path = null;
            RequestMapping requestMapping = methodHelper.getAnnotation(RequestMapping.class);
            if(requestMapping != null) {
                path = requestMapping.value();
            }

            ControllerMethodMeta controllerMethodMeta = new ControllerMethodMeta(
                    method,
                    path);
            return controllerMethodMeta;
        }

        @Override
        public ControllerParameterMeta readParameter(
                ControllerClassMeta classContext,
                ControllerMethodMeta methodContext,
                Class<?> parameterClass,
                Annotation[] parameterAnnotations,
                ParameterHelper parameterHelper) {

            ControllerParameterMeta controllerParameterMeta = new ControllerParameterMeta(
                    parameterClass,
                    parameterAnnotations);

            return controllerParameterMeta;
        }
    }

}
