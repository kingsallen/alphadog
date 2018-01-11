package com.moseeker.useraccounts;

import org.junit.Test;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class GenerateDoc {

    @Test
    public void test() throws ClassNotFoundException {
        Class clazz=Class.forName("com.moseeker.useraccounts.thrift.EmployeeServiceImpl");
        Method[] methods=clazz.getDeclaredMethods();
        for(Method method:methods){
            method.getModifiers();
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
        }
    }


    private String describe(){
        return DocBuilder.newInstance()
                .append("##  接口名称")
                .end();
    }


    private String requestDescribe(Method method){
        return DocBuilder.newInstance()
                .append("#### 请求说明")
                .append("http请求方式：无")
                .emptyLine()
                .append(getMethodName(method))
//                .append("[http://api.moseeker.com/retrieval/profile](http://api.moseeker.com/retrieval/profile)")
                .end();
    }
    public String getMethodName(Method method){
        return method.getDeclaringClass().getSimpleName()+"."+method.getName();
    }

    private String requestExample(){
        return DocBuilder.newInstance()
                .append("#### 请求示例")
                .end();
    }

    private String paramDescribe(Method method){
        return DocBuilder.newInstance()
                .append("#### 参数说明")
                .emptyLine()
                .append(buildParam(method))
                .end();
    }
    private String buildParam(Method method){
        ParameterNameDiscoverer parameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();

        String names[]=parameterNameDiscoverer.getParameterNames(method);

        Parameter[] parameters=method.getParameters();
        DocBuilder builder=DocBuilder.newInstance().append(ParamBuilder.TITLE);
        for(int i=0;i<parameters.length;i++){
            Parameter parameter=parameters[i];
            String paramDoc=ParamBuilder.newInstance()
                    .param(names[i])
                    .type(parameter.getType().getSimpleName())
                    .isNecessary("是")
                    .describe("这是描述").build();
            builder.append(paramDoc);
        }
        return builder.end();
    }

    private String rootDescribe(){
        return DocBuilder.newInstance()
                .append("##### 权限说明")
                .emptyLine()
                .append(" 暂无")
                .end();
    }

    private String result(Method method){
        return DocBuilder.newInstance()
                .append("##### 返回结果")
                .end();
    }

    private String resultParam(Method method){
        return DocBuilder.newInstance()
                .append("##### 参数说明")
                .append(buildParam(method.getReturnType()))
                .end();
    }

    private String buildParam(Class clazz){
        Field fields[]=clazz.getFields();
        DocBuilder builder=DocBuilder.newInstance().append(ParamBuilder.TITLE);
        for(Field field:fields){
            String paramDoc=ParamBuilder.newInstance()
                    .param(field.getName())
                    .type(field.getType().getSimpleName())
                    .isNecessary("是")
                    .describe("这是描述").build();
            builder.append(paramDoc);
        }
        return builder.end();
    }


    private static class DocBuilder{
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
        private String title;
        private String param;
        private String type;
        private String isNecessary;
        private String describe;

        private final static String TITLE;

        static {
            StringBuilder builder=new StringBuilder();
            TITLE=builder
                    .append("| 参数 | 类型 | 必须 | 说明 |")
                    .append("\r\n")
                    .append("|--|--|--|--|")
                    .toString();
        }

        public static ParamBuilder newInstance(){
            return new ParamBuilder();
        }
        public static ParamBuilder newInstanceWithDefaultTitle(){
            return new ParamBuilder().title(TITLE);
        }

        public ParamBuilder title(String title){
            this.title=title;
            return this;
        }

        public ParamBuilder param(String param){
            this.param=param;
            return this;
        }

        public ParamBuilder type(String type){
            this.type=type;
            return this;
        }

        public ParamBuilder isNecessary(String isNecessary){
            this.isNecessary=isNecessary;
            return this;
        }

        public ParamBuilder describe(String describe){
            this.describe=describe;
            return this;
        }

        public String build(){
            StringBuilder builder=new StringBuilder();
            return builder
                    .append("|")
                    .append(param).append("|")
                    .append(type).append("|")
                    .append(isNecessary).append("|")
                    .append(describe).append("|")
                    .toString();
        }


    }
}
