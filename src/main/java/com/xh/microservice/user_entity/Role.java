package com.xh.microservice.user_entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Authore: x18266
 * @Description:
 * @Date: Created in 22:06 2020/9/20
 */
@TableName("base_role")
public class Role {
    @TableId("role_id")
    private Integer roleId;

    @TableField("role_name")
    private String roleName;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
