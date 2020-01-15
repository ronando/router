package com.crgt.demoprotocol;

import android.content.Context;
import android.widget.Toast;

import com.crgt.protocol.AbsPreProcessor;


/**
 * 协议预处理，拦截掉
 *
 * @author jesse.lu
 * @Date 2019/6/15
 * @mail： jesse.lu@foxmail.com
 */
public class PreProcessorInterrupt extends AbsPreProcessor {
    @Override
    public boolean preProcess(Context context) {
        //do something
        Toast.makeText(context, "协议预处理器处理中(已拦截)...", Toast.LENGTH_SHORT).show();
        return true;
    }
}
