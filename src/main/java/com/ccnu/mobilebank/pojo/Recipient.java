package com.ccnu.mobilebank.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
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
public class Recipient implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 收款人的银行卡id
     */
    @TableField("otherId")
    private Integer otherId;

    /**
     * 收款人的手机id
     */
    @TableField("telId")
    private Integer telId;

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

    @TableField(exist=false)
    private Personinfo personinfo;
}
