package com.ccnu.mobilebank.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Transrecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 支付的账户id
     */
    @TableField("accountId")
    private Integer accountId;

    /**
     * 收款的账户id
     */
    @TableField("otherId")
    private Integer otherId;

    /**
     * 交易金额
     */
    private BigDecimal money;

    /**
     * 交易日期
     */
    @TableField("transDate")
    private String transDate;

    /**
     * 交易类型
     */
    @TableField("transtypeId")
    private Integer transtypeId;

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


    //冗余字段
    private String toPerson;

    //冗余字段
    private String type;
    //冗余字段:对方的账户名
    private String toAccountName;
    //冗余字段
    private String myAccountName;

}
