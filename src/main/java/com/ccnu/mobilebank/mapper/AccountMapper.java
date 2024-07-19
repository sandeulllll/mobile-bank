package com.ccnu.mobilebank.mapper;

import com.ccnu.mobilebank.pojo.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
