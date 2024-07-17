package com.ccnu.mobilebank.controller;

import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    // 修改银行卡密码


}
