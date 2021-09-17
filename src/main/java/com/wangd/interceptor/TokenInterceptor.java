package com.wangd.interceptor;

import com.sun.javafx.util.Utils;
import com.sun.tools.javac.util.StringUtils;
import com.wangd.utils.RequestResult;
import com.wangd.utils.TokenUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.web.servlet.HandlerInterceptor;
import sun.jvm.hotspot.runtime.Bytes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @author wangd
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        if ("OPTIONS".equals(request.getMethod().toUpperCase()) || "PUT".equals(request.getMethod().toUpperCase())){

            return true;
        }
        System.out.println("request = " + request.getMethod());
        String authorization = request.getHeader("Authorization");
        System.out.println("校验字段  authorization = " + authorization);
//        if (request.getRequestURI().contains("login")){
//            return true;
//        }
        if (authorization == null){

            PrintWriter writer = response.getWriter();
            RequestResult result = new RequestResult();
            result.data = null;
            result.status = 302;
            result.msg = "校验Token失败 请重新登录";
            writer.write(result.getResult());
            return false;
        }
        authorization = request.getHeader("Authorization").replaceAll("Bearer ", "");
        if (authorization != null){
            System.out.println("校验 authorization = " + authorization);
            boolean result = TokenUtils.verify(authorization);
            System.out.println("result = " + result);
            if (result){
                return true;
            }
        }
        PrintWriter writer = response.getWriter();
        RequestResult result = new RequestResult();
        result.data = null;
        result.status = 302;
        result.msg = "校验Token失败 请重新登录";
        writer.write(result.getResult());
        return false;
    }
}
