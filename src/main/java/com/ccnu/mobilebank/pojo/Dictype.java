package com.ccnu.mobilebank.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
public class Dictype implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dicTypeId", type = IdType.AUTO)
    private Integer dicTypeId;

    private Integer dicTypeCode;

    private String dicTypeName;
}
