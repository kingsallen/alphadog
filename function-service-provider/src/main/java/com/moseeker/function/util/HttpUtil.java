package com.moseeker.function.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartParamer;

public class HttpUtil {
	private  static String getStringFromJson(JSONObject adata) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("{");  
        for(Object key:adata.keySet()){  
            sb.append("\""+key+"\":\""+adata.get(key)+"\",");  
        }  
        String rtn = sb.toString().substring(0, sb.toString().length()-1)+"}";  
        return rtn;  
    }
    public static JSONObject getHttpFormChaos(ThirdPartParamer param,String url){
    	CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpPost httppost = new HttpPost(url);  
        JSONObject sobj = new JSONObject();  
        try {  
        	    httpclient.getConnectionManager().closeIdleConnections(10000, TimeUnit.MILLISECONDS);
                JSONObject jobj = new JSONObject();  
                jobj.put("user_name", param.getUsername());  
                jobj.put("password", param.getPassword());  
                if(param.getChannel()==1){
                	jobj.put("member_name",param.getMember_name());  
                }
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
                nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj))); 
                httppost.addHeader("Content-type", "application/x-www-form-urlencoded");  
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8") );
                HttpResponse response = httpclient.execute(httppost);  
                if (response.getStatusLine().getStatusCode() == 200) {  
                    /*读返回数据*/  
                    String conResult = EntityUtils.toString(response  
                            .getEntity());      
                    sobj = (JSONObject) JSONObject.toJSON(conResult);  
                      
                } else {  
                	sobj.put("status", "0");
                	sobj.put("message", "请求失败");
                }  
        } catch (Exception e) {  
        	sobj.put("status", "0");
        	sobj.put("message", "httpclient连接失败");
        }  finally{
        }
          return sobj;
    }
    public static JSONObject httpPost(String url, List<NameValuePair> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		JSONObject obj=new JSONObject();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "utf8"));  
			httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
			HttpResponse httpResponse = httpclient.execute(httpPost);  
			if(httpResponse.getStatusLine().getStatusCode() == 200)  
			{  
			    HttpEntity httpEntity = httpResponse.getEntity();  
			    String result = EntityUtils.toString(httpEntity);//取出应答字符串
			    obj=JSONObject.parseObject(result);
			}
		} catch (Exception e1) {
			Logger.getLogger(HttpClientUtils.class).error("exception.stacktrace:"+e1.getStackTrace());
			e1.printStackTrace();
			obj.put("status", "0");
        	obj.put("message", "httpclient连接失败");
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				Logger.getLogger(HttpClientUtils.class).error("httpclient.close:"+e.getStackTrace(), e);
			}
		}
		return obj;
	}
}
