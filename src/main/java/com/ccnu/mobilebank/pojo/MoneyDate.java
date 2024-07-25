package com.ccnu.mobilebank.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MoneyDate {
    private BigDecimal money;
    private String transDate;

    public MoneyDate(BigDecimal money, String transDate) {
        this.money = money;
        this.transDate = transDate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }
}
