package com.crgt.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * auto bind bundle data
 *
 * @author android
 * @date 2019/5/28
 * @mail android@crgecent.com
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouterParam {

    // param key
    String key() default "";
}
