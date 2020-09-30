package com.xh.microservice.user_vo;

import com.xh.microservice.user_entity.User;
import lombok.Data;

@Data
public class UserVo extends User {
    private String roleName;
}
