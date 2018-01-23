package com.moseeker.application.context.listener;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.application.context.event.ViewApplicationListEvent;
import com.moseeker.application.domain.component.state.ApplicationStatus;
import com.moseeker.application.infrastructure.DaoManagement;
import com.moseeker.common.util.ConfigPropertiesUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * HR浏览申请积分添加监听器
 * Created by jack on 17/01/2018.
 */
@Component
public class ViewApplicationAwardListener implements SmartApplicationListener {

    @Autowired
    DaoManagement daoManagement;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String domain = ConfigPropertiesUtil.getInstance().get("apply.view_application_domain", String.class);
    private final String url = domain+"/employee/award";

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (eventType == ViewApplicationListEvent.class) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        if (sourceType == List.class) {
            return true;
        }
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        logger.info("ViewApplicationAwardListener onApplicationEvent");

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            List<Integer> applicationIdList = (List<Integer>) event.getSource();
            ApplicationStatus status = ApplicationStatus.CVChecked;

            HttpPost post = new HttpPost(url);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("application_ids", applicationIdList);
            jsonObject.put("event_type", status.getState());
            StringEntity entity = new StringEntity(jsonObject.toString());
            post.setEntity(entity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = httpclient.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("onApplicationEvent response error", response.getEntity().toString());
            }
            response.close();

        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
