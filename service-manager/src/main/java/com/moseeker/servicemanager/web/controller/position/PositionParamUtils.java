package com.moseeker.servicemanager.web.controller.position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;

import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThridPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThridPartyPositionForm;

public class PositionParamUtils extends ParamUtils {
	
	@SuppressWarnings("unchecked")
	public static ThridPartyPositionForm parseSyncParam(HttpServletRequest request) {
		ThridPartyPositionForm form = new ThridPartyPositionForm();
		try {
			HashMap<String, Object> data = parseRequestParam(request);
			form.setAppid((Integer)data.get("appid"));
			form.setPosition_id((Integer)data.get("position_id"));
			List<ThridPartyPosition> cs = new ArrayList<>();
			List<HashMap<String, Object>> channels = (List<HashMap<String, Object>>)data.get("channels");
			if(channels != null) {
				channels.forEach(channel -> {
					try {
						ThridPartyPosition c = ParamUtils.initModelForm(channel, ThridPartyPosition.class);
						cs.add(c);
					} catch (Exception e) {
						e.printStackTrace();
						LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
					} finally {
						//do nothing
					}
				});
			}
			form.setChannels(cs);
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
		}
		return form;
	}

	/**
	 * 解析职位刷新参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap<Integer, Integer>> parseRefreshParam(HttpServletRequest request) {
		
		List<HashMap<Integer, Integer>> paramList = new ArrayList<>();
		try {
			Params<String, Object> params = parseRequestParam(request);
			List<Map<String, Object>> positions = (List<Map<String, Object>>)params.get("positions");
			if(positions != null && positions.size() > 0) {
				positions.forEach(position -> {
					int positionId = (Integer)position.get("position_id");
					List<Integer> channels = (List<Integer>)position.get("channels");
					if(channels != null && channels.size() > 0) {
						channels.forEach(channel -> {
							HashMap<Integer, Integer> param = new HashMap<>();
							param.put(positionId, channel);
							paramList.add(param);
						});
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return paramList;
	}
}
