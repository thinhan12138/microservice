package com.xh.microservice.user_service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.microservice.user_entity.Role;
import com.xh.microservice.user_mapper.RoleMapper;
import com.xh.microservice.user_service.RoleService;
import org.springframework.stereotype.Service;


/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 20:42 2020/9/27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
