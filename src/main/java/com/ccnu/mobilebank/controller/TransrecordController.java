package com.ccnu.mobilebank.controller;

import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.MoneyDate;
import com.ccnu.mobilebank.pojo.PagedResponse;
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

@RestController
@RequestMapping("/transrecord")
public class TransrecordController {
    @Autowired
    private ITransrecordService transrecordService;

    //根据账户id和时间段返回收入和支出概况（已测试）
    @PostMapping("/period-amounts")
    public JsonResponse<List<List<MoneyDate>>> getPeriodAmounts(
            @RequestParam(required = false) Integer accountId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end){
        List<Transrecord> income = transrecordService.getPeriodIncome(accountId,start,end);
        List<Transrecord> outcome = transrecordService.getPeriodOutcome(accountId,start,end);

        /*Map<BigDecimal,String> incomeList = new HashMap<>();
        Map<BigDecimal,String> outcomeList = new HashMap<>();*/
        List<MoneyDate> incomeList = new ArrayList<>();
        List<MoneyDate> outcomeList = new ArrayList<>();
        for(Transrecord transrecord : income){
//            incomeList.put(transrecord.getMoney(),transrecord.getTransDate());
            incomeList.add(new MoneyDate(transrecord.getMoney(),transrecord.getTransDate()));
        }
        for (Transrecord transrecord : outcome){
//            outcomeList.put(transrecord.getMoney(),transrecord.getTransDate());
            outcomeList.add(new MoneyDate(transrecord.getMoney(),transrecord.getTransDate()));
        }
        List<List<MoneyDate>> result = new ArrayList<>();
        /*Map<BigDecimal,String> incomeResults = new PagedResponse<>(incomeList,income.getTotal());
        Map<BigDecimal,String> outcomeResults = new PagedResponse<>(outcomeList,outcome.getTotal());*/
        result.add(incomeList);
        result.add(outcomeList);
        return new JsonResponse<>(result);
    }

    //根据账户id查询所有交易记录(已测试)
    //TODO:返回数据总条数
    @PostMapping("/all-records")
    public JsonResponse<PagedResponse<List<Transrecord>>> getTransrecords(
            @RequestParam(required = false) Integer accountId,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            @RequestParam int page,
            @RequestParam int size) {
        //分页查询数据
        List<Transrecord> allRecord =  transrecordService.getTransrecordsByAccountId(accountId,start,end, page, size);
       //查询total
        long total = transrecordService.getTotalCountByAccountId(accountId, start, end);
        //返回
        PagedResponse<List<Transrecord>> response = new PagedResponse<>(allRecord,total);
        return new JsonResponse<>(response);
    }

    //转账接口(已测试)
    @PostMapping("/trans-money")
    public JsonResponse<Transrecord> transferMoney(
            @RequestParam Integer fromAccountId,
            @RequestParam String toAccountName,
            @RequestParam String toPersonName,
            @RequestParam BigDecimal amount,
            @RequestParam String password) {
        Transrecord transrecord = transrecordService.transferMoney(fromAccountId,toAccountName, toPersonName,amount, password);
        return new JsonResponse<>(transrecord);
    }
}
