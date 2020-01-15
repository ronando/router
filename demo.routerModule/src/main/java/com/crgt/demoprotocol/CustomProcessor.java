package com.crgt.demoprotocol;

import android.content.Context;
import android.widget.Toast;

import com.crgt.protocol.AbsProtocolProcessor;
import com.crgt.protocol.annotation.ProtocolPath;


/**
 *
 * @author jesse.lu
 * @Date 2019/6/15
 * @mail： jesse.lu@foxmail.com
 */
@ProtocolPath(path = {"fragment_activity", "path2/mainactivity"})
public class CustomProcessor extends AbsProtocolProcessor {
    @Override
    public void process(Context context) {
        //do something
        Toast.makeText(context,"自定义协议处理器处理中...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void parseParameters() {

    }
}
