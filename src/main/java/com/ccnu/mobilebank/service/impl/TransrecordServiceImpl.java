package com.ccnu.mobilebank.service.impl;

import com.ccnu.mobilebank.mapper.AccountMapper;
import com.ccnu.mobilebank.mapper.PersoninfoMapper;
import com.ccnu.mobilebank.pojo.Account;
import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.pojo.Transrecord;
import com.ccnu.mobilebank.mapper.TransrecordMapper;
import com.ccnu.mobilebank.pojo.exception.ConditionException;
import com.ccnu.mobilebank.service.ITransrecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccnu.mobilebank.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
@Service
public class TransrecordServiceImpl extends ServiceImpl<TransrecordMapper, Transrecord> implements ITransrecordService {

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PersoninfoMapper personinfoMapper;

    @Override
    public List<Transrecord> getPeriodIncome(Integer accountId, LocalDateTime start, LocalDateTime end) {
        List<Transrecord> income = baseMapper.getPeriodIncome(accountId, start, end);
        return income;
    }

    @Override
    public List<Transrecord> getPeriodOutcome(Integer accountId, LocalDateTime start, LocalDateTime end) {
        List<Transrecord> outcome = baseMapper.getPeriodOutcome(accountId,start,end);
        return outcome;
    }

    @Override
    public List<Transrecord> getTransrecordsByAccountId(Integer accountId, int page, int size) {
//        分页查询
        int offset = (page - 1) * size;
//        得到该账户的交易记录
        List<Transrecord> transrecords =  baseMapper.getTransrecordsByAccountId(accountId, offset, size);
        for(Transrecord transrecord : transrecords){
            Integer fromAccountId = transrecord.getAccountId();
            Integer toAccountId = transrecord.getOtherId();
            Integer personId;
            if(fromAccountId.equals(accountId)){
                personId = accountMapper.getPersonIdByAccountId(toAccountId);
            }else {
                personId = accountMapper.getPersonIdByAccountId(fromAccountId);
            }
            String personName = personinfoMapper.getPersonNameById(personId);
            transrecord.setToPerson(personName);
        }
        return transrecords;
    }

    @Override
    public Transrecord transferMoney(Integer fromAccountId,String toAccountName, BigDecimal amount, String password) {
        Account fromAccount = accountMapper.getAccountById(fromAccountId);
        if(fromAccount.getStatusId() == 0){
            throw new ConditionException("508","本账户已被冻结!");
        }

        //验证收款账户是否被冻结
        Account toAccount = accountMapper.getAccountByAccountName(toAccountName);
        if(toAccount.getStatusId() == 0){
            throw new ConditionException("509","收款账户已被冻结!");
        }

        //验证支付密码
        String dbPassword = fromAccount.getPassword();
        if(!dbPassword.equals(password)){
            throw new ConditionException("510","支付密码错误!");
        }

        //检查当前用户余额是否足够
        if(fromAccount.getBalance().compareTo(amount) < 0){
            throw new ConditionException("511","当前账户余额不足！");
        }
        //更新支付账户余额
        BigDecimal balance = fromAccount.getBalance();
        BigDecimal newFromBalance = balance.subtract(amount);
        fromAccount.setBalance(newFromBalance);
        accountMapper.updateBalance(fromAccount);

        //更新收款用户余额
        BigDecimal newToBalance = toAccount.getBalance().add(amount);
        toAccount.setBalance(newToBalance);
        accountMapper.updateBalance(toAccount);

        //更新交易记录
        Transrecord transrecord = new Transrecord();
        transrecord.setAccountId(fromAccountId);
        Integer toAccountId = toAccount.getId();
        transrecord.setOtherId(toAccountId);
        transrecord.setMoney(amount);
        transrecord.setTransDate(LocalDateTime.now().toString());
        transrecord.setTranstypeId(2);
        transrecord.setIsDelete(false);
        baseMapper.insertTransRecord(transrecord);
        return transrecord;
    }
}
