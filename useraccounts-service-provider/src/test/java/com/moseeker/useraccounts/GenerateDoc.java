package com.moseeker.useraccounts;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.weixin.QrcodeType;
import com.moseeker.common.weixin.SceneType;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeVO;
import org.apache.thrift.TBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class GenerateDoc {
    Logger logger= LoggerFactory.getLogger(GenerateDoc.class);


    @Test
    public void test() throws ClassNotFoundException {
        Class clazz=Class.forName("ThriftUtil");
        Method[] methods=clazz.getDeclaredMethods();
        int i=0;
        DocBuilder docBuilder=DocBuilder.newInstance();
        docBuilder.append(service(clazz));
        for(Method method:methods){
            if(!Modifier.isPublic(method.getModifiers())){
                continue;
            }
            docBuilder
                .append(describe())
                .append(requestDescribe())
                .append(requestExample(method))
                .append(paramDescribe(method))
                .append(rootDescribe())
                .append(result(method))
                .append(resultParam(method))
                .append(exception(method));
            i++;
        }
        System.out.print(docBuilder.end());
        System.out.println(i);
    }

    private String service(Class clazz){
        Class iface=(Class)clazz.getGenericInterfaces()[0];
        Class thriftService=iface.getDeclaringClass();
        return DocBuilder.newInstance()
                .append("```")
                .append(ThriftUtil.getServiceThrift(Arrays.asList(thriftService.getSimpleName())))
                .append("```")
                .end();
    }

    private String describe(){
        return DocBuilder.newInstance()
                .append("##  接口名称")
                .end();
    }


    private String requestDescribe(){
        return DocBuilder.newInstance()
                .append("#### 请求说明:")
                .append("请求方式：Thrift")
//                .append("[http://api.moseeker.com/retrieval/profile](http://api.moseeker.com/retrieval/profile)")
                .end();
    }

    private String requestExample(Method method){
        if(getMethodName(method).contains("UserHrAccountService.employeeExport")){
            System.out.println();
        }
        return DocBuilder.newInstance()
                .append("#### 请求示例:")
                .emptyLine()
                .append(getMethodName(method))
                .end();
    }

    public String getMethodName(Method method){
        String interfaceName=method.getDeclaringClass().getGenericInterfaces()[0].getTypeName();
        String thriftName=interfaceName.substring(interfaceName.lastIndexOf(".")+1,interfaceName.indexOf("$"));

        DocBuilder docBuilder=new DocBuilder();
        docBuilder.append(thriftName+"."+method.getName()).emptyLine();

        Parameter[] parameters=method.getParameters();

        for(Parameter parameter:parameters){
            if(!isSpecialReturnType(parameter)) {
                String struct=getThrift(parameter.getType(), parameter.getParameterizedType());
                if(StringUtils.isNullOrEmpty(struct)){
                    continue;
                }
                docBuilder.append(struct);
            }
        }

        return docBuilder.end();
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
                builder.append(paramDoc);
            }

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
        List<Class> returnClasses=Arrays.asList(method.getReturnType());

        if(isSpecialReturnType(method)){
            if(isCollection(method.getReturnType())){
                returnClasses=getGenericType(method.getGenericReturnType());
            }
        }


        DocBuilder builder = DocBuilder.newInstance().append(ParamBuilder.RESULT_TITLE);
        for(Class returnClass:returnClasses) {
            Field fields[] = returnClass.getFields();
            for (Field field : fields) {
                if (filterField(field)) {
                    continue;
                }
                String paramDoc = ParamBuilder.newInstance()
                        .append(returnClass.getSimpleName()+"."+field.getName())
                        .append(getSpecialName(field))
                        .append("这是描述").build();
                builder.append(paramDoc);
            }
        }
        return builder.end();
    }

    private String exception(Method method){
        Class<?>[] exceptions=method.getExceptionTypes();
        if(exceptions==null || exceptions.length==0){
            return "";
        }
        return DocBuilder.newInstance()
                .append("##### 抛出异常:")
                .append(exceptions[0].getSimpleName())
                .emptyLine()
                .append(ParamBuilder.EXCEPTION_MSG_TITLE).end();
    }

    private String getThrift(Class clazz,Type type){
        if(clazz==null) return "";

        List<String> thriftNames=new ArrayList<>();

        List<Class> classes=Arrays.asList(clazz);

        if(isCollection(clazz)) { //【2】
            classes=getGenericType(type);
        }

        if(clazz==null){
            return "";
        }
        StringBuilder thrifts=new StringBuilder();
        for(Class temp:classes) {
            Type interfaces[] = temp.getGenericInterfaces();

            if (interfaces != null && interfaces.length > 0 && interfaces[0].getTypeName().contains("TBase")) {
                thriftNames.add(temp.getSimpleName());
            }

            String thrift=ThriftUtil.getStructThrift(thriftNames);

            if (StringUtils.isNotNullOrEmpty(thrift)) {
                thrifts.append(DocBuilder.newInstance()
                        .append("```")
                        .append(thrift)
                        .append("```")
                        .end());
            }
        }

        return thrifts.toString();


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

        if(isCollection(clazz)) //【2】
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
            return clazz.getSimpleName();
        }

        if(isCollection(clazz)) //【2】
        {
            return getGenericTypeSimpleName(clazz,fc);
//            return clazz.getSimpleName()+"&lt;"+getGenericTypeSimpleName(fc)+"&gt;";
        }

        return clazz.getSimpleName();
    }


    private List<Class> getGenericType(Type fc){
        if(fc == null) return new ArrayList<>();

        if(fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
        {
            ParameterizedType pt = (ParameterizedType) fc;

            List<Class> genericTypes=new ArrayList<>();

            for(Type type:pt.getActualTypeArguments()) {
                if(type instanceof  ParameterizedType){
                    ParameterizedType pType=(ParameterizedType)type;
                    if(pType.getRawType() instanceof Class){
                        genericTypes.add((Class)pType.getRawType());
                    }
                }else if (type instanceof Class) {
                    Class genericClazz = (Class) type; //【4】 得到泛型里的class类型对象。
                    genericTypes.add(genericClazz);
                }
            }
            return genericTypes;
        }
        return new ArrayList<>();
    }
    private String getGenericTypeSimpleName(Class clazz,Type type){
        if(type instanceof ParameterizedType) // 【3】如果是泛型参数的类型
        {
            ParameterizedType pt = (ParameterizedType) type;

            List<Class> genericTypes=new ArrayList<>();

            StringBuilder str=new StringBuilder(clazz.getSimpleName()+"\\<");
            for(Type innerType:pt.getActualTypeArguments()) {
                if(innerType instanceof ParameterizedType){
                    ParameterizedType innerPt=(ParameterizedType)innerType;
                    if(innerPt.getRawType() instanceof Class) {
                        Class rawType=(Class)innerPt.getRawType();
                        if (isCollection(rawType)){
                            str.append(getGenericTypeSimpleName(rawType,innerPt)).append(",");
                        }
                    }
                }else if(innerType instanceof Class){
                    Class innerTypeClass=(Class)innerType;
                    str.append(innerTypeClass.getSimpleName()).append(",");
                }
            }
            str.append("\\>");
            if(str.lastIndexOf(",")>0){
                str.deleteCharAt(str.lastIndexOf(","));
            }
            return str.toString();
        }


        /*List<Class> clazzes=getGenericType(type);
        if(!StringUtils.isEmptyList(clazzes)){
            StringBuilder builder=new StringBuilder();
            clazzes.forEach(c->{
                if(isCollection(c)){
                    builder.append(getGenericTypeSimpleName(c,c.getGenericSuperclass()));
                }else {
                    builder.append(clazz.getSimpleName()+"\\<" + c.getSimpleName()+",\\>");
                }
            });

            return builder.toString();
        }*/
        return "";
    }

    @Test
    public void test2() throws NoSuchFieldException {
        Field field=UserEmployeeVO.class.getField("customFieldValues");
        System.out.println(getGenericTypeSimpleName(field.getType(),field.getGenericType()));
    }


    private boolean isCollection(Class clazz){
        return clazz.isAssignableFrom(List.class)||clazz.isAssignableFrom(Set.class)||clazz.isAssignableFrom(Map.class);
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

        private final static String EXCEPTION_MSG_TITLE;

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
            builder=new StringBuilder();
            EXCEPTION_MSG_TITLE=builder
                    .append("| code | 说明 |")
                    .append("\r\n")
                    .append("|---|---|")
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

    @Test
    public void testQrcodeType(){
//        assertEquals(true, QrcodeType.QR_LIMIT_SCENE.equals(QrcodeType.fromInt(1)));
        assertEquals("APPLY_FROM_PC", SceneType.APPLY_FROM_PC.toString());
    }

}
