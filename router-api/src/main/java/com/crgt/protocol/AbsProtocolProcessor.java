package com.crgt.protocol;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.crgt.router.ParamBuilder;


public abstract class AbsProtocolProcessor {
    public String protocol;
    public String scheme;
    public String host;
    public String path;
    public ParamBuilder param;
    private Uri uri;

    public abstract void process(Context applicationOrActivityContext);

    public abstract void parseParameters();

    public String getParameter(String key) {
        if (uri == null) {
            return null;
        } else {
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
