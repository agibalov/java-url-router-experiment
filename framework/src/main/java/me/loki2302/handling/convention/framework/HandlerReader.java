package me.loki2302.handling.convention.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HandlerReader<TClassMeta, TMethodMeta, TParameterMeta, THandler> {
    private final MetadataReader<TClassMeta, TMethodMeta, TParameterMeta, THandler> metadataReader;

    public HandlerReader(MetadataReader<TClassMeta, TMethodMeta, TParameterMeta, THandler> metadataReader) {
        this.metadataReader = metadataReader;
    }

    public List<THandler> readClass(Class<?> clazz) {
        List<THandler> handlers = new ArrayList<THandler>();
        ClassHelper classHelper = new ClassHelper(clazz);
        TClassMeta classMeta = metadataReader.readClass(clazz, classHelper);
        if(classMeta == null) {
            return handlers;
        }

        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {
            MethodHelper methodHelper = new MethodHelper(method);
            TMethodMeta methodMeta = metadataReader.readMethod(
                    classMeta,
                    method,
                    methodHelper);
            if(methodMeta == null) {
                continue;
            }

            List<TParameterMeta> parameterMetas = new ArrayList<TParameterMeta>();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Annotation[][] parameterAnnotationArrays = method.getParameterAnnotations();
            int numberOfParameters = parameterTypes.length;
            for(int i = 0; i < numberOfParameters; ++i) {
                Class<?> parameterClass = parameterTypes[i];
                Annotation[] parameterAnnotations = parameterAnnotationArrays[i];
                ParameterHelper parameterHelper = new ParameterHelper(parameterClass, parameterAnnotations);
                TParameterMeta parameterContext = metadataReader.readParameter(
                        classMeta,
                        methodMeta,
                        parameterClass,
                        parameterAnnotations,
                        parameterHelper);
                if(parameterContext == null) {
                    continue;
                }

                parameterMetas.add(parameterContext);
            }

            if(parameterMetas.size() != numberOfParameters) {
                continue;
            }

            THandler handler = metadataReader.makeHandler(
                    classMeta,
                    methodMeta,
                    parameterMetas);
            handlers.add(handler);
        }

        return handlers;
    }
}