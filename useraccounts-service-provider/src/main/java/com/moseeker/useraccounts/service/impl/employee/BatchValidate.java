package com.moseeker.useraccounts.service.impl.employee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.dao.employeedb.EmployeeCustomOptionJooqDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeCustomFieldsDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.employeedb.tables.pojos.EmployeeOptionValue;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.useraccounts.struct.ImportErrorUserEmployee;
import com.moseeker.thrift.gen.useraccounts.struct.ImportUserEmployeeStatistic;
import com.moseeker.useraccounts.exception.UserAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @Author jack
 * @Date 2019/6/14 1:50 PM
 * @Version 1.0
 */
@Component
public class BatchValidate {

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    protected HrEmployeeCustomFieldsDao customFieldsDao;

    @Autowired
    protected EmployeeCustomOptionJooqDao customOptionJooqDao;

    ThreadPool threadPool = ThreadPool.Instance;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将员工自定义字段json字符串解析成List<Map<String,String>>
     * @param customFieldValueStr 自定义字段json字符串
     * @return list
     */
    public List<Map<String, String>> parseCustomFieldValues(String customFieldValueStr) {
        logger.info("BatchValidate parseCustomFieldValues");
        if (org.apache.commons.lang.StringUtils.isNotBlank(customFieldValueStr) && !customFieldValueStr.equals("[]")) {
            logger.info("BatchValidate parseCustomFieldValues customFieldValues not null");

            List customFieldValues = JSONObject.parseObject(customFieldValueStr, List.class);
            logger.info("BatchValidate parseCustomFieldValues customFieldValues:{}", customFieldValues);
            if (customFieldValues != null && customFieldValues.size() > 0) {
                List<Map<String, String>> jsonArray = new ArrayList<>(customFieldValues.size());
                for (Object customFieldValue : customFieldValues) {
                    logger.info("BatchValidate parseCustomFieldValues customFieldValue:{}", JSONObject.toJSONString(customFieldValue));
                    Map<String, String> jsonObject = new HashMap<>();

                    JSONObject customFieldJSONObject = (JSONObject)customFieldValue;
                    customFieldJSONObject.forEach((key, value) -> {
                        logger.info("BatchValidate parseCustomFieldValues key:{}, vlaue:{}", key, value);
                        if (value instanceof JSONArray) {
                            String valueStr;
                            if (((JSONArray)value).get(0) instanceof String) {
                                valueStr = (String) ((JSONArray)value).get(0);
                                jsonObject.put(key, valueStr);
                            } else if(((JSONArray)value).get(0) instanceof Integer) {
                                valueStr = ((JSONArray)value).get(0).toString();
                                jsonObject.put(key, valueStr);
                            } else {
                                //do nothing
                            }
                        } else if (value instanceof JSONObject) {
                            ((JSONObject)value).forEach((keyKey, valueValue) -> {
                                String valueStr;
                                if (valueValue instanceof String) {
                                    valueStr = (String) valueValue;
                                    jsonObject.put(keyKey, valueStr);
                                } else if(valueValue instanceof Integer) {
                                    valueStr = valueValue.toString();
                                    jsonObject.put(keyKey, valueStr);
                                } else {
                                    //do nothing
                                }
                            });
                        } else {
                            String valueStr = BeanUtils.converToString(value);
                            if (valueStr != null) {
                                jsonObject.put(key, BeanUtils.converToString(value));
                            }
                        }
                    });
                    if (jsonObject.size() > 0) {
                        jsonArray.add(jsonObject);
                    }
                }

                return jsonArray;
            } else {
                return new ArrayList<>(0);
            }
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 查询是否有重复数据
     *
     * @param userEmployeeMap 参数
     * @param companyId 公司编号
     * @param dbEmployeeDOList 员工数据
     */
    public ImportUserEmployeeStatistic importCheck(Map<Integer, UserEmployeeDO> userEmployeeMap, Integer companyId,
                                                   List<UserEmployeeDO> dbEmployeeDOList) throws UserAccountException {
        ImportUserEmployeeStatistic importUserEmployeeStatistic = new ImportUserEmployeeStatistic();

        // 重复的对象
        List<ImportErrorUserEmployee> importErrorUserEmployees = new ArrayList<>();
        int repetitionCounts = 0;
        int errorCounts = 0;

        /**
         * 为校验自定义下拉项数据做准备
         */
        ArrayListMultimap<Integer, CustomOptionRel> employeeCustomFiledValues = employeeParam(userEmployeeMap);
        Map<Integer, List<EmployeeOptionValue>> dbCustomFieldValues = fetchOptionsValues(employeeCustomFiledValues, companyId);

        // 提交上的数据
        for (Map.Entry<Integer, UserEmployeeDO> entry : userEmployeeMap.entrySet()) {
            UserEmployeeDO userEmployeeDO = entry.getValue();
            ImportErrorUserEmployee importErrorUserEmployee = new ImportErrorUserEmployee();
            // 姓名不能为空
            if (StringUtils.isNullOrEmpty(userEmployeeDO.getCname())) {
                importErrorUserEmployee.setUserEmployeeDO(userEmployeeDO);
                importErrorUserEmployee.setMessage("cname不能为空");
                errorCounts = errorCounts + 1;
                importErrorUserEmployee.setRowNum(entry.getKey());
                importErrorUserEmployees.add(importErrorUserEmployee);
                continue;
            }
            if (userEmployeeDO.getCompanyId() == 0) {
                userEmployeeDO.setCompanyId(companyId);
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(userEmployeeDO.getCustomFieldValues())
                    && !userEmployeeDO.getCustomFieldValues().equals("[]")) {
                if (employeeCustomFiledValues.get(entry.getKey()) != null
                        && employeeCustomFiledValues.get(entry.getKey()).size() > 0) {
                    boolean flag = checkOptions(employeeCustomFiledValues.get(entry.getKey()), dbCustomFieldValues);
                    if (!flag) {
                        logger.info("BatchValidate importCheck employeeCustomFiledValues:{}", employeeCustomFiledValues.get(entry.getKey()));
                        logger.info("BatchValidate importCheck dbCustomFieldValues:{}", JSONObject.toJSONString(dbCustomFieldValues));
                        importErrorUserEmployee.setUserEmployeeDO(userEmployeeDO);
                        importErrorUserEmployee.setMessage("自定义选项错误");
                        errorCounts = errorCounts + 1;
                        importErrorUserEmployee.setRowNum(entry.getKey());
                        importErrorUserEmployees.add(importErrorUserEmployee);
                        continue;
                    } else {
                        JSONArray customFieldValues = convertOptionId(employeeCustomFiledValues.get(entry.getKey()), dbCustomFieldValues);
                        logger.info("BatchValidate importCheck success customFieldValues:{}", customFieldValues);
                        userEmployeeDO.setCustomFieldValues(customFieldValues.toJSONString());
                    }
                }
            }
            if (StringUtils.isNullOrEmpty(userEmployeeDO.getCustomField())) {
                continue;
            }
            if (!StringUtils.isEmptyList(dbEmployeeDOList)) {
                // 数据库的数据
                for (UserEmployeeDO dbUserEmployeeDO : dbEmployeeDOList) {
                    // 非自定义员工,忽略检查
                    if (StringUtils.isNullOrEmpty(dbUserEmployeeDO.getCustomField())
                            || StringUtils.isNullOrEmpty(dbUserEmployeeDO.getCname())) {
                        continue;
                    }
                    // 当提交的数据和数据库中的数据，cname和customField都相等时候，认为是重复数据
                    if (userEmployeeDO.getCname().equals(dbUserEmployeeDO.getCname())
                            && userEmployeeDO.getCustomField().equals(dbUserEmployeeDO.getCustomField())) {
                        repetitionCounts = repetitionCounts + 1;
                        importErrorUserEmployee.setUserEmployeeDO(userEmployeeDO);
                        importErrorUserEmployee.setRowNum(entry.getKey());
                        importErrorUserEmployee.setMessage("cname和customField和数据库的数据一致");
                        importErrorUserEmployees.add(importErrorUserEmployee);
                    }
                }
            }
        }
        importUserEmployeeStatistic.setTotalCounts(userEmployeeMap.size());
        importUserEmployeeStatistic.setErrorCounts(errorCounts);
        importUserEmployeeStatistic.setRepetitionCounts(repetitionCounts);
        importUserEmployeeStatistic.setUserEmployeeDO(importErrorUserEmployees);
        if (repetitionCounts == 0 && errorCounts == 0) {
            importUserEmployeeStatistic.setInsertAccept(true);
        } else {
            importUserEmployeeStatistic.setInsertAccept(false);
        }
        return importUserEmployeeStatistic;
    }

    /**
     * 查询是否有重复数据
     *
     * @param userEmployeeMap 参数
     * @param companyId 公司编号
     * @param dbEmployeeDOList 员工数据
     */
    public ImportUserEmployeeStatistic updateCheck(List<UserEmployeeDO> userEmployeeMap, Integer companyId,
                                                   List<UserEmployeeDO> dbEmployeeDOList) throws UserAccountException {
        ImportUserEmployeeStatistic importUserEmployeeStatistic = new ImportUserEmployeeStatistic();
        importUserEmployeeStatistic.setTotalCounts(userEmployeeMap.size());
        // 重复的数据
        List<ImportErrorUserEmployee> importErrorUserEmployees = new ArrayList<>();
        int repetitionCounts = 0;
        int errorCounts = 0;
        // 提交上的数据
        List<Integer> employeeIdList;
        if (dbEmployeeDOList != null && dbEmployeeDOList.size() > 0) {
            employeeIdList = dbEmployeeDOList.parallelStream()
                    .map(UserEmployeeDO::getId)
                    .collect(Collectors.toList());
        } else {
            employeeIdList = new ArrayList<>();
        }

        /**
         * 为校验自定义下拉项数据做准备
         */
        ArrayListMultimap<Integer, CustomOptionRel> employeeCustomFiledValues = employeeParam(userEmployeeMap);
        Map<Integer, List<EmployeeOptionValue>> dbCustomFieldValues = fetchOptionsValues(employeeCustomFiledValues, companyId);

        for (int i=0; i<userEmployeeMap.size(); i++) {
            UserEmployeeDO userEmployeeDO = userEmployeeMap.get(i);
            ImportErrorUserEmployee importErrorUserEmployee = new ImportErrorUserEmployee();
            if (userEmployeeDO.getId() <= 0) {
                importErrorUserEmployee.setUserEmployeeDO(userEmployeeDO);
                importErrorUserEmployee.setMessage("编号错误");
                errorCounts = errorCounts + 1;
                importErrorUserEmployee.setRowNum(i);
                importErrorUserEmployees.add(importErrorUserEmployee);
                continue;
            }
            if (!employeeIdList.contains(userEmployeeDO.getId())) {
                importErrorUserEmployee.setUserEmployeeDO(userEmployeeDO);
                importErrorUserEmployee.setMessage("数据不允许修改");
                errorCounts = errorCounts + 1;
                importErrorUserEmployee.setRowNum(i);
                importErrorUserEmployees.add(importErrorUserEmployee);
                continue;
            }
            if (userEmployeeDO.getActivation() != EmployeeActiveState.Actived.getState()
                    && userEmployeeDO.getActivation() != EmployeeActiveState.Cancel.getState()) {
                importErrorUserEmployee.setUserEmployeeDO(userEmployeeDO);
                importErrorUserEmployee.setMessage("只允许修改成取消认证的状态");
                errorCounts = errorCounts + 1;
                importErrorUserEmployee.setRowNum(i);
                importErrorUserEmployees.add(importErrorUserEmployee);
                continue;
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(userEmployeeDO.getCustomFieldValues())
                    && !userEmployeeDO.getCustomFieldValues().equals("[]")) {
                if (employeeCustomFiledValues.get(i) != null
                        && employeeCustomFiledValues.get(i).size() > 0) {
                    boolean flag = checkOptions(employeeCustomFiledValues.get(i), dbCustomFieldValues);
                    if (!flag) {
                        importErrorUserEmployee.setUserEmployeeDO(userEmployeeDO);
                        importErrorUserEmployee.setMessage("自定义选项错误");
                        errorCounts = errorCounts + 1;
                        importErrorUserEmployee.setRowNum(i);
                        importErrorUserEmployees.add(importErrorUserEmployee);
                        continue;
                    }
                }
            }
        }
        importUserEmployeeStatistic.setTotalCounts(userEmployeeMap.size());
        importUserEmployeeStatistic.setErrorCounts(errorCounts);
        importUserEmployeeStatistic.setRepetitionCounts(repetitionCounts);

        return importUserEmployeeStatistic;
    }


    /**
     * 将自定义字段解析成结构体
     * @param userEmployeeMap 文件中的员工数据
     * @return 自定义字段与行数的对应关系
     */
    public ArrayListMultimap<Integer, CustomOptionRel> employeeParam(Map<Integer, UserEmployeeDO> userEmployeeMap) {
        ArrayListMultimap<Integer, CustomOptionRel> map = ArrayListMultimap.create();

        userEmployeeMap.forEach((row, employee) -> {
            JSONArray array = JSONArray.parseArray(employee.getCustomFieldValues());
            packageRel(map, array, row);
        });
        return map;
    }

    /**
     * 将自定义字段解析成结构体
     * @param userEmployeeMap 文件中的员工数据
     * @return 自定义字段与行数的对应关系
     */
    public ArrayListMultimap<Integer, CustomOptionRel> employeeParam(List<UserEmployeeDO> userEmployeeMap) {
        ArrayListMultimap<Integer, CustomOptionRel> map = ArrayListMultimap.create();

        for (int j=0; j<userEmployeeMap.size(); j++) {
            UserEmployeeDO employee = userEmployeeMap.get(j);
            JSONArray array = JSONArray.parseArray(employee.getCustomFieldValues());
            packageRel(map, array, j);
        }
        return map;
    }

    /**
     * 校验单个员工的自定义配置是否正确
     * @param customFieldValues 自定义配置
     */
    public boolean validateCustomFieldValues(Map<Integer, String> customFieldValues, int companyId) {

        /**
         * 校验必填项
         */
        List<HrEmployeeCustomFields> customSupplyVOS = customFieldsDao.fetchRequiredByCompanyId(companyId);
        logger.info("BatchValidate validateCustomFieldValues customSupplyVOS:{}", JSONObject.toJSONString(customSupplyVOS));
        List<HrEmployeeCustomFields> notSupportList = customSupplyVOS
                .parallelStream()
                .filter(hrEmployeeCustomFields -> customFieldValues == null || !customFieldValues.containsKey(hrEmployeeCustomFields.getId()))
                .collect(Collectors.toList());
        logger.info("BatchValidate validateCustomFieldValues notSupportList:{}", JSONObject.toJSONString(notSupportList));
        if (notSupportList != null && notSupportList.size() > 0) {
            return false;
        }

        logger.info("BatchValidate validateCustomFieldValues customFieldValues:{}", JSONObject.toJSONString(customFieldValues));
        if (customFieldValues == null || customFieldValues.size() == 0) {
            return true;
        }

        /**
         * 校验下拉项选择
         */
        List<CustomOptionRel> rels = packageMapRel(customFieldValues);
        logger.info("BatchValidate validateCustomFieldValues rels:{}", JSONObject.toJSONString(rels));
        if (rels != null) {
            Set<Integer> customFieldIdList = rels
                    .parallelStream()
                    .map(CustomOptionRel::getCustomId)
                    .collect(Collectors.toSet());
            List<HrEmployeeCustomFields> fields = customFieldsDao.listSelectOptionByIdList(companyId, customFieldIdList);

            logger.info("BatchValidate validateCustomFieldValues fields:{}", JSONObject.toJSONString(fields));

            if (fields.size() > 0) {
                Map<Integer, Integer> params = new HashMap<>(fields.size());
                for (HrEmployeeCustomFields field : fields) {
                    try {
                        Integer optionId = Integer.valueOf(customFieldValues.get(field.getId()));
                        params.put(field.getId(), optionId);
                    } catch (NumberFormatException e) {
                        logger.info("BatchValidate validateCustomFieldValues NumberFormatException");
                        return false;
                    }

                }
                int count = customOptionJooqDao.countByCustomIdAndId(params);
                logger.info("BatchValidate validateCustomFieldValues count == fields.size():{}", count == fields.size());
                return count == fields.size();
            }
        }
        return false;
    }

    /**
     * 将提交的自定义信息中，如果有想下拉项，则改成id
     * @param rels 自定义信息
     * @param dbOptions 下拉项
     * @return map
     */
    private JSONArray convertOptionId(List<CustomOptionRel> rels, Map<Integer, List<EmployeeOptionValue>> dbOptions) {
        if (rels != null && rels.size() > 0) {
            JSONArray jsonArray = new JSONArray(rels.size());

            rels.forEach(customOptionRel -> {
                List<EmployeeOptionValue> list = dbOptions.get(customOptionRel.getCustomId());
                if (list != null && list.size() > 0) {

                    Optional<EmployeeOptionValue> optionValue = list.parallelStream()
                            .filter(employeeOptionValue -> employeeOptionValue.getName().equals(customOptionRel.getOption()))
                            .findAny();
                    if (optionValue.isPresent()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(String.valueOf(customOptionRel.getCustomId()), String.valueOf(optionValue.get().getId()));
                        jsonArray.add(jsonObject);
                    }
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(String.valueOf(customOptionRel.getCustomId()), customOptionRel.getOption());
                    jsonArray.add(jsonObject);
                }
            });
            logger.info("BatchValidate checkOptions convertOptionId:{}", jsonArray);

            return jsonArray;
        } else {
            return new JSONArray(0);
        }
    }

    /**
     * 将自定义字段解析成结构体
     * @param map 结构体
     * @param array json数组
     * @param row
     */
    private void packageRel(ArrayListMultimap<Integer, CustomOptionRel> map, JSONArray array, Integer row) {
        for (int i=0; i<array.size(); i++) {
            if (array.get(i) != null) {
                logger.info("BatchValidate packageRel array[{}]:{}", i, array.get(i));
                if (array.get(i) instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray)array.get(i);
                    if (jsonArray != null && jsonArray.size() > 0) {
                        parseJson(map, (JSONObject)jsonArray.get(0), row);
                    }
                } else if (array.get(i) instanceof JSONObject) {
                    parseJson(map, (JSONObject)array.get(i), row);
                }
            }
        }
    }

    /**
     * 将自定义字段解析成结构体
     * @param customFieldValues json数组
     */
    private List<CustomOptionRel> packageMapRel(Map<Integer, String> customFieldValues) {
        if (customFieldValues != null && customFieldValues.size() > 0) {
            List<CustomOptionRel> rels = new ArrayList<>(customFieldValues.size());
            customFieldValues.forEach((key, value) -> {
                CustomOptionRel customOptionRel = new CustomOptionRel();
                customOptionRel.setCustomId(Integer.valueOf(key));
                customOptionRel.setOption(value);
                rels.add(customOptionRel);
            });
            return rels;
        } else {
            return new ArrayList<>(0);
        }
    }



    /**
     * 解析custonFieldValues json信息
     * @param map 结构体
     * @param jsonObject json对象
     */
    private void parseJson(ArrayListMultimap<Integer, CustomOptionRel> map, JSONObject jsonObject, int i) {
        if (jsonObject != null && jsonObject.size() > 0) {
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                CustomOptionRel customOptionRel = new CustomOptionRel();
                customOptionRel.setCustomId(Integer.valueOf(entry.getKey()));
                customOptionRel.setOption(entry.getValue().toString());
                map.put(i, customOptionRel);
            }
        }
    }

