package com.xh.microservice.user_controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xh.microservice.common.controller.BasicController;
import com.xh.microservice.common.result.R;
import com.xh.microservice.common.support.Query;
import com.xh.microservice.user_entity.Menu;
import com.xh.microservice.user_pojo.MenuPojo;
import com.xh.microservice.user_service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/menu")
public class MenuController extends BasicController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/list")
    public R<IPage<Menu>> listMenu(@RequestParam(value = "menuName", required = false) String menuName, @RequestBody Query query){
        final IPage<Menu> listMenu = menuService.listMenu(menuName, query);
        return R.data(listMenu);
    }

    @GetMapping("/detail/{menuId}")
    public R detailRole(@PathVariable String menuId){
        Assert.notNull(menuId, "菜单id不能为空!");
        final Menu menu = menuService.getOne(new QueryWrapper<Menu>().lambda().eq(Menu::getMenuId, menuId));
        return R.data(menu);
    }

    @PostMapping("/add")
    public R addRole(@RequestBody MenuPojo menuPojo){
        try {
            //TODO menuPojo字段非空校验
            Assert.notNull(menuPojo, "菜单信息不能为空！");
            Assert.isTrue(!StringUtils.isEmpty(menuPojo.getMenuName()), "菜单名不能为空！");
            final Menu addMenu = menuService.addMenu(menuPojo);
            return addMenu == null ? R.fail("添加角色失败") : R.success(addMenu);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/update")
    public R updateRole(@RequestBody MenuPojo menuPojo){
        try {
            Assert.notNull(menuPojo, "菜单信息不能为空！");
            Assert.isTrue(!StringUtils.isEmpty(menuPojo.getMenuName()), "菜单名不能为空！");
            int count = menuService.updateMenu(menuPojo);
            return count > 0 ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @GetMapping("/delete/{menuId}")
    public R deleteRole(@PathVariable String menuId) {
        Assert.notNull(menuId, "菜单id不能为空！");
        try {
            final boolean result = menuService.deleteMenu(menuId);
            return result ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/deleteBatch")
    public R deleteRoleBatch(@RequestParam("menuIds") List<String> menuIds) {
        try {
            Assert.notNull(menuIds, "菜单ids不能为空！");
            final boolean result = menuService.deleteMenuBatch(menuIds);
            return result ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}
