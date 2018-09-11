package com.moseeker.profile.service.impl.talentpoolmvhouse.constant;

/**
 * 简历搬家操作类型
 * @author cjm
 * @date 2018-09-06 15:28
 **/
public enum CrawlTypeEnum {
    /**
     * 主动投递简历
     */
    APPLY_CRAWL((byte)0),
    /**
     * 已下载简历
     */
    DOWNLOAD_CRAWL((byte)1);


    CrawlTypeEnum(byte status) {
        this.status = status;
    }

    byte status;

    public byte getStatus() {
        return status;
    }

}
