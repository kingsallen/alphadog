package com.moseeker.position.service.position;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;

/**
 * 
 * 用于同步职位给支付宝校园招聘
 * @author wyf
 *
 */
public class PositionForAlipaycampusPojo {

	private String source_id;           // jobposition.id
	private String job_name;           // 职位标题
	private String job_desc;              //职位描述
	private String job_tier_one_code;     // 职位一级分类code opj_XXX
	private String job_tier_two_code;     //职位二级分类code opj_XXX
	private String job_tier_three_code;   //职位三级分类code opj_XXX
	private String job_tier_one_name;     //职位一级分类name IT互联网
	private String job_tier_two_name;   //职位二级分类name 软件
	private String job_tier_threee_name;  //职位三级分类name java
	private String job_perk;           // 职业亮点、关键字
	private int job_hire_number;      // 招聘人数
	private int job_type;              // 招聘类型 1实习 2 兼职 3全职
	private int job_rq_education;      // 学历要求 1 大专以下 2 大专 3 本科 4 硕士 5 博士 6 其他 7 不限
	private int job_resume_lg;           // 要求简历语言 1中文 2 英文
	private int payment_min;           // 最小薪资
	private int payment_max;                // 最大薪资
	private int payment_unit;              // 薪资单位 1天 2月 3周
	private String company_source;          // company.id
	private String company_name;             // 公司名称
	private String company_lawname;      // 公司法律名称
	private String company_logo;      // 公司logo
	private int area_province_code;         // 省份编码（直辖市）， 110100
	private String area_city_code;         // 城市编码   110100
	private String area_district_code;      // 区编码  110105
	private String area_city_name;          // 城市名称 北京市
	private String area_province_name;      // 省份名称（直辖市） 北京市
	private String area_district_name;      // 区名称  朝阳区
	private String area_street_name;        // 街道名称
	private String area_job_address;        // 工作详细地址
	private int tra_job_freq;              // 每周到岗天数
	private int tra_job_period;           // 实习时间长度（单位月）
	private int tra_job_promot;       // 是否可以转正
	private String gmt_expired;  // 过期时间（毫秒数）  1494926993617
	private String gmt_refresh;    // 刷新时间 （毫秒数）  1494926993617

