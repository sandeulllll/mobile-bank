package com.ccnu.mobilebank.service;

import com.ccnu.mobilebank.pojo.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
public interface IAccountService extends IService<Account> {
    void updatePassword(Integer id, Integer personId, String password);

    boolean addRelatedAccount(String accountName, Integer personId);
}
