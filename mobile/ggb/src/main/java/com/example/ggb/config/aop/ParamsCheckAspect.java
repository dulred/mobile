package com.example.ggb.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import com.example.ggb.util.PcheckUtil;

@Aspect
@Component
public class ParamsCheckAspect {
 
 
    /***
     * 定义Pcheck使用的方法为切入点
     * @param: []
     * @return: void
     * @author: dulred
     * @date: 2023/5/10 9:56
     */
    @Pointcut("@annotation(com.example.ggb.config.annotation.PCheck)") // 定义切点
    public void pointcut() {}
 
    /***
     * 生命周期 为方法执行前
     * @param: [joinPoint]
     * @return: void
     * @author: dulred
     * @date: 2023/5/10 9:56
     */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) throws Exception {
        // 获取方法参数
        Object[] args = joinPoint.getArgs(); 
        for (Object arg : args) {
            // 对参数进行校验
            PcheckUtil.validate(arg); 
        }
    }
 
}
