package com.moseeker.useraccounts.aspect;

import com.moseeker.useraccounts.annotation.RadarSwitchLimit;
import com.moseeker.useraccounts.exception.UserAccountException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class RadarSwitchAspect {

    private static ThreadLocal<Map> local = new ThreadLocal<>();

    @Pointcut("@annotation(com.moseeker.useraccounts.annotation.RadarSwitchLimit)")
    private void cut() {
    }

    private static final String POINCUT = "@annotation(com.moseeker.useraccounts.annotation.RadarSwitchLimit)";

    @Before(POINCUT)
    public void beforeCall(JoinPoint joinPoint) {
        Object obj = joinPoint.getArgs()[0];
        int companyId =0;
        if(obj instanceof  Integer){
            companyId = (Integer) obj;
        }
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        boolean isExit = method.isAnnotationPresent(RadarSwitchLimit.class);
        if(isExit){
            RadarSwitchLimit switchLimit = method.getAnnotation(RadarSwitchLimit.class);
            if(switchLimit.status()){
                checkSoftAuthorityLimit();
            }else{
                checkStrictAuthorityLimit();
            }
        }
        Map<String, String> map = new HashMap<>();
        local.set(map);
    }

    @After(POINCUT)
    public void afterCall(JoinPoint joinPoint) {
        local.remove();
    }

    public static Map getRadarLimit(){
        return local.get();
    }

    public static void checkStrictAuthorityLimit(){
        if("0".equals(local.get().get("switch"))){
            throw UserAccountException.RADAR_STATUS_CLOSE;
        }
    }

    public static boolean checkSoftAuthorityLimit(){
        return "0".equals(local.get().get("switch"));
    }

}
