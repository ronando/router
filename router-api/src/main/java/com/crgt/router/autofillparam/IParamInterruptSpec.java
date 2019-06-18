package com.crgt.router.autofillparam;

import android.net.Uri;

import com.crgt.router.ParamBuilder;

import java.util.List;

/**
 * Created by lujie on 2019/6/14.
 * jesse.lu@crgecent.com
 */
public interface IParamInterruptSpec {


    /**
     * 是否需要重定向到新的页面
     *
     * @param componentName 服务端下发的原有链接
     * @param paramBuilder  服务端下发的原有链接所带参数
     * @return true: 重定向  ; false:原有路径跳转
     */
    boolean redirect(String componentName, ParamBuilder paramBuilder);


    /**
     * 解析并返回目标页面地址
     *
     * @param componentName 原有链接的页面地址
     * @param param         原有链接的参数
     * @return 新的目标页面地址
     */
    Uri parsePagePath(String componentName, ParamBuilder param);

    /**
     * 解析并生成参数填充器
     *
     * @param componentName 原有链接目标页面地址
     * @param param         原有链接的参数
     * @return 参数填充器
     */
    List<Uri> parseProviders(String componentName, ParamBuilder param);

    /**
     * 解析并返回method参数封装
     *
     * @return method 上下文封装
     */
    IMethodParser getMethodParser();


}