	public String getSource_id() {
		return source_id;
	}

	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}

	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public String getJob_desc() {
		return job_desc;
	}

	public void setJob_desc(String job_desc) {
		this.job_desc = job_desc;
	}

	public String getJob_tier_one_code() {
		return job_tier_one_code;
	}

	public void setJob_tier_one_code(String job_tier_one_code) {
		this.job_tier_one_code = job_tier_one_code;
	}

	public String getJob_tier_two_code() {
		return job_tier_two_code;
	}

	public void setJob_tier_two_code(String job_tier_two_code) {
		this.job_tier_two_code = job_tier_two_code;
	}

	public String getJob_tier_three_code() {
		return job_tier_three_code;
	}

	public void setJob_tier_three_code(String job_tier_three_code) {
		this.job_tier_three_code = job_tier_three_code;
	}

	public String getJob_tier_one_name() {
		return job_tier_one_name;
	}

	public void setJob_tier_one_name(String job_tier_one_name) {
		this.job_tier_one_name = job_tier_one_name;
	}

	public String getJob_tier_two_name() {
		return job_tier_two_name;
	}

	public void setJob_tier_two_name(String job_tier_two_name) {
		this.job_tier_two_name = job_tier_two_name;
	}

	public String getJob_tier_threee_name() {
		return job_tier_threee_name;
	}

	public void setJob_tier_threee_name(String job_tier_threee_name) {
		this.job_tier_threee_name = job_tier_threee_name;
	}

	public String getJob_perk() {
		return job_perk;
	}

	public void setJob_perk(String job_perk) {
		this.job_perk = job_perk;
	}

	public int getJob_hire_number() {
		return job_hire_number;
	}

	public void setJob_hire_number(int job_hire_number) {
		this.job_hire_number = job_hire_number;
	}

	public int getJob_type() {
		return job_type;
	}

	public void setJob_type(int job_type) {
		this.job_type = job_type;
	}

	public int getJob_rq_education() {
		return job_rq_education;
	}

	public void setJob_rq_education(int job_rq_education) {
		this.job_rq_education = job_rq_education;
	}

	public int getJob_resume_lg() {
		return job_resume_lg;
	}

	public void setJob_resume_lg(int job_resume_lg) {
		this.job_resume_lg = job_resume_lg;
	}

	public int getPayment_min() {
		return payment_min;
	}

	public void setPayment_min(int payment_min) {
		this.payment_min = payment_min;
	}

	public int getPayment_max() {
		return payment_max;
	}

	public void setPayment_max(int payment_max) {
		this.payment_max = payment_max;
	}

	public int getPayment_unit() {
		return payment_unit;
	}

	public void setPayment_unit(int payment_unit) {
		this.payment_unit = payment_unit;
	}

	public String getCompany_source() {
		return company_source;
	}

	public void setCompany_source(String company_source) {
		this.company_source = company_source;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_lawname() {
		return company_lawname;
	}

	public void setCompany_lawname(String company_lawname) {
		this.company_lawname = company_lawname;
	}

	public String getCompany_logo() {
		return company_logo;
	}

	public void setCompany_logo(String company_logo) {
		this.company_logo = company_logo;
	}

	public int getArea_province_code() {
		return area_province_code;
	}

	public void setArea_province_code(int area_province_code) {
		this.area_province_code = area_province_code;
	}

	public String getArea_city_code() {
		return area_city_code;
	}

	public void setArea_city_code(String area_city_code) {
		this.area_city_code = area_city_code;
	}

	public String getArea_district_code() {
		return area_district_code;
	}

	public void setArea_district_code(String area_district_code) {
		this.area_district_code = area_district_code;
	}

	public String getArea_city_name() {
		return area_city_name;
	}

	public void setArea_city_name(String area_city_name) {
		this.area_city_name = area_city_name;
	}

	public String getArea_province_name() {
		return area_province_name;
	}

	public void setArea_province_name(String area_province_name) {
		this.area_province_name = area_province_name;
	}

	public String getArea_district_name() {
		return area_district_name;
	}

	public void setArea_district_name(String area_district_name) {
		this.area_district_name = area_district_name;
	}

	public String getArea_street_name() {
		return area_street_name;
	}

	public void setArea_street_name(String area_street_name) {
		this.area_street_name = area_street_name;
	}

	public String getArea_job_address() {
		return area_job_address;
	}

	public void setArea_job_address(String area_job_address) {
		this.area_job_address = area_job_address;
	}

	public int getTra_job_freq() {
		return tra_job_freq;
	}

	public void setTra_job_freq(int tra_job_freq) {
		this.tra_job_freq = tra_job_freq;
	}

	public int getTra_job_period() {
		return tra_job_period;
	}

	public void setTra_job_period(int tra_job_period) {
		this.tra_job_period = tra_job_period;
	}

	public int getTra_job_promot() {
		return tra_job_promot;
	}

	public void setTra_job_promot(int tra_job_promot) {
		this.tra_job_promot = tra_job_promot;
	}

	public String getGmt_expired() {
		return gmt_expired;
	}

	public void setGmt_expired(String gmt_expired) {
		this.gmt_expired = gmt_expired;
	}

	public String getGmt_refresh() {
		return gmt_refresh;
	}

	public void setGmt_refresh(String gmt_refresh) {
		this.gmt_refresh = gmt_refresh;
	}

	@Override
	public String toString() {
		// "{'source_code':'101','source_id':'123123'}"
		HashMap hashMap = new HashMap();
		hashMap.put("source_id",source_id);
		hashMap.put("job_name",job_name);
		hashMap.put("job_desc",job_desc);
		hashMap.put("job_tier_one_code",job_tier_one_code);
		if (job_tier_two_code != null){
			hashMap.put("job_tier_two_code",job_tier_two_code);
		}

		hashMap.put("job_tier_one_name",job_tier_one_name);
		if (job_tier_two_name != null){
			hashMap.put("job_tier_two_name",job_tier_two_name);
		}

		if (job_perk != null) {
			hashMap.put("job_perk",job_perk);
		}
		if ( job_hire_number > 0) {
			hashMap.put("job_hire_number",job_hire_number);
		}

		hashMap.put("job_type",job_type);
		hashMap.put("job_rq_education",job_rq_education);

		hashMap.put("payment_min",payment_min);

		if (payment_max != 0 ) {
			hashMap.put("payment_max",payment_max);
		}

		hashMap.put("payment_unit",payment_unit);

		hashMap.put("company_source",company_source);
		hashMap.put("company_name",company_name);
		hashMap.put("company_lawname",company_lawname);

		if (company_logo != null){
			hashMap.put("company_logo",company_logo);
		}

		hashMap.put("area_province_code",area_province_code);
		hashMap.put("area_province_name",area_province_name);
		hashMap.put("area_city_code",area_city_code);
		hashMap.put("area_city_name",area_city_name);


		hashMap.put("gmt_expired",gmt_expired);
		hashMap.put("gmt_refresh",gmt_refresh);


		String s =  JSON.toJSONString(hashMap, SerializerFeature.UseSingleQuotes);
		System.out.print(s);
		return s;

//		return "{" +
//				"id=" + id +
//				", code=" + code +
//				", name='" + name + '\'' +
//				", priority=" + priority +
//				", parent_code=" + parent_code +
//				", create_time=" + create_time +
//				", update_time=" + update_time +
//				'}';
	}
}
