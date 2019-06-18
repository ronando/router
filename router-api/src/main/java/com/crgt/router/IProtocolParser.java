package com.crgt.router;

import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * interface of protocol parser
 *
 * @author android
 * @date 2019/5/27
 * @mail android@crgecent.com
 */

public interface IProtocolParser {

    /**
     * parse router path
     *
     * @param protocol
     * @return
     */
    String parsePath(@NonNull Uri protocol);

}
