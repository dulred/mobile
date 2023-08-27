package com.example.ggb.service.impl;

import com.example.ggb.entity.MallUser;
import com.example.ggb.repository.MallUserMapper;
import com.example.ggb.service.MallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MallUserServiceImpl  implements MallUserService {
    @Autowired
    MallUserMapper mallUserMapper;
    @Override
    public MallUser selectByPrimaryKey(Long id) {
        return mallUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public MallUser selectByLoginName(String loginName) {
        return  mallUserMapper.selectByLoginName(loginName);
    }
}
