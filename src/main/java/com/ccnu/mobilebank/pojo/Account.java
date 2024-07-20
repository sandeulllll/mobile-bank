package com.ccnu.mobilebank.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
@Getter
@Setter
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 银行卡id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 银行卡账户名
     */
    @TableField("accountName")
    private String accountName;

    /**
     * 银行卡密码
     */
    private String password;

    /**
     * 银行卡余额
     */
    private BigDecimal balance;

    /**
     * 银行卡状态
     */
    @TableField("statusId")
    private Integer statusId;

    /**
     * 持卡人信息id
     */
    @TableField("personId")
    private Integer personId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除（0 未删除、1 删除）
     */
    @TableField("isDelete")
    private Boolean isDelete;

    /**
     * 所属银行
     */
    @TableField("bankName")
    private String bankName;
}
