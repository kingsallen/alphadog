package com.moseeker.common.util;

import com.alibaba.fastjson.JSONArray;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    Logger logger = LoggerFactory.getLogger(ConfigPropertiesUtil.class);
    private static Properties properties;            //储配置文件内容存
    private static ConfigPropertiesUtil self;
    private Set<String> files = new HashSet<>();

    /**
     * 读取配置信息帮助类 默认读取serviceprovider.properties配置文件
     */
    private ConfigPropertiesUtil() {
        properties = new Properties();
        InputStreamReader inputStreamReader = null;
        try {
            //需要load common项目本地配置文件
            //load service provider的配置文件
            inputStreamReader = new InputStreamReader(ConfigPropertiesUtil.class.getClassLoader().getResourceAsStream("common.properties"), "UTF-8");
            properties.load(inputStreamReader);
            files.add("common.properties");
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
        if (!files.contains(fileName)) {
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(ConfigPropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
                properties.load(inputStreamReader);
            } catch (Exception e) {
                //todo 错误信息需要记录到日志中
                throw new Exception("can not find properties");
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
    }

    /**
     * 读取指定名字的配置文件。如果配置文件的key和已存在的key冲突，会覆盖已存在的key的内容。
     *
     * @param fileName 配置文件的名称
     * @throws Exception 如果配置文件不存在，抛出异常
     */
    public void reloadResource(String fileName) throws Exception {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(ConfigPropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
            properties.load(inputStreamReader);
            files.add(fileName);
        } catch (Exception e) {
            //todo 错误信息需要记录到日志中
            throw new Exception("can not find properties");
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
     * @param absoluteFile 配置文件的名称
     * @throws Exception 如果配置文件不存在，抛出异常
     */
    public void loadAbsoluteResource(String absoluteFile) throws Exception {
        if (!files.contains(absoluteFile)) {
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(new FileInputStream(absoluteFile), "utf-8");
                properties.load(inputStreamReader);
            } catch (Exception e) {
                //todo 错误信息需要记录到日志中
                throw new Exception("can not find properties");
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
    }

    /**
     * 读取指定名字的配置文件。如果配置文件的key和已存在的key冲突，会覆盖已存在的key的内容。
     *
     * @param absoluteFile 配置文件的名称
     * @throws Exception 如果配置文件不存在，抛出异常
     */
    public void reloadAbsoluteResource(String absoluteFile) throws Exception {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(absoluteFile), "utf-8");
            properties.load(inputStreamReader);
            files.add(absoluteFile);
        } catch (Exception e) {
            //todo 错误信息需要记录到日志中
            throw new Exception("can not find properties");
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

    public <T> T get(String key, Class<T> clazz) {
        if (properties.get(key) != null) {
            return convertTo(properties.get(key), clazz);
        }
        return null;
    }

    public <T> T get(String key, Class<T> clazz, T defaultValue) {
        if (properties.get(key) != null) {
            T t = convertTo(properties.get(key), clazz);
            if (t != null) {
                return t;
            } else {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public Set<Object> returnKeys() {
        if (properties != null)
            return properties.keySet();
        return new HashSet<Object>();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<Object> converToList(Object value) {
        List<Object> result = new ArrayList<>();
        if (value instanceof JSONArray) {
            Collections.addAll(result, ((JSONArray) value).toArray());
        } else if (value instanceof ArrayList || value.getClass().getTypeName().equals("java.util.Arrays$ArrayList")) {
            return (List) value;
        } else if (value instanceof Set) {
            result.addAll((Set) value);
        } else {
            Object[] params = (Object[]) value;
            Collections.addAll(result, params);
        }
        return result;
    }

    /**
     *
     * @param value
     * @param clazzType
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertTo(Object value, Class<?> clazzType) {
        if (value == null || clazzType == null) {
            return null;
        }
        if (clazzType.isAssignableFrom(String.class)) {
            return (T) converToString(value);
        } else if (clazzType.isAssignableFrom(Long.class) || clazzType.isAssignableFrom(long.class)) {
            return (T) converToLong(value);
        } else if (clazzType.isAssignableFrom(Byte.class) || clazzType.isAssignableFrom(byte.class)) {
            return (T) converToByte(value);
        } else if (clazzType.isAssignableFrom(Integer.class) || clazzType.isAssignableFrom(int.class)) {
            return (T) converToInteger(value);
        } else if (clazzType.isAssignableFrom(Float.class) || clazzType.isAssignableFrom(float.class)) {
            return (T) converToFloat(value);
        } else if (clazzType.isAssignableFrom(Double.class) || clazzType.isAssignableFrom(double.class)) {
            return (T) converToDouble(value);
        } else if (clazzType.isAssignableFrom(Boolean.class) || clazzType.isAssignableFrom(boolean.class)) {
            return (T) convertToBoolean(value);
        } else if (clazzType.isAssignableFrom(BigInteger.class)) {
            return (T) convertToBigInteger(value);
        } else if (clazzType.isAssignableFrom(java.sql.Date.class)) {
            return (T) convertToSQLDate(value);
        } else if (clazzType.isAssignableFrom(BigDecimal.class)) {
            return (T) convertToBigDecimal(value);
        } else if (clazzType.isAssignableFrom(java.sql.Timestamp.class)) {
            return (T) convertToSQLTimestamp(value);
        } else if (clazzType.isAssignableFrom(Short.class) || clazzType.isAssignableFrom(short.class)) {
            return (T) convertToShort(value);
        } else if (clazzType.isAssignableFrom(Map.class)) {
            // Map对象, 暂时不做转换, 有需求的时候再添加转换方法
            return (T) value;
        } else if(clazzType.isAssignableFrom(List.class)) {
            return (T) converToList(value);
        } else {
            return null;
        }
    }

    public static BigInteger convertToBigInteger(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            try {
                return BigInteger.valueOf(Integer.valueOf(((String)value).trim()));
            } catch (NumberFormatException e) {
                return BigInteger.valueOf(0);
            }
        } else if (value instanceof Boolean) {
            if ((Boolean) value) {
                return BigInteger.valueOf(1);
            } else {
                return BigInteger.valueOf(0);
            }
        } else if (value instanceof Integer) {
            return BigInteger.valueOf((Integer) value);
        }else if (value instanceof BigDecimal) {
            return BigInteger.valueOf(((BigDecimal)value).intValue());
        } else if (value instanceof Byte) {
            return BigInteger.valueOf((Byte) value);
        } else if (value instanceof Float) {
            return BigInteger.valueOf(((Float) value).intValue());
        } else if (value instanceof Long) {
            return BigInteger.valueOf(((Long) value).shortValue());
        } else if (value instanceof Double) {
            return BigInteger.valueOf(((Double) value).shortValue());
        }  else if (value instanceof Date) {
            return BigInteger.valueOf((short) ((Date) value).getTime());
        } else if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else {
            return null;
        }
    }

    public static BigDecimal convertToBigDecimal(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            try {
                return BigDecimal.valueOf(Double.valueOf(((String)value).trim()));
            } catch (NumberFormatException e) {
                return BigDecimal.valueOf(0);
            }
        } else if (value instanceof Boolean) {
            if ((Boolean) value) {
                return BigDecimal.valueOf(1);
            } else {
                return BigDecimal.valueOf(0);
            }
        } else if (value instanceof Integer) {
            return BigDecimal.valueOf((Integer) value);
        }else if (value instanceof BigInteger) {
            return BigDecimal.valueOf(((BigInteger)value).intValue());
        } else if (value instanceof Byte) {
            return BigDecimal.valueOf((Byte) value);
        } else if (value instanceof Float) {
            return BigDecimal.valueOf(((Float) value).floatValue());
        } else if (value instanceof Long) {
            return BigDecimal.valueOf(((Long) value).longValue());
        } else if (value instanceof Double) {
            return BigDecimal.valueOf(((Double) value).longValue());
        } else if (value instanceof Date) {
            return BigDecimal.valueOf((short) ((Date) value).getTime());
        }  else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else {
            return null;
        }
    }

    public static Short convertToShort(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            try {
                return Short.valueOf(((String) value).trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        } else if (value instanceof Boolean) {
            if ((Boolean) value) {
                return 1;
            } else {
                return 0;
            }
        } else if (value instanceof Integer) {
            return ((Integer) value).shortValue();
        } else if (value instanceof BigInteger) {
            return Short.valueOf(((BigInteger) value).shortValue());
        } else if (value instanceof Byte) {
            return ((Byte) value).shortValue();
        } else if (value instanceof Float) {
            return ((Float) value).shortValue();
        } else if (value instanceof Long) {
            return ((Long) value).shortValue();
        } else if (value instanceof Double) {
            return ((Double) value).shortValue();
        } else if (value instanceof Date) {
            return Long.valueOf(((Date) value).getTime()).shortValue();
        } else if (value instanceof BigDecimal) {
            return Short.valueOf(((BigDecimal) value).shortValue());
        } else {
            return null;
        }
    }


    public static java.sql.Timestamp convertToSQLTimestamp(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            try {
                if(((String)value).length() == 10) {
                    return new java.sql.Timestamp(DateUtils.shortDateToDate((String) value).getTime());
                } else if(((String)value).length() == 19) {
                    return new java.sql.Timestamp(DateUtils.shortTimeToDate((String) value).getTime());
                } else {
                    return new java.sql.Timestamp(DateUtils.shortTimeToDate((String) value).getTime());
                }
            } catch (ParseException e) {
                return null;
            }
        } else if (value instanceof Boolean) {
            return null;
        } else if (value instanceof Short) {
            return new java.sql.Timestamp((Short) value);
        } else if (value instanceof Integer) {
            return new java.sql.Timestamp((Integer) value);
        } else if (value instanceof BigInteger) {
            return new java.sql.Timestamp(((BigInteger) value).intValue());
        }  else if (value instanceof Byte) {
            return new java.sql.Timestamp((Byte) value);
        } else if (value instanceof Float) {
            return new java.sql.Timestamp(((Float) value).longValue());
        } else if (value instanceof Long) {
            return new java.sql.Timestamp((Long) value);
        } else if (value instanceof Double) {
            return new java.sql.Timestamp(((Double) value).longValue());
        }  else if (value instanceof java.sql.Timestamp) {
            return (java.sql.Timestamp) value;
        } else if (value instanceof java.sql.Date) {
            return new java.sql.Timestamp(((java.sql.Date) value).getTime());
        } else if (value instanceof Date) {
            return new java.sql.Timestamp(((Date) value).getTime());
        } else if (value instanceof BigDecimal) {
            return new java.sql.Timestamp(((BigDecimal) value).longValue());
        } else {
            return null;
        }
    }

    public static java.sql.Date convertToSQLDate(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            try {
                return new java.sql.Date(DateUtils.nomalDateToDate((String) value).getTime());
            } catch (ParseException e) {
                return null;
            }
        } else if (value instanceof Boolean) {
            return null;
        }  else if (value instanceof Short) {
            return new java.sql.Date((Short) value);
        } else if (value instanceof Integer) {
            return new java.sql.Date((Integer) value);
        } else if (value instanceof BigInteger) {
            return new java.sql.Date(((BigInteger) value).intValue());
        }  else if (value instanceof Byte) {
            return new java.sql.Date((Byte) value);
        } else if (value instanceof Float) {
            return new java.sql.Date(((Float) value).longValue());
        } else if (value instanceof Long) {
            return new java.sql.Date((Long) value);
        } else if (value instanceof Double) {
            return new java.sql.Date(((Double) value).longValue());
        } else if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        } else if (value instanceof java.sql.Timestamp) {
            return new java.sql.Date(((java.sql.Timestamp) value).getTime());
        } else if (value instanceof Date) {
            return new java.sql.Date(((Date) value).getTime());
        } else if (value instanceof BigDecimal) {
            return new java.sql.Date(((BigDecimal) value).longValue());
        } else {
            return null;
        }
    }

    public static Boolean convertToBoolean(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            try {
                return Boolean.valueOf(((String) value).trim());
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Short) {
            if ((Short) value > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else if (value instanceof Integer) {
            if ((Integer) value > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else if (value instanceof BigInteger) {
            if (((BigInteger) value).intValue() > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else if (value instanceof Byte) {
            if ((Byte) value > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else if (value instanceof Float) {
            if ((Float) value > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else if (value instanceof Long) {
            if ((Long) value > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else if (value instanceof Double) {
            if ((Double) value > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else if (value instanceof BigDecimal) {
            if (((BigDecimal) value).longValue() > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else {
            // sql.date sql.timestamp date timestamp
            if (value != null) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
    }

    public static Double converToDouble(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            try {
                return Double.valueOf(((String) value).trim());
            } catch (NumberFormatException e) {
                return Double.valueOf(0);
            }
        } else if (value instanceof Boolean) {
            if ((Boolean) value) {
                return Double.valueOf(1);
            } else {
                return Double.valueOf(0);
            }
        } else if (value instanceof Short) {
            return Double.valueOf((Short) value);
        } else if (value instanceof Integer) {
            return Double.valueOf((Integer) value);
        } else if (value instanceof BigInteger) {
            return Double.valueOf(((BigInteger) value).intValue());
        } else if (value instanceof Byte) {
            return Double.valueOf((Byte) value);
        } else if (value instanceof Float) {
            return Double.valueOf((Float) value);
        } else if (value instanceof Long) {
            return Double.valueOf((Long) value);
        } else if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Date) {
            return Double.valueOf(((Date) value).getTime());
        }  else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).doubleValue();
        } else {
            return null;
        }
    }

    public static Float converToFloat(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            try {
                return Float.valueOf(((String)value).trim());
            } catch (NumberFormatException e) {
                return Float.valueOf(0f);
            }
        } else if (value instanceof Boolean) {
            if ((Boolean) value) {
                return Float.valueOf(1);
            } else {
                return Float.valueOf(0);
            }
        } else if (value instanceof Short) {
            return (Float) ((Short) value).floatValue();
        }  else if (value instanceof Integer) {
            return (Float) ((Integer) value).floatValue();
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).floatValue();
        } else if (value instanceof Byte) {
            return (Float) ((Byte) value).floatValue();
        } else if (value instanceof Float) {
            return (Float) value;
        } else if (value instanceof Long) {
            return (Float) ((Long) value).floatValue();
        } else if (value instanceof Double) {
            return (Float) ((Double) value).floatValue();
        }  else if (value instanceof Date) {
            return (Float) Float.valueOf(((Date) value).getTime());
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).floatValue();
        } else {
            return null;
        }
    }

    public static Integer converToInteger(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            try {
                return Integer.valueOf(((String)value).trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return Integer.valueOf(0);
            }
        } else if (value instanceof Boolean) {
            if ((Boolean) value) {
                return Integer.valueOf(1);
            } else {
                return Integer.valueOf(0);
            }
        } else if (value instanceof Short) {
            return (Integer) ((Short) value).intValue();
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).intValue();
        } else if (value instanceof Byte) {
            return (Integer) ((Byte) value).intValue();
        } else if (value instanceof Float) {
            return (Integer) ((Float) value).intValue();
        } else if (value instanceof Long) {
            return (Integer) ((Long) value).intValue();
        } else if (value instanceof Double) {
            return (Integer) ((Double) value).intValue();
        } else if (value instanceof Date) {
            return (int) ((Date) value).getTime();
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).intValue();
        } else {
            return null;
        }
    }

    public static Byte converToByte(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            return Byte.valueOf(((String) value).trim());
        } else if (value instanceof Byte) {
            return (Byte) value;
        } else if (value instanceof Boolean) {
            // todo 需要考虑一个类似l表示long基本数据类型 存在一个b表示该数值是byte的方式
            byte b = 1;
            if ((Boolean) value) {
                return Byte.valueOf(b);
            } else {
                b = 0;
                return Byte.valueOf(b);
            }
        } else if (value instanceof Short) {
            return (Byte) ((Short) value).byteValue();
        }  else if (value instanceof Integer) {
            return (Byte) ((Integer) value).byteValue();
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).byteValue();
        } else if (value instanceof Float) {
            return (Byte) ((Float) value).byteValue();
        } else if (value instanceof Long) {
            return (Byte) ((Long) value).byteValue();
        } else if (value instanceof Double) {
            return (Byte) ((Double) value).byteValue();
        }  else if (value instanceof Date) {
            return (byte) ((Date) value).getTime();
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).byteValue();
        } else {
            return null;
        }
    }

    public static Long converToLong(Object value) {
        if(value instanceof String[]) {
            value = ((String[])value)[0];
        }
        if (value instanceof String && !((String) value).trim().equals("")) {
            return Long.valueOf(((String) value).trim());
        } else if (value instanceof Byte) {
            return Long.valueOf((Byte) value);
        } else if (value instanceof Boolean) {
            if ((Boolean) value) {
                return Long.valueOf(1);
            } else {
                return Long.valueOf(0);
            }
        } else if (value instanceof Short) {
            return (Long) ((Short) value).longValue();
        }  else if (value instanceof Integer) {
            return Long.valueOf((Integer) value);
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).longValue();
        } else if (value instanceof Float) {
            return Long.valueOf(((Float) value).longValue());
        } else if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Double) {
            return (Long) ((Double) value).longValue();
        }  else if (value instanceof Date) {
            return ((Date) value).getTime();
        }  else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).longValue();
        } else {
            return null;
        }
    }

    public static String converToString(Object value) {
        if (value instanceof String[]) {
            value = ((String[]) value)[0];
        }
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof java.sql.Timestamp) {
            return DateUtils.dateToShortTime(((java.sql.Timestamp) value));
        } else if (value instanceof Date) {
            return DateUtils.dateToNormalDate(((Date) value));
        } else if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

}
