package com.moseeker.useraccounts.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictIndustryDao;
import com.moseeker.baseorm.dao.userdb.UserPositionEmailDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserPositionEmailDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;

@Service
public class UserPositionEmailService {
	@Autowired
	private UserPositionEmailDao userPositionEmailDao;
	@Autowired
	private UserUserDao userUserDao;
	@Autowired
	private DictCityDao dictCityDao;
	@Autowired
	private DictIndustryDao dictIndustryDao;
	private SearchengineServices.Iface searchengineServices = ServiceManager.SERVICE_MANAGER.getService(SearchengineServices.Iface.class);
	private MqService.Iface mqService = ServiceManager.SERVICE_MANAGER.getService(MqService.Iface.class);
	static Logger logger = LoggerFactory.getLogger(UserPositionEmailService.class);
	//处理职位推荐邮件的的插入或者更新
	@CounterIface
	public int postUserPositionEmail(int userId,String conditions){
		logger.info("UserPositionEmailService postUserPositionEmail conditions======{}",conditions);
		return userPositionEmailDao.insertOrUpdateData(userId, conditions);
	}
	//发送邮箱验证邮件
	@CounterIface
	public int  sendEmailvalidation(String email,int userId,String conditions,String urls) throws TException{
		logger.info("email={0},userId={1},conditions={2},urls={3}",email,userId,conditions,urls);
		if(StringUtils.isEmpty(email)||userId==0){
			logger.info("***************************************");
			return 0;
		}
		UserUserDO user=getUserInfobyId(userId);
		logger.info("userId={0}",user);
		if(user==null){
			return 0;
		}
		Map<String,String > data=new HashMap<String,String>();
		String conditionWords=this.convertConditionForEmail(conditions);
		if(conditionWords==null){
			conditionWords="";
		}
		data.put("#auth_url#", urls);
		data.put("#search_condition#", conditionWords);
		ConfigPropertiesUtil propertiesUtil = ConfigPropertiesUtil.getInstance();
		String senderName = propertiesUtil.get("email.verify.sendName", String.class);
		String subject = "请验证邮箱完成推荐职位订阅";
		String senderDisplay = org.apache.commons.lang.StringUtils.defaultIfEmpty("", "");
		mqService.sendAuthEMail(data, Constant.EVENT_TYPE_RECOMMEND_VALID_EMAIL, email, subject, senderName, senderDisplay);
		return 1;
	}
	//发送职位推荐邮件
	@CounterIface
	public int sendEmailPosition(int userId) throws Exception{
		UserUserDO userDO=getUserInfobyId(userId);
		UserPositionEmailDO emailDO=getUserPositionByUserId(userId);
		logger.info("UserPositionEmailService email======={}",emailDO);
		if(userDO==null||emailDO==null){
			return 0;
		}
		String email=userDO.getEmail();
		String conditions=emailDO.getConditions();
		logger.info("UserPositionEmailService conditions======={}",conditions);
		int result=handleEmailRecommendPosition(email,conditions);
		return result;
	}
	//根据userid获取email的信息
	public UserUserDO getUserInfobyId(int userId){
		Query query=new Query.QueryBuilder().where("id",userId).buildQuery();
		UserUserDO user=userUserDao.getData(query);
		return user;
	}
	//获取邮件发送的信息
	public UserPositionEmailDO getUserPositionByUserId(int userId){
		Query query=new Query.QueryBuilder().where("user_id",userId).and("status",0).buildQuery();
		UserPositionEmailDO DO=userPositionEmailDao.getData(query);
		return DO;
	}

	//处理发送职位推荐邮件
	public int handleEmailRecommendPosition(String email,String conditions) throws Exception{
		Map<String,Object> condition=new HashMap<String,Object>();
		if(!StringUtils.isEmpty(conditions)){
			condition=(Map<String,Object>) JSON.parse(conditions);
		}
		Map<String,Object> emailData=getRecommendPosition(condition);
		if(emailData!=null&&!emailData.isEmpty()){
			sendPositionEmail(email,emailData, conditions);
			return 1;
		}
		return 0;
	}

