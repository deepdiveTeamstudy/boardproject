package com.practice.boardproject.global.util;

import com.practice.boardproject.global.exception.TokenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new TokenException("유효하지 않은 토큰입니다.");
        }
        return authentication;
    }

    public static Long getCurrentMemberId() {
        Authentication authentication = getAuthentication();
        return Long.valueOf(authentication.getName());
    }

}
