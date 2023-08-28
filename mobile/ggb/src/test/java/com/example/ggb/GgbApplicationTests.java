package com.example.ggb;

import com.example.ggb.entity.TestUser;
import com.example.ggb.service.TestUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.Date;


@SpringBootTest
@Component
class GgbApplicationTests {

    @Autowired
    TestUserService testUserService;

  
    @Test
    void contextLoads() {
        TestUser testUser = testUserService.selectByPrimaryKey(1);
        System.out.println(testUser.toString());
    }

    public static void main(String[] args) {
        //当前时间
        Date now = new Date();
        System.out.println(now);
        //过期时间
        Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//过期时间 48 小时
        System.out.println(expireTime);

    }

}
