package me.loki2302.handling.convention;

import me.loki2302.handling.convention.framework.ClassHelper;
import me.loki2302.handling.convention.framework.MetadataReader;
import me.loki2302.handling.convention.framework.MethodHelper;
import me.loki2302.handling.convention.framework.ParameterHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class ControllerMetadataReader implements MetadataReader<ControllerClassMeta, ControllerMethodMeta, ControllerParameterMeta, ControllerMethodHandler> {
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

    @Override
    public ControllerMethodHandler makeHandler(
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

        String path = controllerPath + methodPath;

        Class<?> controllerClass = controllerClassMeta.getControllerClass();
        Method controllerMethod = controllerMethodMeta.getMethod();

        ControllerMethodHandler controllerMethodHandler = new ControllerMethodHandler(
                controllerClass,
                controllerParameterMetas,
                controllerMethod,
                path);

        return controllerMethodHandler;
    }
}
