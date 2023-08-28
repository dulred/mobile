package com.example.ggb.controller;

import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.controller.param.MallUserLoginParam;
import com.example.ggb.service.MallUserService;
import com.example.ggb.util.NumberUtil;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "v1",tags = "商城用户接口")
@RequestMapping(value ="/api/v1")
public class MallPersonalAPI {
    @Autowired
    MallUserService mallUserService;

    /**
     * 登录
     *
     * @param mallUserLoginParam 商城用户登录参数
     * @return @return {@code Result<String> }
     * @author dulred
     * @date 2023/08/27
     */
    @PostMapping("/user/login")
    @ApiOperation("用户登录")
    public Result<String>login(@RequestBody @Valid MallUserLoginParam mallUserLoginParam){

        if (!NumberUtil.isPhone(mallUserLoginParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }

        if(mallUserService.selectByLoginName(mallUserLoginParam.getLoginName())==null){
            return ResultGenerator.genFailResult("用户不存在");
        }

        if (mallUserLoginParam.getPasswordSha256().equals(mallUserService.selectByLoginName(mallUserLoginParam.getLoginName()).getPasswordSha256()) )
        {
            return ResultGenerator.genSuccessResult("asfasfsafsafas");
        }

        return ResultGenerator.genFailResult("密码错误");
  

    }




}
