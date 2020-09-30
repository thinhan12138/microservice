package com.xh.microservice.common.controller;

import com.xh.microservice.auth_security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

public class BasicController {

    @Autowired
    private HttpServletRequest request;

    public BasicController() { }

    public HttpServletRequest getRequest() { return this.request; }

    public String getUserName(){
        return SecurityUtils.getUsername();
    }
}
