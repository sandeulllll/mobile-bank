package com.ccnu.mobilebank.support;

import com.ccnu.mobilebank.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserSupport {

    public Integer getCurrentMobileId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Integer mobileId = TokenUtil.verifyToken(token).get(0);
        return mobileId;
    }

    public Integer getCurrentPersonId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Integer personId = TokenUtil.verifyToken(token).get(1);
        return personId;
    }
}
