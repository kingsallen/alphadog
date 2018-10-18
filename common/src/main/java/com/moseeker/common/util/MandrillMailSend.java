package com.moseeker.common.util;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.MergeVar;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.MergeVarBucket;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by moseeker on 2017/12/20.
 */
public class MandrillMailSend {
    private static Logger logger = LoggerFactory.getLogger(MandrillMailSend.class);
    private static MandrillApi mandrillApi;
    /**
     * 初始化业务邮件处理工具
     *
     * @throws IOException
     * @throws MessagingException
     */
    private static void initConstantlyMail(String mandrillApikey) throws IOException, MessagingException {
        mandrillApi = new MandrillApi(mandrillApikey);
    }

    public static Response  sendEmail(Map<String, Object> params,String mandrillApikey) {

        try {
            initConstantlyMail(mandrillApikey);
            if (params == null || params.get("to_email") == null || params.get("to_name") == null || params.get("mergeVars") == null
                     || params.get("templateName") == null)
                return null;

            MandrillMessage message = new MandrillMessage();

            List<Recipient> recipients = new ArrayList<Recipient>();
            Recipient recipient = new Recipient();
            recipient.setEmail((String) params.get("to_email"));

            if (StringUtils.isNotNullOrEmpty((String) params.get("to_name"))) {
                recipient.setName((String) params.get("to_name"));
            }
            recipients.add(recipient);
            message.setTo(recipients);

            List<MergeVarBucket> mergeVars = new ArrayList<MergeVarBucket>();
            MergeVarBucket mergeVar = new MergeVarBucket();
            MergeVar[] vars = new MergeVar[((Map<String, Object>) params.get("mergeVars")).size()];
            int vars_i = 0;
            for (Map.Entry<String, Object> entry : ((Map<String, Object>) params.get("mergeVars")).entrySet()) {
                vars[vars_i] = new MergeVar();
                vars[vars_i].setName(entry.getKey());
                vars[vars_i].setContent(entry.getValue());
                vars_i++;
            }

            if (vars_i > 0) {
                mergeVar.setVars(vars);
                mergeVar.setRcpt((String) params.get("to_email"));
                mergeVars.add(mergeVar);
                message.setMergeVars(mergeVars);
            }

            if (StringUtils.isNotNullOrEmpty((String) params.get("subject"))) {
                message.setSubject((String) params.get("subject"));
            }

            if (params.get("from_email") != null && StringUtils.isNotNullOrEmpty((String) params.get("from_email"))) {
                message.setFromEmail((String) params.get("from_email"));
            }

            if (params.get("from_name")!=null && StringUtils.isNotNullOrEmpty((String) params.get("from_name"))) {
                message.setFromName((String) params.get("from_name"));
            }

            message.setTo(recipients);
            message.setMerge(true);
            message.setInlineCss(true);
            message.setMergeLanguage("handlebars");
            message.setImportant(false);
            message.setTrackingDomain("moseeker.com");
            message.setTrackClicks(true);
            message.setTrackOpens(true);
            message.setViewContentLink(true);

            MandrillMessageStatus[] messageStatus = mandrillApi.messages().sendTemplate((String) params.get("templateName"),
                    null, message, false);


            if (messageStatus.length == 0) {
                logger.error("mandrill send failed: " + params.get("to_email"));
                return ResponseUtils.fail(9999,"failed," + params.get("templateName") + "," + message.getSubject());

            } else {
                logger.debug(messageStatus[0].getEmail() + " " + messageStatus[0].getStatus());

                return ResponseUtils.fail(0,messageStatus[0].getStatus() + "," + params.get("templateName") + "," + message.getSubject());

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ResponseUtils.fail(9999,"failed," + params.get("templateName"));
    }
}