	//根据条件获取职位
	public Map<String,Object> getRecommendPosition(Map<String,Object> map) throws Exception{
		String keyWord=(String) map.get("keyWord");
		String citys=(String) map.get("citys");
		String industry=(String) map.get("industry");
		String candidateSource=(String)map.get("candidateSource");
		if(StringUtils.isBlank(candidateSource)){
			candidateSource="-1";
		}
		int page=1;
		int pageSize=10;
		Map<String,Integer> salaryMap= (Map<String, Integer>) map.get("salaryCode");
		List<Map<String,Integer>> salaryCodeList=new ArrayList<>();
		if(salaryMap!=null&&!salaryMap.isEmpty()){
			salaryCodeList.add(salaryMap);
		}
		String salaryCode= JSONObject.toJSONString(salaryCodeList);
		Calendar lastDate = Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" );
		lastDate.roll(Calendar.DATE, -7);//日期回滚7天
		String startTime=format.format(lastDate.getTime());
		Response res=searchengineServices.positionQuery(keyWord, citys, industry, salaryCode, page, pageSize,
				null, null,0,0,0,1,0,candidateSource);
		if(res.getStatus()==0&&!Strings.isNullOrEmpty(res.getData())){
			String data=res.getData();
			Map<String,Object> result=JSON.parseObject(data);
			return result;
		}
		return null;
	}
	//发送职位推荐邮件
	public void sendPositionEmail(String email,Map<String,Object> data,String conditions) throws TException{
		int totalNum=(int) data.get("totalNum");
		Map<String,String> map=new HashMap<String,String>();
		if(totalNum>0){
			String conditionWords=this.convertConditionForEmail(conditions);
			String positiondata=this.handlePositionData(data);
			if(conditionWords==null){
				conditionWords="";
			}
			int positioNum=((List<Map<String,Object>>)data.get("positions")).size();
			map.put("#search_conditions#", conditionWords);
			map.put("#search_position#", positiondata);
			map.put("#search_num#", positioNum+"");
			ConfigPropertiesUtil propertiesUtil = ConfigPropertiesUtil.getInstance();
			String senderName = propertiesUtil.get("email.verify.sendName", String.class);
			String subject = "每周职位推荐";
			String senderDisplay = org.apache.commons.lang.StringUtils.defaultIfEmpty(propertiesUtil.get("email.verify.sendDisplay", String.class), "仟寻招聘");
			mqService.sendAuthEMail(map, Constant.EVENT_TYPE_RECOMMEND_POSITION_EMAIL, email, subject, senderName, senderDisplay);
		}

	}
	private String handlePositionData(Map<String,Object> data){
		int totalNum=(int) data.get("totalNum");
		if(totalNum>0){
			String showData="";
			List<Map<String,Object>> list=(List<Map<String, Object>>) data.get("positions");
			for(Map<String,Object> map:list){
				Map<String,Object> jdPic=(Map<String, Object>) map.get("jd_pic");
				Map<String,Object> company=(Map<String, Object>) map.get("company");
				Map<String,Object> position=(Map<String, Object>) map.get("position");
				String pic=(String) position.get("banner");
				if(jdPic!=null&&!jdPic.isEmpty()){
					Map<String,Object> positionPic=(Map<String, Object>) jdPic.get("position_pic");
					if(positionPic!=null&&!positionPic.isEmpty()){
						Map<String,Object>firstPic=(Map<String,Object>) positionPic.get("first_pic");
						if(firstPic!=null&&!firstPic.isEmpty()){
							pic=(String)firstPic.get("res_url");
						}
					}
				}
				if(StringUtils.isEmpty(pic)){
					pic="http://cdn.moseeker.com/profile/email_verifier_qx_email_logo.png";
				}else{
					pic="https://cdn.moseeker.com/"+pic;
				}
				String title=(String) position.get("title");
				String companyName=(String) company.get("abbreviation");
				String citys=(String) position.get("city");
				String salary="  面议  ";
				int positionId=(int)position.get("id");
				double salaryTop=0;
				if(position.get("salary_top")!=null){
					salaryTop=Double.parseDouble(position.get("salary_top").toString());
				}
				double salaryBottom=0;
				if(position.get("salary_bottom")!=null){
					salaryBottom=Double.parseDouble(position.get("salary_bottom").toString());
				}
				if(salaryTop!=0||salaryBottom!=0){
					salary="  "+salaryBottom+"k--"+salaryTop+"k"+"  ";
				}
				String experience=(String) position.get("experience");
				if(StringUtils.isEmpty(experience)){
					experience="  暂无  ";
				}else{
					if(!experience.contains("年")) {
						experience=" "+experience+"年";
					}
					int experienceAbove=(int) position.get("experience_above");
					if(experienceAbove>0){
						experience=experience+"及以上经验 ";
					}else{
						experience=experience+"经验 ";
					}

				}
				String singleData=this.getPositionHtmlData(pic, title, citys, companyName, salary, experience,positionId);
				showData+=singleData;
			}
			return showData;
		}

		return null;
	}

