package com.activityManager.user.aspect;

import com.activityManager.user.service.EmailService;
import com.activityManager.user.entity.dto.UserResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreationAspect {

    private final EmailService emailService;

    @AfterReturning(
            pointcut = "execution(* com.example.service.impl.AuthServiceImpl.register(..))",
            returning = "response"
    )
    public void sendWelcomeMail(JoinPoint joinPoint, UserResponse response) {
        String joinpointName = joinPoint.getSignature().getName();
        log.info("Method {}",joinpointName);
        log.info("here the aspect is doing its job ....");
        emailService.sendEmail(
                response.getEmail(),
                response.getName()
        );
    }
}
