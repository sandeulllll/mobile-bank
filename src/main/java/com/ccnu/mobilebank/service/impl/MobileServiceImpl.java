package com.ccnu.mobilebank.service.impl;

import com.ccnu.mobilebank.mapper.PersoninfoMapper;
import com.ccnu.mobilebank.pojo.Mobile;
import com.ccnu.mobilebank.mapper.MobileMapper;
import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.pojo.exception.ConditionException;
import com.ccnu.mobilebank.service.IMobileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccnu.mobilebank.service.IPersoninfoService;
import com.ccnu.mobilebank.util.TokenUtil;
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
        if(dbPersonInfo == null){
            throw new ConditionException("该用户尚未登记！");
        }
        //TODO:检查手机号在Mobile表里是否已经存在
        Mobile dbMobile = baseMapper.getMobileByTel(telephone);
        if(dbMobile != null) {
            throw new ConditionException("该手机号已经注册！");
        }

        //TODO：进行注册操作
        //TODO：可能需要对其他字段进行设置
        mobile.setIsDelete(false);
        baseMapper.addMobile(mobile);
    }

    @Override
    public void updateMobilePassword(Mobile mobile) {
        //TODO:可能需要对密码进行判断
        //TODO:可能需要对其他字段进行操作
        baseMapper.updateMobilePassword(mobile);
    }

    @Override
    public String login(Mobile mobile) throws Exception {
        //TODO:验证手机号账户是否存在
        //TODO:验证密码是否正确
        String telephone = mobile.getTelephone();
        String password = mobile.getPassword();
        Mobile dbMobile = baseMapper.getMobileByTel(telephone);
        if(dbMobile == null){
            throw new ConditionException("该手机号未注册！");
        }
        String dbPassword = dbMobile.getPassword();
        if(!password.equals(dbPassword)){
            throw new ConditionException("密码错误!");
        }
        Personinfo dbPersonInfo = personinfoMapper.getPersonInfoByTel(telephone);
        Integer personId = dbPersonInfo.getId();
        Integer mobileId = dbMobile.getId();
        return TokenUtil.generateToken(mobileId,personId);
    }
}
