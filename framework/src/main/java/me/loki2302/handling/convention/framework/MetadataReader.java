package me.loki2302.handling.convention.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface MetadataReader<TClassMeta, TMethodMeta, TParameterMeta, THandler> {
    TClassMeta readClass(
            Class<?> clazz,
            ClassHelper classHelper);

    TMethodMeta readMethod(
            TClassMeta classContext,
            Method method,
            MethodHelper methodHelper);

    TParameterMeta readParameter(
            TClassMeta classContext,
            TMethodMeta methodContext,
            Class<?> parameterClass,
            Annotation[] parameterAnnotations,
            ParameterHelper parameterHelper);

    THandler makeHandler(
            TClassMeta classMeta,
            TMethodMeta methodMeta,
            List<TParameterMeta> parameterMetas);
}
