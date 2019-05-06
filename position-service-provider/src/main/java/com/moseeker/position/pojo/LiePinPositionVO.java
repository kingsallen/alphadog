package com.moseeker.position.pojo;

/**
 * 猎聘职位vo
 * @author cjm
 * @date 2018-05-29 19:38
 **/
public class LiePinPositionVO {
    private String ejob_extRefid;//第三方的职位主键
    private String ejob_extfield;//自定义的职位字段
    private String ejob_title;//职位标题
    private String ejob_jobtitle;//职位所属职能，最多3个，用“,”隔开，CODE表见附件 sys_jobtitle.dic(必须选择小职能，及code是6位数)
    private String ejob_dq;//职位所属地区,只能一个,CODE表见附件sys_dq.dic
    private String ejob_privacyreq;//职位隐私状态(保密="1",不保密="0")默认：不保密
    private String ejob_salarydiscuss;//职位薪资面议状态(面议="1",非面议="0")默认：非面议
    private Float ejob_salarylow;//年薪，薪资下限，单位是万，默认值：11
    private Float ejob_salaryhigh;//年薪，薪资上限，单位是万，默认值：11
    private Integer ejob_salary_month;
    private String detail_workyears;//工作年限，阿拉伯数字，默认值：1
    private String detail_sex;//性别 男，女  默认：不限
    private Integer detail_agelow;//最低年龄要求 默认：0  范围（20--65）
    private Integer detail_agehigh;//最高年龄要求 默认：0  范围（20--65）
    private String detail_edulevel;//学历要求(不限="",博士后="005",博士="010",MBA/EMBA="020",硕士="030",本科="040",大专="050",中专="060",中技="070",高中="080",初中="090")默认：不限
    private Integer detail_edulevel_tz;//是否统招(不限="0",统招="1")默认：不限
    private String detail_report2;//汇报对象
    private Integer detail_subordinate;//下属人数, 阿拉伯数字
    private String detail_duty;//工作职责,最少30个符
    private String detail_require;//任职要求,最少30个符
    private String detail_dept;//所属部门名称
    private String detail_tags;//职位亮点用“,”隔开，每个亮点最多8个字，亮点总数最多16个
    private Integer detail_language_english;//语言要求-英语(有=1,无=0)默认:0
    private Integer detail_language_japanese;//语言要求-日语(有=1,无=0)默认:0
    private Integer detail_language_french;//语言要求-法语(有=1,无=0)默认:0
    private Integer detail_language_putong;//语言要求-普通话(有=1,无=0)默认:0
    private Integer detail_language_yueyu;//语言要求-普通话(有=1,无=0)默认:0
    private Integer detail_language_other;//语言要求-其它(有=1,无=0)默认:0
    private String detail_language_content;//语言要求-其它内容，最多80个字如果填写此字段，则detail_language_other要求为1
    private String detail_special;//专业要求,最多32个字
    private String email_list_array;//应聘简历邮件接收地址，用","隔开的字符串，最多4个
    private String ejob_level;//职位级别，0：低级职位 1：中高级职位
    private Integer publisher;//发布人id hr_account.id
    private Integer positionId;//向猎聘请求的id
    private Integer count;//招聘人数，0为不限
    private Integer ejob_id;//猎聘企业职位id
    private Integer companyId;// 公司id

    public Integer getEjob_salary_month() {
        return ejob_salary_month;
    }

