package com.moseeker.chat.utils;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.chat.constant.ChatVoiceConstant;
import com.moseeker.chat.exception.VoiceErrorEnum;
import com.moseeker.common.constants.RespnoseUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 上传下载工具
 *
 * @author cjm
 * @date 2018-05-14 15:06
 **/
public class UpDownLoadUtil {
    
    private static Logger logger = LoggerFactory.getLogger(UpDownLoadUtil.class);
    /**
     * 语音下载路径
     */
    public static final String VOICE_DOWNLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    /**
     * 语音上传路径
     */
    public static final String VOICE_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    /**
     * 下载多媒体文件（语音）
     * @param accessToken token
     * @return 本地路径
     * @author cjm
     * @date
     */
    public static Response downloadMediaFileFromWechat(String accessToken, String mediaId) {
        HttpURLConnection connection = null;
        BufferedInputStream in = null;
        BufferedOutputStream outputStream = null;
        try {
            String requestUrl = VOICE_DOWNLOAD_URL.replace("ACCESS_TOKEN", accessToken);
            requestUrl = requestUrl.replace("MEDIA_ID", mediaId);
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 设置通用的请求属性
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Content-type", "application/json");
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(25000);
            connection.setReadTimeout(25000);
            in = new BufferedInputStream(connection.getInputStream());
            String fileURL = ChatVoiceConstant.VOICE_LOCAL_URL;
            File dir = new File(fileURL);
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
                if (!flag) {
                    logger.error("==================语音存储文件夹创建失败==================");
                    return RespnoseUtil.PROGRAM_EXCEPTION.toResponse();
                }
            }
            // 如果微信返回值中无文件信息，发送报警邮件
            String contentDispostion = connection.getHeaderField("Content-disposition");
            if(StringUtils.isNullOrEmpty(contentDispostion)){
                byte[] bytes = new byte[1024];
                in.read(bytes);
                String str = new String(bytes, "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(str);
                return ResponseUtils.fail(VoiceErrorEnum.VOICE_SEND_WARN_EMAIL.getCode(), VoiceErrorEnum.VOICE_SEND_WARN_EMAIL.getMsg(), jsonObject);
            }
            // 获取文件名字
            String weixinServerFileName = contentDispostion.substring(contentDispostion.indexOf("filename") + 10, contentDispostion.length() - 1);
            DateTime now = DateTime.now();
            String dirPath = fileURL + File.separator + now.getYear() + File.separator + now.getMonthOfYear() + File.separator +  now.getDayOfMonth();
            File file = new File(dirPath);
            if(!file.exists()){
                file.mkdirs();
            }

            String fileLocalUrl = dirPath + File.separator + weixinServerFileName;
            outputStream = new BufferedOutputStream(new FileOutputStream(fileLocalUrl));
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            JSONObject urlObject = new JSONObject();
            urlObject.put("fileLocalUrl", fileLocalUrl);
            urlObject.put("file_address", dirPath);
            urlObject.put("file_name", weixinServerFileName);
            return ResponseUtils.success(urlObject);
        } catch (Exception e) {
            logger.error("=============下载语音时发生错误,mediaId:{}==============", mediaId);
            e.printStackTrace();
        } finally {
            // 释放资源
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return RespnoseUtil.PROGRAM_EXCEPTION.toResponse();
    }

    /**
     *
     * @param   fileType 文件类型
     * @param   filePath 本地文件路径
     * @author  cjm
     * @date  2018/5/14
     * @return 微信服务器response对象
     */
    public static JSONObject upload(String accessToken, String fileType, String filePath) throws Exception {
        String result;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        /**
         * 第一部分
         */
        URL urlObj = new URL(VOICE_UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", fileType));
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        // 以Post方式提交表单，默认get方式
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        // post方式不能使用缓存
        con.setUseCaches(false);
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String boundary = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ boundary);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(boundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分，定义最后数据分隔线
        byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            result = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if(reader!=null){
                reader.close();
            }
        }
        return JSONObject.parseObject(result);
    }

}
