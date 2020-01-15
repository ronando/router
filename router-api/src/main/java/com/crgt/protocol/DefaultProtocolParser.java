package com.crgt.protocol;

import android.net.Uri;

/**
 * default implement of IProtocolParser
 *
 * @author jesse.lu
 * @date 2019/5/27
 * @mail jesse.lu@foxmail.com
 */

public class DefaultProtocolParser implements IProtocolParser {

    private String mScheme;
    private ComponentIdentifier mIdentifier;

    public enum ComponentIdentifier {
        HOST,
        PATH,
        HOST_AND_PATH
    }

    /**
     * 配置 scheme， 指定使用 host ，还是 path，还是两者同时来标识一个页面。
     * 比如常见协议格式：
     * 1，appname://pagename?param1=123&param2=123
     * 2，appname://segment1/segment2?param1=123&param2=123
     * 3，companyname://appname/pagename?param1=123&param2=123
     * 4，companyname://appname/segment1/segment2?param1=123&param2=123
     * 标识一个页面有三种选择：
     * HOST：适用1场景，使用 pagename 标识
     * PATH：适用3和4场景，使用 pagename 或者 /segment1/segment2 标识
     * HOST_AND_PATH：适用2场景，使用 segment1/segment2 标识
     *
     * @param scheme
     * @param componentIdentifier
     */
    public DefaultProtocolParser(String scheme, ComponentIdentifier componentIdentifier) {
        mScheme = scheme;
        mIdentifier = componentIdentifier;
    }

    @Override
    public String parsePath(Uri uri) {
        if (!mScheme.equals(uri.getScheme())) {
            return null;
        }
        if (mIdentifier == ComponentIdentifier.HOST) {
            return uri.getHost();
        } else if (mIdentifier == ComponentIdentifier.PATH) {
            return uri.getPath();
        } else if (mIdentifier == ComponentIdentifier.HOST_AND_PATH) {
            return uri.getHost() + "/" + uri.getPath();
        }
        return null;
    }
}
