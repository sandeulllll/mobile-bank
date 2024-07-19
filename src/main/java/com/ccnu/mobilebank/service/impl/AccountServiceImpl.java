package com.ccnu.mobilebank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccnu.mobilebank.mapper.AccountMapper;
import com.ccnu.mobilebank.pojo.Account;
import com.ccnu.mobilebank.service.IAccountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private PersoninfoMapper personinfoMapper;
    @Autowired
    private MobileMapper mobileMapper;
    @Autowired
    private MobileaccountMapper mobileaccountMapper;
    @Override
    public void updatePassword(Integer id, Integer personId, String password){
        // 1. 要更新的数据
        Account account = new Account();
        account.setPassword(password);
        // 2. 更新的条件
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        // 3. 执行更新
        accountMapper.update(account, wrapper);
    }

    @Override
    public boolean addRelatedAccount(String accountName, Integer personId){
        // 1. 在account表中查询卡号和用户是否对应
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accountName",accountName);
        Account account = getOne(queryWrapper);

        // 2. 在personInfo中查询对应的telId
        if(account.getPersonId()!=personId)
            return false;
        Personinfo personinfo = new Personinfo();
        personinfo = personinfoMapper.selectById(personId);
        String tel = personinfo.getTelephone();
        QueryWrapper<Mobile> mobileQueryWrapper = new QueryWrapper<>();
        mobileQueryWrapper.eq("telephone",tel);
        Mobile mobile = mobileMapper.selectOne(mobileQueryWrapper);

        // 3. 将telId和accountId插入mobileaccount表中
        Mobileaccount mobileaccount = new Mobileaccount();
        mobileaccount.setAccountId(account.getId());
        mobileaccount.setTelId(mobile.getId());

        return mobileaccountMapper.insert(mobileaccount)>0;
    }
}
