package com.moseeker.baseorm.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.exception.CommonServiceException;
import com.moseeker.common.util.ConfigPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 配置解析
 *
 * @Author jack
 * @Date 2019/7/19 3:39 PM
 * @Version 1.0
 */
public class ConfigurationClient {

    public ConfigurationClient(String configServerDomain, String application, String profile, String branch) {

        if (StringUtils.isNotBlank(configServerDomain)) {
            this.configServerDomain = configServerDomain;
        }
        if (StringUtils.isNotBlank(application)) {
            this.application = application;
        }
        if (StringUtils.isNotBlank(profile)) {
            this.profile = profile;
        }
        if (StringUtils.isNotBlank(branch)) {
            this.branch = branch;
        }
    }

    /**
     * 获取配置信息
     * @throws CommonServiceException 业务异常
     */
    public void fetchProperties() throws CommonServiceException {
        String url = generateURL();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(httpGet);) {
            HttpEntity responseEntity = response.getEntity();
            logger.debug("ConfigurationClient fetchProperties 响应状态：{}", response.getStatusLine());
            if (responseEntity != null) {
                logger.debug("ConfigurationClient fetchProperties 文本长度：{}", responseEntity.getContentLength());
                config = EntityUtils.toString(responseEntity, "utf-8");
            }
        } catch (Exception e) {
            throw CommonServiceException.CONFIG_SERVER_ERROR;
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 更新数据库相关配置
     */
    public void appendDBProperty() throws CommonServiceException {
        try {
            JSONObject jsonObject = JSONObject.parseObject(config);
            if (jsonObject != null && jsonObject.get("propertySources") != null) {
                JSONObject jsonObj;
                if (jsonObject.get("propertySources") instanceof JSONArray) {
                    jsonObj = ((JSONArray) jsonObject.get("propertySources")).getJSONObject(0);
                } else if (jsonObject.get("propertySources") instanceof JSONObject) {
                    jsonObj = (JSONObject) jsonObject.get("propertySources");
                } else {
                    jsonObj = null;
                }
                if (jsonObj != null && jsonObj.get("source") != null) {
                    JSONObject source = jsonObj.getJSONObject("source");
                    String driverClassName = source.getString("spring.datasource.primary.driver-class-name");
                    if (StringUtils.isNotBlank(driverClassName)) {
                        configPropertiesUtil.appendConfig("mycat.classname", driverClassName);
                    }

                    String dialect = source.getString("spring.jooq.sql-dialect");
                    if (StringUtils.isNotBlank(dialect)) {
                        configPropertiesUtil.appendConfig("mycat.dialect", dialect);
                    }
                    //String type = (String) source.getString("spring.datasource.primary.type");
                    String host = source.getString("spring.datasource.primary.host");
                    String port = source.getInteger("spring.datasource.primary.port").toString();
                    String database = source.getString("spring.datasource.primary.database");
                    String url = (source.getString("spring.datasource.primary.url"))
                            .replace("${spring.datasource.primary.host}", host)
                            .replace("${spring.datasource.primary.port}", port)
                            .replace("${spring.datasource.primary.database}", database);
                    if (StringUtils.isNotBlank(url)) {
                        configPropertiesUtil.appendConfig("mycat.url", url);
                    }
                    String username = (String) source.get("spring.datasource.primary.username");
                    if (StringUtils.isNotBlank(username)) {
                        configPropertiesUtil.appendConfig("mycat.userName", username);
                    }
                    String password = (String) source.get("spring.datasource.primary.password");
                    if (StringUtils.isNotBlank(password)) {
                        configPropertiesUtil.appendConfig("mycat.password", password);
                    }
                    //Boolean testWhileIdle = (Boolean) source.get("spring.datasource.primary.testWhileIdle");
                    //Integer timeBetweenEvictionRunsMillis = (Integer) source.get("spring.datasource.primary.timeBetweenEvictionRunsMillis");
                    //String validationQuery = (String) source.get("spring.datasource.primary.validationQuery");

                }
            }
        } catch (Exception e) {
            throw CommonServiceException.CONFIG_SERVER_ERROR;
        }
    }

    /**
     * 获取配置服务器地址
     * @return 配置服务器地址
     */
    public String getConfigServerDomain() {
        return configServerDomain;
    }

    /**
     * 获取应用名称
     * @return 应用名称
     */
    public String getApplication() {
        return application;
    }

    /**
     * 获取分支名称
     * @return 分支名称
     */
    public String getBranch() {
        return branch;
    }

    /**
     * 获取环境名称
     * @return 环境名称
     */
    public String getProfile() {
        return profile;
    }

    /**
     * 获取配置信息
     * 如果调用获取服务器配置信息的防范，那么改方法返回服务器的配置内容
     * @return 配置信息
     */
    public String getConfig() {
        return config;
    }

    /**
     * 获取配置服务器上的配置信息
     * @return 配置服务器具体配置信息
     */
    private String generateURL() {
        if (StringUtils.isBlank(configServerDomain) || StringUtils.isBlank(application)
                || StringUtils.isBlank(profile) || StringUtils.isBlank(branch)) {
            throw CommonServiceException.CONFIG_SERVER_ERROR;
        }
        return configServerDomain + "/" + application + "/" + profile + "/" + branch;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * url地址
     */
    private String configServerDomain;
    /**
     * 应用
     */
    private String application = "user";
    /**
     * 分支
     */
    private String branch = "master";
    /**
     * 环境
     */
    private String profile;

    /**
     * 配置信息
     */
    private String config;

    /**
     * 配置信息参考库
     */
    private ConfigPropertiesUtil configPropertiesUtil = ConfigPropertiesUtil.getInstance();
}
