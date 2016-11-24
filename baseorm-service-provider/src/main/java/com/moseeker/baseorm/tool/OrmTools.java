package com.moseeker.baseorm.tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.thrift.TBase;
import org.jooq.impl.UpdatableRecordImpl;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.struct.DictOccupation;
/**
 * 
 * @author zztaiwll
 * @time 2016-11-17
 * function:对一些常用的orm方法进行封装
 */
public class OrmTools {
	/*
	 * 按照内部是三层嵌套的方式的list集合的response
	 */
	public  static Response getAll(BaseDaoImpl<?,?> dao){
		List<DictOccupation> result=new ArrayList<DictOccupation>();
		try {
			CommonQuery query=new CommonQuery();
			query.setPer_page(Integer.MAX_VALUE);
			List<DictOccupation> allData = new ArrayList<>();
			List<? extends UpdatableRecordImpl<?>> list = dao.getResources(query);
			if(list != null && list.size() > 0) {
				list.forEach(r -> {
					DictOccupation occu = (DictOccupation) BeanUtils.DBToStruct(DictOccupation.class, r);
					allData.add(occu);
				});
			}
			if(allData.size() > 0) {
				result = arrangeOccupation(allData);
			}
			return ResponseUtils.success(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		
		/*CommonQuery query=new CommonQuery();
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("level", "1");
		query.setEqualFilter(map);
		query.setPer_page(100);
		try{
			
			List<? extends UpdatableRecordImpl<?>> list=dao.getResources(query);
			List<DictOccupation> result=new ArrayList<DictOccupation>();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					DictOccupation occu=(DictOccupation) BeanUtils.DBToStruct(DictOccupation.class,list.get(i) );
					int id=occu.getCode();
					CommonQuery query1=new CommonQuery();
					HashMap<String,String> map1=new HashMap<String,String>();
					map1.put("parent_id", id+"");
					query1.setEqualFilter(map1);
					query1.setPer_page(100);
					List<? extends UpdatableRecordImpl<?>> list1=dao.getResources(query1);
					List<DictOccupation> result1=new ArrayList<DictOccupation>();
					if(list1!=null&&list1.size()>0){
						for(int j=0;j<list1.size();j++){
							DictOccupation occ1=(DictOccupation) BeanUtils.DBToStruct(DictOccupation.class,list1.get(j) );
							int id1=occ1.getCode();
							CommonQuery query2=new CommonQuery();
							HashMap<String,String> map2=new HashMap<String,String>();
							map2.put("parent_id", id1+"");
							query2.setEqualFilter(map2);
							query2.setPer_page(100);
							List<? extends UpdatableRecordImpl<?>> list2=dao.getResources(query2);
							List<DictOccupation> result2=new ArrayList<DictOccupation>();
							if(list2!=null&&list2.size()>0){
								for(int z=0;z<list2.size();z++){
									DictOccupation occ2=(DictOccupation) BeanUtils.DBToStruct(DictOccupation.class,list2.get(z) );
									occ2.setChildren(null);
									result2.add(occ2);
								}
								occ1.setChildren(result2);
								result1.add(occ1);
							}
						}
					}
					occu.setChildren(result1);
					result.add(occu);
				}
			}
			return ResponseUtils.success(result);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}*/
	}
	private static List<DictOccupation> arrangeOccupation(List<DictOccupation> allData) {
		List<DictOccupation> result = arrangeFirstLevel(allData);
		arrangeSecondLevel(result, allData);
		return result;
	}
	private static void arrangeSecondLevel(List<DictOccupation> results, List<DictOccupation> allData) {
		results.forEach(result -> {
			Iterator<DictOccupation> id = allData.iterator();
			while(id.hasNext()) {
				DictOccupation d = id.next();
				if(d.getParent_id() == result.getCode()) {
					if(result.getChildren() == null) {
						List<DictOccupation> children = new ArrayList<>();
						result.setChildren(children);
					}
					result.getChildren().add(d);
					id.remove();
				}
			}
		});
		
		results.forEach(result -> {
			if(result.getChildren() != null && result.getChildren().size() > 0) {
				result.getChildren().forEach(r -> {
					Iterator<DictOccupation> id = allData.iterator();
					while(id.hasNext()) {
						DictOccupation d = id.next();
						if(d.getParent_id() == r.getCode()) {
							if(r.getChildren() == null) {
								List<DictOccupation> children = new ArrayList<>();
								r.setChildren(children);
							}
							r.getChildren().add(d);
							id.remove();
						}
					}
				});
			}
		});
	}
	private static List<DictOccupation> arrangeFirstLevel(List<DictOccupation> allData) {
		List<DictOccupation> result = new ArrayList<>();
		Iterator<DictOccupation> id = allData.iterator();
		while(id.hasNext()) {
			DictOccupation d = id.next();
			if(d.getParent_id() == 0) {
				result.add(d);
				id.remove();
			}
		}
		return result;
	}
	/*
	 * 返回内部是单层的occupation集合的response，因为有child元素，所以没用公共方法，只能特殊处理
	 */
	public static Response getSingle_layerOccupation(BaseDaoImpl<?,?> dao,CommonQuery query){
		try{
			List<? extends UpdatableRecordImpl<?>> list=dao.getResources(query);
			List<DictOccupation> result=new ArrayList<DictOccupation>();
			if(list!=null&&list.size()>0){
				for(int z=0;z<list.size();z++){
					DictOccupation occ2=(DictOccupation) BeanUtils.DBToStruct(DictOccupation.class,list.get(z) );
					occ2.setChildren(null);
					result.add(occ2);
				}
			}
			
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	/*
	 * 按照内部是数据的list集合的方式返回response
	 */
	public static Response getList(BaseDaoImpl<?,?> dao,CommonQuery query,TBase bean){
		try{
			List<? extends UpdatableRecordImpl<?>> list=dao.getResources(query);
			List<TBase> result=new ArrayList<TBase>();
			if(list!=null&&list.size()>0){
				for(int z=0;z<list.size();z++){
					bean= BeanUtils.DBToStruct(bean.getClass(),list.get(z) );
					result.add(bean);
				}
			}
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
}
