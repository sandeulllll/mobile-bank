package com.ccnu.mobilebank.mapper;

import com.ccnu.mobilebank.pojo.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
public interface AccountMapper extends BaseMapper<Account> {

    void updateBalance(Account fromAccount);

    Account getAccountById(Integer id);

    Integer getPersonIdByAccountId(Integer id);

    Account getAccountByAccountName(String accountName);

    List<Integer> getAccountIdsByPersonId(Integer userPersonId);

    String getAccountNameById(Integer accountId);
}

