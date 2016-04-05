package com.moseeker.common.email;

import org.junit.Test;

import com.moseeker.common.email.attachment.LocalAttachment;
import com.moseeker.common.email.attachment.UrlAttachment;

/**
 * Created by chendi on 4/1/16.
 */
public class EmailTest {

    @Test
    public void sendTest() {

        try {
            Email registerSuccessEmail = new Email();
            registerSuccessEmail.setSubject("Congratulations")
                    .setBody("<h1 style='color: red; font-size: 30px;'>Email Test</h1><p>Hello world</p>")
                    .addAttachment(new UrlAttachment("logo.png", "http://static.moseeker.com/official/images/moseeker-logo-phone-b92430087c.png"))
//                    .addAttachment(new LocalAttachment("logo2.png", "/Users/chendi/Desktop/images/kobe.jpg"))
                    .addRecipient("chendi@moseeker.com")
                    .addRecipient("joker_chendi@126.com")
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
