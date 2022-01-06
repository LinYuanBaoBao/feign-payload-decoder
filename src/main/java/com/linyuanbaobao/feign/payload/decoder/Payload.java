package com.linyuanbaobao.feign.payload.decoder;

/**
 * @author linyuan - 765371578@qq.com
 * @since 2022/1/18
 */
public interface Payload<T> {
    T parseData();
}
