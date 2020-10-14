package com.xh.microservice.user_mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.microservice.user_entity.Role;
import com.xh.microservice.user_pojo.MenuPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 22:18 2020/9/20
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    IPage<Role> findRoleList(Page<Role> page, @Param("roleName") String roleName);

    List<MenuPojo> listMenuByRoleId(@Param("roleId") String roleId);
}
