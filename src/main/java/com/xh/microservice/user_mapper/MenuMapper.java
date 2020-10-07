package com.xh.microservice.user_mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.microservice.user_entity.Menu;
import com.xh.microservice.user_entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 22:18 2020/9/20
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
}
