package com.xh.microservice.user_entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xh.microservice.common.entity.BasicEntity;
import lombok.Builder;
import lombok.Data;

/**
 * @Authore: x18266
 * @Description:
 * @Date: Created in 22:06 2020/9/20
 */
@TableName("base_user")
@Data
public class User extends BasicEntity {

    private static final long serialVersion = 1L;

    @TableId("user_id")
    private String userId;

    @TableField("user_name")
    private String userName;

    @TableField("user_phone")
    private String userPhone;

    @TableField("role_id")
    private String roleId;

    @TableField("password")
    private String password;
}
