package com.ccnu.mobilebank.controller;

import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.Transrecord;
import com.ccnu.mobilebank.service.ITransrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/transrecord")
public class TransrecordController {
    @Autowired
    private ITransrecordService transrecordService;

    //根据账户id和时间段返回收入和支出概况（已测试）
    @PostMapping("/period-amounts")
    public JsonResponse<List<Map<BigDecimal, LocalDateTime>>> getPeriodAmounts(@RequestParam Integer accountId, @RequestParam LocalDateTime start,@RequestParam LocalDateTime end){
        List<Transrecord> income = transrecordService.getPeriodIncome(accountId,start,end);
        List<Transrecord> outcome = transrecordService.getPeriodOutcome(accountId,start,end);
        Map<BigDecimal,LocalDateTime> incomeList = new HashMap<>();
        Map<BigDecimal,LocalDateTime> outcomeList = new HashMap<>();
        for(Transrecord transrecord : income){
            incomeList.put(transrecord.getMoney(),transrecord.getUpdateTime());
        }
        for (Transrecord transrecord : outcome){
            outcomeList.put(transrecord.getMoney(),transrecord.getUpdateTime());
        }
        List<Map<BigDecimal, LocalDateTime>> periodAmounts = new ArrayList<>();
        periodAmounts.add(incomeList);
        periodAmounts.add(outcomeList);
        return new JsonResponse<>(periodAmounts);
        /*List<List<Transrecord>> periodAmounts = new ArrayList<>();
        periodAmounts.add(income);
        periodAmounts.add(outcome);
        return new JsonResponse<>(periodAmounts);*/
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
            @RequestParam String toAccountName,
            @RequestParam BigDecimal amount,
            @RequestParam String password) {
        Transrecord transrecord = transrecordService.transferMoney(fromAccountId,toAccountName, amount, password);
        return new JsonResponse<>(transrecord);
    }
}
