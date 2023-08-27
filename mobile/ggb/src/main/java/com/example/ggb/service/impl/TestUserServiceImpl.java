package com.example.ggb.service.impl;

import com.example.ggb.entity.TestUser;
import com.example.ggb.repository.TestUserMapper;
import com.example.ggb.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUserServiceImpl implements TestUserService {

    @Autowired
    TestUserMapper testUserMapper;
    @Override
    public TestUser selectByPrimaryKey(int id) {
       TestUser testUser= testUserMapper.selectByPrimaryKey(id);
        System.out.println(testUser.toString());
        return testUser;
    }
}
