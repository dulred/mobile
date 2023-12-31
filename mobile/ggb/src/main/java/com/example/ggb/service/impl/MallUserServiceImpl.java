package com.example.ggb.service.impl;

import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.entity.MallUser;
import com.example.ggb.entity.MallUserToken;
import com.example.ggb.repository.MallUserMapper;
import com.example.ggb.repository.MallUserTokenMapper;
import com.example.ggb.repository.TestUserMapper;
import com.example.ggb.service.MallUserService;
import com.example.ggb.util.NumberUtil;
import com.example.ggb.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MallUserServiceImpl  implements MallUserService {
    @Autowired
    MallUserMapper mallUserMapper;
    @Autowired
    MallUserTokenMapper mallUserTokenMapper;
    @Autowired
    TestUserMapper testUserMapper;

    /**
     * 登录
     *
     * @param loginName      登录名
     * @param passwordSha256 密码sha256
     * @return @return {@code String }
     * @author dulred
     * @date 2023/08/29
     */
    @Override
    public String login(String loginName, String passwordSha256) {

        MallUser user  = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordSha256);

        if (user != null) {

            if (user.getLockedFlag() == 1){
                return ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult();
            }
// 登录后即执行修改token的操作
            MallUserToken mallUserToken  = mallUserTokenMapper.selectByPrimaryKey(user.getUserId());

            String token = genToken(System.currentTimeMillis()+ "",user.getUserId());
            Date now = new Date();
            Date expireTime = new Date(now.getTime() + 24*2*3600*1000);

            if (mallUserToken == null) {
                mallUserToken = new MallUserToken();
                mallUserToken.setUserId(user.getUserId());
                mallUserToken.setToken(token);
                mallUserToken.setExpireTime(expireTime);
                mallUserToken.setUpdateTime(now);
//                添加token记录后返回
                if (mallUserTokenMapper.insertSelective(mallUserToken)> 0) {
                    return token;
                }



            } else{
                mallUserToken.setToken(token);
                mallUserToken.setExpireTime(expireTime);
                mallUserToken.setUpdateTime(now);
//                添加token记录后返回
                if (mallUserTokenMapper.updateByPrimaryKeySelective(mallUserToken)> 0) {
                    return token;
                }
            }



        }


        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }


    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return @return {@code String }
     * @author dulred
     * @date 2023/08/29
     */
    private String genToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }

}
