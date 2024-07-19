package com.ccnu.mobilebank.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
public class Mobileaccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 手机号
     */
    @TableField("telId")
    private Integer telId;

    /**
     * 跟该手机号关联的账户
     */
    @TableField("accountId")
    private Integer accountId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private String createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private String updateTime;

    /**
     * 逻辑删除（0 未删除、1 删除）
     */
    @TableLogic
    @TableField("isDelete")
    private Boolean isDelete;

    @TableField(exist = false)
    private Account account;
}
