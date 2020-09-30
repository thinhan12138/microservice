package com.xh.microservice.user_controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.microservice.common.controller.BasicController;
import com.xh.microservice.common.result.R;
import com.xh.microservice.common.support.Query;
import com.xh.microservice.user_entity.User;
import com.xh.microservice.user_pojo.UserPojo;
import com.xh.microservice.user_service.UserService;
import com.xh.microservice.user_vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public R<IPage<UserVo>> listUser(@RequestParam(value = "userName", required = false) String userName,
                                     @RequestBody Query query){
        final IPage<UserVo> listUser = userService.listUser(userName, query);
        return R.data(listUser);
    }

    @PostMapping("/detail")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public R detailUser(@RequestParam("userId") String userId){
        Assert.notNull(userId, "用户id不能为空!");
        final UserVo userVo = userService.getUserById(userId);
        return R.data(userVo);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public R addUser(@RequestBody UserPojo userPojo){
        //TODO userPojo字段非空校验
        Assert.notNull(userPojo, "用户信息不能为空！");
        Assert.notNull(userPojo.getUserName(), "用户名不能为空！");
        Assert.notNull(userPojo.getPassword(), "用户密码不能为空！");
        try {
            final User addUser = userService.addUser(userPojo);
            return addUser == null ? R.fail("添加用户失败") : R.success(addUser);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public R updateUser(@RequestBody UserPojo userPojo){
        //TODO userPojo字段非空校验
        Assert.notNull(userPojo, "用户信息不能为空！");
        Assert.notNull(userPojo.getUserId(), "用户id不能为空！");
        try {
            int count = userService.updateUser(userPojo);
            return count > 0 ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public R deleteUser(@RequestParam("userId") String userId) {
        Assert.notNull(userId, "用户id不能为空！");
        try {
            final boolean result = userService.deleteUser(userId);
            return result ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/deleteBatch")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public R deleteUserBatch(@RequestParam("userIds") List<String> userIds) {
        Assert.notNull(userIds, "用户ids不能为空！");
        try {
            final boolean result = userService.deleteUserBatch(userIds);
            return result ? R.success() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}
