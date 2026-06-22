package com.nursinghome.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        if (user == null) {
            // 判断是否是AJAX请求
            String requestedWith = request.getHeader("X-Requested-With");
            String accept = request.getHeader("Accept");
            boolean isAjax = "XMLHttpRequest".equals(requestedWith)
                    || (accept != null && accept.contains("application/json"));

            if (isAjax) {
                // AJAX请求返回JSON错误，而不是重定向到HTML登录页
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(401);
                response.getWriter().write("{\"code\":401,\"message\":\"登录已过期，请重新登录\",\"data\":null}");
            } else {
                // 页面请求：重定向到登录页
                response.sendRedirect("/login");
            }
            return false;
        }
        return true;
    }
}
