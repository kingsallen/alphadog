package com.moseeker.useraccounts;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.moseeker.common.util.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThriftUtil {

    private static String thirftPath="/Users/pyb/IdeaProjects/alphadog/thrift-jooq/thrift";

//    private static String thirftPath="D:\\Git-Moseeker\\alphadog\\thrift-jooq\\thrift";

    public static String testGetStructThrift(List<String> names){
        String result=getStructThrift(names);
        System.out.println(result);
        return result;
    }

    public static String getServiceThrift(List<String> names){
        return getThrift(names,ThriftType.SERVICE);
    }

    public static String getStructThrift(List<String> names){
        return getThrift(names,ThriftType.STRUCT);
    }

    public static String getThrift(List<String> names,ThriftType thriftType){
        if(names==null || names.isEmpty()){
            return "";
        }

        Path path= Paths.get(thirftPath);

        StringBuilder sb=new StringBuilder();


        try {
            Files.walkFileTree(path,new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(!file.toFile().getName().endsWith(".thrift")){
                        return FileVisitResult.CONTINUE;
                    }

                    List<String> lines=Files.readAllLines(file, Charsets.UTF_8);

                    for(int i=0;i<lines.size();i++) {
                        String line=lines.get(i);
                        for (String name : names) {
                            if(StringUtils.isNullOrEmpty(name)){
                                continue;
                            }
                            if (Pattern.matches(thriftType.pattern().replace("{}",name), line)) {
                                GenerateDoc.DocBuilder builder=GenerateDoc.DocBuilder.newInstance();
                                List<String> innerStruct=new ArrayList<>();
                                for(int j=i;j<lines.size();j++){
                                    String innerLine=lines.get(j);
                                    builder.append(innerLine);
                                    if(Pattern.matches(".*[0-9]+:\\s?(required|optional){0,1}.*",innerLine)){
                                        if(!isThriftGenericType(innerLine)) {
                                            innerStruct.addAll(Arrays.asList(getThriftStructParam(innerLine)));
                                        }
                                    }
                                    if(innerLine.contains("}")){
                                        i=j;
                                        break;

                                    }
                                }
                                if(!innerStruct.isEmpty()) {
                                    sb.append(getThrift(innerStruct, thriftType));
                                }
                                sb.append(builder.end());
                            }
                        }
                    }



                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private static String filterParams[]={"bool","byte","i16","i32","i64","double","string","map<"};
    private static boolean isThriftGenericType(String line){
        for(String param:filterParams) {
            if (line.contains(param)){
                return true;
            }
        }
        return false;
    }

    private static String typeFirst=".*[0-9]+:\\s?(required|optional){1}\\s+(list<|set<|map<)?";
    private static String typeLast="(>)?\\s+.*";
    private static String[] getThriftStructParam(String str){
        if(StringUtils.isNotNullOrEmpty(str)){
            return str.replaceAll(typeFirst,"").replaceAll(typeLast,"").split(",");
        }
        return new String[]{};
    }

    @Test
    public void test(){
        testGetStructThrift(Arrays.asList("User"));
//        testGetStructThrift(Arrays.asList("ProfileApplicationForm"));
//        testGetStructThrift(Arrays.asList("EmployeeResponse"));
//        testGetStructThrift(Arrays.asList("Result"));
//        testGetStructThrift(Arrays.asList("EmployeeCustomFieldsConf"));
//        testGetStructThrift(Arrays.asList("RewardsResponse"));
//        testGetStructThrift(Arrays.asList("EmployeeAward"));
//        System.out.println("1:  required  BindStatus  bindStatus,".replaceAll("[0-9]+:\\s+(required|optional){1}\\s+(list<|set<|map<)?","").replaceAll("(>)?\\s+.*",""));

//        Pattern pattern=Pattern.compile("[0-9]+:\\s?(required|optional){1}");
//        Matcher matcher=pattern.matcher("    1: required BindStatus bindStatus,");
//        System.out.println(matcher.find());
//        System.out.println(Pattern.matches(".*[0-9]+:\\s+(required|optional){1}.*","    1: required BindStatus bindStatus,"));
    }

    private enum ThriftType{
        STRUCT(".*[struct|enum]{1}\\s+{}\\s?\\{.*"),
        SERVICE(".*service\\s+{}\\s?\\{.*")
        ;


        ThriftType(String pattern) {
            this.pattern = pattern;
        }

        private String pattern;

        private String pattern(){
            return pattern;
        }
    }
}
