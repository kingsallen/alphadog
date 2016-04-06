package com.moseeker.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件读取帮助类。利用getResourceAsStream方法读取默认的配置文件，
 * 将读取的结果交给java.util.Properties解析并存储。
 * <p>
 * <p>Company: MoSeeker</P>
 * <p>date: Mar 27, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @author wjf
 * @version Beta
 */
public class ConfigPropertiesUtil {

    private static Properties properties;            //储配置文件内容存
    private static ConfigPropertiesUtil self;

    /**
     * 读取配置信息帮助类 默认读取serviceprovider.properties配置文件
     *
     * @throws Exception
     */
    private ConfigPropertiesUtil() {
        properties = new Properties();
        InputStreamReader inputStreamReader = null;
        try {
            //需要load common项目本地配置文件
            //load service provider的配置文件
            File directory = new File("");//参数为空
            String courseFile = directory.getCanonicalPath();
            System.out.println(courseFile);
            URL xmlpath = this.getClass().getClassLoader().getResource(".");
            System.out.println(xmlpath);
            inputStreamReader = new InputStreamReader(ConfigPropertiesUtil.class.getClassLoader().getResourceAsStream("common.properties"), "UTF-8");
            properties.load(inputStreamReader);
        } catch (Exception e) {
            //todo 错误信息需要记录到日志中
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取指定名字的配置文件。如果配置文件的key和已存在的key冲突，会覆盖已存在的key的内容。
     *
     * @param fileName 配置文件的名称
     * @throws Exception 如果配置文件不存在，抛出异常
     */
    public void loadResource(String fileName) throws Exception {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(ConfigPropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
            properties.load(inputStreamReader);
        } catch (Exception e) {
            //todo 错误信息需要记录到日志中
            throw new Exception("can not find default properties");
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static synchronized ConfigPropertiesUtil getInstance() {
        if (self == null) {
            try {
                self = new ConfigPropertiesUtil();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return self;
    }

    /**
     * 将配置文件中的字符串类型转成其他类型指定的类型
     *
     * @param key       关键词
     * @param clazzType 关键词对应内容的类型
     * @return 转成指定类型之后的内容
     */
    public <T> T get(String key, Class<T> clazzType) {
        Object object = properties.get(key);
        if (object != null) {
            return convertTo(object, clazzType);
        }
        return null;
    }

    /**
     * 类型转换。提供将对象转成指定的类型的功能
     *
     * @param value     被转换的对象
     * @param clazzType 指定一个转成的类型
     * @return 返回转换的结果
     */
    @SuppressWarnings("unchecked")
    private <T> T convertTo(Object value, Class<T> clazzType) {
        if (clazzType.isAssignableFrom(String.class)) {
            return (T) value.toString();
        } else if (clazzType.isAssignableFrom(Long.class)) {
            return (T) new Long(value.toString());
        } else if (clazzType.isAssignableFrom(Byte.class)) {
            return (T) new Byte(value.toString());
        } else if (clazzType.isAssignableFrom(Integer.class)) {
            return (T) new Integer(value.toString());
        } else if (clazzType.isAssignableFrom(Float.class)) {
            return (T) new Float(value.toString());
        } else if (clazzType.isAssignableFrom(Float.class)) {
            return (T) new Double(value.toString());
        } else if (clazzType.isAssignableFrom(Boolean.class)) {
            return (T) new Boolean(value.toString());
        } else {
            return (T) value.toString();
        }
    }
}
