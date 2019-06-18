package com.crgt.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * use to auto generate router roster
 *
 * @author android
 * @date 2019/6/2
 * @mail android@crgecent.com
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouterPath {

    // router path
    String[] path();
}
