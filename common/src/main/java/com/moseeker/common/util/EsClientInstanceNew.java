package com.moseeker.common.util;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Message;
import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zztaiwll on 17/12/5.
 */
public class EsClientInstanceNew {
    private static Logger logger=Logger.getLogger(EsClientInstanceNew.class);
    private static Integer SLEEP_TIME=0;
    private static Integer CONNECTION_NUM=0;
    private static TransportClient client=null;
    private static Integer failNum=0;
    //初始化每次连接失败的睡眠时间和最大的链接次数
    static {
        try{
            ConfigPropertiesUtil property = ConfigPropertiesUtil.getInstance();
            property.loadResource("common.properties");
            SLEEP_TIME=property.get("sleep_time",Integer.class);
            CONNECTION_NUM=property.get("connection_num",Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  static  TransportClient getClient(){
        if(client!=null){
            return client;
        }else{
            synchronized(EsClientInstanceNew.class) {
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
                            logger.info("es客户端连接失败，失败次数为="+failNum);
                            if(CONNECTION_NUM==null||CONNECTION_NUM==0){
                                CONNECTION_NUM=20;
                            }
                            if(failNum>CONNECTION_NUM){
                                failNum=1;
                                logger.info("es客户端连接失败，连接失败次数重置");
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
            String cluster_name = propertiesReader.get("es.cluster.name1", String.class);
            String es_connection = propertiesReader.get("es.connection1", String.class);
            Integer es_port = propertiesReader.get("es.port", Integer.class);
            Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                    .put("client.transport.sniff", true)
                    .build();
            List<String> esList=StringConvertList(es_connection,",");
            for(int i=0;i<esList.size();i++){
                if(i==0){
                    client = TransportClient.builder().settings(settings).build()
                            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esList.get(i)), es_port));
                }
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esList.get(i)), es_port));
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
//            recipients.add("wengjianfei@moseeker.com");
//            recipients.add("zhangzeteng@moseeker.com");
        }else{
            recipients=list;
        }
        if(!StringUtils.isEmptyList(recipients)){
            mailContent.setRecipients(recipients);
            mailContent.setSenderDisplay(org.apache.commons.lang.StringUtils.defaultIfEmpty("", ""));
            mailContent.setSubject("es错误");
            String cluster_name = propertiesUtil.get("es.cluster.name", String.class);
            mailContent.setContent("es服务器停止服务，请相关人员查看并处理! 环境："+cluster_name);
            message.setEmailContent(mailContent);
            Mail mail = mailBuilder.buildSessionConfig(sessionConfig).build(message.getEmailContent());
            mail.send();
        }

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

}