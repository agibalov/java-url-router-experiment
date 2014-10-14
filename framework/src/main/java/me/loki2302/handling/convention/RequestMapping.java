package me.loki2302.handling.convention;

import me.loki2302.routing.advanced.RequestMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value();
    RequestMethod[] method() default { };
}
