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
    }

    public static void main(String[] args) {

        

    }

}
