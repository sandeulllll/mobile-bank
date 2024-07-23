package com.ccnu.mobilebank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccnu.mobilebank.exception.ConditionException;
import com.ccnu.mobilebank.mapper.MobileMapper;
import com.ccnu.mobilebank.mapper.PersoninfoMapper;
import com.ccnu.mobilebank.pojo.Mobile;
import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.service.IMobileService;
import com.ccnu.mobilebank.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileServiceImpl extends ServiceImpl<MobileMapper, Mobile> implements IMobileService {

    @Autowired
    private PersoninfoMapper personinfoMapper;

    /**
     * 1.检查手机号的用户是否在PersonInfo里存在
     * 2.检查手机号在Mobile表里是否已经存在
     * 3.进行注册操作
     * @param mobile
     */
    @Override
    public void addMobile(Mobile mobile) {
        String telephone = mobile.getTelephone();
        String password = mobile.getPassword();
        Personinfo dbPersonInfo = personinfoMapper.getPersonInfoByTel(telephone);
        if(dbPersonInfo == null){
            throw new ConditionException("503","该用户尚未实名登记！");
        }
        Mobile dbMobile = baseMapper.getMobileByTel(telephone);
        if(dbMobile != null) {
            throw new ConditionException("504","该手机号已经注册！");
        }

        //TODO：进行注册操作
        //TODO：可能需要对其他字段进行设置
        mobile.setIsDelete(false);
        baseMapper.addMobile(mobile);
    }

    /**
     * 1. 验证原密码是否正确
     * 2. 进行update操作
     * @param id
     * @param password
     * @param newPassword
     */
    @Override
    public void updateMobilePassword(Integer id, String password, String newPassword) {
        Mobile dbMobile = baseMapper.getMobileById(id);
        String dbPassword = dbMobile.getPassword();
        if(!dbPassword.equals(password)){
            throw new ConditionException("505","请输入正确的原密码!");
        }
        dbMobile.setPassword(newPassword);
        baseMapper.updateMobilePassword(dbMobile);
    }

    @Override
    public void updateMobilePassword(String telephone, String newPassword) {
        Mobile dbMobile = baseMapper.getMobileByTel(telephone);
        dbMobile.setPassword(newPassword);
        baseMapper.updateMobilePassword(dbMobile);
    }

    /**
     * 1. 验证手机号是否已注册
     * 2. 验证密码是否正确
     * 3. 生成token返回
     * @param mobile
     * @return
     * @throws Exception
     */
    @Override
    public String login(Mobile mobile) throws Exception {
        String telephone = mobile.getTelephone();
        Mobile dbMobile = baseMapper.getMobileByTel(telephone);
        if(dbMobile == null){
            throw new ConditionException("506","该手机号未注册！");
        }
        if(mobile.getPassword() != null){
            String password = mobile.getPassword();
            String dbPassword = dbMobile.getPassword();
            if(!password.equals(dbPassword)){
                throw new ConditionException("507","请重新输入密码！");
            }
        }
        Personinfo dbPersonInfo = personinfoMapper.getPersonInfoByTel(telephone);
        Integer personId = dbPersonInfo.getId();
        Integer mobileId = dbMobile.getId();
        return TokenUtil.generateToken(mobileId,personId);
    }


}