	private String getPositionHtmlData(String pic,String title,String citys,String companyName,String salary,String experience,int positionId){
		if(!StringUtils.isEmpty(pic)){
			pic=pic+"?imageMogr2/thumbnail/80x50";
		}
		logger.info("============================================================================");
		logger.info(pic);
		logger.info("===========================================================================");
		StringBuffer sb=new StringBuffer();
		sb.append("<tr><td align='center'><table width='520' height='90' cellpadding='0' cellspacing='0' border='0' class='wrapper' bgcolor='#ffffff'>");
		sb.append("<tr><td align='center'><table width='500' height='90' cellpadding='0' cellspacing='0' border='0' class='wrapper'>");
		sb.append("<tr><td height='20'style='-ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; font-size: 10px; line-height: 10px; mso-table-lspace: 0pt; mso-table-rspace: 0pt;'></td></tr>");
		sb.append("<tr valign='middle' >");
		sb.append("<td width='80' height='50' align='center'><img src='"+pic+"' style='margin:0; padding:0; display:block;-ms-interpolation-mode: bicubic; border: 0; line-height: 100%; outline: none; text-decoration: none;' border='0' alt='' /></td>");
		sb.append("<td height='10' width='20' style='font-size:70px; line-height:10px;' class='mobileOn'>&nbsp;</td>");
		sb.append("<td width='400' height='50'><table width='400' height='50' cellpadding='0' cellspacing='0' border='0' class='wrapper'><tr>");
		sb.append("<td class='mobile' style='-ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; mso-table-lspace: 0pt; mso-table-rspace: 0pt;'>");
		sb.append("<table cellpadding='0' cellspacing='0' border='0'><tr>");
		sb.append("<td valign='middle' align='left' style='-moz-hyphens: auto; -webkit-hyphens: auto; hyphens: auto; border: none; border-collapse: collapse !important; color: #66A4F9; font-family: Helvetica, Arial, sans-serif; font-size: 14px; font-weight: normal; line-height: 1; margin: 0; padding: 0; word-wrap: break-word;'>");
		sb.append("<a href='https://www.moseeker.com/job/:"+positionId+"?fr=edm' target='_blank' alias='' style='font-size:14px;font-family: Helvetica, Arial, sans-serif; color:#66A4F9; display: inline-block; text-decoration: none; margin: 0; padding: 0; font-weight:normal;width: 400px;'>"+title+"</a >");
		sb.append("</td></tr></table></td></tr><tr>");
		sb.append("<td class='mobile' style='-ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color: #4B525C; font-family: arial, sans-serif; font-size: 14px; line-height: 1; mso-table-lspace: 0pt; mso-table-rspace: 0pt;word-wrap: break-word;'>");
		sb.append("<span>"+companyName+"</span>");
		if(StringUtils.isNotEmpty(citys)){
			sb.append("<span>/</span><span>"+citys+"</span>");
		}
		if(StringUtils.isNotEmpty(salary)){
			sb.append("<span>/</span> <span>"+salary+"</span>");
		}
		if(StringUtils.isNotEmpty(experience)){
			sb.append("<span>/</span><span>"+experience+"</span>");
		}
		sb.append("</td></tr></table></td></tr><tr>");
		sb.append("<td height='20'style='-ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; font-size: 10px; line-height: 10px; mso-table-lspace: 0pt; mso-table-rspace: 0pt;'></td></tr></table>");
		sb.append("</td></tr></table></td></tr>");
		logger.info("============================================================================");
		logger.info(sb.toString());
		logger.info("===========================================================================");
		return sb.toString();
	}

	//获取搜索条件的字符串，用于发送email的元素
	private String convertConditionForEmail(String condition){
		if(StringUtils.isEmpty(condition)){
			return null;
		}
		Map<String,Object> data=(Map<String, Object>) JSON.parse(condition);
		String cityCode=(String) data.get("citys");
		String industry=(String) data.get("industry");
		Map<String,Integer> salaryCode=(Map<String, Integer>) data.get("salaryCode");
		String keyWord=(String) data.get("keyWord");
		String conditionWords=this.getSearchCodetion(cityCode, industry, salaryCode, keyWord);
		return conditionWords;
	}

