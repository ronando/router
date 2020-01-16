package com.crgt.demorouter.param;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.crgt.router.ParamBuilder;
import com.crgt.router.autofillparam.IMethodParser;
import com.crgt.router.autofillparam.IParamInterruptSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lujie on 2019/6/13.
 * jesse.lu@crgecent.com
 */
public class JumpUrlInterruptSpec implements IParamInterruptSpec {

    public static final String ROUTER_PROVIDR_URL = "crgt://ccrgt.com/jump?page=crgt://ccrgt.com/fragment_activity?time=20190613&provider={crgt://ccrgt.com/trip/data/travelid?time1=providerTimeValue2018}";
//    public static final String ROUTER_PROVIDR_URL = "crgt://ccrgt.com/jump?page=pagePath&provider={providerUrl1,providerUrl2}";
//    public static final String PROVIDER_URL = "crgt://ccrgt.com/trip/data/path";

    public static final String TAG_JUMP = "jump";
    public static final String TAG_PAGE = "page";
    public static final String TAG_PROVIDER = "provider";


    @Override
    public boolean redirect(String componentName, ParamBuilder paramBuilder) {
        if (!TextUtils.isEmpty(componentName) && componentName.endsWith(TAG_JUMP)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Uri parsePagePath(String componentName, ParamBuilder param) {
        Uri pagePath = null;
        if (param != null && param.getBundle() != null) {
            try {
                pagePath = Uri.parse(param.getBundle().getString(TAG_PAGE));
            } catch (Exception e) {
                Log.e("JumpUrlInterruptSpec", "parsePagePath rawUri illegal");
            }
        }
        return pagePath;
    }

    @Override
    public List<Uri> parseProviders(String componentName, ParamBuilder param) {
        if (param == null || param.getBundle() == null) {
            return null;
        }

        //eg.sProvider={providerUrl1,providerUrl2}
        String sProvider = param.getBundle().getString(TAG_PROVIDER);
        if (TextUtils.isEmpty(sProvider)) {
            return null;
        }

        sProvider.replace("{", "");
        sProvider.replace("}", "");

        String[] providers = sProvider.split(",");

        List<Uri> providerUris = new ArrayList<>();
        for (String provider : providers) {
            if (TextUtils.isEmpty(provider)) {
                continue;
            }
            providerUris.add(Uri.parse(provider));
        }
        return providerUris;
    }


    /**
     * 原有链接"crgt://ccrgt.com/jump?page=crgt://ccrgt.com/trip/detail&provider={crgt://ccrgt.com/trip/data/travelid?time=20190613}"
     *
     * @return
     */
    @Override
    public IMethodParser getMethodParser() {
        return new ParamMethodParser();
    }


    /**
     * 解析规则说明
     * rawUri : crgt://ccrgt.com/trip/data/travelId?time=20190613
     * serviceUri: crgt://ccrgt.com/trip/data
     * methodName: travelId
     * inputParams: {time:20190613}
     */
    private class ParamMethodParser implements IMethodParser {
        @Override
        public String getServicePath(Uri rawUri) {
            if (rawUri != null) {
                String servicePath = rawUri.getPath();
                if (!TextUtils.isEmpty(servicePath)) {
                    return servicePath.replaceAll("/" + rawUri.getLastPathSegment() + "$", "").replaceAll("^/","");
                }
            }
            return "";
        }

        @Override
        public String getMethodName(Uri rawUri) {
            if (rawUri != null) {
                return rawUri.getLastPathSegment();
            }
            return "";
        }

        @Override
        public Map<String, String> getInputParams(Uri rawUri) {
            if (rawUri == null) {
                return null;
            }
            Map<String, String> paramMap = new HashMap<>();
            Set<String> keys = rawUri.getQueryParameterNames();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                paramMap.put(key, rawUri.getQueryParameter(key));
            }
            return paramMap;
        }
    }
}
