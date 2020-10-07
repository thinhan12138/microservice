package com.xh.microservice.user_service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.microservice.common.support.Query;
import com.xh.microservice.user_entity.Menu;
import com.xh.microservice.user_pojo.MenuPojo;

import java.util.List;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 18:41 2020/10/07
 */
public interface MenuService extends IService<Menu> {

    IPage<Menu> listMenu(String menuName, Query query);

    Menu addMenu(MenuPojo menuPojo);

    int updateMenu(MenuPojo menuPojo);

    boolean deleteMenu(String menuId);

    boolean deleteMenuBatch(List<String> menuIds) throws Exception;
}
