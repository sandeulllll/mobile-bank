package com.ccnu.mobilebank.service;

import com.ccnu.mobilebank.pojo.PagedResponse;
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
    PagedResponse<List<Transrecord>> getPeriodIncome(Integer accountId, LocalDateTime start, LocalDateTime end, int page, int size);

    PagedResponse<List<Transrecord>> getPeriodOutcome(Integer accountId, LocalDateTime start, LocalDateTime end,int page,int size);

    List<Transrecord> getTransrecordsByAccountId(Integer accountId, LocalDateTime start,LocalDateTime end,int page, int size);

    Transrecord transferMoney(Integer fromAccountId,String toAccountName, String toPersonName,BigDecimal amount, String password);

    long getTotalCountByAccountId(Integer accountId, LocalDateTime start, LocalDateTime end);
}
