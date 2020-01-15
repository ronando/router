package com.crgt.protocol;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.crgt.protocol.model.ProtocolMap;


class ProtocolImpl {

    private static final String TAG = "ProtocolImpl";
    private volatile static ProtocolImpl mInstance;
    private IProtocolParser mProtocolParser;
    private ProtocolMap mProtocolMap = new ProtocolMap();
    private AbsProtocolProcessor mDefaultProcessor;
    private AbsPreProcessor mPreProcessor;
    private ProtocolCollectGenerator mProtocolGenerator;

    private ProtocolImpl() {
        collectProtocols();
    }

    public static ProtocolImpl getInstance() {
        if (mInstance == null) {
            synchronized (ProtocolImpl.class) {
                if (mInstance == null) {
                    mInstance = new ProtocolImpl();
                }
            }
        }
        return mInstance;
    }

    /**
     * 配置scheme， 指定使用host，还是path，还是两者同时来标识一个页面。
     * 比如常见协议格式：
     * 1，appname://pagename?param1=123&param2=123
     * 2，appname://segment1/segment2?param1=123&param2=123
     * 3，companyname://appname/pagename?param1=123&param2=123
     * 4，companyname://appname/segment1/segment2?param1=123&param2=123
     * 标识一个页面有三种选择：
     * HOST：适用1场景，使用pagename标识
     * PATH：适用3和4场景，使用pagename或者/segment1/segment2标识
     * HOST_AND_PATH：适用2场景，使用segment1/segment2标识
     *
     * @param protocolParser 自定义ProtocolParser ，
     */
    public void setParser(IProtocolParser protocolParser) {
        mProtocolParser = protocolParser;
    }

    /**
     * 收集协议
     */
    private synchronized void collectProtocols() {
        mProtocolGenerator = new ProtocolCollectGenerator();
        mProtocolGenerator.collect(mProtocolMap);
    }

    /**
     * 设置协议预处理器
     *
     * @param preProcessor
     */
    public void setPreProcessor(AbsPreProcessor preProcessor) {
        mPreProcessor = preProcessor;
    }

    /**
     * 设置默认处理器，在没有注册协议的情况下调用默认处理器
     *
     * @param processor
     */
    public void setDefaultProcessor(AbsProtocolProcessor processor) {
        mDefaultProcessor = processor;
    }

    /**
     * 跳转协议 （支持子线程调用）
     *
     * @param context  可以是Application Context或Activity Context.
     * @param protocol 协议URI
     */
    public synchronized void gotoProtocol(Context context, String protocol) {
        if (mProtocolParser == null) {
            Toast.makeText(context, "Protocol Parser is null!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!checkProtocol(protocol)) {
            Log.e(TAG, "com.crgt.protocol illegal:" + protocol);
            return;
        }

        if (preProcess(context, protocol)) {
            return;
        }

        String processorClass = mProtocolMap.get(mProtocolParser.parsePath(Uri.parse(protocol)));
        if (TextUtils.isEmpty(processorClass)) {
            doProcess(context, mDefaultProcessor, protocol);
            return;
        }
        process(context, processorClass, protocol);
    }

    private boolean checkProtocol(String protocol) {
        Uri uri;
        try {
            uri = Uri.parse(protocol);
        } catch (Exception ignore) {
            return false;
        }
        if (uri == null) {
            return false;
        }
        return true;
    }

    private boolean preProcess(Context context, String protocol) {
        if (mPreProcessor == null) {
            return false;
        }
        mPreProcessor.protocol = protocol;
        Uri uri = Uri.parse(protocol);
        mPreProcessor.scheme = uri.getScheme();
        mPreProcessor.host = uri.getHost();
        mPreProcessor.path = uri.getPath();
        mPreProcessor.setUri(uri);
        return mPreProcessor.preProcess(context);
    }


    private void process(Context context, String processorClass, String protocol) {
        AbsProtocolProcessor processor = mDefaultProcessor;
        try {
            processor = (AbsProtocolProcessor) Class.forName(processorClass).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processor != null) {
            doProcess(context, processor, protocol);
        }
    }


    private void doProcess(Context context, AbsProtocolProcessor processor, String protocol) {
        if (processor != null) {
            processor.protocol = protocol;
            Uri uri = Uri.parse(protocol);
            processor.scheme = uri.getScheme();
            processor.host = uri.getHost();
            processor.path = uri.getPath();
            processor.setUri(uri);
            processor.parseParameters();
            processor.process(context);
        }
    }


}
