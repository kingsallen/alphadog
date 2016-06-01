package com.moseeker.common.email.attachment;

import javax.activation.DataHandler;
import java.net.URL;

/**
 * Created by chendi on 4/5/16.
 */
public class UrlAttachment extends Attachment {

    public UrlAttachment(String fileName, String url) throws AttachmentException {
        super(fileName, url);
    }

    public void setAttachmentContentFrom(String url) throws AttachmentException {
        try {
            this.getAttachment().setDataHandler(new DataHandler(new URL(url)));
        } catch (Exception e) {
            throw new AttachmentException("url attachment error" + url);
        }
    }

}
