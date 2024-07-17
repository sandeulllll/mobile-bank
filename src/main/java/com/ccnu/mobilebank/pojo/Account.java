package com.ccnu.mobilebank.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

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
    private Integer statusId;

    /**
     * 持卡人信息id
     */
    private Integer personId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除（0 未删除、1 删除）
     */
    private Boolean isDelete;

    /**
     * 所属银行
     */
    private String bankName;
}
