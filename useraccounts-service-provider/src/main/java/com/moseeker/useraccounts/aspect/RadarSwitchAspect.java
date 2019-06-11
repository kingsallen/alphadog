package com.moseeker.useraccounts.aspect;

import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.CompanySwitchVO;
import com.moseeker.useraccounts.annotation.RadarSwitchLimit;
import com.moseeker.useraccounts.exception.UserAccountException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RadarSwitchAspect {

    private static ThreadLocal<Map> local = new ThreadLocal<>();

    public static final String RADAR_LANAGUE="人脉雷达";

    CompanyServices.Iface service = ServiceManager.SERVICEMANAGER.getService(CompanyServices.Iface.class);

    private static final String POINCUT = "@annotation(com.moseeker.useraccounts.annotation.RadarSwitchLimit)";

    @Before(POINCUT)
    public void beforeCall(JoinPoint joinPoint) {
        if(getRadarLimit() == null) {
            Object obj = joinPoint.getArgs()[0];
            int companyId = 0;
            if (obj instanceof Integer) {
                companyId = (Integer) obj;
            }
            Map<String, Object> params = new HashMap<>();
      //      params.put("radar_status", switchCheck(companyId));
            params.put("radar_status", 1);
            local.set(params);
        }
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        boolean isExit = method.isAnnotationPresent(RadarSwitchLimit.class);
        if(isExit){
            RadarSwitchLimit switchLimit = method.getAnnotation(RadarSwitchLimit.class);
            if(switchLimit.status()){
                checkStrictAuthorityLimit();
            }
        }
    }

    @After(POINCUT)
    public void afterCall() {
        local.remove();
    }

    public static Map getRadarLimit(){
        return local.get();
    }

    private int switchCheck(int companyId){
        try {
            CompanySwitchVO  switchVO = service.companySwitch(companyId, RADAR_LANAGUE);
            if(switchVO != null){
               if(switchVO.getKeyword().equals(RADAR_LANAGUE) && switchVO.getValid() == 1){
                   return 1;
               }

            }
        } catch (TException e) {
           throw CommonException.PROGRAM_EXCEPTION;
        }
        return 0;
    }

    public static void checkStrictAuthorityLimit(){
        if("0".equals(String.valueOf(local.get().get("radar_status")))){
            throw UserAccountException.RADAR_STATUS_CLOSE;
        }
    }

    public static boolean checkSoftAuthorityLimit(){
        return "1".equals(String.valueOf(local.get().get("radar_status")));
    }


}
