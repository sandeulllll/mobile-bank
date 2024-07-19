package com.ccnu.mobilebank;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
<<<<<<< HEAD
import org.junit.jupiter.api.Test;
=======
import com.ccnu.mobilebank.mapper.PersoninfoMapper;
import com.ccnu.mobilebank.pojo.Personinfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 8c168996a5521210c139fa84b4594aecbf31b971
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;

@SpringBootTest
class MobileBankApplicationTests {
<<<<<<< HEAD

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
=======
    @Autowired
    private PersoninfoMapper personinfoMapper;

    @Test
    void contextLoads() {
        Personinfo personinfo = new Personinfo();
        personinfo.setId(1);
        personinfo.setSex(0);
        personinfo.setRealname("张三");
        personinfo.setAddress("许昌");
        personinfo.setBirthday("2000-01-01");
        personinfo.setMail("123456789@qq.com");
        personinfo.setTelephone("18600000022");
        personinfo.setCardId("123456789012345678");
        int result = personinfoMapper.insert(personinfo);
>>>>>>> 8c168996a5521210c139fa84b4594aecbf31b971
    }

}
