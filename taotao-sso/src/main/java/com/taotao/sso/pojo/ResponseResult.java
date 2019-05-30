package com.taotao.sso.pojo;

import java.io.Serializable;

public class ResponseResult implements Serializable {
    private Integer status;
    private String message;
    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(Object data) {
        this.data = data;
    }

    public ResponseResult(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseResult(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
