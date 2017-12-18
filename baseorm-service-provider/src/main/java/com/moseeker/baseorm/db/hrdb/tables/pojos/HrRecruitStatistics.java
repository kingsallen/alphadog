/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Date;

import javax.annotation.Generated;


/**
 * 招聘数据次数统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrRecruitStatistics implements Serializable {

    private static final long serialVersionUID = 572214236;

    private Integer id;
    private Integer positionId;
    private Integer companyId;
    private Integer jdPv;
    private Integer recomJdPv;
    private Integer favNum;
    private Integer recomFavNum;
    private Integer applyNum;
    private Integer recomApplyNum;
    private Date    createDate;
    private Integer afterReviewNum;
    private Integer recomAfterReviewNum;
    private Integer afterInterviewNum;
    private Integer recomAfterInterviewNum;
    private Integer onBoardNum;
    private Integer recomOnBoardNum;
    private Integer notViewedNum;
    private Integer recomNotViewedNum;
    private Integer notQualifiedNum;
    private Integer recomNotQualifiedNum;

    public HrRecruitStatistics() {}

    public HrRecruitStatistics(HrRecruitStatistics value) {
        this.id = value.id;
        this.positionId = value.positionId;
        this.companyId = value.companyId;
        this.jdPv = value.jdPv;
        this.recomJdPv = value.recomJdPv;
        this.favNum = value.favNum;
        this.recomFavNum = value.recomFavNum;
        this.applyNum = value.applyNum;
        this.recomApplyNum = value.recomApplyNum;
        this.createDate = value.createDate;
        this.afterReviewNum = value.afterReviewNum;
        this.recomAfterReviewNum = value.recomAfterReviewNum;
        this.afterInterviewNum = value.afterInterviewNum;
        this.recomAfterInterviewNum = value.recomAfterInterviewNum;
        this.onBoardNum = value.onBoardNum;
        this.recomOnBoardNum = value.recomOnBoardNum;
        this.notViewedNum = value.notViewedNum;
        this.recomNotViewedNum = value.recomNotViewedNum;
        this.notQualifiedNum = value.notQualifiedNum;
        this.recomNotQualifiedNum = value.recomNotQualifiedNum;
    }

    public HrRecruitStatistics(
        Integer id,
        Integer positionId,
        Integer companyId,
        Integer jdPv,
        Integer recomJdPv,
        Integer favNum,
        Integer recomFavNum,
        Integer applyNum,
        Integer recomApplyNum,
        Date    createDate,
        Integer afterReviewNum,
        Integer recomAfterReviewNum,
        Integer afterInterviewNum,
        Integer recomAfterInterviewNum,
        Integer onBoardNum,
        Integer recomOnBoardNum,
        Integer notViewedNum,
        Integer recomNotViewedNum,
        Integer notQualifiedNum,
        Integer recomNotQualifiedNum
    ) {
        this.id = id;
        this.positionId = positionId;
        this.companyId = companyId;
        this.jdPv = jdPv;
        this.recomJdPv = recomJdPv;
        this.favNum = favNum;
        this.recomFavNum = recomFavNum;
        this.applyNum = applyNum;
        this.recomApplyNum = recomApplyNum;
        this.createDate = createDate;
        this.afterReviewNum = afterReviewNum;
        this.recomAfterReviewNum = recomAfterReviewNum;
        this.afterInterviewNum = afterInterviewNum;
        this.recomAfterInterviewNum = recomAfterInterviewNum;
        this.onBoardNum = onBoardNum;
        this.recomOnBoardNum = recomOnBoardNum;
        this.notViewedNum = notViewedNum;
        this.recomNotViewedNum = recomNotViewedNum;
        this.notQualifiedNum = notQualifiedNum;
        this.recomNotQualifiedNum = recomNotQualifiedNum;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositionId() {
        return this.positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getJdPv() {
        return this.jdPv;
    }

    public void setJdPv(Integer jdPv) {
        this.jdPv = jdPv;
    }

    public Integer getRecomJdPv() {
        return this.recomJdPv;
    }

    public void setRecomJdPv(Integer recomJdPv) {
        this.recomJdPv = recomJdPv;
    }

    public Integer getFavNum() {
        return this.favNum;
    }

    public void setFavNum(Integer favNum) {
        this.favNum = favNum;
    }

    public Integer getRecomFavNum() {
        return this.recomFavNum;
    }

    public void setRecomFavNum(Integer recomFavNum) {
        this.recomFavNum = recomFavNum;
    }

    public Integer getApplyNum() {
        return this.applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getRecomApplyNum() {
        return this.recomApplyNum;
    }

    public void setRecomApplyNum(Integer recomApplyNum) {
        this.recomApplyNum = recomApplyNum;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getAfterReviewNum() {
        return this.afterReviewNum;
    }

    public void setAfterReviewNum(Integer afterReviewNum) {
        this.afterReviewNum = afterReviewNum;
    }

    public Integer getRecomAfterReviewNum() {
        return this.recomAfterReviewNum;
    }

    public void setRecomAfterReviewNum(Integer recomAfterReviewNum) {
        this.recomAfterReviewNum = recomAfterReviewNum;
    }

    public Integer getAfterInterviewNum() {
        return this.afterInterviewNum;
    }

    public void setAfterInterviewNum(Integer afterInterviewNum) {
        this.afterInterviewNum = afterInterviewNum;
    }

    public Integer getRecomAfterInterviewNum() {
        return this.recomAfterInterviewNum;
    }

    public void setRecomAfterInterviewNum(Integer recomAfterInterviewNum) {
        this.recomAfterInterviewNum = recomAfterInterviewNum;
    }

    public Integer getOnBoardNum() {
        return this.onBoardNum;
    }

    public void setOnBoardNum(Integer onBoardNum) {
        this.onBoardNum = onBoardNum;
    }

    public Integer getRecomOnBoardNum() {
        return this.recomOnBoardNum;
    }

    public void setRecomOnBoardNum(Integer recomOnBoardNum) {
        this.recomOnBoardNum = recomOnBoardNum;
    }

    public Integer getNotViewedNum() {
        return this.notViewedNum;
    }

    public void setNotViewedNum(Integer notViewedNum) {
        this.notViewedNum = notViewedNum;
    }

    public Integer getRecomNotViewedNum() {
        return this.recomNotViewedNum;
    }

    public void setRecomNotViewedNum(Integer recomNotViewedNum) {
        this.recomNotViewedNum = recomNotViewedNum;
    }

    public Integer getNotQualifiedNum() {
        return this.notQualifiedNum;
    }

    public void setNotQualifiedNum(Integer notQualifiedNum) {
        this.notQualifiedNum = notQualifiedNum;
    }

    public Integer getRecomNotQualifiedNum() {
        return this.recomNotQualifiedNum;
    }

    public void setRecomNotQualifiedNum(Integer recomNotQualifiedNum) {
        this.recomNotQualifiedNum = recomNotQualifiedNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrRecruitStatistics (");

        sb.append(id);
        sb.append(", ").append(positionId);
        sb.append(", ").append(companyId);
        sb.append(", ").append(jdPv);
        sb.append(", ").append(recomJdPv);
        sb.append(", ").append(favNum);
        sb.append(", ").append(recomFavNum);
        sb.append(", ").append(applyNum);
        sb.append(", ").append(recomApplyNum);
        sb.append(", ").append(createDate);
        sb.append(", ").append(afterReviewNum);
        sb.append(", ").append(recomAfterReviewNum);
        sb.append(", ").append(afterInterviewNum);
        sb.append(", ").append(recomAfterInterviewNum);
        sb.append(", ").append(onBoardNum);
        sb.append(", ").append(recomOnBoardNum);
        sb.append(", ").append(notViewedNum);
        sb.append(", ").append(recomNotViewedNum);
        sb.append(", ").append(notQualifiedNum);
        sb.append(", ").append(recomNotQualifiedNum);

        sb.append(")");
        return sb.toString();
    }
}
