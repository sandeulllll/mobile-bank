package com.ccnu.mobilebank.service.impl;

import com.ccnu.mobilebank.pojo.Mobile;
import com.ccnu.mobilebank.mapper.MobileMapper;
import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.pojo.exception.ConditionException;
import com.ccnu.mobilebank.service.IMobileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.aop.ThrowsAdvice;
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
public class MobileServiceImpl extends ServiceImpl<MobileMapper, Mobile> implements IMobileService {

}
