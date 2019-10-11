package com.moseeker.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.moseeker.common.exception.CommonException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

import static com.moseeker.common.constants.Constant.RETRY_UPPER_LIMIT;


public class HttpClient {

    private final static Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private static final int TIMEOUT = 30000 ; // s

    public static <V extends Object> String sendGet(String url, Map<String,V> params) {
        MultiValueMap<String,Object> multiValueMap = new LinkedMultiValueMap<>();
        params.forEach((k,v)->multiValueMap.add(k,v));
        return sendGet(url,multiValueMap);
    }

    public static <V extends Object> String sendGet(String url, String key,List<V> values) {
        MultiValueMap<String,V> multiValueMap = new LinkedMultiValueMap<>();
        values.forEach((v)->multiValueMap.add(key,v));
        return sendGet(url,multiValueMap);
    }

    public static <V extends Object> String sendGet(String url, MultiValueMap<String,V> params) {
        return sendGet(url,null,params);
    }
    public static <V extends Object> String sendGet(String url, MultiValueMap<String,String> headers,MultiValueMap<String,V> params) {
        return sendGet(concatUrl(url,params),headers,"");
    }

    private static  <V extends Object>  String concatUrl(String url, MultiValueMap<String,V> params){
        String param = null ;

        if(params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, List<V>> entry : params.entrySet()) {
                if (StringUtils.isNotNullOrEmpty(entry.getKey()) && entry.getValue() != null && !entry.getValue().isEmpty()) {
                    for (Object v : entry.getValue()) {
                        if (v != null && !"".equals(v)) {
                            String value = null;
                            try {
                                value = URLEncoder.encode(v.toString().trim(), Charsets.UTF_8.displayName());
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException("不支持utf8编码", e);
                            }
                            sb.append("&").append(entry.getKey().trim()).append("=").append(value);
                        }
                    }
                }
            }

            if (sb.length() > 1) {
                param = sb.toString().substring(1);
            }
        }
        String urlNameString = url ;
        if( StringUtils.isNotNullOrEmpty(param) ){
            urlNameString += (url.contains("?") ? "&" : "?") + param  ;
        }
        return urlNameString;
    }


    /**
     * htttps get 请求（忽略ssl证书）
     * @param url
     * @param headers
     * @param params
     * @param <V>
     * @return
     * @throws Exception
     */
    public static <V> String httpsTrustAllGet(String url, MultiValueMap<String,String> headers,MultiValueMap<String,V> params) throws Exception {
        //trustAllHttpsCertificates();
        url = concatUrl(url,params) ;
        logger.info("https请求 url:{} headers:{}",url,headers);
        try(DefaultHttpClient httpclient = new TrustAllSSLClient()) {
            HttpGet httpGet = new HttpGet(url);
            if(headers != null && headers.size() > 0){
                headers.forEach((k,list)->list.forEach(v->{
                    httpGet.setHeader(k,v);
                }));
            }
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).build();//设置请求和传输超时时间

            httpGet.setConfig(requestConfig);

            // 超时重试
            int times = 0 ;
            while (times++ <= RETRY_UPPER_LIMIT) {
                try {
                    try(CloseableHttpResponse response = httpclient.execute(httpGet)) {
                        //logger.debug("----------------------------------------");
                        //logger.debug(response.getStatusLine());
                        HttpEntity resEntity = response.getEntity();
                        try(InputStream is = resEntity.getContent()){
                            return IOUtils.toString(is,"UTF-8");
                        }
                    }
                } catch (java.net.ConnectException e) {
                    logger.error(String.format("https请求错误 url:%s params:%s headers:%s",url,params,headers),e);
                }
            }
        }
        return "" ;
    }

    /**
     *  htttps post 请求（忽略ssl证书）
     * @param url
     * @param paramter
     * @return
     */
    public static String httpsTrustAllPost(String url,String paramter){
        Map<String,String> map =new HashMap<>();
        String[] args=paramter.split("&");

        for(String str: args){
            String[] arg=str.split("=");
            map.put(arg[0],arg[1]);
        }
        String charset="utf-8";
        org.apache.http.client.HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new TrustAllSSLClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            logger.error(String.format("https请求错误 url:%s param:%s",url,paramter),ex);
        }
        return result;
    }


    public static String sendGet(String url, String param) {
        return sendGet(url,null,param);
    }
	public static String sendGet(String url, MultiValueMap<String,String> headers,String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            if( StringUtils.isNotNullOrEmpty(param) ){
                urlNameString += (url.contains("?") ? "&" : "?") + param  ;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            if(headers == null || headers.isEmpty()){
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
            }else{
                headers.forEach((k,vs)->vs.forEach(v->connection.setRequestProperty(k,v)));
            }

            // 建立实际的连接
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                logger.debug(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            //return IOUtils.toString(connection.getInputStream(),Charsets.UTF_8.name());
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),Charsets.UTF_8));
            result = new String(IOUtils.toString(in).getBytes());
        } catch (IOException e) {
            logger.error("发送GET请求出现异常！" , e);
        } finally {
            IOUtils.closeQuietly(in);

        }
        return result;
    }

	public static String sendPost(String url, String param) throws ConnectException {
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-type","application/json"); 
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	throw new ConnectException();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
	public static String sendPost(String url, String param, int connectionTimeOut, int readTimeout) throws ConnectException {
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-type","application/json"); 
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(connectionTimeOut);
            conn.setReadTimeout(readTimeout);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	throw new ConnectException();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * POST发送MultipartForm表单
     * 表单parameter参数值常用数据类型有java基本类型、String、byte[] 、InputStream 、File MultipartFile 或者直接写ContentBody
     * 当表单含有文件时，建议声明contentType,fileName。contentType为空，使用默认ContentType.DEFAULT_BINARY。
     * filename如果为空，先从parameter中查找有没filename（忽略大小写）值，如果有使用此值。
     * 当传入byte[]文件内容但未指定文件名fileName参数，同时parameter中也不包含filename（忽略大小写）参数时，抛出异常。
     * @param url
     * @param parameter form内容
     * @param contentType 文件类型
     * @param fileName 文件名称
     * @return
     * @throws IllegalArgumentException 当传入byte[]文件内容但未指定文件名fileName时，抛出异常。
     * @throws IOException 文件读取或网络请求错误是抛出IOException
     */
    public static String postMultipartForm(String url, Map<String,Object> parameter, ContentType contentType, String fileName) throws IOException{
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = new HttpPost(url);

            if(parameter != null && !parameter.isEmpty()){
                if( fileName == null){
                    for(Map.Entry<String,Object> entry : parameter.entrySet()){
                        if ( "FILENAME".equalsIgnoreCase(entry.getKey()) && entry.getValue() instanceof String){
                            fileName = entry.getValue().toString();
                        }
                    }
                }
                String filename = fileName;
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                parameter.forEach((name,value)->{
                    if( value != null){
                        ContentBody contentBody;
                        if( value instanceof String){
                            contentBody = new StringBody( value.toString(), ContentType.TEXT_PLAIN);
                        }else if( value instanceof File) {
                            if( contentType != null && org.apache.commons.lang.StringUtils.isNotBlank(filename)){
                                contentBody = new FileBody((File) value,contentType,filename);
                            }else{
                                contentBody = new FileBody((File) value);
                            }
                        }else if( value instanceof byte[]) {
                            if( filename == null) {
                                throw new IllegalArgumentException("filename不可为空" );
                            }
                            if( contentType != null && org.apache.commons.lang.StringUtils.isNotBlank(filename)){
                                contentBody = new ByteArrayBody((byte[]) value,contentType,filename);
                            }else{
                                contentBody = new ByteArrayBody((byte[]) value,filename);
                            }
                        }else if( value instanceof InputStream) {
                            if( contentType != null){
                                contentBody = new InputStreamBody((InputStream) value,contentType,filename);
                            }else{
                                contentBody = new InputStreamBody((InputStream) value,filename);
                            }
                        }else if( value instanceof MultipartFile) {
                            MultipartFile multipartFile = (MultipartFile)value;
                            byte[] bytes ;
                            try {
                                bytes = multipartFile.getBytes();
                            } catch (IOException e) {
                                throw new RuntimeException("上传文件读取异常",e);
                            }
                            if( StringUtils.isNotNullOrEmpty(multipartFile.getContentType())){
                                contentBody = new ByteArrayBody(bytes,multipartFile.getContentType(),multipartFile.getOriginalFilename());
                            }else{
                                contentBody = new ByteArrayBody(bytes,contentType != null ? contentType : ContentType.DEFAULT_BINARY,multipartFile.getOriginalFilename());
                            }
                        }else if( value instanceof ContentBody) {
                            contentBody = (ContentBody)value ;
                        }else {
                            // 其他参数统统转化为String
                            contentBody = new StringBody( value.toString(), ContentType.TEXT_PLAIN);
                        }
                        builder.addPart(name,contentBody);
                    }
                });

                HttpEntity reqEntity = builder.build();
                httppost.setEntity(reqEntity);
            }

            //logger.debug("executing request " + httppost.getRequestLine());
            try(CloseableHttpResponse response = httpclient.execute(httppost)) {
                //logger.debug("----------------------------------------");
                //logger.debug(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //logger.debug("Response content length: " + resEntity.getContentLength());
                }
                try(InputStream is = resEntity.getContent()){
                    return IOUtils.toString(is,"UTF-8");
                }
            }
        }
    }



    public static <T> T postMultipartForm(String url, Map<String,Object> parameter,String fileName, Class<T> jsonClass) throws IOException{
        logger.debug("postMultipartForm url: {}, parameter :{} ,fileName :{},jsonClass :{} ",url,parameter,fileName,jsonClass);
        String resText = postMultipartForm(url,parameter,null,fileName);
        logger.debug("postMultipartForm response {} ",resText);
        return getDataFromJsonString(resText,jsonClass);
    }

    public static <T> T getDataFromJsonString(String resText, Class<T> tClass) {
        if (StringUtils.isNullOrEmpty(resText)) return null;
        Map<String, Object> resMap = JSONObject.parseObject(resText, Map.class);
        if (resMap != null) {
            Object codeStr = resMap.getOrDefault("code",resMap.getOrDefault("status","0"));
            int code = codeStr == null ? 0 : Integer.valueOf(codeStr.toString().replaceAll("\\D+", ""));
            if (code != 0) {
                throw new CommonException(code, Objects.toString(resMap.get("message")));
            }
            JSONObject data = (JSONObject) resMap.get("data");
            if (data != null && !"".equals(data) ) {
                if( tClass.isAssignableFrom(data.getClass())){
                    return (T)data ;
                }
                return JSONObject.toJavaObject(data, tClass);
            }
        }
        return null;
    }


    /**
     * java在访问https资源时，忽略证书信任问题
     * @throws Exception
     */
    public static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{new TrustAllTrustManager()};
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier hv = (urlHostName, session) ->{
                System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                        + session.getPeerHost());
                return true;
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private static class TrustAllTrustManager implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    private static class TrustAllSSLClient extends DefaultHttpClient {
        public TrustAllSSLClient() throws Exception{
            SSLContext ctx = SSLContext.getInstance("TLS");
            TrustManager[] trustAllCerts = new TrustManager[]{new TrustAllTrustManager()};
            ctx.init(null,trustAllCerts, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = this.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf,443));
        }
    }



}
