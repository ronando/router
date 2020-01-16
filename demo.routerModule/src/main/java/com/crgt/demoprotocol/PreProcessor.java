package com.crgt.demoprotocol;

import android.content.Context;
import android.widget.Toast;

import com.crgt.protocol.AbsProtocolProcessor;


/**
 * @author jesse.lu
 * 2019/6/15
 * jesse.lu@foxmail.com
 */
public class PreProcessor extends AbsProtocolProcessor {
    @Override
    public boolean process(Context context) {
        //do something
        Toast.makeText(context, "协议预处理器处理中(不拦截)...", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void parseParameters() {
        //do nothing
    }
}
