package com.ccnu.mobilebank.service.impl;

import com.ccnu.mobilebank.mapper.PersoninfoMapper;
import com.ccnu.mobilebank.pojo.Mobile;
import com.ccnu.mobilebank.mapper.MobileMapper;
import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.pojo.exception.ConditionException;
import com.ccnu.mobilebank.service.IMobileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccnu.mobilebank.service.IPersoninfoService;
import jakarta.annotation.Resource;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileServiceImpl extends ServiceImpl<MobileMapper, Mobile> implements IMobileService {

    @Autowired
    private PersoninfoMapper personinfoMapper;

    @Override
    public void addMobile(Mobile mobile) {
        String telephone = mobile.getTelephone();
        String password = mobile.getPassword();
        //TODO:检查手机号的用户是否在PersonInfo里存在
        Personinfo dbPersonInfo = personinfoMapper.getPersonInfoByTel(telephone);
        if(dbPersonInfo != null){
            throw new ConditionException("该用户尚未登记！");
        }
        //TODO:检查手机号在Mobile表里是否已经存在
        Mobile dbMobile = baseMapper.getMobileByTel(telephone);
        if(dbMobile != null) {
            throw new ConditionException("该手机号已经注册！");
        }

        //TODO：进行注册操作
    }
}
