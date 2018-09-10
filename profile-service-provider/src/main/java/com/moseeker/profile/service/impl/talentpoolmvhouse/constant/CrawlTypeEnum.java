package com.moseeker.profile.service.impl.talentpoolmvhouse.constant;

import com.moseeker.thrift.gen.common.struct.BIZException;

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

    public static CrawlTypeEnum getCrawlType(byte status) throws BIZException {
        for(CrawlTypeEnum crawlTypeEnum : CrawlTypeEnum.values()){
            if(status == crawlTypeEnum.getStatus()){
                return crawlTypeEnum;
            }
        }
        throw new BIZException(99999, "不存在简历类型");
    }
}