	private String getSearchCodetion(String cityCode,String industry,Map<String,Integer> salaryCode,String keyWord){
		String conditions="";
		if(StringUtils.isNotEmpty(cityCode)){
			String cities=getCitys( cityCode);
			if(StringUtils.isNotEmpty(cities)){
				conditions+="城市："+cities+";";
			}
		}
		if(StringUtils.isNotEmpty(industry)){
			String industries=getIndustries(industry);
			if(StringUtils.isNotEmpty(industries)){
				conditions+="行业："+industries+";";
			}
		}
		if(salaryCode!=null&&!salaryCode.isEmpty()){
			String salary=getSalary(salaryCode);
			conditions+=salary;
		}
		if(StringUtils.isNotEmpty(keyWord)){
			conditions+="关键字："+keyWord;
		}

		return conditions;
	}
	//获取薪资范围字符串
	private String getSalary(Map<String,Integer> salaryCode){
		String salary="";
		Integer salaryBottom=salaryCode.get("salaryBottom");
		Integer salaryTop=salaryCode.get("salaryTop");
		if(salaryBottom!=null&&salaryTop!=null){
			if(salaryBottom==0&&salaryTop==0){
				salary+="薪资：面议;";
			}else{
				salary+="薪资："+salaryBottom+"k--"+salaryTop+"k;";
			}
		}else{
			if(salaryBottom==null){
				salaryBottom=0;
			}
			if(salaryTop==null){
				salaryTop=0;
			}
			salary+="薪资："+salaryBottom+"k--"+salaryTop+"k;";
		}
		return salary;
	}
	//获取city的字符串
	private String getCitys(String cityCode){
		List<Integer> data=stringConvertToList(cityCode);
		if(data==null||data.size()==0){
			return null;
		}
		List<DictCityDO>cityList=getSearchCity(data);
		if(cityList==null||cityList.size()==0){
			return null;
		}
		String cities=cityListToString(cityList);
		return cities;
	}
	// 获取行业的字符串
	private String getIndustries(String industry){
		List<Integer> data=stringConvertToList(industry);
		if(data==null||data.size()==0){
			return null;
		}
		List<DictIndustryDO> industryList=getSearchIndustry(data);
		if(industryList==null||industryList.size()==0){
			return null;
		}
		String industries=industryListConvertString(industryList);
		return industries;
	}
	//将字符串转换为数字list
	private List<Integer> stringConvertToList(String data){
		if(StringUtils.isEmpty(data)){
			return null;
		}
		List<Integer> list=new ArrayList<Integer>();
		String[] array=data.split(",");
		for(String arr:array){
			list.add(Integer.parseInt(arr));
		}

		return list;
	}

	//获取城市信息
	public List<DictCityDO> getSearchCity(List<Integer> data){
		if(data==null||data.size()==0){
			return null;
		}
		Query query=new Query.QueryBuilder().where(new Condition("code",data.toArray(),ValueOp.IN)).buildQuery();
		List<DictCityDO> list=dictCityDao.getDatas(query);
		return list;
	}
	//获取行业信息
	public List<DictIndustryDO> getSearchIndustry(List<Integer> data){
		if(data==null||data.size()==0){
			return null;
		}
		Query query=new Query.QueryBuilder().where(new Condition("code",data.toArray(),ValueOp.IN)).buildQuery();
		List<DictIndustryDO> list=dictIndustryDao.getDatas(query);
		return list;
	}
	//将DictCityDO转化为city的字符串
	public String cityListToString( List<DictCityDO> list){
		if(list==null||list.size()==0){
			return null;
		}
		String cities="";
		for(DictCityDO DO:list){
			cities+=DO.getName()+",";
		}
		cities=cities.substring(0, cities.lastIndexOf(","));
		return cities;
	}
	//将DictIndustryDO转化为自符串
	public String  industryListConvertString(List<DictIndustryDO> list){
		if(list==null||list.size()==0){
			return null;
		}
		String industry="";
		for(DictIndustryDO DO:list){
			industry+=DO.getName()+",";
		}
		industry=industry.substring(0, industry.lastIndexOf(","));
		return industry;
	}
}
