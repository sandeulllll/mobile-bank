package com.ccnu.mobilebank.mapper;

import com.ccnu.mobilebank.pojo.Transrecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
public interface TransrecordMapper extends BaseMapper<Transrecord> {
    List<Transrecord> getPeriodIncome(@Param("accountId") Integer accountId,
                                      @Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);

    List<Transrecord> getPeriodOutcome(@Param("accountId") Integer accountId,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);

    List<Transrecord> getTransrecordsByAccountId(Integer accountId, int offset, int size);

    void insertTransRecord(Transrecord transrecord);

    List<Transrecord> getTransrecordsByAccountIdAndTime(
            @Param("accountId") Integer accountId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("offset") int offset,
            @Param("size") int size);

    List<Transrecord> getTransrecordsByAccountIds(
            @Param("accountIds") List<Integer> accountIds,
            @Param("offset") int offset,
            @Param("size") int size);

    List<Transrecord> getTransrecordsByAccountIdsAndTime(
            @Param("accountIds") List<Integer> accountIds,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("offset") int offset,
            @Param("size") int size);


    List<Transrecord> getPeriodIncomeByAccountIds(
            @Param("accountIds") List<Integer> accountIds,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    List<Transrecord> getPeriodOutcomeByAccountIds(
            @Param("accountIds") List<Integer> accountIds,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    long getTotalCountByAccountId(Integer accountId);

    long getTotalCountByAccountIdAndTime(Integer accountId, LocalDateTime start, LocalDateTime end);

    long getTotalCountByAccountIds(List<Integer> accountIds);

    long getTotalCountByAccountIdsAndTime(List<Integer> accountIds, LocalDateTime start, LocalDateTime end);

    long getTotalIncomeByAccountIdAndTime(Integer accountId, LocalDateTime start, LocalDateTime end);

    long getTotalIncomeByAccountIdsAndTime(List<Integer> accountIds, LocalDateTime start, LocalDateTime end);

    long getTotalOutcomeByAccountIdAndTime(Integer accountId, LocalDateTime start, LocalDateTime end);

    long getTotalOutcomeByAccountIdsAndTime(List<Integer> accountIds, LocalDateTime start, LocalDateTime end);
}

