package com.moseeker.entity.Constant;

/**
 * 简历字段长度限制常量
 * Created by jack on 09/11/2017.
 */
public enum ProfileAttributeLengthLimit {

    AttachmentName(256, "附件名称"), AttachmentPath(1000, "附件存储路径"), AttachmentDescription(1000, "附件描述")
    , AwardName(256, "获奖名称"), AwardWinningStatus(256, "获奖身份"), AwardDescription(1000, "获奖描述")
    , BasicName(100, "姓名"), BasicNationalityName(100, "国籍名称"), BasicCityName(50, "现居住地, 城市名称"), BasicWeiXin(50, "微信号"), BasicQQ(10, "QQ"), BasicMotto(50, "座右铭"), BasicSelfIntroduction(1000, "自我介绍")
    , CredentialName(256, "证书名称"), CredentialOrganization(256, "证书机构"), CredentialCode(256, "证书编码"), CredentialURL(256, "认证链接"), CredentialScore(50, "成绩")
    , EducationCollegeName(256, "院校名称"), EducationCollegeLogo(256, "院校LOGO"), EducationMajorName(256, "专业名称"), EducationDescription(1000, "教育描述"), EducationStudyAbroadCountry(100, "海外留学国家")
    , IntentionTag(1010, "关键词"), IntentionCityName(50, "期望城市名称"), IntentionIndustryName(100, "行业名称"), IntentionPositionName(100, "职能名称")
    , LanguageName(100, "语言")
    , ProjectExpName(100, "项目名称"), ProjectExpCompanyName(100, "公司名称"), ProjectExpDevTool(255, "开发环境"), ProjectExpHardWare(255, "硬件环境"), ProjectSoftWare(255, "软件环境"), ProjectExpURL(512, "项目网址"), ProjectExpDescription(1000, "项目描述"), ProjectExpRole(100, "项目角色"), ProjectExpResponsibility(995, "项目职责"), ProjectExpAchievement(1000, "项目业绩"), ProjectExpMember(100, "项目成员")
    , SkillName(128, "技能名称")
    , WorkExpIndustryName(100, "行业名称"), WorkExpDepartmentName(100, "部门名称"), WorkExpPositionName(100, "职能字典名称"), WorkExpDescription(5000, "工作描述"), WorkExpCityName(50, "工作地点（城市）名称"), WorkExpReportTo(50, "汇报对象"), WorkExpReference(50, "证明人"), WorkExpResignReason(100, "离职原因"), WorkExpAchievement(1000, "主要业绩"), WorkExpJob(100, "所处职位")
    , WorksName(256, "作品名称"), WorksURL(1000, "作品网址"), WorksCover(1000, "作品封面"), WorksDescription(1000, "作品描述")
    ,CompanyName(999,"公司名称"),CompanyIndustry(100,"公司行业")
    ;

    private int lengthLimit;
    private String attributeName;
    private String name;

    private ProfileAttributeLengthLimit(int lengthLimit, String attributeName) {
        this.lengthLimit = lengthLimit;
        this.attributeName = attributeName;
        name = attributeName+":"+900;
    }

    public int getLengthLimit() {
        return lengthLimit;
    }

    @Override
    public String toString() {
        return name;
    }
}
