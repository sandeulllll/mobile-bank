package com.ccnu.mobilebank.mapper;

import com.ccnu.mobilebank.pojo.Personinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
public interface PersoninfoMapper extends BaseMapper<Personinfo> {
    // 获取用户信息
    Personinfo selectByPersonId(@Param("personId") Integer personId);
}
