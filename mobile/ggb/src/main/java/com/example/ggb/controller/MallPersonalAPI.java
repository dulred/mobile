package com.example.ggb.controller;

import com.example.ggb.common.Contants;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.controller.param.MallUserLoginParam;
import com.example.ggb.service.MallUserService;
import com.example.ggb.util.NumberUtil;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    private static final Logger logger = LoggerFactory.getLogger(MallPersonalAPI.class);

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
//  验证手机
        if (!NumberUtil.isPhone(mallUserLoginParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }

        String loginResult =mallUserService.login(mallUserLoginParam.getLoginName(),mallUserLoginParam.getPasswordSha256());

        logger.info("login api,loginName={},loginResult={}", mallUserLoginParam.getLoginName(), loginResult);
//登录成功
        if (StringUtils.hasText(loginResult) && loginResult.length() == Contants.TOKEN_LENGTH ){
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }

//登录失败
        return ResultGenerator.genFailResult(loginResult);
  

    }




}
