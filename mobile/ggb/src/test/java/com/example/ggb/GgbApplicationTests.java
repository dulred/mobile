package com.example.ggb;

import com.example.ggb.common.IndexConfigTypeEnum;
import com.example.ggb.entity.TestUser;
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
    }

    public static void main(String[] args) {

        System.out.println(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType());
        System.out.println(IndexConfigTypeEnum.INDEX_GOODS_HOT.getName());

    }

}
