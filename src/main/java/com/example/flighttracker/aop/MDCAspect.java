package com.example.flighttracker.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MDCAspect {
    private static final Logger logger = LoggerFactory.getLogger(MDCAspect.class);


    @Before(value = "execution(* com.example.flighttracker.*.*.*(..))")
    public void setMDCValues(JoinPoint joinPoint) {
        logger.info("AOP Before log");
        String userId = "UserIdInMDC";
        MDC.put("userId", userId);
    }

    @After(value = "execution(* com.example.flighttracker.*.*.*(..))")
    public void clearMDCValues() {
        logger.info("AOP after log");
        MDC.clear();
    }

}
