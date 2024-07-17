package com.ccnu.mobilebank.mapper;

import com.ccnu.mobilebank.pojo.Mobile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccnu.mobilebank.pojo.Personinfo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
public interface MobileMapper extends BaseMapper<Mobile> {
    Mobile getMobileByTel(String telephone);

    Integer addMobile(Mobile mobile);

    Integer updateMobilePassword(Mobile mobile);
}
