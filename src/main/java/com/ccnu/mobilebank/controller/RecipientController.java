package com.ccnu.mobilebank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccnu.mobilebank.exception.ConditionException;
import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.pojo.Recipient;
import com.ccnu.mobilebank.service.IMobileService;
import com.ccnu.mobilebank.service.IPersoninfoService;
import com.ccnu.mobilebank.service.IRecipientService;
import com.ccnu.mobilebank.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
@RestController
@RequestMapping("/friends")
public class RecipientController {
    @Autowired
    private IRecipientService recipientService;
    @Autowired
    private IMobileService mobileService;
    @Autowired
    private IPersoninfoService personinfoService;

    @Autowired
    private UserSupport userSupport;

    // 获取联系人列表
    @GetMapping("/list")
    public JsonResponse<List> getRecipientList() {
        // 1. 根据登录用户的telId在recipient中查询联系人的列表
        Integer mobileId = userSupport.getCurrentMobileId();
//        Integer mobileId = 16;
        Map<String,Object> map = new HashMap<>();
        map.put("telId",mobileId);
        List<Recipient> list = recipientService.listByMap(map);

        // 2. 使用列表中的otherId在personinfo表中获取联系人的具体信息，并与recipent中的id绑定返回
        for (Recipient recipients:list){
            Personinfo personinfo = personinfoService.getById(recipients.getOtherId());
            recipients.setPersoninfo(personinfo);
        }

        return new JsonResponse<>(list);
    }

    // 删除联系人
    @DeleteMapping("/delete/{id}")
    public JsonResponse deleteRecipient(@PathVariable Integer id) {
        recipientService.removeById(id);
        return JsonResponse.success();
    }

    // 添加联系人
    @PostMapping("/add")
    public JsonResponse<String> addRecipient(@RequestParam String telephone, @RequestParam String name) {
        // 1. 判断该用户是否存在
        QueryWrapper<Personinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone",telephone).eq("realname",name);
        Personinfo personinfo = personinfoService.getOne(queryWrapper);
        if(personinfo == null)
            throw new ConditionException("用户不存在!");

        // 2. 存在，将其加入recipient
        Recipient recipient = new Recipient();
        recipient.setOtherId(personinfo.getId());
        Integer mobileId = userSupport.getCurrentMobileId();
        recipient.setTelId(mobileId);
        recipientService.save(recipient);
        return JsonResponse.success();
    }
}
