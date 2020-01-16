package com.crgt.router;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 路由入口类
 *
 * @author android
 * 2019/5/8
 * android@crgecent.com
 */

public final class Router {

    public static void init(Application application) {
        RouterImpl.getInstance().init(application);
    }

    public static void configRouter(@NonNull IProtocolParser parser) {
        RouterImpl.getInstance().configProtocol(parser);
    }

    public static void addInterceptor(@NonNull RouterInterceptor interceptor) {
        RouterImpl.getInstance().addInterceptor(interceptor);
    }

    public static void setNotFoundHandler(@NonNull RouterNotFoundHandler handler) {
        RouterImpl.getInstance().setNotFoundHandler(handler);
    }

    public static void inject(@NonNull Object object) {
        RouterBindUtil.inject(object);
    }

    public static ParamBuilder buildParams() {
        return new ParamBuilder();
    }

    public static String getActivityClassName(String name) {
        return RouterImpl.getInstance().getActivityClassName(name);
    }


    public static String getServiceClassName(String name) {
        return RouterImpl.getInstance().getServiceClassName(name);
    }

    public static Class getServiceClass(String name) {
        return RouterImpl.getInstance().getServiceClass(name);
    }

    public static String getFragmentClassName(String name) {
        return RouterImpl.getInstance().getFragmentClassName(name);
    }

    public static Class getFragmentClass(String name) {
        return RouterImpl.getInstance().getFragmentClass(name);
    }

    public static Object getFragmentInstance(String name) {
        return RouterImpl.getInstance().getFragmentInstance(name);
    }

    /**
     * 跳转页面
     * @param path  跳转路径, eg.scheme://host/path  or  path only
     */
    public static void toActivity(Context context, String path, ParamBuilder params) {
        RouterImpl.getInstance().toProtocol(context, path, params);
    }


    /**
     * 这个方法必须在init之后调用，如果原来存在componentName，会覆盖掉原来的activityClassName, 如果不存在，会增加一个.
     *
     */
    public static void setActivityClassName(String componentName, String activityClassName) {
        RouterImpl.getInstance().setActivityClassName(componentName, activityClassName);
    }

    /**
     * 这个方法必须在init之后调用，如果原来存在componentName，会覆盖掉原来的fragmentClassName, 如果不存在，会增加一个.
     *
     */
    public static void setFragmentClassName(String componentName, String fragmentClassName) {
        RouterImpl.getInstance().setFragmentClassName(componentName, fragmentClassName);
    }

}
