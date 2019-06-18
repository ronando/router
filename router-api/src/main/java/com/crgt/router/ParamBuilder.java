package com.crgt.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * 路由跳转参数封装类
 *
 * @author android
 * @date 2019/5/9
 * @mail android@crgecent.com
 */

public class ParamBuilder {

    private Bundle mBundle;
    private Uri mRawUri;
    private int mFlag;

    private int mEnterAnim;
    private int mExitAnim;

    public ParamBuilder() {
        mBundle = new Bundle();
    }

    @IntDef({
            Intent.FLAG_ACTIVITY_SINGLE_TOP,
            Intent.FLAG_ACTIVITY_NEW_TASK,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION,
            Intent.FLAG_DEBUG_LOG_RESOLUTION,
            Intent.FLAG_FROM_BACKGROUND,
            Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT,
            Intent.FLAG_ACTIVITY_CLEAR_TASK,
            Intent.FLAG_ACTIVITY_CLEAR_TOP,
            Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS,
            Intent.FLAG_ACTIVITY_FORWARD_RESULT,
            Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY,
            Intent.FLAG_ACTIVITY_MULTIPLE_TASK,
            Intent.FLAG_ACTIVITY_NO_ANIMATION,
            Intent.FLAG_ACTIVITY_NO_USER_ACTION,
            Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP,
            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED,
            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT,
            Intent.FLAG_ACTIVITY_TASK_ON_HOME,
            Intent.FLAG_RECEIVER_REGISTERED_ONLY
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FlagInt {
    }

    public ParamBuilder setRaw(Uri uri) {
        mRawUri = uri;
        return this;
    }

    public Uri getRawUri() {
        return mRawUri;
    }

    public ParamBuilder setFlag(@FlagInt int flag) {
        mFlag = flag;
        return this;
    }

    public int getFlag() {
        return mFlag;
    }

    public int getEnterAnim() {
        return mEnterAnim;
    }

    public int getExitAnim() {
        return mExitAnim;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public ParamBuilder withTransition(int enterAnim, int exitAnim) {
        this.mEnterAnim = enterAnim;
        this.mExitAnim = exitAnim;
        return this;
    }

    public ParamBuilder withInt(@Nullable String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public ParamBuilder withBoolean(@Nullable String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public ParamBuilder withIntArray(@Nullable String key, @Nullable int[] value) {
        mBundle.putIntArray(key, value);
        return this;
    }

    public ParamBuilder withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public ParamBuilder withLong(@Nullable String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public ParamBuilder withFloat(@Nullable String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public ParamBuilder withDouble(@Nullable String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    public ParamBuilder withString(@Nullable String key, @Nullable String value) {
        mBundle.putString(key, value);
        return this;
    }

    public ParamBuilder withStringArray(@Nullable String key, @Nullable String[] value) {
        mBundle.putStringArray(key, value);
        return this;
    }

    public ParamBuilder withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

    public ParamBuilder withParcelable(@Nullable String key, @Nullable Parcelable value) {
        mBundle.putParcelable(key, value);
        return this;
    }

    public ParamBuilder withParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
        mBundle.putParcelableArrayList(key, value);
        return this;
    }

    public ParamBuilder withSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    public ParamBuilder withBundle(String key, Bundle value) {
        mBundle.putBundle(key, value);
        return this;
    }

    public void toActivity(Context context, String name) {
        RouterImpl.getInstance().toActivity(context, name, this);
    }

    public void toActivityForResult(Activity context, String name, int requestCode) {
        RouterImpl.getInstance().toActivityForResult(context, name, this, requestCode);
    }

    public Object getFragmentInstance(String name) {
        return RouterImpl.getInstance().getFragmentInstance(name, this);
    }

    public void withUriParam(Uri uri) {
        if (uri == null) {
            return;
        }
        Set<String> keys = uri.getQueryParameterNames();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            this.withString(key, uri.getQueryParameter(key));
        }
    }


}
