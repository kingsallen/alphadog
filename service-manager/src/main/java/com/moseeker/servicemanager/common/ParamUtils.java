package com.moseeker.servicemanager.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.JsonToMap;

/**
 * 
 * 主要用于生成form表单 
 * <p>Company: MoSeeker</P>  
 * <p>date: Aug 22, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class ParamUtils {
	
	/**
	 * 通用的参数解析方法。将request参数信息解析出来，如果属性名字和参数名称一致，则设法将参数的值赋值给类对象的值。
	 * @param request HttpServletRequest
	 * @param clazz 转换类类型
	 * @return 转换类
	 * @throws Exception 异常信息
	 */
	public static <T> T initModelForm(HttpServletRequest request, Class<T> clazz)
			throws Exception {
		Map<String, Object> data = parseRequestParam(request);
		T t = initModelForm(data, clazz);
		return t;
	}
	
	/**
	 * 用于解析常用的查询类。和initModelForm不同的是，会将form表单不存在的属性存入到EqualFilter中
	 * @param request HttpServletRequest 
	 * @param clazz 转换类的类型属性
	 * @return 转换后的类
	 * @throws Exception 异常
	 */
	public static <T> T initCommonQuery(HttpServletRequest request,
			Class<T> clazz) throws Exception {
		Map<String, Object> data = parseRequestParam(request);
		T t = initModelForm(data, clazz);
		buildEqualParam(data, t);
		return t;
	}
	
	/**
	 * 将request请求中的参数，不管是request的body中的参数还是以getParameter方式获取的参数存入到HashMap并染回该HashMap
	 * @param request request请求
	 * @return 存储通过request请求传递过来的参数
	 * @throws Exception 
	 */
	public static Map<String, Object> parseRequestParam(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameter(request));
		
		if (data.get("appid") == null){
			throw new Exception("请设置 appid!");
		}		
		return data;
	}
	
	/**
	 * 通用参数解析工具。将request parameter中的参数放入到对象中。
	 * 
	 * @param request
	 *            request请求
	 * @param t
	 *            参数对象
	 * @return t 参数对象
	 * @throws Exception
	 */
	private static <T> T buildEqualParam(Map<String, Object> data, T t)
			throws Exception {
		if (t != null) {
			try {
				Map<String, Object> param = new HashMap<>();
				
				if (data != null) {
					for (Entry<String, Object> entry : data.entrySet()) {
						if (!entry.getKey().equals("appid")
								&& !entry.getKey().equals("page")
								&& !entry.getKey().equals("per_page")
								&& !entry.getKey().equals("sortby")
								&& !entry.getKey().equals("order")
								&& !entry.getKey().equals("fields")
								&& !entry.getKey().equals("nocache")) {
							param.put(entry.getKey(), entry.getValue());
						}
					}
				}
				
				Method method = t.getClass().getMethod("setEqualFilter",
						Map.class);
				method.invoke(t, param);
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// warning 报警 / logging 日志
				e.printStackTrace();
			} finally {
				//do nothing
			}
		}
		return t;
	}
	
	/**
	 * 将request请求中的参数，不管是request的body中的参数还是以getParameter方式获取的参数存入到HashMap并染回该HashMap
	 * @param request request请求
	 * @return 存储通过request请求传递过来的参数
	 * @throws Exception 
	 */
	public static Map<String, Object> mergeRequestParameterList(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameterList(request));
		
		if (data.get("appid") == null){
			throw new Exception("请设置 appid!");
		}		
		return data;
	}

	public static <T> T initModelFormForList(HttpServletRequest request, Class<T> clazz)
			throws Exception {
		
		Map<String, Object> data = mergeRequestParameterList(request);
		
		T t = initModelForm(data, clazz);

		return t;
	}
	
	public static <T> T initModelForm(Map<String, Object> data, Class<T> clazz) throws Exception {
		T t = null;
		if(data != null && data.size() > 0) {
			t = clazz.newInstance();
			if (data != null && data.size() > 0) {
				Field[] fields = clazz.getDeclaredFields();
				for (Entry<String, Object> entry : data.entrySet()) {
					for (int i = 0; i < fields.length; i++) {
						if (fields[i].getName().equals(entry.getKey())) {
							String methodName = "set"
									+ fields[i].getName().substring(0, 1)
											.toUpperCase()
									+ fields[i].getName().substring(1);
							Method method = clazz.getMethod(methodName,
									fields[i].getType());
							Object cval = BeanUtils.convertTo(
									entry.getValue(), fields[i].getType());
							try {
								method.invoke(t, cval);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return t;
	}

	private static Map<String, Object> initParamFromRequestParameter(
			HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();

		Map<String, String[]> reqParams = request.getParameterMap();
		if (reqParams != null) {
			for (Entry<String, String[]> entry : reqParams.entrySet()) {
				param.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		return param;
	}
	
	private static Map<String, Object> initParamFromRequestParameterList(
			HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();

		Map<String, String[]> reqParams = request.getParameterMap();
		if (reqParams != null) {
			for (Entry<String, String[]> entry : reqParams.entrySet()) {
				param.put(entry.getKey(), entry.getValue());
			}
		}
		return param;
	}

	/**
	 * 获取post中的信息
	 * 
	 * @return map 参数键值对
	 */
	private static Map<String, Object> initParamFromRequestBody(
			HttpServletRequest request) {

		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
		} catch (IOException | IllegalStateException e) {
		}
		Map<String, Object> map = JsonToMap.parseJSON2Map(jb.toString());
		return map;
	}
}
