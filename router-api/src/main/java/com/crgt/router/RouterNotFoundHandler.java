package com.crgt.router;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 路由查找失败时，自定义返回值
 *
 * @author android
 * @date 2018/4/8
 * @mail android@crgecent.com
 */
public abstract class RouterNotFoundHandler {

    public String getActivityClassName(String path) {
        return null;
    }

    public String getServiceClassName(String path) {
        return null;
    }

    public Class getServiceClass(String path) {
        return null;
    }

    public String getFragmentClassName(String name) {
        return BlankFragment.class.getName();
    }

    public Class getFragmentClass(String name) {
        return BlankFragment.class;
    }

    public void noActivityFound(Context appContext, String path, String errorMsg) {
        Toast.makeText(appContext, "Activity not found: " + errorMsg, Toast.LENGTH_SHORT).show();
    }

    public Object noFragmentFound(Context appContext, String path, String errorMsg) {
        Log.e("RouterNotFoundHandler", "can't find fragment: " + path + " error: " + errorMsg);
        BlankFragment blankFragment = new BlankFragment();
        blankFragment.setPathName(path);
        return blankFragment;
    }
}
