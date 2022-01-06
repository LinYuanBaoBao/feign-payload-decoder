package com.linyuanbaobao.feign.payload.decoder;

import java.util.Date;

/**
 * @author linyuan - 765371578@qq.com
 * @since 2022/1/6
 */
public class DefaultPayload<T> implements Payload<T> {

    private Integer code;

    private Boolean success;

    private String message;

    private Date timestamp;

    private T data;

    @Override
    public T parseData() {
        return this.data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

