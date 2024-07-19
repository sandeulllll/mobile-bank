package com.ccnu.mobilebank.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class VerificationCodeUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 生成验证码
     * @param key 键
     * @param expire 过期时间（秒）
     * @return 验证码
     */
    public String generateCode(String key, long expire) {
        // 生成四位数字的验证码
        String code = String.format("%04d", new Random().nextInt(9999));
        // 将验证码存储到 Redis 中
        redisTemplate.opsForValue().set(key, code, expire, TimeUnit.SECONDS);
        return code;
    }
}

