package com.moseeker.position.service.position.base.config;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.iface.ISyncRequestType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.constants.CheckStrategy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTransferCheckConfig implements ISyncRequestType{

    public abstract Map<ChannelType,Map<String,Map<CheckStrategy,String>>> getStrategy();

    protected ConfigBuilder buildStrategy(ChannelType channelType){
        ConfigBuilder configBuilder=new ConfigBuilder();
        configBuilder.channelType=channelType;
        return configBuilder;
    }

    /**
     * 构建器
     */
    public class ConfigBuilder{
        private ChannelType channelType;
        private Map<String,Map<CheckStrategy,String>> channelStrategy=new ConcurrentHashMap<>();

        private ConfigBuilder(){

        }

        public ConfigBuilder channelType(ChannelType channelType){
            this.channelType=channelType;
            return this;
        }

        public FieldBuilder field(String field){
            if(StringUtils.isNullOrEmpty(field)){
                throw new IllegalArgumentException("field must not be null");
            }
            return new FieldBuilder().field(field);
        }

        public Map<ChannelType,Map<String,Map<CheckStrategy,String>>> add(){
            if(channelType==null){
                throw new IllegalArgumentException("has not set channelType");
            }
            return new HashMap<ChannelType,Map<String,Map<CheckStrategy,String>>>(){
                {
                    put(channelType,channelStrategy);
                }
            };
        }

        public class FieldBuilder{
            private String field;
            private Map<CheckStrategy,String> fieldStrategy=new ConcurrentHashMap<>();

            private FieldBuilder field(String field){
                this.field=field;
                return this;
            }

            public FieldBuilder strategy(CheckStrategy checkStrategy){
                return strategy(checkStrategy,"");
            }

            public FieldBuilder strategy(CheckStrategy checkStrategy,String val ){
                fieldStrategy.put(checkStrategy,val);
                return this;
            }

            public ConfigBuilder finish(){
                ConfigBuilder.this.channelStrategy.put(field,fieldStrategy);
                return ConfigBuilder.this;
            }
        }
    }

}
