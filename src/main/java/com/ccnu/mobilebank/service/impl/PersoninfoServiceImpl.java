package com.ccnu.mobilebank.service.impl;

import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.mapper.PersoninfoMapper;
import com.ccnu.mobilebank.service.IPersoninfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class PersoninfoServiceImpl extends ServiceImpl<PersoninfoMapper, Personinfo> implements IPersoninfoService {

    @Override
    public Personinfo getPersonInfo(Integer personId){
        return baseMapper.selectByPersonId(personId);
    }
}
