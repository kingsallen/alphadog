package com.moseeker.dict;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictZpinOccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.dict.service.impl.DictOccupationService;
import com.moseeker.dict.service.impl.occupation.LiepinOccupationHandler;
import com.moseeker.dict.service.impl.occupation.VeryEastOccupationHandler;
import com.moseeker.dict.service.impl.occupation.ZhilianOccupationHandler;
import com.moseeker.dict.service.impl.occupation.Job51OccupationHandler;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TestService {
    Map<Class,AbstractOccupationHandler> map;

    @Autowired
    DictOccupationService service;



    @Autowired
    public TestService(List<AbstractOccupationHandler> list){
        map=list.stream().collect(Collectors.toMap(h->h.getClass(), h->h));
    }

    public void test() throws BIZException {
        OccupationChannel channel= OccupationChannel.Job51;
        Param param=new Param();
        /*param.channel=channel.code;
        param.code=110000;
        param.level=1;
        param.parent_id=1;
        param.single_layer=1;

        print(map.get(channel.clazz).getSingle(JSON.parseObject(JSON.toJSONString(param))));

        param.channel=OccupationChannel.LiePin.code;
        param.parent_id=0;
        print(map.get(channel.clazz).getSingle(JSON.parseObject(JSON.toJSONString(param))));

        param.channel=OccupationChannel.ZhiLian.code;
        param.code=1;
        print(map.get(channel.clazz).getSingle(JSON.parseObject(JSON.toJSONString(param))));*/

        param.channel=OccupationChannel.VeryEast.code;

        print(map.get(channel.clazz).getAll());

        /*print(service.queryOccupation());
        print(ResponseUtils.success(map.get(channel.clazz).handle())));
        print("==================");
        channel= OccupationChannel.LiePin;
        print(service.queryOccupation());
        print(ResponseUtils.success(map.get(channel.clazz).handle())));
        print("==================");
        channel= OccupationChannel.ZhiLian;
        print(service.queryOccupation());
        print(ResponseUtils.success(map.get(channel.clazz).handle())));*/

    }

    public void print(Object str){
        System.out.println(JSON.toJSONString(str));
    }


    private enum OccupationChannel{
        Job51(ChannelType.JOB51.getValue(),"51JobList", Job51OccupationHandler.class),
        ZhiLian(ChannelType.ZHILIAN.getValue(),"zPinList",ZhilianOccupationHandler.class),
        LiePin(ChannelType.LIEPIN.getValue(),"liePinList",LiepinOccupationHandler.class),
        VeryEast(ChannelType.VERYEAST.getValue(),"veryEastList",VeryEastOccupationHandler.class);

        private OccupationChannel(int code,String key,Class clazz){
            this.code=code;
            this.key=key;
            this.clazz=clazz;
        }

        private int code;
        private String key;
        private Class clazz;

        public String key(){
            return key;
        }

        public static OccupationChannel getInstance(int code){
            for(OccupationChannel oc:values()){
                if(oc.code==code){
                    return oc;
                }
            }
            return null;
        }
    }
}

class Param{
    public int channel;
    public int single_layer;
    public int level;
    public int parent_id;
    public int code;
}