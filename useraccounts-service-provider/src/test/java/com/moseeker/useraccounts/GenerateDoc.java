package com.moseeker.useraccounts;

import com.moseeker.common.util.StringUtils;
import org.apache.thrift.TBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GenerateDoc {
    Logger logger= LoggerFactory.getLogger(GenerateDoc.class);


    @Test
    public void test() throws ClassNotFoundException {
        Class clazz=Class.forName("com.moseeker.useraccounts.thrift.UserHrAccountServiceImpl");
        Method[] methods=clazz.getDeclaredMethods();
        int i=0;
        for(Method method:methods){
            if(!Modifier.isPublic(method.getModifiers())){
                continue;
            }
            String doc=DocBuilder.newInstance()
                    .append(describe())
                    .append(requestDescribe(method))
                    .append(requestExample())
                    .append(paramDescribe(method))
                    .append(rootDescribe())
                    .append(result(method))
                    .append(resultParam(method))
                    .end();
            System.out.print(doc);
            i++;
        }
        System.out.println(i);
    }


    private String describe(){
        return DocBuilder.newInstance()
                .append("##  接口名称")
                .end();
    }


    private String requestDescribe(Method method){
        return DocBuilder.newInstance()
                .append("#### 请求说明:")
                .append("请求方式：Thrift")
                .emptyLine()
                .append(getMethodName(method))
//                .append("[http://api.moseeker.com/retrieval/profile](http://api.moseeker.com/retrieval/profile)")
                .end();
    }
    public String getMethodName(Method method){
        String interfaceName=method.getDeclaringClass().getGenericInterfaces()[0].getTypeName();
        String thriftName=interfaceName.substring(interfaceName.lastIndexOf(".")+1,interfaceName.indexOf("$"));

        return thriftName+"."+method.getName();
    }

    private String requestExample(){
        return DocBuilder.newInstance()
                .append("#### 请求示例:")
                .end();
    }

    private String paramDescribe(Method method){
        return DocBuilder.newInstance()
                .append("#### 参数说明:")
                .emptyLine()
                .append(buildParam(method))
                .end();
    }
    private String buildParam(Method method){
        ParameterNameDiscoverer parameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();

        String names[]=parameterNameDiscoverer.getParameterNames(method);

        Parameter[] parameters=method.getParameters();

        if(names==null || names.length==0 || parameters==null || parameters.length==0){
            return "";
        }

        DocBuilder builder=DocBuilder.newInstance().append(ParamBuilder.TITLE);
        for(int i=0;i<parameters.length;i++){
            Parameter parameter=parameters[i];
            String paramDoc=ParamBuilder.newInstance()
                    .append(names[i])
                    .append(getSpecialName(parameter))
                    .append("是")
                    .append("这是描述").build();
            builder.append(paramDoc);
            if(!isSpecialReturnType(parameter)){
                Field fields[] = parameter.getType().getFields();
                for (Field field : fields) {
                    if (filterField(field)) {
                        continue;
                    }
                    paramDoc = ParamBuilder.newInstance()
                            .append(names[i]+"."+field.getName())
                            .append(getSpecialName(field))
                            .append("是")
                            .append("这是描述").build();
                    builder.append(paramDoc);
                }
            }
            builder.append(paramDoc);
        }
        return builder.end();
    }

    private String rootDescribe(){
        return DocBuilder.newInstance()
                .append("##### 权限说明:")
                .emptyLine()
                .append(" 暂无")
                .end();
    }

    private String result(Method method){
        return DocBuilder.newInstance()
                .append("##### 返回结果:")
                .emptyLine()
                .append(getReturnName(method))
                .emptyLine()
                .append(getThrift(method.getReturnType(),method.getGenericReturnType()))
                .end();
    }

    private String resultParam(Method method){
        return DocBuilder.newInstance()
                .append("##### 参数说明:")
                .append(buildReturnParam(method))
                .end();
    }

    private String buildReturnParam(Method method){
        Class returnClass=method.getReturnType();

        if(isSpecialReturnType(method)){
            if(method.getReturnType().isAssignableFrom(List.class)){
                returnClass=getGenericType(method.getGenericReturnType());
            }
        }

        Field fields[] = returnClass.getFields();
        DocBuilder builder = DocBuilder.newInstance().append(ParamBuilder.RESULT_TITLE);
        for (Field field : fields) {
            if (filterField(field)) {
                continue;
            }
            String paramDoc = ParamBuilder.newInstance()
                    .append(field.getName())
                    .append(getSpecialName(field))
                    .append("这是描述").build();
            builder.append(paramDoc);
        }
        return builder.end();
    }

    private String getThrift(Class clazz,Type type){
        if(clazz==null) return "";

        List<String> thriftNames=new ArrayList<>();

        if(clazz.isAssignableFrom(List.class)) { //【2】
            clazz=getGenericType(type);
        }

        if(clazz==null){
            return "";
        }

        Type interfaces[]=clazz.getGenericInterfaces();

        if(interfaces!=null && interfaces.length>0 && interfaces[0].getTypeName().contains("TBase")){
            thriftNames.add(clazz.getSimpleName());
        }

        String thrift= ThriftUtil.getStructThrift(thriftNames);

        if(StringUtils.isNotNullOrEmpty(thrift)){
            thrift=DocBuilder.newInstance()
                    .append("```")
                    .append(thrift)
                    .append("```")
                    .end();
        }

        return thrift;


    }

    private boolean isSpecialReturnType(Parameter parameter){
        return isSpecialReturnType(parameter.getType(),parameter.getParameterizedType());
    }

    private boolean isSpecialReturnType(Method method){
        return isSpecialReturnType(method.getReturnType(),method.getGenericReturnType());
    }

    private boolean isSpecialReturnType(Class clazz,Type fc){
        if(clazz.isPrimitive()) {  //判断是否为基本类型
            return true;
        }

        if(clazz.getName().startsWith("java.lang")) { //getName()返回field的类型全路径；
            return true;
        }

        if(clazz.isAssignableFrom(List.class)) //【2】
        {
            if(fc == null) return false;

            if(fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
            {
                ParameterizedType pt = (ParameterizedType) fc;

                Class genericClazz = (Class)pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。

                if(genericClazz!=null) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getSpecialName(Field field){
        return getSpecialName(field.getType(),field.getGenericType());
    }

    private String getSpecialName(Parameter parameter){
        return getSpecialName(parameter.getType(),parameter.getParameterizedType());
    }

    private String getReturnName(Method method){
        return getSpecialName(method.getReturnType(),method.getGenericReturnType());
    }

    private String getSpecialName(Class clazz,Type fc){
        if(clazz.isPrimitive()) {  //判断是否为基本类型
            return clazz.getSimpleName();
        }

        if(clazz.getName().startsWith("java.lang")) { //getName()返回field的类型全路径；
            return clazz.getName();
        }

        if(clazz.isAssignableFrom(List.class)) //【2】
        {
            return clazz.getSimpleName()+"&lt;"+getGenericTypeSimpleName(fc)+"&gt;";
        }

        return clazz.getName();
    }


    private Class getGenericType(Type fc){
        if(fc == null) return null;

        if(fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
        {
            ParameterizedType pt = (ParameterizedType) fc;

            Class genericClazz = (Class)pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。

            return genericClazz;
        }
        return null;
    }
    private String getGenericTypeSimpleName(Type fc){
        Class clazz=getGenericType(fc);
        if(clazz!=null){
            return clazz.getSimpleName();
        }
        return "";
    }




    private boolean filterField(Field field){
        if(field.getName().equals("metaDataMap")){
            return true;
        }
        return false;
    }


    static class DocBuilder{
        private final StringBuilder builder=new StringBuilder();
        public static DocBuilder newInstance(){
            return new DocBuilder();
        }
        public DocBuilder append(String str){
            builder.append(str).append("\r\n");
            return this;
        }
        public DocBuilder emptyLine(){
            builder.append("\r\n");
            return this;
        }
        public String end(){
            return builder.toString();
        }
    }

    private static class ParamBuilder{
        private final StringBuilder builder=new StringBuilder();

        private final static String TITLE;

        private final static String RESULT_TITLE;

        static {
            StringBuilder builder=new StringBuilder();
            TITLE=builder
                    .append("| 参数 | 类型 | 必须 | 说明 |")
                    .append("\r\n")
                    .append("|---|---|---|---|")
                    .toString();
            builder=new StringBuilder();
            RESULT_TITLE=builder
                    .append("| 参数 | 类型 | 说明 |")
                    .append("\r\n")
                    .append("|---|---|---|")
                    .toString();
        }

        public static ParamBuilder newInstance(){
            return new ParamBuilder();
        }

        public ParamBuilder append(String str){
            builder.append("|").append(str);
            return this;
        }

        public String build(){
            return builder.append("|")
                    .toString();
        }


    }
}
