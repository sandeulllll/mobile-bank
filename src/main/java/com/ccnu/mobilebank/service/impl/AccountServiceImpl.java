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

}
