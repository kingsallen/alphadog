package com.moseeker.position.service.position.job58.dto;

/**
 * @author cjm
 * @date 2018-11-30 11:56
 **/
public class Job58PositionUpshelfDTO extends Base58UserInfoDTO {

    private Boolean useFreeResourceOnly;
    private String extension;
    private String infoId;

    public Boolean getUseFreeResourceOnly() {
        return useFreeResourceOnly;
    }

    public void setUseFreeResourceOnly(Boolean useFreeResourceOnly) {
        this.useFreeResourceOnly = useFreeResourceOnly;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }
}
