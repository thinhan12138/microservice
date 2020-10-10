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
import com.xh.microservice.user_entity.Role;
import com.xh.microservice.user_entity.User;
import com.xh.microservice.user_mapper.UserMapper;
import com.xh.microservice.user_pojo.UserPojo;
import com.xh.microservice.user_service.RoleService;
import com.xh.microservice.user_service.UserService;
import com.xh.microservice.user_vo.UserVo;
import com.xh.microservice.user_vo.UserWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @Author: x18266
 * @Description:
 * @Date: Created in 20:41 2020/9/27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RoleService roleService;

    private static final String ROLE_PREIX = "ROLE_";

    @Override
    public User findByUsername(String username) {
        final User user = this.baseMapper.findByUsername(username);
        return user;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> perms = new HashSet<>();
        final User user = this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, username));
        final Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleId, user.getRoleId()));
        if ( null != role ) {
            perms.add(ROLE_PREIX + role.getRoleName().toUpperCase());
        }
        return perms;
    }

    @Override
    public IPage<UserVo> listUser(String userName, Query query) {
        userName = userName == null ? "" : userName;
        Page<User> page = new Page<>();
        page.setAsc(StringUtils.toStrArray(query.getAscs()));
        page.setDesc(StringUtils.toStrArray(query.getDescs()));
        page.setSize(query.getPageSize());
        page.setCurrent(query.getCurrent());

//        IPage<User> userPage = this.baseMapper.findUserList(page, userName);
        IPage<User> userPage = this.baseMapper.selectPage(page, new QueryWrapper<User>().lambda()
                                .eq(User::getDeleted, UserConstants.USER_NOT_DELETED)
                                .like(User::getUserName, userName));



        IPage<UserVo> userVoPage = UserWrapper.build().wrapperPage(userPage);
        return userVoPage;
    }

    @Override
    public UserVo getUserById(final String userId) {
        final User user = this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserId, userId));
        return user == null ? null : UserWrapper.build().wrapper(user);
    }

    @Override
    @Transactional
    public User addUser(final UserPojo userPojo){
        final String userName = userPojo.getUserName();
        final int exists = this.baseMapper.selectCount(new QueryWrapper<User>().lambda()
                .eq(User::getUserName, userName)
                .eq(User::getDeleted, UserConstants.USER_NOT_DELETED));
        Assert.isTrue(exists == 0, userName + ", 该用户名已存在! ");
        User addUser = new User();
        addUser.setUserId(IdWorker.getIdStr());
        BeanUtils.copyProperties(userPojo, addUser);
        addUser.setPassword(new BCryptPasswordEncoder().encode(addUser.getPassword()));
        addUser.setRoleId(UserConstants.DEFAULT_ROLE_ID);
        addUser.setCreateTime(new Date());
        addUser.setCreateUser(SecurityUtils.getUsername());
        this.baseMapper.insert(addUser);
        return addUser;
    }

    @Override
    @Transactional
    public int updateUser(final UserPojo userPojo) {
        final int exists = this.baseMapper.selectCount(new QueryWrapper<User>().lambda()
                .eq(User::getUserName, userPojo.getUserName())
                .eq(User::getDeleted, UserConstants.USER_NOT_DELETED)
                .ne(User::getUserId, userPojo.getUserId()));
        Assert.isTrue(exists == 0, "该用户名已存在! ");
        User updateUser = this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserId, userPojo.getUserId()));
        Assert.notNull(updateUser, "该用户不存在!");
        updateUser.setUserName(userPojo.getUserName());
        updateUser.setPassword(userPojo.getPassword());
        updateUser.setUserPhone(userPojo.getUserPhone());
        updateUser.setUpdateTime(new Date());
        updateUser.setUpdateUser(SecurityUtils.getUsername());
        return this.baseMapper.updateById(updateUser);
    }

    @Override
    @Transactional
    public boolean deleteUser(final String userId) {
        User delUser = this.baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserId, userId));
        Assert.notNull(delUser, "该用户不存在！");
        delUser.setDeleted(UserConstants.USER_DELETED);
        return this.baseMapper.updateById(delUser) > 0;
    }

    @Override
    @Transactional
    public boolean deleteUserBatch(List<String> userIds) throws Exception{
        for(String userId : userIds){
            final boolean result = deleteUser(userId);
            if(!result){
                throw new Exception("删除用户失败, 用户id: " + userId);
            }
        }
        return true;
    }

    @Override
    public List<String> findPermissionsByUserId(String userId) {
        Assert.notNull(userId, "用户id不能为空!");
        return this.baseMapper.findPermissionsByUserId(userId);
    }
}
