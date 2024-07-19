package com.ccnu.mobilebank.controller;

import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.Transrecord;
import com.ccnu.mobilebank.service.ITransrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
//@RequestMapping("/transrecord")
public class TransrecordController {
    @Autowired
    private ITransrecordService transrecordService;

    @PostMapping("/period-amounts")
    public JsonResponse<List<List<BigDecimal>>> getPeriodAmounts(@RequestParam Integer accountId, @RequestParam LocalDateTime start,@RequestParam LocalDateTime end){
        List<BigDecimal> income = transrecordService.getPeriodIncome(accountId,start,end);
        List<BigDecimal> outcome = transrecordService.getPeriodOutcome(accountId,start,end);
        List<List<BigDecimal>> periodAmounts = new ArrayList<>();
        periodAmounts.add(income);
        periodAmounts.add(outcome);
        return new JsonResponse<>(periodAmounts);
    }

    //根据账户id查询所有交易记录(已测试)
    @PostMapping("/all-records")
    public JsonResponse<List<Transrecord>> getTransrecords(
            @RequestParam Integer accountId,
            @RequestParam int page,
            @RequestParam int size) {
        List<Transrecord> allRecord =  transrecordService.getTransrecordsByAccountId(accountId, page, size);
        return new JsonResponse<>(allRecord);
    }

    //转账接口(已测试)
    @PostMapping("/trans-money")
    public JsonResponse<Transrecord> transferMoney(
            @RequestParam Integer fromAccountId,
            @RequestParam Integer toAccountId,
            @RequestParam BigDecimal amount,
            @RequestParam String password) {
        Transrecord transrecord = transrecordService.transferMoney(fromAccountId, toAccountId, amount, password);
        return new JsonResponse<>(transrecord);
    }
}
