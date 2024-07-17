package com.ccnu.mobilebank.service;

import com.ccnu.mobilebank.pojo.Transrecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
public interface ITransrecordService extends IService<Transrecord> {

    List<BigDecimal> getPeriodIncome(Integer accountId, LocalDateTime start, LocalDateTime end);

    List<BigDecimal> getPeriodOutcome(Integer accountId, LocalDateTime start, LocalDateTime end);

    List<Transrecord> getTransrecordsByAccountId(Integer accountId, int page, int size);

    Transrecord transferMoney(Integer fromAccountId, Integer toAccountId, BigDecimal amount, String password);
}
