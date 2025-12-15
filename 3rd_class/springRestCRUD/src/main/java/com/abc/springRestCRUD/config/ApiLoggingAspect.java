package com.abc.springRestCRUD.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ApiLoggingAspect {

    @Pointcut("execution(* com.abc.springRestCRUD.service.ProductService.*(..))")
    public void productMethods(){}

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods(){}

     @Around("restControllerMethods()")
     public Object logRestApi(ProceedingJoinPoint pjp) throws Throwable{
        long start = System.currentTimeMillis();
         MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

         String className = methodSignature.getDeclaringType().getSimpleName();
         String methodName = methodSignature.getName();
         Object[]args = pjp.getArgs();

         System.out.println("===============================");
         System.out.println("API CALL");
         System.out.println("Controller: " + className);
         System.out.println("Method : " + methodName);
         System.out.println("Arguments: " + Arrays.toString(args));

         Object response = pjp.proceed();

         long duration = System.currentTimeMillis() - start;

         System.out.println("API RESPONSE");
         System.out.println("Returned : " + response);
         System.out.println("Time: " + duration + " ms");
         System.out.println("=======================\n");
         return response;
     }

//     @Before("productMethods()")
//    public void beforeAdvice(JoinPoint joinPoint){
//         System.out.println("Before Method: " + joinPoint.getSignature());
//     }

//    @After("productMethods()")
//    public void afterAdvice(JoinPoint joinPoint){
//        System.out.println("After Method: " + joinPoint.getSignature());
//    }



//    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//     public void controllerMethods(){}
//
//    @Around("controllerMethods()")
//    public Object logApiCall(ProceedingJoinPoint pjp) throws Throwable{
//        long start = System.currentTimeMillis();
//        String methodName = pjp.getSignature().toShortString();
//        Object[]args = pjp.getArgs();
//        System.out.println("API Call: " + methodName);
//        System.out.println("Arguments: " + Arrays.toString(args));
//        Object result = pjp.proceed();
//        long timeTaken = System.currentTimeMillis() - start;
//        System.out.println("API Response: " + result);
//        System.out.println("Execution Time: " + timeTaken + " ms");
//        return result;
//    }



//    public static void printBefore(){
//        System.out.println("---------------Hello--------------------");
//    }


}
