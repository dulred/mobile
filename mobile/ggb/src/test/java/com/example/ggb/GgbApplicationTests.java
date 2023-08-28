package com.example.ggb;

import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.entity.TestUser;
import com.example.ggb.entity.User;
import com.example.ggb.service.TestUserService;
import com.example.ggb.util.NumberUtil;
import com.example.ggb.util.ResultGenerator;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;


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
      System.out.println(System.currentTimeMillis());
    }

}
