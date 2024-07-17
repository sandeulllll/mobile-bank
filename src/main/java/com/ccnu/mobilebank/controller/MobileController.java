package com.ccnu.mobilebank.controller;

import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.Mobile;
import com.ccnu.mobilebank.service.IMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@RestController
//@RequestMapping("/mobile")
public class MobileController {

    @Autowired
    private IMobileService mobileService;

    /**
     * 注册成功返回提示信息即可
     * @param mobile
     * @return
     */
    @PostMapping("/register")
    public JsonResponse<String> register(@RequestBody Mobile mobile){
        mobileService.addMobile(mobile);
        return JsonResponse.success();
    }
}
