package com.example.demo.config;


import com.example.demo.web.dto.response.CMRespDto;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

// 공통 기능 = Advice (공통기능)
// Controller, RestController, component(그 외 모든), configuration(설정할 때, 진입 전)
@Aspect
@Component
@Log4j2
public class BindingAdvice {

    // 함수제어 : 얖 뒤

    // 함수제어 : 앞 Before (username 안들어왔으면 내가 강제로 넣어주고 실행)

    // 함수제어 : 뒤 After (응답만 제어)

    // Around 통과해야 로그가 남음 우선순위 Around > After > Before

    @Before("execution(* com.example.demo.web..*Controller.*(..))")
    public void beforeTest(){
        log.info("전처리 로그를 남겼습니다");

    }

    @After("execution(* com.example.demo.web..*Controller.*(..))")
    public void afterTest(){
        log.info("후처리 로그를 남겼습니다");
    }


    @Around("execution(* com.example.demo.web..*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        BindingResult bindingResult = null;
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();

        log.info("type = " + type + " method = " + method);

        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg: args) {
            if(arg instanceof BindingResult){
                bindingResult = (BindingResult) arg;
            }
            if (bindingResult != null && bindingResult.hasErrors()){
                Map<String, String> errorMap = new HashMap<>();
                for(FieldError fe : bindingResult.getFieldErrors()){
                    errorMap.put(fe.getField(), fe.getDefaultMessage());
                }
                return new ResponseEntity<>(CMRespDto.builder()
                        .code(-1)
                        .msg(errorMap.toString())
                        .build(), HttpStatus.BAD_REQUEST); // 400 = Bad Request
            }
        }
        return proceedingJoinPoint.proceed(); //함수의 스택을 진행하라
    }

}
