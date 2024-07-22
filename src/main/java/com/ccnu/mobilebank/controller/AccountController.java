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

import java.math.BigDecimal;
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
    public JsonResponse<List<Mobileaccount>> listBankAccount(){
        // 1. 获取登录用户的手机号id
        Integer mobileId = userSupport.getCurrentMobileId();
//        Integer mobileId = 16;

        // 2. 在mobileaccount表中查询绑定该手机号的accountid
        Map<String,Object> map = new HashMap<>();
        map.put("telId",mobileId);
        List<Mobileaccount> list = mobileaccountService.listByMap(map);

        // 3. 在account列表中查询银行卡信息，返回mobileaccount中的id和account的信息的map
        for(Mobileaccount mobileaccount : list){
            Account account = accountService.getById(mobileaccount.getAccountId());
            mobileaccount.setAccount(account);
        }

        return new JsonResponse<>(list);
    }

    // 新增关联账户
    @PostMapping("/addRelatedAccount")
    public JsonResponse<String> addRelatedAccount(@RequestParam String accountName){
        // 1. 获取用户的personId，mobileId
        Integer personId = userSupport.getCurrentPersonId();
        Integer mobileId = userSupport.getCurrentMobileId();
//        Integer personId = 3;
        // 2. 在account表中查询对应的银行卡信息
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accountName",accountName).eq("personId",personId);
        Account account = accountService.getOne(queryWrapper);
        if(account==null)
            throw new ConditionException("507","银行卡号错误!");

        // 3. 在mobileaccount表中查询绑定该手机号的accountid
        Map<String,Object> map = new HashMap<>();
        map.put("telId",mobileId);
        List<Mobileaccount> list = mobileaccountService.listByMap(map);

        // 4. 判断该银行卡是否已经绑定
        for(Mobileaccount mobileaccount : list){
            if(mobileaccount.getAccountId().equals(account.getId()))
                 throw new ConditionException("513","该银行卡已绑定!");
        }

        // 5. 未绑定，将信息加入mobileaccount表中
        Mobileaccount mobileaccount = new Mobileaccount();
        mobileaccount.setAccountId(account.getId());
        mobileaccount.setTelId(mobileId);
        mobileaccountService.save(mobileaccount);

        return JsonResponse.success();
    }

    // 移除银行卡账户
    @RequestMapping("/removeBankAccount/{id}")
    public JsonResponse<String> removeBankAccount(@PathVariable Integer id){
        // 在mobileaccount中删除id=id的记录
        mobileaccountService.removeById(id);
        return JsonResponse.success();
    }

    // 验证银行卡密码
    @RequestMapping("/verifyBankPassword")
    public JsonResponse<String> verifyBankPassword(@RequestParam String accountName, @RequestParam String password){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accountName",accountName);
        Account account = accountService.getOne(queryWrapper);
        if(!account.getPassword().equals(password))
            throw new ConditionException("510","支付密码错误!");
        return JsonResponse.success();
    }

    // 修改支付密码
    @PutMapping("/updatePayPassword")
    public JsonResponse<String> updatePayPassword(@RequestParam String accountName, @RequestParam String password){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accountName",accountName);
        Account account = new Account();
        account.setPassword(password);
        accountService.update(account,queryWrapper);
        return JsonResponse.success();
    }

     // 查询总资产
     @RequestMapping("/queryTotalAssets")
     public JsonResponse<BigDecimal> queryTotalAssets(){
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
        BigDecimal totalAssets = new BigDecimal(0);
        for(Account account : accountList){
            totalAssets = totalAssets.add(account.getBalance());
        }

        return new JsonResponse<>(totalAssets);
     }
}
