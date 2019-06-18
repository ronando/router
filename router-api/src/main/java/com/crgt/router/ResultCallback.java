package com.crgt.router;

/**
 * 路由跳转回调
 *
 * @author android
 * @date 2019/5/8
 * @mail android@crgecent.com
 */

public interface ResultCallback {

    enum ResultState {
        OK,
        FAILED,
        CANCELED
    }

    public void onResult(ResultState state, Object data);
}
