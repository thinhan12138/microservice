package com.xh.microservice.user_mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xh.microservice.user_entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 22:18 2020/9/20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findByUsername(@Param("userName") String userName);

    IPage<User> findUserList(IPage<User> page, @Param("userName") String userName);

    List<String> findPermissionsByUserId(@Param("userId") String userId);
}
