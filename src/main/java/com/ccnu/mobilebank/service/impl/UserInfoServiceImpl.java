package com.ccnu.mobilebank.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccnu.mobilebank.pojo.UserInfo;
import com.ccnu.mobilebank.mapper.UserInfoMapper;
import com.ccnu.mobilebank.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public String selectAvatar(Integer mobileId){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobileId", mobileId);
        UserInfo user = userInfoMapper.selectOne(queryWrapper);
        return user.getAvatar();
    }
}
