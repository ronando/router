package com.crgt.router.autofillparam;

/**
 * 每个组件实现该接口提供路由参数填充的provider
 * <p>
 * Created by lujie on 2019/6/13.
 * jesse.lu@crgecent.com
 */
public interface IParamProvider {

    /**
     * 自动填充参数,由各组件分别实现自己的自动填充器
     * @param postcard
     */
    void provideParam(MethodPostcard postcard);

}
