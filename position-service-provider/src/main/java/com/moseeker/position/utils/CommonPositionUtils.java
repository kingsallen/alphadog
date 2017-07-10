package com.moseeker.position.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
@Service
public class CommonPositionUtils {
	@Autowired
	private JobPositionCityDao jobPositionCityDao;
	@Autowired
	private DictCityDao dictCityDao;

	 //根据positionid列表获取list的jobPositioncity
	 public List<JobPositionCityDO> getJObPositionCity(List<Integer> positionIds){
		 if(StringUtils.isEmptyList(positionIds)){
			 return null;
		 }
		 Query query=new Query.QueryBuilder().where(new Condition("pid",positionIds.toArray(),ValueOp.IN)).buildQuery();
		 return jobPositionCityDao.getDatas(query);
	 }
	 //获取职位的城市格式是map形式，（positionId，List<String>)形式
	 public Map<Integer,List<String>> handlePositionCity(List<Integer> list){
		 if(StringUtils.isEmptyList(list)){
			 return null;
		 }
		 List<JobPositionCityDO>jobPositionCityList=this.getJObPositionCity(list);
		 if(StringUtils.isEmptyList(jobPositionCityList)){
			 return null;
		 }
		 List<Integer> codes=this.getPositionCodes(jobPositionCityList);
		 List<DictCityDO> citys=getDictCity(codes);
		 if(StringUtils.isEmptyList(citys)){
			 return null;
		 }
		 Map<Integer,List<String>> map=new HashMap<Integer,List<String>>();
		 for(JobPositionCityDO jobDo:jobPositionCityList){
			 int positionId=jobDo.getPid();
			 int code=jobDo.getCode();
			 List<String> positionCity=new ArrayList<String>();
			 if(map.get(positionId)!=null){
				 positionCity=map.get(positionId+"");
			 }			 
			 for(DictCityDO city:citys){
				 int code1=city.getCode();
				 String name=city.getName();
				 if(code==code1){
					 positionCity.add(name);
				 }
			 }
			 if(!StringUtils.isEmptyList(positionCity)){
				 map.put(positionId,positionCity);
			 }
		 }
		 return map;
	 }
	 //获取position的city code
	 public List<Integer> getPositionCodes(List<JobPositionCityDO> list){
		 if(StringUtils.isEmptyList(list)){
			 return null;
		 }
		 List<Integer> codes=new ArrayList<Integer>();
		 for(JobPositionCityDO jobCity:list){
			 codes.add(jobCity.getCode());
		 }
		 return codes;
	 }
	 //获取list的dictcity数据
	 public List<DictCityDO> getDictCity(List<Integer> codes){
		 if(StringUtils.isEmptyList(codes)){
			 return null;
		 }
		 Query query=new Query.QueryBuilder().where(new Condition("code",codes.toArray(),ValueOp.IN)).buildQuery();
		 return dictCityDao.getDatas(query);
	 }

	 /*
     * 处理城市数据
     */
    public String handlerCity(int positionId){
      String citys="";
      List<JobPositionCityDO> jobCityList=this.getJobPositionCityList(positionId);
      List<Integer> codeList=this.getCodeIdlist(jobCityList);
      List<DictCityDO> dictList=this.getDictCityList(codeList);
      if(!StringUtils.isEmptyList(dictList)){
        for(DictCityDO dict:dictList){
          String name=dict.getName();
          citys+=name+",";
        }
      }
      if(StringUtils.isNotNullOrEmpty(citys)){
        citys=citys.substring(0, citys.lastIndexOf(","));
      }
      return citys;
    }
    
    /*
     * 获取job_position_city的数据
     */
    public List<JobPositionCityDO> getJobPositionCityList(int positionId){
      Query query=new Query.QueryBuilder().where("pid",positionId).buildQuery();
      List<JobPositionCityDO> list=jobPositionCityDao.getDatas(query);
      return list;
    }
    /*
     * 获取code的list
     */
    public List<Integer> getCodeIdlist(List<JobPositionCityDO> list){
      if(StringUtils.isEmptyList(list)){
        return null;
      }
      List<Integer> result=new ArrayList<Integer>();
      for(JobPositionCityDO DO:list){
        result.add(DO.getCode());
      }
      return result;
    }
    /*
     * 获取dictcity的数据
     */
    public List<DictCityDO> getDictCityList(List<Integer> codes){
      if(StringUtils.isEmptyList(codes)){
        return null;
      }
      Query query=new Query.QueryBuilder().where(new Condition("code",codes.toArray(),ValueOp.IN)).buildQuery();
      List<DictCityDO> list=dictCityDao.getDatas(query);
      return list;
    }
}
