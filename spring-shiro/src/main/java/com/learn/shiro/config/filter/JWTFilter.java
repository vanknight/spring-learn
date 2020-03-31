package com.learn.shiro.config.filter;

import com.learn.shiro.model.JWTToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(isLoginAttempt(request, response)){
            return executeLogin(request, response);
        }
        return true;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String auth = req.getHeader("Authorization");
        return auth != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest req = (HttpServletRequest) request;
        String auth = req.getHeader("Authorization");
        JWTToken token = new JWTToken(auth);
        getSubject(request, response).login(token);
        return true;
//        return super.executeLogin(request, response);
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
        res.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
        res.setHeader("Access-Control-Allow-Headers",req.getHeader("Access-Control-Request-Headers"));
        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            res.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
