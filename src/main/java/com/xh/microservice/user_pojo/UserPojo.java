package com.xh.microservice.user_pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPojo {

    private String userId;

    private String userName;

    private String password;

    private String userPhone;
}
