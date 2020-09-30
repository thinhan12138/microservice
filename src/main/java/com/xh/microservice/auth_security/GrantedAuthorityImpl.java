package com.xh.microservice.auth_security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 17:07 2020/9/26
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
