package com.crgt.protocol;

import android.content.Context;

import com.crgt.router.ParamBuilder;

/**
 * 协议框架入口
 *
 * @author jesse.lu
 * @Date 2019/6/16
 * @mail： jesse.lu@foxmail.com
 */
public class Protocol {

    public static void setParser(IProtocolParser protocolParser) {
        ProtocolImpl.getInstance().setParser(protocolParser);
    }

    public static void setPreProcessor(AbsPreProcessor preProcessor) {
        ProtocolImpl.getInstance().setPreProcessor(preProcessor);
    }

    public static void setDefaultProcessor(AbsProtocolProcessor processor) {
        ProtocolImpl.getInstance().setDefaultProcessor(processor);
    }

    public static void gotoProtocol(Context context, String protocol, ParamBuilder param) {
        ProtocolImpl.getInstance().gotoProtocol(context, protocol, param);
    }
}
