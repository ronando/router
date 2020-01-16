package com.crgt.demorouter;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.crgt.demoprotocol.PreProcessor;
import com.crgt.demorouter.param.JumpUrlInterruptSpec;
import com.crgt.protocol.AbsProtocolProcessor;
import com.crgt.protocol.Protocol;
import com.crgt.protocol.ProtocolParser;
import com.crgt.router.RouterParser;
import com.crgt.router.ParamBuilder;
import com.crgt.router.Router;
import com.crgt.router.RouterInterceptor;
import com.crgt.router.RouterNotFoundHandler;
import com.crgt.router.autofillparam.ParamAutoFillInterceptor;

/**
 * Created by lujie on 2020-01-16.
 * jesse.lu@crgecent.com
 */
public class Configer {
    public void config(Application application) {
        configRouter(application);
        configProtocols();
    }


    private void configProtocols() {
        Protocol.setParser(new ProtocolParser("in", ProtocolParser.ComponentIdentifier.SCHEME));
        Protocol.setPreProcessor(new PreProcessor());
        Protocol.setDefaultProcessor(new DefaultProcessor());
    }

    private void configRouter(final Application application) {
        Router.init(application);
        Router.configRouter(new RouterParser("crgt", RouterParser.ComponentIdentifier.PATH));
        Router.setNotFoundHandler(new RouterNotFoundHandler() {
            @Override
            public void noActivityFound(Context appContext, String path, String errorMsg) {
                Toast.makeText(appContext, "Custom msg: " + path + " not found", Toast.LENGTH_SHORT).show();
            }
        });
        Router.addInterceptor(new RouterInterceptor() {
            @Override
            public void intercept(Context context, String componentName, @Nullable ParamBuilder param, Callback callback) {
                callback.onContinue();
            }
        });
        Router.addInterceptor(new RouterInterceptor() {
            @Override
            public void intercept(Context context, String componentName, @Nullable ParamBuilder param, Callback callback) {
                if ("intercept_test".equals(componentName)) {
                    Toast.makeText(application, "Activity was intercepted", Toast.LENGTH_SHORT).show();
                    callback.onIntercept();
                } else {
                    callback.onContinue();
                }
            }
        });

        Router.addInterceptor(new ParamAutoFillInterceptor(new JumpUrlInterruptSpec()) {
            @Override
            public void intercept(Context context, String componentName, @Nullable ParamBuilder param, @NonNull Callback callback) {
                super.intercept(context, componentName, param, callback);
            }
        });
    }


    public class DefaultProcessor extends AbsProtocolProcessor {

        @Override
        public boolean process(Context context) {
            ParamBuilder paramBuilder = param == null ? Router.buildParams() : param;
            paramBuilder.setRaw(rawUri);
            paramBuilder.withUriParam(rawUri);
            if (path.startsWith("/")) {
                path = path.replaceFirst("/", "");
            }
            //默认协议处理器,尝试跳转路由
            Router.toActivity(context, path, paramBuilder);
            return true;
        }
    }


}
