package com.ccnu.mobilebank.controller;

import com.ccnu.mobilebank.exception.ConditionException;
import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.Mobile;
import com.ccnu.mobilebank.service.IMobileService;
import com.ccnu.mobilebank.util.TokenUtil;
import com.ccnu.mobilebank.util.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@RestController
@RequestMapping("/users")
public class MobileController {

    @Autowired
    private IMobileService mobileService;
    @Autowired
    private VerificationCodeUtil verificationCodeUtil;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/test")
    public String test(){
        return "第六组测试!";
    }
    //获取验证码(测试完毕)
    @PostMapping("/get-code")
    public JsonResponse<String> getCode(@RequestParam String telephone) {
        String phone = telephone;
        //TODO:判断是否存在
        mobileService.checkPhoneInPersonInfo(telephone);
        //生成验证码并存储到 Redis 中，有效期为60秒
        String code = verificationCodeUtil.generateCode(phone, 60);
        return new JsonResponse<>(code);
    }

    //校验验证码(测试完毕)
    @PostMapping("/verify-code")
    public JsonResponse<String> verifyCode(@RequestParam String phone, @RequestParam String code){
        String cacheCode = redisTemplate.opsForValue().get(phone);
        if (cacheCode == null) {
            throw new ConditionException("501","验证码已过期，请重新获取！");
        }
        if (!cacheCode.equals(code)) {
            throw new ConditionException("502","验证码错误！");
        }
        return JsonResponse.success();
    }

    //注册接口(测试完毕）
    @PostMapping("/register")
    public JsonResponse<String> register(@RequestBody Mobile mobile){
        mobileService.addMobile(mobile);
        return JsonResponse.success();
    }

    //使用密码登录(测试完毕)
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody Mobile mobile) throws Exception {
        String token = mobileService.login(mobile);
        return new JsonResponse<>(token);
    }

    //使用验证码登录(测试完毕但前端不需要)
    @PostMapping("/login")
    public JsonResponse<String> login(@RequestParam String phone, @RequestParam String code) throws Exception {
        //从 Redis 中获取验证码
        String cacheCode = redisTemplate.opsForValue().get(phone);
        if (cacheCode == null) {
            throw new ConditionException("501","验证码已过期，请重新获取！");
        }
        if (!cacheCode.equals(code)) {
            throw new ConditionException("502","验证码错误！");
        }
        //TODO：验证码通过，需要进行其他逻辑判断
        Mobile mobile = new Mobile();
        mobile.setTelephone(phone);
        String token = mobileService.login(mobile);
        return new JsonResponse<>(token);
    }

    //使用原密码修改登录密码(测试完毕)
    @PutMapping("/password")
    public JsonResponse<String> updateMobilePassword(@RequestParam String password,@RequestParam String newPassword){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Integer mobileId = TokenUtil.verifyToken(token).get(0);
        mobileService.updateMobilePassword(mobileId,password,newPassword);
        return JsonResponse.success();
    }

    //验证码通过后只提交新密码(测试完毕)
    @PostMapping("/password")
    public JsonResponse<String> updatePwdByCode(@RequestParam String telephone,@RequestParam String newPassword){
        mobileService.updateMobilePassword(telephone,newPassword);
        return JsonResponse.success();
    }
}

