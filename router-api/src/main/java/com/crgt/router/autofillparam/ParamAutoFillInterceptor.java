package com.crgt.router.autofillparam;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.crgt.router.ParamBuilder;
import com.crgt.router.Router;
import com.crgt.router.RouterInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2019/6/13.
 * jesse.lu@crgecent.com
 */
public class ParamAutoFillInterceptor implements RouterInterceptor {

    private IParamInterruptSpec interruptSpec;

    public ParamAutoFillInterceptor(@NonNull IParamInterruptSpec interruptSpec) {
        this.interruptSpec = interruptSpec;
    }

    @Override
    public void intercept(final Context context, final String componentName, @Nullable final ParamBuilder param, @NonNull final Callback callback) {
        //不需要重定向,原有链接继续跳转
        if (!interruptSpec.redirect(componentName, param)) {
            callback.onContinue();
            return;
        } else {
            callback.onIntercept();
        }

        //解析重定向路径
        final Uri newPage = interruptSpec.parsePagePath(componentName, param);
        //安全检查:空路径,中断跳转
        if (newPage == null) {
            return;
        }

        List<Uri> providers = interruptSpec.parseProviders(componentName, param);
        //安全检查:无需填充参数, 直接重定向到新页面
        if (providers == null || providers.isEmpty()) {
            Router.toProtocol(context, newPage.toString());
            return;
        }

        final ParamBuilder newPageParam = Router.buildParams();
        List<MethodPostcard> methods = genMethods(newPageParam, providers);
        //安全检查:无匹配的method, 直接重定向到新页面
        if (methods == null || methods.isEmpty()) {
            Router.toProtocol(context, newPage.toString());
            return;
        }

        //遍历填充所有动态参数
        provideParam(methods, 0, new Callback() {
            @Override
            public void onContinue() {
                //重定向
                Router.toProtocol(context, newPage.toString(), newPageParam);
            }

            @Override
            public void onIntercept() {
                //已经拦截掉了,do nothing
                Log.w("ParamInterceptor", "onIntercept interrupted!!!");
            }
        });
    }


    private List<MethodPostcard> genMethods(ParamBuilder resultBuilder, List<Uri> providers) {
        IMethodParser methodParser = interruptSpec.getMethodParser();
        List<MethodPostcard> methodPostcards = new ArrayList<>();

        for (Uri provider : providers) {
            MethodPostcard methodPostcard = new MethodPostcard(provider, methodParser);
            methodPostcard.setResultBuilder(resultBuilder);
            methodPostcards.add(methodPostcard);
        }
        return methodPostcards;
    }

    private void provideParam(final List<MethodPostcard> methodPostcards, final int index, final Callback callback) {
        MethodPostcard methodPostcard = methodPostcards.get(index);
        Callback methodCallback = new RouterInterceptor.Callback() {
            @Override
            public void onContinue() {
                if (index == methodPostcards.size() - 1) {
                    callback.onContinue();
                } else {
                    provideParam(methodPostcards, index + 1, callback);
                }
            }

            @Override
            public void onIntercept() {
                callback.onIntercept();
            }
        };
        methodPostcard.setCallback(methodCallback);
        ParamMethodInvoker.invoke(methodPostcard);
    }

}
