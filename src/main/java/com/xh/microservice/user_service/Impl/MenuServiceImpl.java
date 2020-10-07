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
import com.xh.microservice.user_entity.Menu;
import com.xh.microservice.user_mapper.MenuMapper;
import com.xh.microservice.user_pojo.MenuPojo;
import com.xh.microservice.user_pojo.RolePojo;
import com.xh.microservice.user_service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;


/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 18:42 2020/10/07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public IPage<Menu> listMenu(String menuName, Query query) {
        menuName = menuName == null ? "" : menuName;
        Page<Menu> page = new Page<>();
        page.setAsc(StringUtils.toStrArray(query.getAscs()));
        page.setDesc(StringUtils.toStrArray(query.getDescs()));
        page.setSize(query.getPageSize());
        page.setCurrent(query.getCurrent());

        IPage<Menu> menuPage = this.baseMapper.selectPage(page, new QueryWrapper<Menu>().lambda()
                                                                .eq(Menu::getDeleted, UserConstants.USER_NOT_DELETED)
                                                                .like(Menu::getMenuName, menuName));
        return menuPage;
    }

    @Override
    @Transactional
    public Menu addMenu(MenuPojo menuPojo) {
        final String menuName = menuPojo.getMenuName();
        final int exists = this.baseMapper.selectCount(new QueryWrapper<Menu>().lambda()
                .eq(Menu::getMenuName, menuName)
                .eq(Menu::getDeleted, UserConstants.USER_NOT_DELETED));
        Assert.isTrue(exists == 0, menuName + ", 该菜单名已存在! ");
        Menu addMenu = new Menu();
        addMenu.setMenuId(IdWorker.getIdStr());
        addMenu.setMenuName(menuName);
        addMenu.setCreateTime(new Date());
        addMenu.setCreateUser(SecurityUtils.getUsername());
        this.baseMapper.insert(addMenu);
        return addMenu;
    }

    @Override
    public int updateMenu(MenuPojo menuPojo) {
        Menu updateMenu = this.baseMapper.selectOne(new QueryWrapper<Menu>().lambda()
                                                .eq(Menu::getMenuId, menuPojo.getMenuId()));
        Assert.notNull(updateMenu, "该菜单不存在！");
        final int exists = this.baseMapper.selectCount(new QueryWrapper<Menu>().lambda()
                .eq(Menu::getMenuName, menuPojo.getMenuName())
                .eq(Menu::getDeleted, UserConstants.USER_NOT_DELETED)
                .ne(Menu::getMenuId, menuPojo.getMenuId()));
        Assert.isTrue(exists == 0, "该菜单名已存在! ");
        updateMenu.setMenuName(menuPojo.getMenuName());
        updateMenu.setUpdateTime(new Date());
        updateMenu.setUpdateUser(SecurityUtils.getUsername());
        return this.baseMapper.updateById(updateMenu);
    }

    @Override
    public boolean deleteMenu(String menuId) {
        Menu delMenu = this.baseMapper.selectOne(new QueryWrapper<Menu>().lambda().eq(Menu::getMenuId, menuId));
        Assert.notNull(delMenu, "该菜单不存在！");
        delMenu.setDeleted(UserConstants.USER_DELETED);
        return this.baseMapper.updateById(delMenu) > 0;
    }

    @Override
    public boolean deleteMenuBatch(List<String> menuIds) throws Exception{
        for(String menuId : menuIds){
            final boolean result = deleteMenu(menuId);
            if(!result){
                throw new Exception("删除菜单失败, 菜单id: " + menuId);
            }
        }
        return true;
    }
}
