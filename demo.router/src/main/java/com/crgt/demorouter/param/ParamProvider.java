package com.crgt.demorouter.param;

import android.util.Log;

import com.crgt.router.RouterPath;
import com.crgt.router.autofillparam.IParamProvider;
import com.crgt.router.autofillparam.MethodPostcard;

/**
 * Created by lujie on 2019/6/14.
 * jesse.lu@crgecent.com
 */
@RouterPath(path = "trip/data")
public class ParamProvider implements IParamProvider {
    @Override
    public void provideParam(MethodPostcard postcard) {
        Log.d("ParamProvider", "provideParam()");

        postcard.getResultBuilder().withString("jesse", "jesse ok");
        postcard.setResult(true);
    }
}
