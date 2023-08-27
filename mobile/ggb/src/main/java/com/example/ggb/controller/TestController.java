package com.example.ggb.controller;

import com.example.ggb.entity.TestUser;
import com.example.ggb.service.TestUserService;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "v1",tags = "测试用户接口")
public class TestController {
    @Autowired
    TestUserService testUserService;

    /**
     * 测试
     *
     * @return @return {@code Result<TestUser> }
     * @author dulred
     * @date 2023/08/27
     */
    @ApiOperation("根据id查询用户")
    @GetMapping("/test")
    public Result<TestUser> testUser(int id) {
       TestUser testUser = testUserService.selectByPrimaryKey(id);
        return ResultGenerator.genSuccessResult(testUser);
    }
}
