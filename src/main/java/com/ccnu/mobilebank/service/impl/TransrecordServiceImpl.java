package com.ccnu.mobilebank.service.impl;

import com.ccnu.mobilebank.mapper.AccountMapper;
import com.ccnu.mobilebank.pojo.Account;
import com.ccnu.mobilebank.pojo.Transrecord;
import com.ccnu.mobilebank.mapper.TransrecordMapper;
import com.ccnu.mobilebank.pojo.exception.ConditionException;
import com.ccnu.mobilebank.service.ITransrecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private AccountMapper accountMapper;

    @Override
    public List<BigDecimal> getPeriodIncome(Integer accountId, LocalDateTime start, LocalDateTime end) {
        List<BigDecimal> income = baseMapper.getPeriodIncome(accountId, start, end);
        return income;
    }

    @Override
    public List<BigDecimal> getPeriodOutcome(Integer accountId, LocalDateTime start, LocalDateTime end) {
        List<BigDecimal> outcome = baseMapper.getPeriodOutcome(accountId,start,end);
        return outcome;
    }

    @Override
    public List<Transrecord> getTransrecordsByAccountId(Integer accountId, int page, int size) {
        int offset = (page - 1) * size;
        return baseMapper.getTransrecordsByAccountId(accountId, offset, size);
    }

    @Override
    public Transrecord transferMoney(Integer fromAccountId, Integer toAccountId, BigDecimal amount, String password) {
        //验证收款账户是否存在
        Account toAccount = accountMapper.selectById(toAccountId);
        if(toAccount == null){
            throw new ConditionException("收款账户不存在!");
        }
        //验证支付密码
        Account fromAccount = accountMapper.selectById(fromAccountId);
        String dbPassword = fromAccount.getPassword();
        if(!dbPassword.equals(password)){
            throw new ConditionException("支付密码错误!");
        }
        //TODO:转账金额限制

        //检查当前用户余额是否足够
        if(fromAccount.getBalance().compareTo(amount) < 0){
            throw new ConditionException("账户余额不足！");
        }
        //更新支付账户余额
        BigDecimal newFromBalance = fromAccount.getBalance().subtract(amount);
        fromAccount.setBalance(newFromBalance);
        accountMapper.updateBalance(fromAccount);

        //更新收款用户余额
        BigDecimal newToBalance = toAccount.getBalance().add(amount);
        toAccount.setBalance(newToBalance);
        accountMapper.updateBalance(toAccount);

        //更新交易记录
        Transrecord transrecord = new Transrecord();
        transrecord.setAccountId(fromAccountId);
        transrecord.setOtherId(toAccountId);
        transrecord.setMoney(amount);
        transrecord.setTransDate(LocalDateTime.now().toString());
        transrecord.setTranstypeId(2);
        transrecord.setIsDelete(false);
        baseMapper.insertTransRecord(transrecord);
        return transrecord;
    }
}
