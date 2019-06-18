package com.crgt.router.autofillparam;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.crgt.router.ParamBuilder;
import com.crgt.router.RouterInterceptor;

import java.util.Map;

/**
 * MethodProvider-provideParam(MethodPostcard postcard),该方法的入参封装
 * <p>
 * Created by lujie on 2019/6/13.
 * jesse.lu@crgecent.com
 */

public class MethodPostcard {
    private Uri rawUri;
    private RouterInterceptor.Callback mCallback = null;
    private ParamBuilder mResultBuilder = null;
    private IMethodParser mMethodParser;

    public MethodPostcard(@NonNull Uri uri, @NonNull IMethodParser methodParser) {
        this.rawUri = uri;
        this.mMethodParser = methodParser;
    }

    public String getServicePath() {
        return mMethodParser.getServicePath(rawUri);
    }

    public String getMethodName() {
        return mMethodParser.getMethodName(rawUri);
    }


    public Map<String, String> getInputParams() {
        return mMethodParser.getInputParams(rawUri);
    }

    public void setCallback(RouterInterceptor.Callback callback) {
        mCallback = callback;
    }

    public void setResult(boolean gotoNext) {
        if (mCallback == null) {
            return;
        }
        if (gotoNext) {
            mCallback.onContinue();
        } else {
            mCallback.onIntercept();
        }
    }

    public ParamBuilder getResultBuilder() {
        return mResultBuilder;
    }

    public void setResultBuilder(ParamBuilder resultBuilder) {
        mResultBuilder = resultBuilder;
    }
}