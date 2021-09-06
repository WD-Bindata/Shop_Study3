package com.wangd.interceptor;

import com.wangd.utils.TokenUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangd
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        String authorization = request.getHeader("Authorization");
//        if (request.getRequestURI().contains("login")){
//            return true;
//        }
        if (authorization == null){

            return false;
        }
        authorization = request.getHeader("Authorization").replaceAll("Bearer ", "");
        if (authorization != null){

            boolean result = TokenUtils.verify(authorization);
            if (result){
                return true;
            }
        }

        return false;
    }
}
