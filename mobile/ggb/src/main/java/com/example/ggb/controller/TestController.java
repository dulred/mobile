package com.example.ggb.controller;

import com.example.ggb.config.annotation.TokenToMallUser;
import com.example.ggb.entity.MallUser;
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


    @GetMapping(value = "/test1")
    @ApiOperation(value = "测试接口", notes = "方法中含有@TokenToMallUser注解")
    public Result<String> test1(@TokenToMallUser MallUser user) {
        System.out.println("99999999999999999999999999999999");
        //此接口含有@TokenToMallUser注解，即需要登陆验证的接口。
        Result result = null;
        if (user == null) {

            //如果通过请求header中的token未查询到用户的话即token无效，登陆验证失败，返回未登录错误码。
            result = ResultGenerator.genErrorResult(416, "未登录！");

            return result;
        } else {
            //登陆验证通过。
            result = ResultGenerator.genSuccessResult("登陆验证通过");
        }
        return result;
    }

    @GetMapping(value = "/test2")
    @ApiOperation(value = "测试接口", notes = "方法中无@TokenToMallUser注解")
    public Result<String> test2() {
        //此接口不含@TokenToMallUser注解，即访问此接口无需登陆验证，此类接口在实际开发中应该很少，为了安全起见应该所有接口都会做登陆验证。
        Result result = ResultGenerator.genSuccessResult("此接口无需登陆验证，请求成功");
        //直接返回业务逻辑返回的数据即可。
        return result;
    }



}
