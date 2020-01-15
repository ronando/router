package com.crgt.protocol;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;


public abstract class AbsPreProcessor {
    /**
     * 以下为提前预解析好的参数
     */
    public String protocol;
    public String scheme;
    public String host;
    public String path;
    private Uri uri;

    /**
     * 用来做协议过滤和拦截
     *
     * @param applicationOrActivityContext
     * @return 返回true说明已经预处理，协议不进入下一步, 返回false协议继续下一步处理.
     */
    public abstract boolean preProcess(Context applicationOrActivityContext);

    public String getParameter(String key) {
        if(uri == null){
            return null;
        }else{
            return uri.getQueryParameter(key);
        }
    }

    public void startActivitySafely(final Context context, final Intent intent) {
        // 考虑ApplicationContext， 考虑线程
        if (intent == null) {
            Log.e(this.getClass().getSimpleName(), "intent null!");
            return;
        }
        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        _startActivitySafely(context, intent);
    }

    private void _startActivitySafely(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            context.startActivity(intent);
        } else {
            Log.e(this.getClass().getSimpleName(), "no target activity found");
        }
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
