package com.example.ggb;

import com.example.ggb.util.NumberUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class GgbApplicationTests {

    @Test
    void contextLoads() {
        NumberUtil.isPhone2("138");
    }

}
