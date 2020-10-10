package com.xh.microservice.user_entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xh.microservice.common.entity.BasicEntity;
import lombok.Data;

/**
 * @Authore: x18266
 * @Description:
 * @Date: Created in 18:41 2020/10/08
 */
@TableName("role_menu")
@Data
public class RoleMenu {
    @TableId("menu_id")
    private String menuId;

    @TableField("role_id")
    private String roleId;
}
