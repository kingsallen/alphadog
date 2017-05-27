package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.common.util.StringUtils;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by zhangid on 2017/5/26.
 */
public class ProfileDownloadService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    final Map<Integer, String> userProfileUrls = new HashMap<>();

    String downloadApi;
    String password;

    int threadSize = 20;//下载的线程数量

    public ProfileDownloadService(String downloadApi, String password, Set<UInteger> userIds) {
        this.downloadApi = downloadApi;
        this.password = password;
        for (UInteger uId : userIds) {
            userProfileUrls.put(uId.intValue(), null);
        }
        threadSize = userProfileUrls.size() > threadSize ? threadSize : userProfileUrls.size();
    }

    public void startDownload() {
        List<DownloadRunnable> downloadRunnables = new ArrayList<>();

        for (int i = 0; i < threadSize; i++) {
            downloadRunnables.add(new DownloadRunnable());
        }

        int i = 0;
        for (Integer userId : userProfileUrls.keySet()) {
            if (i >= downloadRunnables.size()) {
                i = 0;
            }
            downloadRunnables.get(i).addTask(userId);
            i++;
        }

        for (DownloadRunnable runnable : downloadRunnables) {
            new Thread(runnable).start();
        }
    }

    public boolean isFinished() {
        if (userProfileUrls.size() == 0) {
            return true;
        }

        for (Integer userId : userProfileUrls.keySet()) {
            if (userProfileUrls.get(userId) == null) {
                return false;
            }
        }
        return true;
    }

    class DownloadRunnable implements Runnable {
        final Set<Integer> userIds = new HashSet<>();

        DownloadRunnable() {

        }

        public void addTask(Integer userId) {
            userIds.add(userId);
        }

        String getDownloadUrlByUserId(int userid) {
            try {
                if (StringUtils.isNotNullOrEmpty(downloadApi)) {
                    Map<String, Object> params = new HashMap<String, Object>() {{
                        put("user_id", userid);
                        put("password", password);
                    }};

                    logger.info("getDownloadUrlByUserId:{}:{}:{}", downloadApi, password, userid);
                    String content = HttpClient.sendPost(downloadApi, JSON.toJSONString(params));
                    logger.info("getDownloadUrlByUserId:{}:{}", userid, content);
                    Map<String, Object> mp = JsonToMap.parseJSON2Map(content);
                    Object link = mp.get("downloadlink");
                    if (link != null) {
                        return link.toString();
                    }

                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
            return "";
        }

        @Override
        public void run() {
            for (Integer userId : userIds) {
                userProfileUrls.put(userId, getDownloadUrlByUserId(userId));
            }
        }
    }

    public Map<Integer, String> getUserProfileUrls() {
        return userProfileUrls;
    }
}