    public void setEjob_salary_month(Integer ejob_salary_month) {
        this.ejob_salary_month = ejob_salary_month;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEjob_id() {
        return ejob_id;
    }

    public void setEjob_id(Integer ejob_id) {
        this.ejob_id = ejob_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getEjob_extRefid() {
        return ejob_extRefid;
    }

    public void setEjob_extRefid(String ejob_extRefid) {
        this.ejob_extRefid = ejob_extRefid;
    }

    public String getEjob_extfield() {
        return ejob_extfield;
    }

    public void setEjob_extfield(String ejob_extfield) {
        this.ejob_extfield = ejob_extfield;
    }

    public String getEjob_title() {
        return ejob_title;
    }

    public void setEjob_title(String ejob_title) {
        this.ejob_title = ejob_title;
    }

    public String getEjob_jobtitle() {
        return ejob_jobtitle;
    }

    public void setEjob_jobtitle(String ejob_jobtitle) {
        this.ejob_jobtitle = ejob_jobtitle;
    }

    public String getEjob_dq() {
        return ejob_dq;
    }

    public void setEjob_dq(String ejob_dq) {
        this.ejob_dq = ejob_dq;
    }

    public String getEjob_privacyreq() {
        return ejob_privacyreq;
    }

    public void setEjob_privacyreq(String ejob_privacyreq) {
        this.ejob_privacyreq = ejob_privacyreq;
    }

    public String getEjob_salarydiscuss() {
        return ejob_salarydiscuss;
    }

    public void setEjob_salarydiscuss(String ejob_salarydiscuss) {
        this.ejob_salarydiscuss = ejob_salarydiscuss;
    }

    public Float getEjob_salarylow() {
        return ejob_salarylow;
    }

    public void setEjob_salarylow(Float ejob_salarylow) {
        this.ejob_salarylow = ejob_salarylow;
    }

    public Float getEjob_salaryhigh() {
        return ejob_salaryhigh;
    }

    public void setEjob_salaryhigh(Float ejob_salaryhigh) {
        this.ejob_salaryhigh = ejob_salaryhigh;
    }

    public String getDetail_workyears() {
        return detail_workyears;
    }

    public void setDetail_workyears(String detail_workyears) {
        this.detail_workyears = detail_workyears;
    }

    public String getDetail_sex() {
        return detail_sex;
    }

    public void setDetail_sex(String detail_sex) {
        this.detail_sex = detail_sex;
    }

    public Integer getDetail_agelow() {
        return detail_agelow;
    }

    public void setDetail_agelow(Integer detail_agelow) {
        this.detail_agelow = detail_agelow;
    }

    public Integer getDetail_agehigh() {
        return detail_agehigh;
    }

    public void setDetail_agehigh(Integer detail_agehigh) {
        this.detail_agehigh = detail_agehigh;
    }

    public String getDetail_edulevel() {
        return detail_edulevel;
    }

    public void setDetail_edulevel(String detail_edulevel) {
        this.detail_edulevel = detail_edulevel;
    }

    public Integer getDetail_edulevel_tz() {
        return detail_edulevel_tz;
    }

    public void setDetail_edulevel_tz(Integer detail_edulevel_tz) {
        this.detail_edulevel_tz = detail_edulevel_tz;
    }

    public String getDetail_report2() {
        return detail_report2;
    }

    public void setDetail_report2(String detail_report2) {
        this.detail_report2 = detail_report2;
    }

    public Integer getDetail_subordinate() {
        return detail_subordinate;
    }

    public void setDetail_subordinate(Integer detail_subordinate) {
        this.detail_subordinate = detail_subordinate;
    }

    public String getDetail_duty() {
        return detail_duty;
    }

    public void setDetail_duty(String detail_duty) {
        this.detail_duty = detail_duty;
    }

    public String getDetail_require() {
        return detail_require;
    }

    public void setDetail_require(String detail_require) {
        this.detail_require = detail_require;
    }

    public String getDetail_dept() {
        return detail_dept;
    }

    public void setDetail_dept(String detail_dept) {
        this.detail_dept = detail_dept;
    }

    public String getDetail_tags() {
        return detail_tags;
    }

    public void setDetail_tags(String detail_tags) {
        this.detail_tags = detail_tags;
    }

    public Integer getDetail_language_english() {
        return detail_language_english;
    }

    public void setDetail_language_english(Integer detail_language_english) {
        this.detail_language_english = detail_language_english;
    }

    public Integer getDetail_language_japanese() {
        return detail_language_japanese;
    }

    public void setDetail_language_japanese(Integer detail_language_japanese) {
        this.detail_language_japanese = detail_language_japanese;
    }

    public Integer getDetail_language_french() {
        return detail_language_french;
    }

    public void setDetail_language_french(Integer detail_language_french) {
        this.detail_language_french = detail_language_french;
    }

    public Integer getDetail_language_putong() {
        return detail_language_putong;
    }

    public void setDetail_language_putong(Integer detail_language_putong) {
        this.detail_language_putong = detail_language_putong;
    }

    public Integer getDetail_language_yueyu() {
        return detail_language_yueyu;
    }

    public void setDetail_language_yueyu(Integer detail_language_yueyu) {
        this.detail_language_yueyu = detail_language_yueyu;
    }

    public Integer getDetail_language_other() {
        return detail_language_other;
    }

    public void setDetail_language_other(Integer detail_language_other) {
        this.detail_language_other = detail_language_other;
    }

    public String getDetail_language_content() {
        return detail_language_content;
    }

    public void setDetail_language_content(String detail_language_content) {
        this.detail_language_content = detail_language_content;
    }

    public String getDetail_special() {
        return detail_special;
    }

    public void setDetail_special(String detail_special) {
        this.detail_special = detail_special;
    }

    public String getEmail_list_array() {
        return email_list_array;
    }

    public void setEmail_list_array(String email_list_array) {
        this.email_list_array = email_list_array;
    }

    public String getEjob_level() {
        return ejob_level;
    }

    public void setEjob_level(String ejob_level) {
        this.ejob_level = ejob_level;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "LiePinPositionVO{" +
                "ejob_extRefid='" + ejob_extRefid + '\'' +
                ", ejob_extfield='" + ejob_extfield + '\'' +
                ", ejob_title='" + ejob_title + '\'' +
                ", ejob_jobtitle='" + ejob_jobtitle + '\'' +
                ", ejob_dq='" + ejob_dq + '\'' +
                ", ejob_privacyreq='" + ejob_privacyreq + '\'' +
                ", ejob_salarydiscuss='" + ejob_salarydiscuss + '\'' +
                ", ejob_salarylow=" + ejob_salarylow +
                ", ejob_salaryhigh=" + ejob_salaryhigh +
                ", detail_workyears='" + detail_workyears + '\'' +
                ", detail_sex='" + detail_sex + '\'' +
                ", detail_agelow=" + detail_agelow +
                ", detail_agehigh=" + detail_agehigh +
                ", detail_edulevel='" + detail_edulevel + '\'' +
                ", detail_edulevel_tz=" + detail_edulevel_tz +
                ", detail_report2='" + detail_report2 + '\'' +
                ", detail_subordinate=" + detail_subordinate +
                ", detail_duty='" + detail_duty + '\'' +
                ", detail_require='" + detail_require + '\'' +
                ", detail_dept='" + detail_dept + '\'' +
                ", detail_tags='" + detail_tags + '\'' +
                ", detail_language_english=" + detail_language_english +
                ", detail_language_japanese=" + detail_language_japanese +
                ", detail_language_french=" + detail_language_french +
                ", detail_language_putong=" + detail_language_putong +
                ", detail_language_yueyu=" + detail_language_yueyu +
                ", detail_language_other=" + detail_language_other +
                ", detail_language_content='" + detail_language_content + '\'' +
                ", detail_special='" + detail_special + '\'' +
                ", email_list_array='" + email_list_array + '\'' +
                ", ejob_level='" + ejob_level + '\'' +
                ", publisher=" + publisher +
                ", positionId=" + positionId +
                ", count=" + count +
                ", ejob_id=" + ejob_id +
                ", companyId=" + companyId +
                '}';
    }
}
