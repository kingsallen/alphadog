package com.moseeker.position.service.position;

import java.text.DecimalFormat;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThridPartyPosition;
import com.moseeker.thrift.gen.dao.service.DictDao;
import com.moseeker.thrift.gen.dict.struct.CityMap;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;

/**
 * 职位转换
 * @author wjf
 *
 */
public class PositionChangeUtil {
	
	private static DictDao.Iface dictDao = ServiceManager.SERVICEMANAGER.getService(DictDao.Iface.class);
	
	/**
	 * 将仟寻职位转成第卅方职位
	 * @param form
	 * @param positionDB
	 * @return
	 */
	public static ThirdPartyPositionForSynchronization changeToThirdPartyPosition(ThridPartyPosition form, Position positionDB) {
		LoggerFactory.getLogger(PositionChangeUtil.class).info("---------------------");
		ThirdPartyPositionForSynchronization position = new ThirdPartyPositionForSynchronization();
		position.setChannel(form.getChannel());
		position.setTitle(positionDB.getTitle());
		
		ChannelType channelType = ChannelType.instaceFromInteger(form.getChannel());
		switch(channelType) {
		case JOB51 : 
			DecimalFormat df = new DecimalFormat("0000");
			position.setCategory_main_code(df.format(form.getOccupation_level1()));
			position.setCategory_sub_code(df.format(form.getOccupation_level2()));
			break;
		case ZHILIAN : 
			DecimalFormat df1 = new DecimalFormat("000");
			position.setCategory_main_code(df1.format(form.getOccupation_level1()));
			position.setCategory_sub_code(df1.format(form.getOccupation_level2()));
			break;
			default :
				position.setCategory_main_code(form.getOccupation_level1());
				position.setCategory_sub_code(form.getOccupation_level2());
		}
		
		position.setCategory_main_code(form.getOccupation_level1());
		position.setQuantity(String.valueOf(form.getCount()));
		
		setDegree(positionDB.getDegree(), form.getChannel(), position);
		Integer experience = null;
		try {
			if(StringUtils.isNotNullOrEmpty(positionDB.getExperience())) {
				experience = Integer.valueOf(positionDB.getExperience().trim());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LoggerFactory.getLogger(PositionChangeUtil.class).error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		setExperience(experience, form.getChannel(), position);
		
		position.setSalary_high(String.valueOf(form.getSalary_top()*1000));
		position.setSalary_low(String.valueOf(form.getSalary_bottom()*1000));
		position.setDescription(positionDB.getAccountabilities());
		position.setPosition_id(positionDB.getId());
		position.setWork_place(form.getAddress());
		positionDB.getEmployment_type();
		setEmployeeType(positionDB.getEmployment_type(), form.getChannel(),  position);
		if(positionDB.getStop_date() != null) {
			position.setStop_date(positionDB.getStop_date());
		} else {
			DateTime dt = new DateTime();
			DateTime dayAfter60 = dt.plusDays(60);
			position.setStop_date(dayAfter60.toString("yyyy-MM-dd"));
		}
		//转职位
		if(positionDB.getCities() != null && positionDB.getCities().size() > 0) {
			try {
				String otherCode = changeCity(positionDB.getCities().get(0), form.getChannel());
				position.setPub_place_code(otherCode);
			} catch (Exception e) {
				position.setPub_place_code("");
				e.printStackTrace();
				LoggerFactory.getLogger(PositionChangeUtil.class).error(e.getMessage(), e);
			}
		} else {
			position.setPub_place_code("");
		}
		return position;
	}
	
	private static void setEmployeeType(byte employment_type, int channel, ThirdPartyPositionForSynchronization position) {
		WorkType workType = WorkType.instanceFromInt(employment_type);
		ChannelType channelType = ChannelType.instaceFromInteger(channel);
		switch(channelType) {
		case JOB51: position.setType_code(String.valueOf(WordTypeChangeUtil.getJob51EmployeeType(workType).getValue())); break;
		case ZHILIAN: position.setType_code(String.valueOf(WordTypeChangeUtil.getZhilianEmployeeType(workType).getValue()));break;
		default : position.setType_code("");
		}
	}

	/**
	 * 转学位
	 * @param degreeInt
	 * @param channel
	 * @param position
	 */
	private static void setDegree(int degreeInt, int channel, ThirdPartyPositionForSynchronization position) {
		Degree degree = Degree.instanceFromCode(String.valueOf(degreeInt));
		ChannelType channelType = ChannelType.instaceFromInteger(channel);
		switch(channelType) {
		case JOB51: position.setDegree_code(DegreeChangeUtil.getJob51Degree(degree).getValue());break;
		case ZHILIAN : position.setDegree_code(DegreeChangeUtil.getZhilianDegree(degree).getValue());break;
		default:position.setDegree_code("");
		}
	}
	
	/**
	 * 转工作经验
	 * @param experience
	 * @param channel
	 * @param position
	 */
	private static void setExperience(Integer experience, int channel, ThirdPartyPositionForSynchronization position) {
		ChannelType channelType = ChannelType.instaceFromInteger(channel);
		switch(channelType) {
		case JOB51: position.setExperience_code(ExperienceChangeUtil.getJob51Experience(experience).getValue());break;
		case ZHILIAN : position.setExperience_code(ExperienceChangeUtil.getZhilianExperience(experience).getValue());break;
		default:position.setExperience("");
		}
	}
	
	private static String changeCity(int cityCode, int channel) {
		String cityCodeStr = "";
		DecimalFormat df= null;
		ChannelType channelType = ChannelType.instaceFromInteger(channel);
		switch(channelType) {
		case JOB51 : df = new DecimalFormat("000000");break;
		case ZHILIAN : df = new DecimalFormat("000");break;
		default :
		}
		
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("code", String.valueOf(cityCode));
		qu.addEqualFilter("channel", String.valueOf(channel));
		try {
			CityMap cityMap = dictDao.getDictMap(qu);
			if(cityMap != null) {
				int cityCodeOther = cityMap.getCode_other();
				cityCodeStr = df.format(cityCodeOther);
				LoggerFactory.getLogger(PositionChangeUtil.class).info("otherCodeStr:"+cityCodeStr);
			}
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//do nothing
		}
		return cityCodeStr;
	}
}
