package com.moseeker.position.service.position;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThridPartyPosition;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;

/**
 * 职位转换
 * @author wjf
 *
 */
public class PositionChangeUtil {

	/**
	 * 将仟寻职位转成第卅方职位
	 * @param form
	 * @param positionDB
	 * @return
	 */
	public static ThirdPartyPositionForSynchronization changeToThirdPartyPosition(ThridPartyPosition form, Position positionDB) {
		ThirdPartyPositionForSynchronization position = new ThirdPartyPositionForSynchronization();
		position.setChannel(form.getChannel());
		position.setTitle(positionDB.getTitle());
		position.setCategory_main_code(form.getOccupation_level1());
		position.setCategory_sub_code(form.getOccupation_level2());
		position.setQuantity(String.valueOf(form.getCount()));
		setDegree(positionDB.getDegree(), form.getChannel(), position);
		Integer experience = null;
		try {
			experience = Integer.valueOf(positionDB.getExperience());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LoggerFactory.getLogger(PositionChangeUtil.class).error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		setExperience(experience, form.getChannel(), position);
		position.setSalary_high(String.valueOf(form.getSalary_top()));
		position.setSalary_low(String.valueOf(form.getSalary_bottom()));
		position.setDescription(positionDB.getAccountabilities());
		position.setPosition_id(positionDB.getId());
		position.setWork_place(form.getAddress());
		if(positionDB.getStop_date() != null) {
			position.setStop_date(positionDB.getStop_date());
		} else {
			DateTime dt = new DateTime();
			DateTime dayAfter60 = dt.plusDays(60);
			position.setStop_date(dayAfter60.toString("yyyy-MM-dd"));
		}
		return position;
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
		case JOB51: position.setDegree(DegreeChangeUtil.getJob51Degree(degree).getValue());break;
		case ZHILIAN : position.setDegree(DegreeChangeUtil.getZhilianDegree(degree).getValue());break;
		default:position.setDegree("");
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
}
