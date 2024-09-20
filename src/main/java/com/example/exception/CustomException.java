package com.example.exception;

import com.example.common.ResultCode;

// 自定义异常类
public class CustomException extends RuntimeException {
    // 异常代码
    private String code;
    // 异常信息
    private String msg;

    // 构造方法，接收 ResultCode 对象，设置异常代码和信息
    public CustomException(ResultCode resultCode) {
        this.code = resultCode.code;
        this.msg = resultCode.msg;
    }

    // 构造方法，接收自定义的代码和信息
    public CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 获取异常代码的方法
    public String getCode() {
        return code;
    }

    // 设置异常代码的方法
    public void setCode(String code) {
        this.code = code;
    }

    // 获取异常信息的方法
    public String getMsg() {
        return msg;
    }

    // 设置异常信息的方法
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
