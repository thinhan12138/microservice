package com.xh.microservice.user_vo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.microservice.common.wrapper.BaseWrapper;
import com.xh.microservice.user_entity.Role;
import com.xh.microservice.user_entity.User;
import com.xh.microservice.user_service.RoleService;
import com.xh.microservice.user_service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserWrapper implements BaseWrapper<UserVo, User> {

    private static RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService){
        UserWrapper.roleService = roleService;
    }

    public static UserWrapper build(){ return new UserWrapper(); }

    @Override
    public UserVo wrapper(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        final Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleId, user.getRoleId()));
        if (role != null) {
            userVo.setRoleName(role.getRoleName());
        }
        return userVo;
    }
}
