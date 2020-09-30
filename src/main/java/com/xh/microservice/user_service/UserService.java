package com.xh.microservice.user_service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.microservice.common.support.Query;
import com.xh.microservice.user_entity.User;
import com.xh.microservice.user_pojo.UserPojo;
import com.xh.microservice.user_vo.UserVo;

import java.util.List;
import java.util.Set;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 20:40 2020/9/27
 */
public interface UserService extends IService<User> {
    User findByUsername(String username);

    Set<String> findPermissions(String username);

    User addUser(UserPojo userPojo);

    int updateUser(UserPojo userPojo);

    boolean deleteUser(String userId);

    boolean deleteUserBatch(List<String> userIds) throws Exception;

    IPage<UserVo> listUser(String userName, Query query);

    UserVo getUserById(String userId);
}
