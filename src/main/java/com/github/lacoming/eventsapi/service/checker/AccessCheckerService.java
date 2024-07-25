package com.github.lacoming.eventsapi.service.checker;

import com.github.lacoming.eventsapi.aop.AccessCheckType;
import com.github.lacoming.eventsapi.aop.Accessible;
import jakarta.servlet.http.HttpServletRequest;

public interface AccessCheckerService {
    boolean check(HttpServletRequest request, Accessible accessible);

    AccessCheckType getType();
}
