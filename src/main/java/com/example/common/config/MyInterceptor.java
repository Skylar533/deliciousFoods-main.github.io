package com.example.common.config;

import com.example.entity.Account;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * @author nsw
 */
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 请求处理前检查 session 中是否有用户信息，没有则重定向到登录页并返回 false，有则返回 true 继续处理请求。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("/end/page/login.html");
            return false;
        }
        return true;
    }
    /**
     * 请求处理后，暂未实现具体逻辑。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }
    /**
     * 请求完成后，暂未实现具体逻辑。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }
}