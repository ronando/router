package com.crgt.router.autofillparam;

import android.support.annotation.NonNull;

import com.crgt.router.Router;

import java.lang.reflect.Method;

/**
 * Created by lujie on 2019/6/13.
 * jesse.lu@crgecent.com
 */
public class ParamMethodInvoker {
    private static final String PROVIDE_PARAM_METHOD = "provideParam";

    public static void invoke(@NonNull MethodPostcard methodPostcard) {
        try {
            Class clz = Router.getServiceClass(methodPostcard.getServicePath());
            Object o = clz.newInstance();
            Method m = clz.getMethod(PROVIDE_PARAM_METHOD, MethodPostcard.class);
            m.invoke(o, methodPostcard);
        } catch (Exception e) {
            e.printStackTrace();
            //异常后唤起下一个method
            methodPostcard.setResult(true);
        }
    }
}
