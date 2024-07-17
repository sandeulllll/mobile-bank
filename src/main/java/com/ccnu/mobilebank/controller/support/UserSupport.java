package com.ccnu.mobilebank.controller.support;

import com.ccnu.mobilebank.service.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Component
public class UserSupport {
   //只获取MobileId
    public Long getCurrentMobileId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        List<Long> mobileAndPerson = TokenUtil.verifyToken(token);
        Long mobileId = mobileAndPerson.get(0);
        return mobileId;
    }
    //返回一个List，包含MobileId和PersonId，下标分别是0和1
    public List<Long> getMobileIdAndPersonId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        List<Long> mobileAndPerson = TokenUtil.verifyToken(token);
        return mobileAndPerson;
    }


}
