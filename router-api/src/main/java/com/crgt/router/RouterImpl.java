package com.crgt.router;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由具体实现类, 接口基本与Router类保持一致。
 *
 * @author jesse
 * 2019/5/8
 * jesse_lu@foxmail.com
 */

final class RouterImpl {

    private Application mApplicationContext;
    private RouterRoster mRosterMap;
    private List<RouterInterceptor> mInterceptors;
    private IProtocolParser mProtocolParser;
    private RouterCollectGenerator mCollectorGenerator;
    private RouterNotFoundHandler mNotFoundHandler;

    private static final class INSTANCE_HOLDER {
        private static RouterImpl sInstance = new RouterImpl();
    }

    private RouterImpl() {
        mRosterMap = new RouterRoster();
        mCollectorGenerator = new RouterCollectGenerator();
        mInterceptors = new ArrayList<>();
    }

    static RouterImpl getInstance() {
        return INSTANCE_HOLDER.sInstance;
    }

    void init(Application application) {
        mApplicationContext = application;
        collectRoster();
    }

    void configProtocol(@NonNull IProtocolParser parser) {
        mProtocolParser = parser;
    }

    void setNotFoundHandler(@NonNull RouterNotFoundHandler handler) {
        mNotFoundHandler = handler;
    }

    void addInterceptor(@NonNull RouterInterceptor interceptor) {
        mInterceptors.add(interceptor);
    }


    void toProtocol(Context context, String protocol, ParamBuilder params) {
        if (mProtocolParser == null) {
            Log.e("Router", "Protocol Parser is null!");
            return;
        }
        Uri uri;
        try {
            uri = Uri.parse(protocol);
        } catch (Exception ignore) {
            return;
        }
        if (uri == null) {
            return;
        }
        String shortPath = mProtocolParser.parsePath(uri);
        ParamBuilder paramBuilder = params == null ? new ParamBuilder() : params;
        paramBuilder.setRaw(uri);
        paramBuilder.withUriParam(uri);
        toActivityForResult(context, shortPath, paramBuilder);
    }


    String getActivityClassName(String name) {
        String result = mRosterMap.getActivityName(name);
        if (TextUtils.isEmpty(result) && mNotFoundHandler != null) {
            return mNotFoundHandler.getActivityClassName(name);
        }
        return result;
    }

    private void toActivityForResult(final Context context, final String shortPath, @Nullable final ParamBuilder param) {
        // do intercept
        if (mInterceptors.size() > 0) {
            doIntercept(0, context, shortPath, param, new RouterInterceptor.Callback() {
                @Override
                public void onContinue() {
                    try {
                        Intent intent = generateIntent(context, shortPath, param);
                        gotoStartActivity(context, intent, param);
                    } catch (Exception e) {
                        noActivityFound(shortPath, e.getMessage());
                    }
                }

                @Override
                public void onIntercept() {
                    // intercepted
                }
            });
        } else {
            // start activity
            try {
                Intent intent = generateIntent(context, shortPath, param);
                gotoStartActivity(context, intent, param);
            } catch (Exception e) {
                noActivityFound(shortPath, e.getMessage());
            }
        }
    }

    String getServiceClassName(String name) {
        String result = mRosterMap.getServiceName(name);
        if (TextUtils.isEmpty(result) && mNotFoundHandler != null) {
            return mNotFoundHandler.getServiceClassName(name);
        }
        return result;
    }

