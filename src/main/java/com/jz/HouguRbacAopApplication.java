package com.jz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jz.mapper")
public class HouguRbacAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouguRbacAopApplication.class, args);
    }

}
