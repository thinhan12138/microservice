package com.xh.microservice.user_service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.microservice.common.support.Query;
import com.xh.microservice.user_entity.Role;
import com.xh.microservice.user_pojo.RolePojo;

import java.util.List;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 20:41 2020/9/27
 */
public interface RoleService extends IService<Role> {

    IPage<Role> listRole(String roleName, Query query);

    Role addRole(RolePojo rolePojo);

    int updateUser(RolePojo rolePojo);

    boolean deleteRole(String roleId);

    boolean deleteUserBatch(List<String> roleIds) throws Exception;
}
