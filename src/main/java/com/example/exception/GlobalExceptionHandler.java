package com.example.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.common.Result;
import com.example.common.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

// 全局异常处理器类
@ControllerAdvice(basePackages = "com.example.controller")
public class GlobalExceptionHandler {
    // 日志记录器
    private static final Log log = LogFactory.get();

    // 处理通用异常
    @ExceptionHandler(Exception.class)
    @ResponseBody // 返回 JSON 串
    public Result error(HttpServletRequest request, Exception e) {
        // 记录异常信息
        log.error("异常信息：", e);
        return Result.error();
    }

    // 处理自定义异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody // 返回 JSON 串
    public Result customError(HttpServletRequest request, CustomException e) {
        // 返回包含自定义异常代码和信息的结果对象
        return Result.error(e.getCode(), e.getMsg());
    }
}
