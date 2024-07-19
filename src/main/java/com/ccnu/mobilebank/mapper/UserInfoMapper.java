package com.ccnu.mobilebank.mapper;

import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.pojo.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    // 获取头像
    String getAvatar(String mobileId);



    // 修改头像
    void updateAvatar(String personId, String avatar);
}
