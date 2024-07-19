package com.ccnu.mobilebank.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ccnu.mobilebank.exception.ConditionException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TokenUtil {

    private static final String ISSUER = "签发者";
    public static String generateToken(Integer mobileId,Integer personId) throws Exception{
//        之前已经在RSAUtil中生成了一对密钥，因此直接使用RSA进行加密
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
//        这个日历类主要是为了后续设置过期时间
        Calendar calendar = Calendar.getInstance();
//        获取当前时间
        calendar.setTime(new Date());
//        设置过期时间为30s
        calendar.add(Calendar.SECOND,30);
//        生成JWT token
//        传入参数：唯一标识（此处使用userId作为唯一标识），签发者，过期时间，使用的加密算法类型
        return JWT.create().withKeyId(String.valueOf(mobileId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .withClaim("personId",personId)
                .sign(algorithm);
    }

    //    过期的返回特殊状态码555，前端可以根据状态码进行进一步操作，也就是刷新token，达到无感操作
    public static List<Integer> verifyToken(String token){
        try {
//            指定算法，传入RSAUtil里的公钥和私钥
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
//            传入算法类型的参数，生成一个验证者类
            JWTVerifier verifier = JWT.require(algorithm).build();
//            直接使用封装好的验证方法，返回一个DecodedJWT，也就是解密后的JWT
            DecodedJWT jwt = verifier.verify(token);
//            取出keyId，也就是获取userId
            String mobileId = jwt.getKeyId();
            Integer personId = jwt.getClaim("personId").asInt();
//            类型转换
            List<Integer> mobileAndPerson = new ArrayList<>();
            mobileAndPerson.add(Integer.valueOf(mobileId));
            mobileAndPerson.add(personId);
            return mobileAndPerson;
        }catch (TokenExpiredException e){
            throw new ConditionException("555","token过期！");
        }catch (Exception e){
            throw new ConditionException("非法用户token！");
        }
    }
}

