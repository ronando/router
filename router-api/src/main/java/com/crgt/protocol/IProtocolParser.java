package com.crgt.protocol;

import android.net.Uri;

/**
 * interface of com.crgt.protocol parser
 *
 * @author jesse.lu
 * @date 2019/5/27
 * @mail jesse.lu@foxmail.com
 */

public interface IProtocolParser {

    /**
     * parse router path
     *
     * @param protocol
     * @return
     */
    String parsePath(Uri protocol);

}
