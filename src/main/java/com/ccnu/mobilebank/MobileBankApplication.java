package com.ccnu.mobilebank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ccnu.mobilebank.mapper")
public class MobileBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileBankApplication.class, args);
    }

}
