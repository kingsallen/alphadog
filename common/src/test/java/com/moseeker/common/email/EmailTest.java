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
            Email email = new Email.EmailBuilder("chendi@moseeker.com")
                    .setSubject("Congratulations")
                    .setContent("<h1 style='color: red; font-size: 30px;'>Email Test</h1><p>Hello world</p>")
                    .addAttachment(new UrlAttachment("logo.png", "http://static.moseeker.com/official/images/moseeker-logo-phone-b92430087c.png"))
                    .build();
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
