package com.crgt.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 路由入口类
 *
 * @author android
 * @date 2019/5/8
 * @mail android@crgecent.com
 */

public final class Router {

    public static void init(Application application) {
        //Todo: 注册Activity生命周期回调，在Activity destroy时清空相关数据，防止内存泄漏.
        RouterImpl.getInstance().init(application);
    }

    public static void configProtocol(@NonNull IProtocolParser parser) {
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

    public static void toActivity(Context context, String name) {
        RouterImpl.getInstance().toActivity(context, name);
    }

    public static void toActivityForResult(Activity context, String name, int requestCode) {
        RouterImpl.getInstance().toActivityForResult(context, name, requestCode);
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

    public static void toProtocol(Context context, String protocol) {
        RouterImpl.getInstance().toProtocol(context, protocol);
    }

    public static void toProtocol(Context context, String protocol, ParamBuilder params) {
        RouterImpl.getInstance().toProtocol(context, protocol, params);
    }

    /**
     * 这个方法必须在init之后调用，如果原来存在componentName，会覆盖掉原来的activityClassName, 如果不存在，会增加一个.
     *
     * @param componentName
     * @param activityClassName
     */
    public static void setActivityClassName(String componentName, String activityClassName) {
        RouterImpl.getInstance().setActivityClassName(componentName, activityClassName);
    }

    /**
     * 这个方法必须在init之后调用，如果原来存在componentName，会覆盖掉原来的fragmentClassName, 如果不存在，会增加一个.
     *
     * @param componentName
     * @param fragmentClassName
     */
    public static void setFragmentClassName(String componentName, String fragmentClassName) {
        RouterImpl.getInstance().setFragmentClassName(componentName, fragmentClassName);
    }

}
