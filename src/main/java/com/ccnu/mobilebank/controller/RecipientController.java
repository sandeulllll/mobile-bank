package com.ccnu.mobilebank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccnu.mobilebank.exception.ConditionException;
import com.ccnu.mobilebank.pojo.Account;
import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.pojo.Recipient;
import com.ccnu.mobilebank.service.IAccountService;
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
    private IPersoninfoService personinfoService;
    @Autowired
    private IAccountService accountService;

    @Autowired
    private UserSupport userSupport;

    // 获取联系人列表
    @GetMapping("/list")
    public JsonResponse<List<Recipient>> getRecipientList() {
        // 1. 根据登录用户的telId在recipient中查询联系人的accountId列表
        Integer mobileId = userSupport.getCurrentMobileId();
//        Integer mobileId = 16;
        Map<String,Object> map = new HashMap<>();
        map.put("telId",mobileId);
        List<Recipient> list = recipientService.listByMap(map);

        // 2. 使用列表中的otherId在account表中获取账户的personId，并与recipent中的id绑定返回
        for (Recipient recipients:list){
            Account account = accountService.getById(recipients.getOtherId());
            Personinfo personinfo = personinfoService.getById(account.getPersonId());
            recipients.setPersoninfo(personinfo);
        }

        return new JsonResponse<>(list);
    }

    // 删除联系人
    @DeleteMapping("/delete/{id}")
    public JsonResponse<String> deleteRecipient(@PathVariable Integer id) {
        recipientService.removeById(id);
        return JsonResponse.success();
    }

    // 添加联系人
    @PostMapping("/add")
    public JsonResponse<String> addRecipient(@RequestParam String accountName, @RequestParam String name) {
        // 1. 判断该用户是否存在
        QueryWrapper<Personinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("realname",name);
        Personinfo personinfo = personinfoService.getOne(queryWrapper);
        if(personinfo == null)
            if(personinfo == null)
                throw new ConditionException("512","用户不存在!");

        QueryWrapper<Account> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("accountName",accountName).eq("personId",personinfo.getId());
        Account account = accountService.getOne(queryWrapper1);
        if(account == null)
            throw new ConditionException("512","用户不存在!");

        // 2. 根据登录用户的telId在recipient中查询联系人的列表
        Integer mobileId = userSupport.getCurrentMobileId();
        Map<String,Object> map = new HashMap<>();
        map.put("telId",mobileId);
        List<Recipient> list = recipientService.listByMap(map);

        // 3. 判断该联系人是否已经存在
        for (Recipient recipients:list){
            if(recipients.getOtherId().equals(account.getId()))
                throw new ConditionException("514","联系人已存在!");
        }

        // 4. 不存在，将其加入recipient
        Recipient recipient = new Recipient();
        recipient.setOtherId(account.getId());
        recipient.setTelId(mobileId);
        recipientService.save(recipient);
        return JsonResponse.success();
    }
}
