package com.ccnu.mobilebank.controller;

import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.service.IRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
@Controller
@RequestMapping("/friends")
public class RecipientController {
    @Autowired
    private IRecipientService recipientService;

    // 获取好友列表
    @RequestMapping("/list")
    public JsonResponse listFriends(){
        return JsonResponse.success();
    }
}
