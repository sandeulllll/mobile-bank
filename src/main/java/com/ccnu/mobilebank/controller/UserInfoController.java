package com.ccnu.mobilebank.controller;

import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.service.IPersoninfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * 个人信息
 * @author Baomidou
 * @since 2024-07-16
 */
@Controller
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private IPersoninfoService personinfoService;

    /**
     * 个人信息
     */
    @RequestMapping("/info")
    public JsonResponse info(@RequestHeader String token){
        return JsonResponse();
    }
}
