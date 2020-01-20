package com.crgt.protocol;

import android.content.Context;
import android.net.Uri;

import com.crgt.router.ParamBuilder;


public abstract class AbsProtocolProcessor {
    public String protocol;
    public String scheme;
    public String host;
    public String path;
    public ParamBuilder param;
    public Uri rawUri;

    public abstract boolean process(Context applicationOrActivityContext);

    public void parseParameters(){

    }

    public String getParameter(String key) {
        if (rawUri == null) {
            return null;
        } else {
            return rawUri.getQueryParameter(key);
        }
    }

    public void setUri(Uri uri) {
        this.rawUri = uri;
    }
}
