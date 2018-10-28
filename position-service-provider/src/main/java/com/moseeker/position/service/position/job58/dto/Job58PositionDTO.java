package com.moseeker.position.service.position.job58.dto;

/**
 * 58同城职位同步dto
 *
 * @author cjm
 * @date 2018-10-26 15:05
 **/
public class Job58PositionDTO extends Base58PositionDTO {
    /**
     *  联系人
     */
    private String goblianxiren;
    /**
     *  薪资范围
     */
    private String minxinzi;
    /**
     *  福利保障信息
     */
    private String fulibaozhang;
    /**
     *  职位亮点
     */
    private String zhiweiliangdian;
    /**
     *  工作地址，ID来自workaddress接口
     */
    private String gongzuodizhi;
    /**
     * 发布来源，具体参见wiki GoblastEditClient
     */
    private String postsourceid;
    /**
     * 简历接收邮箱
     */
    private String jieshouyouxiang;
    /**
     * 默认传递0
     */
    private String type;
    /**
     * 最后帖子编辑来源，具体参见wiki GoblastEditClient
     */
    private String goblastEditClient;
    /**
     *  公司传递0
     */
    private String gongsi;
    /**
     * 行业ID
     */
    private String hangyeyaoqiu;
    /**
     * 公司名称
     */
    private String gongsimingcheng;
    /**
     * 工作年限
     */
    private String gongzuonianxian;
    /**
     * 招聘人数
     */
    private String zhaopinrenshu;
    /**
     * 学历id
     */
    private String xueliyaoqiu;
    /**
     * 经纬度
     */
    private String longitude;
    /**
     * 经纬度
     */
    private String latitude;
    /**
     * Google经纬度
     */
    private String glongitude;
    /**
     * Google经纬度
     */
    private String glatitude;
    /**
     * 全职列表页工作地址
     */
    private String joblocation;
    /**
     * 全职发帖来源
     */
    private String qzapisource;
    /**
     * 兼职发帖来源
     */
    private String jzapisource;

    public String getGoblianxiren() {
        return goblianxiren;
    }

    public void setGoblianxiren(String goblianxiren) {
        this.goblianxiren = goblianxiren;
    }

    public String getMinxinzi() {
        return minxinzi;
    }

    public void setMinxinzi(String minxinzi) {
        this.minxinzi = minxinzi;
    }

    public String getFulibaozhang() {
        return fulibaozhang;
    }

    public void setFulibaozhang(String fulibaozhang) {
        this.fulibaozhang = fulibaozhang;
    }

    public String getZhiweiliangdian() {
        return zhiweiliangdian;
    }

    public void setZhiweiliangdian(String zhiweiliangdian) {
        this.zhiweiliangdian = zhiweiliangdian;
    }

    public String getGongzuodizhi() {
        return gongzuodizhi;
    }

    public void setGongzuodizhi(String gongzuodizhi) {
        this.gongzuodizhi = gongzuodizhi;
    }

    public String getPostsourceid() {
        return postsourceid;
    }

    public void setPostsourceid(String postsourceid) {
        this.postsourceid = postsourceid;
    }

    public String getJieshouyouxiang() {
        return jieshouyouxiang;
    }

    public void setJieshouyouxiang(String jieshouyouxiang) {
        this.jieshouyouxiang = jieshouyouxiang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoblastEditClient() {
        return goblastEditClient;
    }

    public void setGoblastEditClient(String goblastEditClient) {
        this.goblastEditClient = goblastEditClient;
    }

    public String getGongsi() {
        return gongsi;
    }

    public void setGongsi(String gongsi) {
        this.gongsi = gongsi;
    }

    public String getHangyeyaoqiu() {
        return hangyeyaoqiu;
    }

    public void setHangyeyaoqiu(String hangyeyaoqiu) {
        this.hangyeyaoqiu = hangyeyaoqiu;
    }

    public String getGongsimingcheng() {
        return gongsimingcheng;
    }

    public void setGongsimingcheng(String gongsimingcheng) {
        this.gongsimingcheng = gongsimingcheng;
    }

    public String getGongzuonianxian() {
        return gongzuonianxian;
    }

    public void setGongzuonianxian(String gongzuonianxian) {
        this.gongzuonianxian = gongzuonianxian;
    }

    public String getZhaopinrenshu() {
        return zhaopinrenshu;
    }

    public void setZhaopinrenshu(String zhaopinrenshu) {
        this.zhaopinrenshu = zhaopinrenshu;
    }

    public String getXueliyaoqiu() {
        return xueliyaoqiu;
    }

    public void setXueliyaoqiu(String xueliyaoqiu) {
        this.xueliyaoqiu = xueliyaoqiu;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getGlongitude() {
        return glongitude;
    }

    public void setGlongitude(String glongitude) {
        this.glongitude = glongitude;
    }

    public String getGlatitude() {
        return glatitude;
    }

    public void setGlatitude(String glatitude) {
        this.glatitude = glatitude;
    }

    public String getJoblocation() {
        return joblocation;
    }

    public void setJoblocation(String joblocation) {
        this.joblocation = joblocation;
    }

    public String getQzapisource() {
        return qzapisource;
    }

    public void setQzapisource(String qzapisource) {
        this.qzapisource = qzapisource;
    }

    public String getJzapisource() {
        return jzapisource;
    }

    public void setJzapisource(String jzapisource) {
        this.jzapisource = jzapisource;
    }

    @Override
    public String toString() {
        return "Job58PositionDTO{" +
                "goblianxiren='" + goblianxiren + '\'' +
                ", minxinzi='" + minxinzi + '\'' +
                ", fulibaozhang='" + fulibaozhang + '\'' +
                ", zhiweiliangdian='" + zhiweiliangdian + '\'' +
                ", gongzuodizhi='" + gongzuodizhi + '\'' +
                ", postsourceid='" + postsourceid + '\'' +
                ", jieshouyouxiang='" + jieshouyouxiang + '\'' +
                ", type='" + type + '\'' +
                ", goblastEditClient='" + goblastEditClient + '\'' +
                ", gongsi='" + gongsi + '\'' +
                ", hangyeyaoqiu='" + hangyeyaoqiu + '\'' +
                ", gongsimingcheng='" + gongsimingcheng + '\'' +
                ", gongzuonianxian='" + gongzuonianxian + '\'' +
                ", zhaopinrenshu='" + zhaopinrenshu + '\'' +
                ", xueliyaoqiu='" + xueliyaoqiu + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", glongitude='" + glongitude + '\'' +
                ", glatitude='" + glatitude + '\'' +
                ", joblocation='" + joblocation + '\'' +
                ", qzapisource='" + qzapisource + '\'' +
                ", jzapisource='" + jzapisource + '\'' +
                '}';
    }
}
