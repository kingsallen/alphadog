package com.moseeker.profile.service.impl.retriveprofile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;

/**
 * Created by YYF
 *
 * Date: 2017/8/28
 *
 * Project_name :alphadog
 */
public class ResumeTest {

    /**
     * 简历解析
     *
     * @param url
     * @param fname
     * @param uid
     * @param pwd
     * @throws Exception
     */
    public static void testResumeParser(String url, String fname, int uid, String pwd) throws Exception {
        byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(new File(fname));
        String data = new String(Base64.encodeBase64(bytes), Consts.UTF_8);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(data, Consts.UTF_8));

        // 设置头字段
        String authStr = "admin:2015";
        String authEncoded = Base64.encodeBase64String(authStr.getBytes());
        httpPost.setHeader("Authorization", "Basic " + authEncoded);
        httpPost.addHeader("content-type", "application/json");

        // 设置内容信息
        JSONObject json = new JSONObject();
        json.put("fname", fname);    // 文件名
        json.put("base_cont", data); // 经base64编码过的文件内容
        json.put("uid", uid);        // 用户id
        json.put("pwd", pwd);        // 用户密码
        StringEntity params = new StringEntity(json.toString());
        httpPost.setEntity(params);

        // 发送请求
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse response = httpclient.execute(httpPost);

        // 处理返回结果
        String resCont = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
        JSONObject res = JSON.parseObject(resCont);
        System.out.println(res);

    }

    public static void main(String[] args) throws Exception {
        String url = "http://www.resumesdk.com/api/parse";
        //替换为你的文件名
        String fname = "/Users/yuyunfeng/Documents/YYF/余云峰的简历.docx";
        int uid = 1707240;    //替换为你的用户名
        String pwd = "462583";    //替换为你的密码（String格式）
        testResumeParser(url, fname, uid, pwd);
    }


}
