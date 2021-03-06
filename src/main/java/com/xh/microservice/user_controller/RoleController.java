package com.xh.microservice.user_controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xh.microservice.common.controller.BasicController;
import com.xh.microservice.common.result.R;
import com.xh.microservice.common.support.Query;
import com.xh.microservice.user_entity.Menu;
import com.xh.microservice.user_entity.Role;
import com.xh.microservice.user_entity.User;
import com.xh.microservice.user_pojo.RoleMenuPojo;
import com.xh.microservice.user_pojo.RolePojo;
import com.xh.microservice.user_pojo.UserPojo;
import com.xh.microservice.user_service.RoleService;
import com.xh.microservice.user_service.UserService;
import com.xh.microservice.user_vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/role")
public class RoleController extends BasicController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/list")
    public R<IPage<Role>> listRole(@RequestParam(value = "roleName", required = false) String roleName, @RequestBody Query query){
        final IPage<Role> listRole = roleService.listRole(roleName, query);
        return R.data(listRole);
    }

    @GetMapping("/detail/{roleId}")
    public R detailRole(@PathVariable String roleId){
        Assert.notNull(roleId, "角色id不能为空!");
        final Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleId, roleId));
        return R.data(role);
    }

    @PostMapping("/add")
    public R addRole(@RequestBody RolePojo rolePojo){
        try {
            //TODO rolePojo字段非空校验
            Assert.notNull(rolePojo, "角色信息不能为空！");
            Assert.isTrue(!StringUtils.isEmpty(rolePojo.getRoleName()), "角色名不能为空！");

            final Role addRole = roleService.addRole(rolePojo);
            return addRole == null ? R.fail("添加角色失败") : R.success(addRole);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/update")
    public R updateRole(@RequestBody RolePojo rolePojo){
        try {
            Assert.notNull(rolePojo, "角色信息不能为空！");
            Assert.isTrue(!StringUtils.isEmpty(rolePojo.getRoleName()), "角色名不能为空！");
            int count = roleService.updateUser(rolePojo);
            return count > 0 ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @GetMapping("/delete/{roleId}")
    public R deleteRole(@PathVariable String roleId) {
        Assert.notNull(roleId, "角色id不能为空！");
        try {
            final boolean result = roleService.deleteRole(roleId);
            return result ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/deleteBatch")
    public R deleteRoleBatch(@RequestParam("roleIds") List<String> roleIds) {
        try {
            Assert.notNull(roleIds, "角色ids不能为空！");
            final boolean result = roleService.deleteUserBatch(roleIds);
            return result ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @GetMapping("/menu/{roleId}")
    public R listMenu(@PathVariable String roleId) {
        try {
            Assert.notNull(roleId, "角色id不能为空！");
            final RoleMenuPojo roleMenu = roleService.listMenu(roleId);
            return R.data(roleMenu);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/menu/bind")
    public R bindMenu(@RequestBody RoleMenuPojo roleMenuPojo) {
        try {
            Assert.notNull(roleMenuPojo, "角色菜单信息不能为空！");
            Assert.notNull(roleMenuPojo.getRoleId(), "角色id不能为空！");
            Assert.notNull(roleMenuPojo.getMenuList(), "菜单信息不能为空！");
            final boolean result = roleService.bindMenu(roleMenuPojo);
            return result ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}
