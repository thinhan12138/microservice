package com.xh.microservice.user_service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.microservice.auth_security.SecurityUtils;
import com.xh.microservice.common.support.Query;
import com.xh.microservice.common.utils.StringUtils;
import com.xh.microservice.user_constants.UserConstants;
import com.xh.microservice.user_entity.Role;
import com.xh.microservice.user_entity.RoleMenu;
import com.xh.microservice.user_mapper.RoleMapper;
import com.xh.microservice.user_mapper.RoleMenuMapper;
import com.xh.microservice.user_pojo.MenuPojo;
import com.xh.microservice.user_pojo.RoleMenuPojo;
import com.xh.microservice.user_pojo.RolePojo;
import com.xh.microservice.user_service.RoleMenuService;
import com.xh.microservice.user_service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 20:42 2020/9/27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public IPage<Role> listRole(String roleName, Query query) {
        roleName = roleName == null ? "" : roleName;
        Page<Role> page = new Page<>();
        page.setAsc(StringUtils.toStrArray(query.getAscs()));
        page.setDesc(StringUtils.toStrArray(query.getDescs()));
        page.setSize(query.getPageSize());
        page.setCurrent(query.getCurrent());
        IPage<Role> rolePage = this.baseMapper.findRoleList(page, roleName);
        return rolePage;
    }

    @Override
    @Transactional
    public Role addRole(final RolePojo rolePojo) {
        final String roleName = rolePojo.getRoleName();
        final int exists = this.baseMapper.selectCount(new QueryWrapper<Role>().lambda()
                .eq(Role::getRoleName, roleName)
                .eq(Role::getDeleted, UserConstants.USER_NOT_DELETED));
        Assert.isTrue(exists == 0, roleName + ", 该角色名已存在! ");
        Role addRole = new Role();
        addRole.setRoleId(IdWorker.getIdStr());
        addRole.setRoleName(roleName);
        addRole.setCreateTime(new Date());
        addRole.setCreateUser(SecurityUtils.getUsername());
        this.baseMapper.insert(addRole);
        return addRole;
    }

    @Override
    @Transactional
    public int updateUser(final RolePojo rolePojo) {
        final int exists = this.baseMapper.selectCount(new QueryWrapper<Role>().lambda()
                .eq(Role::getRoleName, rolePojo.getRoleName())
                .eq(Role::getDeleted, UserConstants.USER_NOT_DELETED)
                .ne(Role::getRoleId, rolePojo.getRoleId()));
        Assert.isTrue(exists == 0, "该角色名已存在! ");
        Role updateRole = this.baseMapper.selectOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleId, rolePojo.getRoleId()));
        Assert.notNull(updateRole, "该角色不存在！");
        updateRole.setRoleName(rolePojo.getRoleName());
        updateRole.setUpdateTime(new Date());
        updateRole.setUpdateUser(SecurityUtils.getUsername());
        return this.baseMapper.updateById(updateRole);
    }

    @Override
    @Transactional
    public boolean deleteRole(final String roleId) {
        Role delRole = this.baseMapper.selectOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleId, roleId));
        Assert.notNull(delRole, "该角色不存在！");
        delRole.setDeleted(UserConstants.USER_DELETED);
        return this.baseMapper.updateById(delRole) > 0;
    }

    @Override
    @Transactional
    public boolean deleteUserBatch(final List<String> roleIds) throws Exception{
        for(String roleId : roleIds){
            final boolean result = deleteRole(roleId);
            if(!result){
                throw new Exception("删除角色失败, 用户id: " + roleId);
            }
        }
        return true;
    }

    @Override
    public RoleMenuPojo listMenu(final String roleId) {
        List<MenuPojo> menuList = this.baseMapper.listMenuByRoleId(roleId);
        RoleMenuPojo roleMenuPojo = new RoleMenuPojo();
        roleMenuPojo.setRoleId(roleId);
        roleMenuPojo.setMenuList(menuList);
        return null;
    }

    @Override
    @Transactional
    public boolean bindMenu(final RoleMenuPojo roleMenuPojo) {
        final String roleId = roleMenuPojo.getRoleId();
        roleMenuService.remove(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, roleId));
        List<RoleMenu> roleMenuList = new ArrayList<>();
        roleMenuPojo.getMenuList().stream().forEach(menuPojo -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuPojo.getMenuId());
            roleMenuList.add(roleMenu);
        });
        return roleMenuService.saveBatch(roleMenuList);
    }
}
