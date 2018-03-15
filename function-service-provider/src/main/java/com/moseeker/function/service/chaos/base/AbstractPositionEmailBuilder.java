package com.moseeker.function.service.chaos.base;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;

public abstract class AbstractPositionEmailBuilder<T> implements PositionEmailBuilder<T>{

    private static String positionEmail;

    static {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        try {
            configUtils.loadResource("chaos.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
        positionEmail=configUtils.get("chaos.email", String.class);
    }

    public String email(JobPositionDO moseekerPosition) {
        StringBuilder emailBuilder=new StringBuilder();
        emailBuilder.append("<b style=\"color:blue;text-decoration:underline\">【简历邮箱】：").append("cv_").append(moseekerPosition.getId()).append(positionEmail).append("</b>");
        emailBuilder.append("<b style=\"color:red\">（手动发布该职位时，请一定将该邮箱填写在简历回收邮箱中）</b>").append(divider);
        return emailBuilder.toString();
    }

    public String describe(JobPositionDO moseekerPosition){
        StringBuffer describe = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(moseekerPosition.getAccountabilities())) {
            describe.append(moseekerPosition.getAccountabilities());
        }
        if (StringUtils.isNotNullOrEmpty(moseekerPosition.getRequirement())) {
            if (!moseekerPosition.getRequirement().contains("职位要求")) {
                describe.append("\n职位要求：\n" + moseekerPosition.getRequirement());
            } else {
                describe.append(moseekerPosition.getRequirement());
            }
        }

        return describe.toString();
    }

    protected static class EmailBodyBuilder{
        private StringBuilder builder=new StringBuilder();

        public EmailBodyBuilder() {

        }

        public EmailBodyBuilder name(String name){
            builder.append(name);
            return this;
        }

        public EmailBodyBuilder value(String value){
            builder.append(value).append(divider);
            return this;
        }

        public EmailBodyBuilder value(int value){
            builder.append(value).append(divider);
            return this;
        }

        public EmailBodyBuilder line(String line){
            builder.append(line).append(divider);
            return this;
        }

        public EmailBodyBuilder lineWithDivider(String line){
            builder.append(line);
            return this;
        }

        @Override
        public String toString() {
            return builder.toString();
        }
    }
}
