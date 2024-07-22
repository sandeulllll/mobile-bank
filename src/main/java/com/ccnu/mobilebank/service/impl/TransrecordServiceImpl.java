package com.ccnu.mobilebank.service.impl;

import com.ccnu.mobilebank.mapper.AccountMapper;
import com.ccnu.mobilebank.mapper.PersoninfoMapper;
import com.ccnu.mobilebank.pojo.Account;
import com.ccnu.mobilebank.pojo.PagedResponse;
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
    public PagedResponse<List<Transrecord>> getPeriodIncome(Integer accountId, LocalDateTime start, LocalDateTime end) {
        List<Transrecord> income;
        long total;
        if(accountId != null){
            income = baseMapper.getPeriodIncome(accountId, start, end);
            total = baseMapper.getTotalIncomeByAccountIdAndTime(accountId, start, end);
        } else{
            Integer userPersonId = userSupport.getCurrentPersonId();
            List<Integer> accountIds = accountMapper.getAccountIdsByPersonId(userPersonId);
            income = baseMapper.getPeriodIncomeByAccountIds(accountIds,start,end);
            total = baseMapper.getTotalIncomeByAccountIdsAndTime(accountIds, start, end);
        }
        return new PagedResponse<>(income, total);
    }

    @Override
    public PagedResponse<List<Transrecord>> getPeriodOutcome(Integer accountId, LocalDateTime start, LocalDateTime end) {
        List<Transrecord> outcome;
        long total;

        if(accountId != null){
           outcome = baseMapper.getPeriodOutcome(accountId,start,end);
           total = baseMapper.getTotalOutcomeByAccountIdAndTime(accountId, start, end);
        }else {
            Integer userPersonId = userSupport.getCurrentPersonId();
            List<Integer> accountIds = accountMapper.getAccountIdsByPersonId(userPersonId);
            outcome = baseMapper.getPeriodOutcomeByAccountIds(accountIds,start,end);
            total = baseMapper.getTotalOutcomeByAccountIdsAndTime(accountIds, start, end);
        }
        return new PagedResponse<>(outcome, total);
    }

    //获取交易记录
    @Override
    public List<Transrecord> getTransrecordsByAccountId(Integer accountId, LocalDateTime start, LocalDateTime end,int page, int size) {
//        分页查询
        int offset = (page - 1) * size;
//        返回的结果
        List<Transrecord> transrecords = new ArrayList<>();

//        accountId不为空时
        if(accountId != null){
            if(start == null && end == null){
                transrecords =  baseMapper.getTransrecordsByAccountId(accountId, offset, size);
            } else if(start != null && end != null){
                transrecords = baseMapper.getTransrecordsByAccountIdAndTime(accountId,start,end,offset,size);
            }
            for(Transrecord transrecord : transrecords){
                //fromAccountId：支付的账户Id
                Integer fromAccountId = transrecord.getAccountId();
                //toAccountId：收款的账户Id
                Integer toAccountId = transrecord.getOtherId();
                Integer personId;

                String toPersonName;
                String type;
                String toAccountName;
                String myAccountName = accountMapper.getAccountNameById(accountId);
                if(fromAccountId.equals(accountId)){
                    personId = accountMapper.getPersonIdByAccountId(toAccountId);
                    type = "支出";
                    toAccountName = accountMapper.getAccountNameById(toAccountId);
                }else {
                    personId = accountMapper.getPersonIdByAccountId(fromAccountId);
                    type = "收入";
                    toAccountName = accountMapper.getAccountNameById(fromAccountId);
                }
                transrecord.setToPerson(personinfoMapper.getPersonNameById(personId));
                transrecord.setType(type);
                transrecord.setToAccountName(toAccountName);
                transrecord.setMyAccountName(myAccountName);
            }
        }
//        accounId为空时
        else if(accountId == null){
            Integer userPersonId = userSupport.getCurrentPersonId();
            List<Integer> accountIds = accountMapper.getAccountIdsByPersonId(userPersonId);
            if(start == null && end == null){
                transrecords = baseMapper.getTransrecordsByAccountIds(accountIds,offset,size);
            } else if(start != null && end != null){
                transrecords = baseMapper.getTransrecordsByAccountIdsAndTime(accountIds,start,end,offset,size);
            }
            for (Transrecord transrecord : transrecords){
                for(Integer id : accountIds){
                    if(id.equals(transrecord.getAccountId())){
                        Integer toPersonId = accountMapper.getPersonIdByAccountId(transrecord.getOtherId());
                        String toAccountName = accountMapper.getAccountNameById(transrecord.getOtherId());
                        String toPersonName = personinfoMapper.getPersonNameById(toPersonId);
                        transrecord.setToPerson(toPersonName);
                        transrecord.setType("支出");
                        transrecord.setMyAccountName(accountMapper.getAccountNameById(id));
                        transrecord.setToAccountName(toAccountName);
                    }else {
                        Integer toPersonId = accountMapper.getPersonIdByAccountId(transrecord.getAccountId());
                        String toAccountName = accountMapper.getAccountNameById(transrecord.getAccountId());
                        String toPersonName = personinfoMapper.getPersonNameById(toPersonId);
                        transrecord.setToPerson(toPersonName);
                        transrecord.setType("收入");
                        transrecord.setMyAccountName(accountMapper.getAccountNameById(id));
                        transrecord.setToAccountName(toAccountName);
                    }
                }
            }
        }
        return transrecords;
    }

    //转账
    @Override
    public Transrecord transferMoney(Integer fromAccountId,String toAccountName,String toPersonName, BigDecimal amount, String password) {
        //验证本账户是否被冻结
        Account fromAccount = accountMapper.getAccountById(fromAccountId);
        if(fromAccount.getStatusId() == 0){
            throw new ConditionException("508","本账户已被冻结!");
        }

        //验证输入账户名和联系人的姓名是否匹配
        Account toAccount = accountMapper.getAccountByAccountName(toAccountName);
        Integer dbPersonId = toAccount.getPersonId();
        String dbRealName = personinfoMapper.getPersonNameById(dbPersonId);
        if(!dbRealName.equals(toPersonName)){
            throw new ConditionException("507","账户名和联系人不匹配,请重新输入账户名!");
        }

        //验证收款账户是否被冻结
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

    //获取所有交易记录的获取total方法
    @Override
    public long getTotalCountByAccountId(Integer accountId, LocalDateTime start, LocalDateTime end) {
        if (accountId != null) {
            if (start == null && end == null) {
                return baseMapper.getTotalCountByAccountId(accountId);
            } else if (start != null && end != null) {
                return baseMapper.getTotalCountByAccountIdAndTime(accountId, start, end);
            }
        } else {
            Integer userPersonId = userSupport.getCurrentPersonId();
            List<Integer> accountIds = accountMapper.getAccountIdsByPersonId(userPersonId);
            if (start == null && end == null) {
                return baseMapper.getTotalCountByAccountIds(accountIds);
            } else if (start != null && end != null) {
                return baseMapper.getTotalCountByAccountIdsAndTime(accountIds, start, end);
            }
        }
        return 0;
    }

}
