package com.moseeker.common.email.attachment;

import javax.mail.internet.MimeBodyPart;

/**
 * Created by chendi on 4/1/16.
 */
abstract public class Attachment {

    private String fileName;
    private MimeBodyPart attachment;

    public Attachment() {
        this.attachment = new MimeBodyPart();
    }

    public Attachment(String fileName, String resource) throws AttachmentException {
        this();
        this.setFileName(fileName);
        this.setAttachmentContentFrom(resource);
    }

    public MimeBodyPart getAttachment() {
        return this.attachment;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    abstract public void setAttachmentContentFrom(String resource) throws AttachmentException;

}


