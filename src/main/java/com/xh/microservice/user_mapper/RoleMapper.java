package com.xh.microservice.user_mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.microservice.user_entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 22:18 2020/9/20
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
