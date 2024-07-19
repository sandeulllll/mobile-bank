package com.ccnu.mobilebank.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccnu.mobilebank.mapper.MobileaccountMapper;
import com.ccnu.mobilebank.pojo.Mobileaccount;
import com.ccnu.mobilebank.service.IMobileaccountService;
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
public class MobileaccountServiceImpl extends ServiceImpl<MobileaccountMapper, Mobileaccount> implements IMobileaccountService {
    @Autowired
    private MobileaccountMapper mobileaccountMapper;

    // 根据手机号id删除账户
    @Override
    public boolean removeBankAccount(Integer id, Integer mobileId){
        QueryWrapper<Mobileaccount> wrapper = new QueryWrapper<>();
        wrapper.eq("telId",mobileId).eq("id",id);
        return mobileaccountMapper.delete(wrapper) > 0;
    }
}
