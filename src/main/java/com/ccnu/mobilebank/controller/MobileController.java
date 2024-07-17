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


}
