package com.github.lacoming.eventsapi.utils;

import com.github.lacoming.eventsapi.exception.ServerException;
import com.github.lacoming.eventsapi.security.AppUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class AuthUtils {
    public Long getCurrentUserId(UserDetails userDetails){
        if(userDetails instanceof AppUserDetails details){
            return details.getId();
        }
        throw new SecurityException("UserDetails is not instanceof AppUserDetails");
    }

    public AppUserDetails getAuthenticatedUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof AppUserDetails details){
            return details;
        }

        throw new ServerException("Principal in security context is not instanceof AppUserDetails");
    }
}
