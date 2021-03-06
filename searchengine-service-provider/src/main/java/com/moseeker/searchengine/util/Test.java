package com.moseeker.searchengine.util;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.EsClientInstance;
import com.moseeker.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zztaiwll on 17/12/15.
 */
public class Test {
    private static Logger logger=Logger.getLogger(EsClientInstance.class);
    private static Integer SLEEP_TIME=0;
    private static Integer CONNECTION_NUM=0;
    private static TransportClient client=null;
    private static Integer failNum=0;
    //初始化每次连接失败的睡眠时间和最大的链接次数
    static {
        try{
            ConfigPropertiesUtil property = ConfigPropertiesUtil.getInstance();
            property.loadResource("common.properties");
            SLEEP_TIME=0;//property.get("sleep_time",Integer.class);
            CONNECTION_NUM=0;//property.get("connection_num",Integer.class);;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  static  TransportClient getClient(){
        if(client!=null){
            return client;
        }else{
            synchronized(EsClientInstance.class) {
                if(client!=null){
                    return client;
                }else {
                    while(true){
                        client=createClient();
                        logger.info("链接es客户端=========");
                        if(client!=null){
                            break;
                        }

                        try {
                            failNum=failNum+1;
                            if(failNum>CONNECTION_NUM){
                                failNum=0;
                                sendEmail();
                            }
                            logger.info("链接es客户端失败，等待1.5秒重新连接=========");
                            if(SLEEP_TIME!=null&&SLEEP_TIME!=0){
                                Thread.sleep(SLEEP_TIME);
                            }else{
                                Thread.sleep(1500);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }

        return client;
    }
    /*
     创建客户端
     */
    private static TransportClient createClient(){
        try {
            ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
            propertiesReader.loadResource("es.properties");
            String cluster_name = propertiesReader.get("es.cluster.name", String.class);
            String es_connection = propertiesReader.get("es.connection", String.class);
            Integer es_port = propertiesReader.get("es.port", Integer.class);
            Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                    .put("client.transport.sniff", true)
                    .build();
            String es_alternate = propertiesReader.get("es.alternate", String.class);
            if(org.apache.commons.lang.StringUtils.isNotBlank(es_alternate)){
                client = TransportClient.builder().settings(settings).build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port))
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_alternate), es_port));
            }else{
                client = TransportClient.builder().settings(settings).build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));
            }

        } catch (Exception e) {
            e.printStackTrace();
            if(client!=null){
                client.close();
            }
            client = null;
        }
        return client;
    }
    //关闭链接
    public synchronized static void closeEsClient(){
        if(client!=null){
            client.close();
            client=null;
        }
    }
    //发送失败报警邮件
    private static void sendEmail() throws Exception {
        ConfigPropertiesUtil propertiesUtil = ConfigPropertiesUtil.getInstance();
        String receives=propertiesUtil.get("email.es.warn",String.class);
        Mail.MailBuilder mailBuilder = new Mail.MailBuilder();
        EmailSessionConfig sessionConfig = new EmailSessionConfig(true, "smtp");
        Message message = new Message();
        message.setAppId(Constant.APPID_ALPHADOG);
        message.setEventType(0);
        EmailContent mailContent = new EmailContent();
        mailContent.setCharset(StandardCharsets.UTF_8.toString());
        List<String> recipients = new ArrayList<>();
        List<String> list=null;StringConvertList(receives,",");
        if(StringUtils.isEmptyList(list)){
            recipients.add("wengjianfei@moseeker.com");
            recipients.add("zhangzeteng@moseeker.com");
        }else{
            recipients=list;
        }
        mailContent.setRecipients(recipients);
        mailContent.setSenderDisplay(org.apache.commons.lang.StringUtils.defaultIfEmpty("", ""));
        mailContent.setSubject("es错误");
        mailContent.setContent("es服务器停止服务，请相关人员查看并处理");
        message.setEmailContent(mailContent);
        Mail mail = mailBuilder.buildSessionConfig(sessionConfig).build(message.getEmailContent());
        mail.send();

    }
    private static List<String> StringConvertList(String params,String separate){
        if(StringUtils.isNullOrEmpty(params)){
            return null;
        }
        String arr[]=params.split(",");
        List<String> list=new ArrayList<>();
        for(String ss:arr){
            list.add(ss);
        }
        return list;
    }
    public static void main(String[] args) {
        Test.getClient();
    }
}
