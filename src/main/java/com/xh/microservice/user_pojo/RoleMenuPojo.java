package com.xh.microservice.user_pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuPojo {
    private String roleId;
    private List<MenuPojo> menuList;
}
