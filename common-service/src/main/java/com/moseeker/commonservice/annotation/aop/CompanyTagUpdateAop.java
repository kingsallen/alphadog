package com.moseeker.commonservice.annotation.aop;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.ProfileCompanyTagService;
import java.util.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author ltf 接口统计 Aop 2016年10月31日
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CompanyTagUpdateAop implements Ordered {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ProfileCompanyTagService tagService;



    /**
     * 切入点
     */
    private static final String POINCUT = "@annotation(com.moseeker.commonservice.annotation.iface.CompanyTagUpadate)";


    /**
     * return after
     */
    @AfterReturning(value = POINCUT)
    public void afterReturn(JoinPoint call) {
        Object[] obj = call.getArgs();
        if(obj != null && obj.length > 0){
            if(obj[0] instanceof List){
                List list = (List)obj[0];
                if(!StringUtils.isEmptyList(list)){
                    for(Object o : list){
                       String str = JSONObject.toJSONString(o);
                       if(JSONObject.parse(str) instanceof  Map){
                            Map<String, Object> map = (Map<String, Object>)JSONObject.parse(str);
                            if(map.get("profile_id") != null) {
                                Integer profile_id = (Integer) map.get("profile_id");
                                tagService.handlerCompanyTag(profile_id);
                            }
                       }
                    }
                }
            }else if(obj[0] instanceof Integer){
                Integer profile_id = (Integer)obj[0];
                tagService.handlerCompanyTag(profile_id);
            }else{
                String str = JSONObject.toJSONString(obj[0]);
                if(JSONObject.parse(str) instanceof  Map){
                    Map<String, Object> map = (Map<String, Object>)JSONObject.parse(str);
                    if(map.get("profile_id") != null) {
                        Integer profile_id = (Integer) map.get("profile_id");
                        tagService.handlerCompanyTag(profile_id);
                    }
                }
            }
        }
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
