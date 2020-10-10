package com.xh.microservice.auth_controller;

import com.xh.microservice.auth_security.JwtAuthenticatioToken;
import com.xh.microservice.auth_security.SecurityUtils;
import com.xh.microservice.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 16:25 2020/9/26
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录接口
     */
    @GetMapping(value = "/login")
    public R login(@RequestParam("username") String username, @RequestParam("password") String password,
                   HttpServletRequest request) throws IOException {
        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
        return token == null ? R.fail("登录失败"):R.success(token.getToken(), "登录成功");
    }

    /**
     * 退出登录接口
     */
    @GetMapping(value = "/logout")
    public R logout() throws IOException {
        return R.data("退出登录");
    }
}
