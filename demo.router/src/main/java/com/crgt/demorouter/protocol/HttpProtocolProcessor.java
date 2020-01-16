package com.crgt.demorouter.protocol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.crgt.protocol.AbsProtocolProcessor;
import com.crgt.protocol.annotation.ProtocolPath;


/**
 * 跳转外部浏览器
 */
@ProtocolPath(path = {"http", "https"})
public class HttpProtocolProcessor extends AbsProtocolProcessor {

    @Override
    public boolean process(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(rawUri);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //非activity context需要加Intent.FLAG_ACTIVITY_NEW_TASK
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
        return true;
    }


}
