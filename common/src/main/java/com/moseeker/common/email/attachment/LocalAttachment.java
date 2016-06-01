package com.moseeker.common.email.attachment;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

/**
 * Created by chendi on 4/5/16.
 */
public class LocalAttachment extends Attachment {

    public LocalAttachment(String fileName, String filePath) throws AttachmentException {
        super(fileName, filePath);
    }

    @Override
    public void setAttachmentContentFrom(String filePath) throws AttachmentException {
        try {
            this.getAttachment().setDataHandler(new DataHandler(new FileDataSource(filePath)));
        } catch (Exception e) {
            throw new AttachmentException("file path error" + filePath);
        }
    }

}
