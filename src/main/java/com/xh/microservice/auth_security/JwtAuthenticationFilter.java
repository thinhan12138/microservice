package com.xh.microservice.auth_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: x18266
 * @Description: 登录认证流程过滤器
 * @Date: Created in 16:20 2020/9/26
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //过滤检查token
        SecurityUtils.checkAuthentication(request);

        final String requestURI = request.getRequestURI();
        if(!requestURI.equalsIgnoreCase("/login") && !requestURI.equalsIgnoreCase("/logout")){
            final Authentication authentication = SecurityUtils.getAuthentication();
            if(null != authentication){
                Collection<GrantedAuthorityImpl> authorities = (Collection<GrantedAuthorityImpl>) authentication.getAuthorities();
                List<String> authorityList = authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
                if (null != authorityList && authorityList.size() > 0 && authorityList.contains(requestURI)) {
                } else {
                    accessFailure(request, response);
                    return;
                }
            } else {
                accessFailure(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private void accessFailure(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.getWriter().print("Unauthorized...");
    }
}
