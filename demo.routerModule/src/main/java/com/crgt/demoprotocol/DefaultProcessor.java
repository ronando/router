package com.crgt.demoprotocol;

import android.content.Context;
import android.widget.Toast;

import com.crgt.protocol.AbsProtocolProcessor;


/**
 *
 * @author jesse.lu
 * @Date 2019/6/15
 * @mail： jesse.lu@foxmail.com
 */
public class DefaultProcessor extends AbsProtocolProcessor {
    @Override
    public boolean process(Context context) {
        Toast.makeText(context,"默认协议处理器处理中...", Toast.LENGTH_SHORT).show();
        return true;
    }


}
