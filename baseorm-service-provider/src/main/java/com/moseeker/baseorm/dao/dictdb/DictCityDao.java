package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.CityPojo;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.apache.thrift.TException;
import org.jooq.Condition;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author xxx
 *         DictCityDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class DictCityDao extends JooqCrudImpl<DictCityDO, DictCityRecord> {

    public DictCityDao() {
        super(DictCity.DICT_CITY, DictCityDO.class);
    }

    public DictCityDao(TableImpl<DictCityRecord> table, Class<DictCityDO> dictCityDOClass) {
        super(table, dictCityDOClass);
    }

    public List<DictCityDO> getCitys(Collection<Integer> cityCodes) {
        if (cityCodes == null || cityCodes.size() == 0) {
            return new ArrayList<>();
        }
        Query query = new Query.QueryBuilder().where(new com.moseeker.common.util.query.Condition(DictCity.DICT_CITY.CODE.getName(), cityCodes, ValueOp.IN)).buildQuery();
        return getDatas(query);
    }

    public List<CityPojo> getCities(int level) {
        Condition cond = null;
        if (level > 0) { // all
            cond = DictCity.DICT_CITY.LEVEL.equal((byte) level);
        }
        List<CityPojo> cities = create.select().from(table).where(cond).fetchInto(CityPojo.class);
        return cities;
    }

    public List<CityPojo> getCitiesByLevelUsingHot(String level, int is_using, int hot_city) {
        Condition cond = null;
        if(StringUtils.isNotNullOrEmpty(level)){
            String[] levels = level.split(",");
            List<Byte> levelList = new ArrayList<>();
            for(String str : levels){
                levelList.add(Byte.valueOf(str));
            }
            cond  = DictCity.DICT_CITY.LEVEL.in(levelList);
        }
        if (is_using >= 0) { // all
            if(cond != null) {
                cond = cond.and(DictCity.DICT_CITY.IS_USING.eq((byte) is_using));
            }else{
                cond = DictCity.DICT_CITY.IS_USING.eq((byte) is_using);
            }
        }
        if (hot_city >= 0) {
            if(cond != null) {
                cond = cond.and(DictCity.DICT_CITY.HOT_CITY.eq((byte) hot_city));
            }else{
                cond = (DictCity.DICT_CITY.HOT_CITY.eq((byte) hot_city));
            }
        }
        List<CityPojo> cities = create.select().from(table).where(cond).orderBy(DictCity.DICT_CITY.LEVEL.asc()).fetchInto(CityPojo.class);
        return cities;
    }


    public List<CityPojo> getCitiesById(int id) {
        int provinceCode = id / 1000 * 1000;
        Condition cond = DictCity.DICT_CITY.CODE.ge((int) (provinceCode)).and(DictCity.DICT_CITY.CODE.lt((int) (provinceCode + 1000)));
        // cond = DictCity.DICT_CITY.CODE.between((int)(provinceCode), (int)(provinceCode+1000));
        // TODO: investigate why between not working
        List<CityPojo> cities = create.select().from(table).where(cond).fetchInto(CityPojo.class);
        return cities;
    }

    public List<DictCityRecord> getCitiesByCodes(List<Integer> cityCodes) {

        List<DictCityRecord> records = new ArrayList<>();
        SelectWhereStep<DictCityRecord> select = create.selectFrom(DictCity.DICT_CITY);
        SelectConditionStep<DictCityRecord> selectCondition = null;
        for (int i = 0; i < cityCodes.size(); i++) {
            if (i == 0) {
                selectCondition = select.where(DictCity.DICT_CITY.CODE.equal((int) (cityCodes.get(i))));
            } else {
                selectCondition.or(DictCity.DICT_CITY.CODE.equal((int) (cityCodes.get(i))));
            }
        }
        if (selectCondition != null) {
            records = selectCondition.fetch();
        }
        return records;
    }

    public List<com.moseeker.baseorm.db.dictdb.tables.pojos.DictCity> getDictCitiesByCodes(List<Integer> cityCodes) {

        return create.selectFrom(DictCity.DICT_CITY)
                .where(DictCity.DICT_CITY.CODE.in(cityCodes))
                .fetchInto(com.moseeker.baseorm.db.dictdb.tables.pojos.DictCity.class);
    }

    public DictCityRecord getCityByCode(int city_code) {
        DictCityRecord record = null;
        Result<DictCityRecord> result = create.selectFrom(DictCity.DICT_CITY)
                .where(DictCity.DICT_CITY.CODE.equal((int) (city_code)))
                .limit(1).fetch();
        if (result != null && result.size() > 0) {
            record = result.get(0);
        }
        return record;
    }

    public DictCityRecord getCityByName(String city_name) {
        DictCityRecord record = null;
        Result<DictCityRecord> result = create.selectFrom(DictCity.DICT_CITY)
                .where(DictCity.DICT_CITY.NAME.equal(city_name))
                .limit(1).fetch();
        if (result != null && result.size() > 0) {
            record = result.get(0);
        }
        return record;
    }

    public DictCityDO getCityByNameOrEname(String city_name) {
        return create
                .selectFrom(DictCity.DICT_CITY)
                .where(DictCity.DICT_CITY.NAME.equal(city_name))
                .or(DictCity.DICT_CITY.ENAME.equal(city_name))
                .limit(1).fetchOneInto(DictCityDO.class);
    }

    public DictCityDO getCityDOByCode(int city_code) {
        Query query=new Query.QueryBuilder().where(DictCity.DICT_CITY.CODE.getName(),city_code).buildQuery();
        return getData(query);
    }


    /**
     * 获取完整的城市级别
     * 例:徐家汇 -> 上海，徐家汇
     * 具体逻辑：
     * 例如：栾城区（130111）- 石家庄（130100） - 河北省（130000）
     * 城市传入的的是130111，先计算出130111、130110、130100、130000、100000
     * 在城市表中找到这些code存在的对应城市list
     * 处理5开头的由于重庆市
     * 返回list
     * @param city
     * @return
     */
    public List<DictCityDO> getMoseekerLevels(DictCityDO city) {
        return getMoseekerLevels(city,null);
    }
    public List<DictCityDO> getMoseekerLevels(DictCityDO city,Map<Integer,DictCityDO> allCity) {

        if (city == null || city.getCode() == 0) {
            return new ArrayList<>();
        }

        if(allCity!=null){
            city=allCity.get(city.getCode());
        }else{
            city=getCityDOByCode(city.getCode());
        }

        if(city == null){
            return new ArrayList<>(Arrays.asList(city));
        }

        Set<Integer> allCodes = calcCityChain(city);
        List<DictCityDO> allCities = getCityChain(allCodes,allCity);
        if (allCities == null || allCities.size() == 0) {
            return new ArrayList<>();
        }

        removeInvalidCity(allCities);
        removeEQLevelCity(allCities,city);

        return allCities;
    }

    /**
     * 去除同级城市
     * @param allCities
     * @param city
     */
    public void removeEQLevelCity(List<DictCityDO> allCities,DictCityDO city){
        Iterator<DictCityDO> cityDOIterator = allCities.iterator();
        while (cityDOIterator.hasNext()) {
            DictCityDO cityD = cityDOIterator.next();
            if (cityD.getLevel() == 0) {
                cityDOIterator.remove();
            } else if (cityD.getLevel()==city.getLevel() && cityD.getCode()!=city.getCode()) {
                cityDOIterator.remove();
            }
        }
    }

    /**
     * 去除不符合规范城市
     * @param allCities
     */
    public void removeInvalidCity(List<DictCityDO> allCities){
        Iterator<DictCityDO> cityDOIterator = allCities.iterator();
        //5开头的由于重庆市的code不符合规范特殊处理
        if (allCities.get(allCities.size() - 1).getCode() >= 500000 && allCities.get(allCities.size() - 1).getCode() < 510000) {
            //去掉不属于重庆的
            while (cityDOIterator.hasNext()) {
                DictCityDO cityD = cityDOIterator.next();
                if (cityD.getCode() >= 510000) {
                    cityDOIterator.remove();
                }
            }
        } else if (allCities.get(allCities.size() - 1).getCode() >= 510000 && allCities.get(allCities.size() - 1).getCode() < 600000) {
            //去掉属于重庆的
            while (cityDOIterator.hasNext()) {
                DictCityDO cityD = cityDOIterator.next();
                if (cityD.getCode() < 510000) {
                    cityDOIterator.remove();
                }
            }
        }
    }

    public List<DictCityDO> getCityChain(Set<Integer> allCodes, Map<Integer,DictCityDO> allCity){
        List<DictCityDO> result=null;
        if(allCity==null){
            Query query = new Query.QueryBuilder()
                    .where(new com.moseeker.common.util.query.Condition(DictCity.DICT_CITY.CODE.getName(), allCodes, ValueOp.IN))
                    .orderBy(DictCity.DICT_CITY.CODE.getName(), Order.ASC)
                    .buildQuery();

            result= getDatas(query);

        }else{
            result=new ArrayList<>();

            TreeSet<Integer> sortAllCodes=new TreeSet<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1-o2;
                }
            });
            sortAllCodes.addAll(allCodes);

            Iterator<Integer> it=sortAllCodes.iterator();
            while (it.hasNext()){
                Integer key=it.next();
                if(allCity.containsKey(key)){
                    result.add(allCity.get(key));
                }
            }
        }

        return result;
    }

    public Set<Integer> calcCityChain(DictCityDO city){
        int divide = 10;

        Set<Integer> allCodes = new HashSet<>();
        allCodes.add(city.getCode());
        while (city.getCode() / divide > 0) {
            allCodes.add((city.getCode() / divide) * divide);
            divide *= 10;
        }
        return allCodes;
    }

    public List<List<DictCityDO>> getFullCity(List<DictCityDO> citys) {
        List<List<DictCityDO>> fullCitys = new ArrayList<>();
        for (DictCityDO city : citys) {
            fullCitys.add(getMoseekerLevels(city));
        }

        return fullCitys;
    }

    public List<DictCityDO> getFullCity() {
        return create.selectFrom(DictCity.DICT_CITY).fetchInto(DictCityDO.class);
    }

    /**
     * 根据城市名称查找城市常量信息
     * @param cityNameList 城市名称
     * @return 城市数据
     */
    public List<DictCityDO> getByNameList(List<String> cityNameList) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder
                .where(new com.moseeker.common.util.query.Condition(
                        DictCity.DICT_CITY.NAME.getName(), cityNameList, ValueOp.IN))
                .or(new com.moseeker.common.util.query.Condition(
                        DictCity.DICT_CITY.ENAME.getName(), cityNameList, ValueOp.IN));
        return getDatas(queryBuilder.buildQuery());
    }

    /*
     处理现居住地城市查询

     需要整改，放在这个位置不对，但是为了简单，没办法
     */

    public String handlerProvinceCity(String cityCode) throws TException {
        if(StringUtils.isNotNullOrEmpty(cityCode)){
            List<Integer> codeList= this.stringConvertIntList(cityCode);
            String codes=this.getCityByprovince(codeList);
            return codes;
        }
        return null;
    }
    //将xx,xx,xx格式的字符串转化为list
    public List<Integer> stringConvertIntList(String keyWords) {
        if (org.apache.commons.lang.StringUtils.isNotEmpty(keyWords)) {
            String[] array = keyWords.split(",");
            List<Integer> list = new ArrayList<Integer>();
            for (String ss : array) {
                list.add(Integer.parseInt(ss));
            }
            return list;
        }
        return null;
    }

    private String getCityByprovince(List<Integer> codeList) throws TException {
        List<Integer> outCodeList=getOutCityCodeList();
        List<Integer> provinceCode=new ArrayList<>();
        for(Integer code:codeList){
            if(!outCodeList.contains(code)&&code%10000==0){
                provinceCode.add(code);
            }
        }
        if(!StringUtils.isEmptyList(provinceCode)){
            List<Integer> provinceCityCode=new ArrayList<>();
            List<Map<String,Object>> list=getCityCodeByProvine(provinceCode);
            if(!StringUtils.isEmptyList(list)){
                for(Map<String,Object> data:list){
                    int cityCode= (int) data.get("code");
                    provinceCityCode.add(cityCode);
                }

            }
            if(!StringUtils.isEmptyList(provinceCityCode)){
                codeList.addAll(provinceCityCode);
                for(Integer code:provinceCode){
                    codeList.remove(code);
                }

            }
        }
        String codes="";
        if(!StringUtils.isEmptyList(codeList)){
            for(Integer code:codeList){
                codes+=code+",";
            }
            if(StringUtils.isNotNullOrEmpty(codes)){
                codes=codes.substring(0,codes.lastIndexOf(","));
            }
        }

        return codes;
    }
    /*
     获取省份下城市数据
     需要整改，放在这个位置不对，但是为了简单，没办法
     */
    @CounterIface
    public List<Map<String,Object>> getCityCodeByProvine(List<Integer> codes) throws CommonException {
        List<Map<String,Object>> result=new ArrayList<>();
        List<Integer> codeList=this.getOutCityCodeList();
        if(!this.isProvinceComfortable(codes,codeList)){
            throw new CommonException(1,"该省份下城市code不可查");
        }
        if(!this.isProvice(codes)){
            throw new CommonException(1,"该code不是省份code");
        }
        Query query=new Query.QueryBuilder().where("is_using",1).and(new com.moseeker.common.util.query.Condition("level",1, ValueOp.GT)).buildQuery();
        List<Map<String,Object>> dataList=this.getMaps(query);
        if(!StringUtils.isEmptyList(dataList)){
            for(Map<String,Object> data:dataList){
                int codeItem= (int) data.get("code");
                for(Integer code:codes){
                    if(!codeList.contains(code)&&code<codeItem&&code+10000>codeItem){
                        result.add(data);
                    }
                }
            }
        }

        return result;
    }
    /*
     判断查询的是否是省份
     */
    private boolean isProvice(List<Integer> code){
        Query query=new Query.QueryBuilder().where("level",1).and(new com.moseeker.common.util.query.Condition("code",code.toArray(),ValueOp.IN)).buildQuery();
        int count=this.getCount(query);
        logger.info("count======"+count);
        logger.info("code.size======"+code.size());
        if(count>0&&count==code.size()){
            return true;
        }
        return false;
    }
    /*
    判断数据是否符合要求
     */
    private boolean isProvinceComfortable(List<Integer> codes, List<Integer> codeList){
        for(Integer code:codes){
            if(codeList.contains(code)){
                return false;
            }
        }
        return true;
    }

    private List<Integer> getOutCityCodeList(){
        List<Integer> codeList=new ArrayList<>();
        codeList.add(110000);
        codeList.add(111111);
        codeList.add(120000);
        codeList.add(233333);
        codeList.add(310000);
        codeList.add(500000);
        codeList.add(810000);
        codeList.add(820000);
        return codeList;
    }


}
