package com.crgt.router;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * Created by Gary on 18/2/9.
 */

public interface RouterInterceptor {

    interface Callback {

        /**
         * ignore interceptor
         */
        void onContinue();

        /**
         * intercept router
         */
        void onIntercept();
    }

    /**
     * interceptor of routing activity, you must invoke callback.onContinue() or callback.onIntercept()
     *
     * @param context activity or application
     * @param componentName 组件名，对应Roster的name。
     * @param param activity bundle data
     * @return
     */
    void intercept(Context context, String componentName, @Nullable ParamBuilder param, Callback callback);
}