    Class getServiceClass(String name) {
        try {
            return mRosterMap.findService(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mNotFoundHandler != null) {
            return mNotFoundHandler.getServiceClass(name);
        }
        return null;
    }

    String getFragmentClassName(String name) {
        String result = mRosterMap.getFragmentName(name);
        if (TextUtils.isEmpty(result) && mNotFoundHandler != null) {
            return mNotFoundHandler.getFragmentClassName(name);
        }
        return result;
    }

    Class getFragmentClass(String name) {
        try {
            return mRosterMap.findFragment(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mNotFoundHandler != null) {
            return mNotFoundHandler.getFragmentClass(name);
        }
        return null;
    }

    Object getFragmentInstance(String name) {
        return getFragmentInstance(name, null);
    }

    Object getFragmentInstance(String name, ParamBuilder param) {
        try {
            Class fragmentClass = mRosterMap.findFragment(name);
            Object instance = fragmentClass.getConstructor().newInstance();
            if (param != null) {
                if (instance instanceof Fragment) {
                    ((Fragment) instance).setArguments(param.getBundle());
                } else if (instance instanceof android.support.v4.app.Fragment) {
                    ((android.support.v4.app.Fragment) instance).setArguments(param.getBundle());
                }
            }
            return instance;
        } catch (Exception ignore) {
            return noFragmentFound(name, ignore.getMessage());
        }
    }

    private void collectRoster() {
        mCollectorGenerator.collect(mRosterMap);
    }

    void setActivityClassName(String componentName, String activityClassName) {
        mRosterMap.setActivityClassName(componentName, activityClassName);
    }

    void setFragmentClassName(String componentName, String fragmentClassName) {
        mRosterMap.setFragmentClassName(componentName, fragmentClassName);
    }

    private void doIntercept(final int index, final Context context, final String name,
                             final ParamBuilder builder, final RouterInterceptor.Callback callback) {
        final RouterInterceptor interceptor = mInterceptors.get(index);
        interceptor.intercept(context, name, builder, new RouterInterceptor.Callback() {
            @Override
            public void onContinue() {
                if (index + 1 == mInterceptors.size()) {
                    callback.onContinue();
                } else {
                    doIntercept(index + 1, context, name, builder, callback);
                }
            }

            @Override
            public void onIntercept() {
                callback.onIntercept();
            }
        });
    }

    private Intent generateIntent(Context context, String shortPath, ParamBuilder param)
            throws IllegalArgumentException {
        if (TextUtils.isEmpty(shortPath)) {
            throw new IllegalArgumentException("Router path can not be empty.");
        }
        Class clazz;
        try {
            clazz = mRosterMap.findActivity(shortPath);
        } catch (Exception ignore) {
            throw new IllegalArgumentException(shortPath + " can not match correct activity class.");
        }
        Intent intent = new Intent(context, clazz);
        if (param != null) {
            // auto parse uri param
            if (param.getRawUri() != null) {
                RouterBindUtil.autoPutParam(clazz, param.getRawUri(), param.getBundle());
            }
            // put extras bundle
            intent.putExtras(param.getBundle());
        }
        if (param != null && param.getFlag() > 0) {
            intent.setFlags(param.getFlag());
        } else if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }

    private void gotoStartActivity(Context context, Intent intent, ParamBuilder param)
            throws IllegalArgumentException {
        int requestCode = parseRequestCode(param);//try extract requestCode from params
        if (requestCode >= 0) {
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                throw new IllegalArgumentException("Context must be activity to start activity for result");
            }
        } else {
            context.startActivity(intent);
        }

        // animation
        if (param != null && (0 != param.getEnterAnim() || 0 != param.getExitAnim()) && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(param.getEnterAnim(), param.getExitAnim());
        }
    }

    private void noActivityFound(String path, String errorMsg) {
        if (mNotFoundHandler != null) {
            mNotFoundHandler.noActivityFound(mApplicationContext, path, errorMsg);
        }
    }

    private Object noFragmentFound(String path, String errorMsg) {
        if (mNotFoundHandler != null) {
            return mNotFoundHandler.noFragmentFound(mApplicationContext, path, errorMsg);
        }
        return new BlankFragment();
    }

    private int parseRequestCode(ParamBuilder params) {
        int DEFAULT_REQUEST_CODE = -1;

        if (params == null) {
            return DEFAULT_REQUEST_CODE;
        }
        if (params.getRequestCode() >= 0) {//1.参数里设定了requestCode
            return params.getRequestCode();
        }

        if (params.getBundle() == null) {
            return DEFAULT_REQUEST_CODE;
        }
        //2.uri里带了requestCode
        String PARAM_REQUEST_CODE = "requestCode";
        int requestCode = params.getBundle().getInt(PARAM_REQUEST_CODE, DEFAULT_REQUEST_CODE);
        if (requestCode > 0) {
            return requestCode;
        } else if (!TextUtils.isEmpty(params.getBundle().getString(PARAM_REQUEST_CODE))) {
            try {
                return Integer.parseInt(params.getBundle().getString(PARAM_REQUEST_CODE));
            } catch (Exception e) {
                //do nothing
            }
        }

        return DEFAULT_REQUEST_CODE;
    }
}
