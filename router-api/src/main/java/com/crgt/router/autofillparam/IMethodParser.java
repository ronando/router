package com.crgt.router.autofillparam;

import android.net.Uri;

import java.util.Map;

/**
 * 暴露给业务层解析method相关接口
 *
 * Created by lujie on 2019/6/13.
 * jesse.lu@crgecent.com
 */

public interface IMethodParser {

    /**
     * 解析服务path
     *
     * @param rawUri 服务端下发的provider uri
     * @return 服务提供者路径
     */
    String getServicePath(Uri rawUri);

    /**
     * 解析获取具体方法名称
     *
     * @param rawUri 服务端下发的provider uri
     * @return 具体方法名称
     */
    String getMethodName(Uri rawUri);

    /**
     * 解析获取参数
     *
     * @param rawUri 服务端下发的provider uri
     * @return 服务端下发的uri 里面包含的参数
     */
    Map<String, String> getInputParams(Uri rawUri);
}
