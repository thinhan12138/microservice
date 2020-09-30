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
@TableName("role_menu")
@Data
public class RoleMenu {
    @TableId("role_id")
    private Integer roleId;

    @TableField("menu_id")
    private Integer menuId;
}
