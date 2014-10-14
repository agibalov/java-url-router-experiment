package me.loki2302.handling.convention;

import me.loki2302.handling.convention.framework.ClassHelper;
import me.loki2302.handling.convention.framework.MetadataReader;
import me.loki2302.handling.convention.framework.MethodHelper;
import me.loki2302.handling.convention.framework.ParameterHelper;
import me.loki2302.routing.advanced.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class ControllerMetadataReader implements MetadataReader<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta, ControllerMethodHandler> {
    @Override
    public ControllerClassMeta readClass(
            Class<?> clazz,
            ClassHelper classHelper) {

        RequestMapping requestMapping = classHelper.getAnnotation(RequestMapping.class);
        if(requestMapping == null) {
            return null;
        }

        String path = requestMapping.value();
        RequestMethod[] requestMethods = requestMapping.method();
        ControllerClassMeta controllerClassMeta = new ControllerClassMeta(
                clazz,
                requestMethods,
                path);

        return controllerClassMeta;
    }

    @Override
    public ControllerMethodMeta readMethod(
            ControllerClassMeta classContext,
            Method method,
            MethodHelper methodHelper) {

        RequestMapping requestMapping = methodHelper.getAnnotation(RequestMapping.class);
        if(requestMapping == null) {
            return null;
        }

        String path = requestMapping.value();
        RequestMethod[] requestMethods = requestMapping.method();

        ControllerMethodMeta controllerMethodMeta = new ControllerMethodMeta(
                method,
                requestMethods,
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

    @Override
    public ControllerMethodHandler makeHandler(
            ControllerClassMeta controllerClassMeta,
            ControllerMethodMeta controllerMethodMeta,
            List<ControllerParameterMeta> controllerParameterMetas) {

        RequestMethod[] requestMethods = computeRequestMethods(controllerClassMeta, controllerMethodMeta);
        String path = computePath(controllerClassMeta, controllerMethodMeta);

        Class<?> controllerClass = controllerClassMeta.getControllerClass();
        Method controllerMethod = controllerMethodMeta.getMethod();

        ControllerMethodHandler controllerMethodHandler = new ControllerMethodHandler(
                controllerClass,
                controllerParameterMetas,
                controllerMethod,
                requestMethods,
                path);

        return controllerMethodHandler;
    }

    private static RequestMethod[] computeRequestMethods(
            ControllerClassMeta controllerClassMeta,
            ControllerMethodMeta controllerMethodMeta) {

        RequestMethod[] requestMethods = controllerMethodMeta.getRequestMethod();
        if(requestMethods.length == 0) {
            requestMethods = controllerClassMeta.getRequestMethods();
        }

        return requestMethods;
    }

    private static String computePath(
            ControllerClassMeta controllerClassMeta,
            ControllerMethodMeta controllerMethodMeta) {

        String controllerPath = controllerClassMeta.getPath();
        if(controllerPath == null) {
            controllerPath = "";
        }

        String methodPath = controllerMethodMeta.getPath();
        if(methodPath == null) {
            methodPath = "";
        }

        String path = controllerPath + methodPath;

        return path;
    }
}
