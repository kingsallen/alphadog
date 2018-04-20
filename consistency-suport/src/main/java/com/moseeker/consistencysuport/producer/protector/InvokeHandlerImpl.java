package com.moseeker.consistencysuport.producer.protector;

import com.moseeker.consistencysuport.common.Notification;
import com.moseeker.consistencysuport.common.ParamConvertTool;
import com.moseeker.consistencysuport.producer.db.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jack on 09/04/2018.
 */
public class InvokeHandlerImpl implements InvokeHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;
    private Map<String, ParamConvertTool> paramConvertToolMap;
    private Notification notification;

    public InvokeHandlerImpl(ApplicationContext applicationContext, Map<String, ParamConvertTool> paramConvertToolMap,
                             Notification notification) {
        this.applicationContext = applicationContext;
        this.paramConvertToolMap = paramConvertToolMap;
        this.notification = notification;
    }

    @Override
    public void invoke(Message message) throws ConsistencyException {
        try {
            String className = message.getClassName();
            String methodName = message.getMethod();
            String params = message.getParam();
            Object object = applicationContext.getBean(className);
            if (object == null) {
                throw ConsistencyException.CONSISTENCY_INVOKE_ERROR;
            }
            Class clazz = object.getClass();
            ParamConvertTool paramConvertTool = paramConvertToolMap.get(message.getName());
            if (paramConvertTool == null) {
                throw ConsistencyException.CONSISTENCY_UNBIND_CONVERTTOOL;
            }
            Object[] paramArray = paramConvertTool.convertStorageToParam(params);
            Class[] paramClassArray = null;
            List<Class> classList = null;
            if (paramArray != null && paramArray.length > 0) {
                classList = new ArrayList<>();
                for (Object param : paramArray) {
                    classList.add(param.getClass());
                }
                paramClassArray = new Class[classList.size()];
                for (int i=0; i< classList.size(); i++) {
                    paramClassArray[i] = classList.get(i).getClass();
                }
            }
            logger.debug("reHandler className:{}, methodName:{}, params:{}", className, methodName, params);
            Method method = clazz.getMethod(methodName, paramClassArray);
            if (method != null) {
                method.invoke(object, paramArray);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage(), e);
            notification.noticeForException(e);
        }
    }
}
