package com.ccnu.mobilebank.handler;

import com.ccnu.mobilebank.pojo.exception.ConditionException;
import com.ccnu.mobilebank.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader("Authorization");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");

        if (token == null || token.isEmpty()) {
            throw new ConditionException("597", "Token is missing");
        }

        try {
            List<Integer> mobileAndPerson = TokenUtil.verifyToken(token);
            // Optionally, set user info in the request context here
        } catch (ConditionException e) {
            // Let the global exception handler catch this exception
            throw e;
        }
        return true;
    }
}