    /**
     * 校验自定义项是否合法
     * @param rels 选择的自定义数据
     * @param dbOptions 数据库可用的自定义数据
     * @return 是否合法。true 合法，false不合法
     */
    private boolean checkOptions(List<CustomOptionRel> rels, Map<Integer, List<EmployeeOptionValue>> dbOptions) {
        if (rels != null && rels.size() > 0) {
            Optional<CustomOptionRel> optional = rels.parallelStream()
                    .filter(customOptionRel -> {
                        List<EmployeeOptionValue> list = dbOptions.get(customOptionRel.getCustomId());
                        if (list != null && list.size() > 0) {
                            Optional<EmployeeOptionValue> optionValue = list.parallelStream()
                                    .filter(employeeOptionValue -> employeeOptionValue.getName().equals(customOptionRel.getOption()))
                                    .findAny();
                            return optionValue.isPresent();
                        } else {
                            return false;
                        }
                    })
                    .findAny();
            return optional.isPresent();
        } else {
            return false;
        }
    }

    /**
     * 查找数据库中的下拉项数据
     * @param params 文件中的员工的自定义数据
     * @param companyId 公司编号
     * @return 数据库中的下拉项
     */
    private Map<Integer, List<EmployeeOptionValue>> fetchOptionsValues(ArrayListMultimap<Integer, CustomOptionRel> params, int companyId) {

        Map<Integer, List<EmployeeOptionValue>> map = new HashMap<>();
        if (params != null && params.size() > 0) {

            Map<Integer, Set<String>> chooseParam = new HashMap<>();
            for (Integer row : params.keySet()) {
                if (params.get(row) != null) {
                    List<CustomOptionRel> rels = params.get(row);
                    if (rels.size() > 0) {
                        rels.forEach(rel -> {
                            if (chooseParam.get(rel.getCustomId()) != null) {
                                chooseParam.get(rel.getCustomId()).add(rel.getOption());
                            } else {
                                chooseParam.put(rel.getCustomId(), new HashSet<String>(){{add(rel.getOption());}});
                            }
                        });
                    }
                }
            }
            List<HrEmployeeCustomFields> fields = customFieldsDao.listSelectOptionByIdList(companyId, chooseParam.keySet());
            if (fields.size() > 0) {
                Map<Integer, Set<String>> dbparams = new HashMap<>();

                fields.forEach(hrEmployeeCustomFields -> {
                    Set<String> values = chooseParam.get(hrEmployeeCustomFields.getId());
                    dbparams.put(hrEmployeeCustomFields.getId(), values);
                            /*if (values != null && values.size() > 0) {
                                Set<Integer> optionValueIds = new HashSet<>(values.size());
                                values.forEach(o -> {
                                    try {
                                        int valueId = Integer.valueOf(o.trim());
                                        optionValueIds.add(valueId);
                                    } catch (NumberFormatException e) {
                                        //do nothing
                                    }
                                });
                                if (optionValueIds.size() > 0) {
                                    if (dbparams.get(hrEmployeeCustomFields.getId()) != null) {
                                        dbparams.get(hrEmployeeCustomFields.getId()).addAll(optionValueIds);
                                    } else {
                                        dbparams.put(hrEmployeeCustomFields.getId(), optionValueIds);
                                    }
                                }
                            }*/
                        });

                if (dbparams.size() > 0) {
                    dbparams.forEach((id, optionIdList) -> {
                        List<EmployeeOptionValue> optionValues = customOptionJooqDao.listByCustomIdAndNames(id, optionIdList);
                        map.put(id, optionValues);
                    });
                }
            }
        }
        return map;
    }

    class CustomOptionRel {

        private int customId;
        private String option;

        public int getCustomId() {
            return customId;
        }

        public void setCustomId(int customId) {
            this.customId = customId;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }
    }


}
