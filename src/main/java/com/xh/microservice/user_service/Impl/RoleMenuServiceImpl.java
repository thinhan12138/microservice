package com.xh.microservice.user_service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.microservice.user_entity.RoleMenu;
import com.xh.microservice.user_mapper.RoleMenuMapper;
import com.xh.microservice.user_service.RoleMenuService;
import org.springframework.stereotype.Service;


/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 20:42 2020/10/10
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
}
