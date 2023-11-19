package dev.dulred.mall.mallerp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("dev.dulred.mall.mallerp.repository") //添加 @Mapper 注解
public class MallErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallErpApplication.class, args);
	}

}
