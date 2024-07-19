package com.ccnu.mobilebank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccnu.mobilebank.exception.ConditionException;
import com.ccnu.mobilebank.pojo.Account;
import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.Mobileaccount;
import com.ccnu.mobilebank.service.IAccountService;
import com.ccnu.mobilebank.service.IMobileaccountService;
import com.ccnu.mobilebank.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IMobileaccountService mobileaccountService;

    // 查询银行卡账户列表
    @GetMapping("/listBankAccount")
    public JsonResponse<Map> listBankAccount(){
        // 1. 获取登录用户的手机号id
        Integer mobileId = userSupport.getCurrentMobileId();
        // 2. 在mobileaccount表中查询绑定该手机号的accountid
        Map<String,Object> map = new HashMap<>();
        map.put("telId",mobileId);
        List<Mobileaccount> list = mobileaccountService.listByMap(map);

        // 3. 在account列表中查询银行卡信息，返回mobileaccount中的id和account的信息的map
        Map<Integer,Account> map1 = new HashMap<>();
        for(Mobileaccount mobileaccount : list){
            Account account = accountService.getById(mobileaccount.getAccountId());
            map1.put(mobileaccount.getId(), account);
        }

        return new JsonResponse<>(map1);
    }

    // 新增关联账户
    @PostMapping("/addRelatedAccount")
    public JsonResponse<String> addRelatedAccount(@RequestParam String accountName){
        // 1. 获取用户的personId
        Integer personId = userSupport.getCurrentPersonId();

        // 2. 在account表中查询对应的银行卡信息
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accountName",accountName).eq("personId",personId);
        Account account = accountService.getOne(queryWrapper);
        if(account==null)
            throw new ConditionException("银行卡号错误");

        // 3. 存在，将信息加入mobileaccount表中
        Integer mobileId = userSupport.getCurrentMobileId();
        Mobileaccount mobileaccount = new Mobileaccount();
        mobileaccount.setAccountId(account.getId());
        mobileaccount.setTelId(mobileId);
        mobileaccountService.save(mobileaccount);

        return JsonResponse.success();
    }

    // 移除银行卡账户
    @RequestMapping("/removeBankAccount/{id}")
    public JsonResponse removeBankAccount(@PathVariable Integer id){
        // 在mobileaccount中删除id=id的记录
        mobileaccountService.removeById(id);
        return JsonResponse.success();
    }

    // 验证银行卡密码
    @RequestMapping("/verifyBankPassword")
    public JsonResponse verifyBankPassword(@RequestParam String accountName, @RequestParam String password){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accountName",accountName);
        Account account = accountService.getOne(queryWrapper);
        if(!account.getPassword().equals(password))
            throw new ConditionException("密码错误");
        return JsonResponse.success();
    }

    // 修改支付密码
    @PutMapping("/updatePayPassword")
    public JsonResponse<String> updatePayPassword(@RequestParam String accountName, @RequestParam String password){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accountName",accountName);
        Account account = new Account();
        account.setPassword(password);
        if(!accountService.update(account, queryWrapper))
            throw new ConditionException("修改密码失败");
        return JsonResponse.success();
    }

     // 查询总资产
     @RequestMapping("/queryTotalAssets")
     public JsonResponse<Integer> queryTotalAssets(){
        Integer mobileId = userSupport.getCurrentMobileId();

        // 1. 在mobileaccount中查询所有的关联账户
        QueryWrapper<Mobileaccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telId",mobileId);
        List<Mobileaccount> list = mobileaccountService.list(queryWrapper);

        // 2. 获取关联账户的accountid
        List<Integer> accountids = new ArrayList<>();
        for(Mobileaccount mobileaccount : list){
            accountids.add(mobileaccount.getAccountId());
        }
        List<Account> accountList = accountService.listByIds(accountids);

        // 3. 在account中查询余额
        Double totalAssets = 0.0;
        for(Account account : accountList){
            totalAssets += account.getBalance();
        }

        return new JsonResponse(totalAssets);
     }

}
