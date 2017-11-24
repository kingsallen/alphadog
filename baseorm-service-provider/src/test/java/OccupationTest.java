import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import org.jooq.util.derby.sys.Sys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class OccupationTest {

    @Autowired
    DictLiepinOccupationDao occupationDao;

    @Test
    public void test(){
        long start=System.currentTimeMillis();
        System.out.println(System.currentTimeMillis()-start);
        start=System.currentTimeMillis();
        Query query=new Query.QueryBuilder().where(new Condition("code",0, ValueOp.NEQ)).buildQuery();
        List<DictLiepinOccupationDO> list=occupationDao.getDatas(query);

        List<JSONObject> occupations=list.stream().map(o->{
            JSONObject obj=new JSONObject();
            /*for(DictLiepinOccupationDO._Fields f:DictLiepinOccupationDO._Fields.values()){
                obj.put(f.getFieldName(),o.getFieldValue(f));
            }*/
            obj.put("code", o.getCode());
            obj.put("parent_id", o.getParentId());
            obj.put("name", o.getName());
            obj.put("code_other", o.getOtherCode());
            obj.put("level", o.getLevel());
            obj.put("status", o.getStatus());
            return obj;
        }).collect(Collectors.toList());

//        List<Integer> parentIDs=list.stream().filter(o->o.getParentId()!=0).map(o->o.getParentId()).distinct().collect(Collectors.toList());

        String parentIdName="parent_id";

        Map<Integer,List<JSONObject>> map=new HashMap<>();
//        Map<Integer,List<JSONObject>> map=parentIDs.stream().collect(Collectors.toMap(id->id,id->occupations.stream().filter(o2->id.equals(o2.getIntValue(parentIdName))).collect(Collectors.toList())));

        for(JSONObject o:occupations){
            int parentId=o.getIntValue(parentIdName);
            if(parentId!=0){
                List<JSONObject> val=null;
                if(!map.containsKey(parentId)){
                    val=new ArrayList<>();
                    map.put(parentId,val);
                }else{
                    val=map.get(parentId);
                }
                val.add(o);
            }
        }

        JSONArray result=new JSONArray();
        occupations.stream().filter(o->o.getIntValue(parentIdName)==0).forEach(o->{
            o.put("parent_id_code",0);
            result.add(createChildren(o,map));
        });

        System.out.println(result.toJSONString());
        System.out.println(System.currentTimeMillis()-start);
    }

    /**
     * 递归查找子职能
     * @param obj 需要查找的职位
     * @param map key为parentID,val为"parentID为key的职位"
     * @return
     */

    public JSONObject createChildren(JSONObject obj,Map<Integer,List<JSONObject>> map){
        Integer fatherCode=obj.getIntValue("code");

        if(map.containsKey(fatherCode)){
            List<JSONObject> sonList=map.remove(fatherCode);

            for(JSONObject son:sonList){
                createChildren(son,map);
            }
            sonList.forEach(o->o.put("parent_id_code",obj.getString("code_other")));
            obj.put("children",sonList);
        }

        return obj;
    }


}
