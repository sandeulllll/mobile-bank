package com.ccnu.mobilebank.support;

import com.ccnu.mobilebank.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Component
public class UserSupport {
    //只获取MobileId
    public Integer getCurrentMobileId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        List<Integer> mobileAndPerson = TokenUtil.verifyToken(token);
        Integer mobileId = mobileAndPerson.get(0);
        return mobileId;
    }
    //返回一个List，包含MobileId和PersonId，下标分别是0和1
    public Integer getCurrentPersonId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Integer personId = TokenUtil.verifyToken(token).get(1);
        return personId;
    }

}
