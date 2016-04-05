package com.moseeker.common.email;

import com.moseeker.common.email.attachment.LocalAttachment;
import com.moseeker.common.email.attachment.UrlAttachment;

/**
 * Created by chendi on 4/1/16.
 */
public class MailTest {

    public static void main(String[] args) {

        try {
            Email registerSuccessEmail = new Email();
            registerSuccessEmail.setSubject("Congratulations")
                    .addAttachment(new UrlAttachment("logo.png", "http://static.moseeker.com/official/images/moseeker-logo-phone-b92430087c.png"))
                    .addAttachment(new LocalAttachment("logo2.png", "/Users/chendi/Desktop/images/kob.jpg"))
                    .addRecipient("chendi@moseeker.com")
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
