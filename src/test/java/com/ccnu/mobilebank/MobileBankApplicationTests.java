package com.ccnu.mobilebank;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;

@SpringBootTest
class MobileBankApplicationTests {

    @Test
    void contextLoads() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/bank", "root", "1234")
                .globalConfig(builder -> builder
                        .author("Baomidou")
                        .outputDir("E:\\java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.ccnu.mobilebank")
                        .entity("pojo")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
