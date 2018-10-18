package com.moseeker.mall.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.historydb.HistoryUserEmployeeDao;
import com.moseeker.baseorm.dao.malldb.MallGoodsInfoDao;
import com.moseeker.baseorm.dao.malldb.MallGoodsOrderDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.mall.annotation.OnlyEmployee;
import com.moseeker.mall.annotation.OnlySuperAccount;
import com.moseeker.mall.constant.OrderEnum;
import com.moseeker.mall.utils.PaginationUtils;
import com.moseeker.mall.vo.MallOrderInfoVO;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.mall.struct.BaseMallForm;
import com.moseeker.thrift.gen.mall.struct.MallGoodsOrderUpdateForm;
import com.moseeker.thrift.gen.mall.struct.OrderForm;
import com.moseeker.thrift.gen.mall.struct.OrderSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cjm
 * @date 2018-10-12 16:24
 **/
@Service
public class OrderService {

    @Autowired
    private MallGoodsOrderDao orderDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private HistoryUserEmployeeDao historyUserEmployeeDao;

    @Autowired
    private MallGoodsInfoDao mallGoodsInfoDao;

    @Autowired
    private GoodsService goodsService;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    /**
     * hr获取公司下的订单 todo 关键词搜索还没做，并且需要数据组装
     * @param  orderSearchForm 订单搜索实体
     * @author  cjm
     * @date  2018/10/16
     * @return 返回订单list和总行数
     */
    @OnlySuperAccount
    public Map<String,String> getCompanyOrderList(OrderSearchForm orderSearchForm) {
        int state = orderSearchForm.getState();
        List<MallOrderDO> orderList;
        Map<String, String> resultMap = new HashMap<>(1 >> 4);
        int totalRows;
        if(state == OrderEnum.All.getState()){
            totalRows = orderDao.getTotalRowsByCompanyId(orderSearchForm.getCompany_id());
            int startIndex = PaginationUtils.getStartIndex(orderSearchForm.getPage_size(), orderSearchForm.getPage_number(), totalRows);
            orderList = orderDao.getOrdersListByPage(orderSearchForm.getCompany_id(), startIndex, orderSearchForm.getPage_size());
        }else {
            totalRows = orderDao.getTotalRowsByCompanyIdAndState(orderSearchForm.getCompany_id(), orderSearchForm.getState());
            int startIndex = PaginationUtils.getStartIndex(orderSearchForm.getPage_size(), orderSearchForm.getPage_number(), totalRows);
            if(StringUtils.isNullOrEmpty(orderSearchForm.getKeyword())){

            }else {
                // todo 关键字查询
            }
            orderList = orderDao.getOrdersListByPageAndState(orderSearchForm.getCompany_id(), orderSearchForm.getState(), startIndex, orderSearchForm.getPage_size());
        }
        Map<Integer, List<MallOrderDO>> employeeOrderMap = getEmployeeOrderMap(orderList);
        List<Integer> employeeIds = orderList.stream().map(MallOrderDO::getEmployee_id).distinct().collect(Collectors.toList());
        Map<Integer, UserEmployeeDO> employeeMap = getEmployeeMap(employeeIds);
        List<MallOrderInfoVO> mallOrderInfoVOS = getMallOrderInfoVOS(employeeOrderMap, employeeMap, employeeIds);
        resultMap.put("total_row", totalRows + "");
        resultMap.put("orders", JSON.toJSONString(mallOrderInfoVOS));
        return resultMap;
    }

    /**
     * 组装订单记录数据
     * @param   employeeOrderMap 员工ID-订单map
     * @param   employeeMap 员工ID-员工map
     * @param   employeeIds 员工IDS
     * @author  cjm
     * @date  2018/10/16
     * @return   mallOrderInfoVOS
     */
    @OnlySuperAccount
    private List<MallOrderInfoVO> getMallOrderInfoVOS(Map<Integer, List<MallOrderDO>> employeeOrderMap, Map<Integer, UserEmployeeDO> employeeMap, List<Integer> employeeIds) {
        List<MallOrderInfoVO> mallOrderInfoVOS = new ArrayList<>();
        for(Integer employeeId : employeeIds){
            List<MallOrderDO> tempList = employeeOrderMap.get(employeeId);
            for(MallOrderDO mallOrderDO : tempList){
                MallOrderInfoVO mallOrderInfoVO = new MallOrderInfoVO();
                mallOrderInfoVO.cloneFromOrderAndEmloyee(mallOrderDO, employeeMap.get(employeeId));
                mallOrderInfoVOS.add(mallOrderInfoVO);
            }
        }
        return mallOrderInfoVOS;
    }

    /**
     *  员工ID-员工  map
     * @param   employeeIds 员工IDS
     * @author  cjm
     * @date  2018/10/16
     * @return map
     */
    private Map<Integer,UserEmployeeDO> getEmployeeMap(List<Integer> employeeIds) {
        List<UserEmployeeDO> employeeDOS = userEmployeeDao.getEmployeeByIds(employeeIds);
        if(employeeDOS.size() != employeeIds.size()){
            employeeDOS.addAll(historyUserEmployeeDao.getHistoryEmployeeByIds(employeeIds));
        }
        return employeeDOS.stream().collect(Collectors.toMap(UserEmployeeDO::getId, userEmployeeDO -> userEmployeeDO));
    }

    /**
     *
     * @param   orderList 订单list
     * @author  cjm
     * @date  2018/10/16
     * @return   员工ID-订单 map
     */
    private Map<Integer,List<MallOrderDO>> getEmployeeOrderMap(List<MallOrderDO> orderList) {
        Map<Integer,List<MallOrderDO>> employeeOrderMap = new HashMap<>(1 >> 4);
        for(MallOrderDO mallOrderDO : orderList){
            if(employeeOrderMap.get(mallOrderDO.getEmployee_id()) == null){
                List<MallOrderDO> tempList = new ArrayList<>();
                tempList.add(mallOrderDO);
                employeeOrderMap.put(mallOrderDO.getEmployee_id(), tempList);
            }else {
                List<MallOrderDO> tempList = employeeOrderMap.get(mallOrderDO.getEmployee_id());
                tempList.add(mallOrderDO);
            }
        }
        return employeeOrderMap;
    }

    /**
     * 确认兑换 todo 做防止重复提交限制 消息模板
     * @param orderForm 确定兑换请求实体
     * @author  cjm
     * @date  2018/10/16
     */
    @OnlyEmployee
    public void confirmOrder(OrderForm orderForm) throws BIZException {
        // todo redis防止重复提交
        UserEmployeeDO userEmployeeDO = getUserEmployeeById(orderForm.getEmployee_id());
        MallGoodsInfoDO mallGoodsInfoDO = goodsService.getUpshelfGoodById(orderForm.getGoods_id(), orderForm.getCompany_id());
        int payCredit = orderForm.getCount() * mallGoodsInfoDO.getCredit();
        int count = orderForm.getCount();
        int stock = mallGoodsInfoDO.getStock();
        if(count > stock){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_STOCK_LACK);
        }
        int award = userEmployeeDO.getAward();
        if(payCredit > award){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_AWARD_LACK);
        }

    }



    /**
     * 确认发放或不发放 todo 做防止重复提交限制 消息模板
     * @param updateForm 订单更新实体
     * @author  cjm
     * @date  2018/10/16
     */
    @Transactional(rollbackFor = Exception.class)
    @OnlySuperAccount
    public void updateOrder(MallGoodsOrderUpdateForm updateForm) throws BIZException {
        // todo redis防止重复提交
        checkOrderOperationState(updateForm.getState());
        List<MallOrderDO> orderList = orderDao.getOrdersByIds(updateForm.getIds());
        checkOrderLimit(orderList, updateForm.getCompany_id());
        if(updateForm.getState() == OrderEnum.CONFIRM.getState()){
            int rows = orderDao.updateOrderStateByIdAndCompanyId(updateForm.getIds(), updateForm.getCompany_id(), updateForm.getState());
            if(rows != updateForm.getIds().size()){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
            }
        }else {
            // todo 不发放请求，需要返回积分

        }


    }

    /**
     * 员工获取积分兑换记录
     * @param  baseMallForm 基础form
     * @author  cjm
     * @date  2018/10/16
     * @return  兑换记录list
     */
    @OnlyEmployee
    public String getEmployeeOrderList(BaseMallForm baseMallForm) throws BIZException {
        List<MallOrderDO> orderList = orderDao.getOrdersListByEmployeeId(baseMallForm.getEmployee_id());
        UserEmployeeDO userEmployeeDO = getUserEmployeeById(baseMallForm.getEmployee_id());
        List<MallOrderInfoVO> mallOrderInfoVOS = new ArrayList<>();
        for(MallOrderDO mallOrderDO : orderList){
            MallOrderInfoVO mallOrderInfoVO = new MallOrderInfoVO();
            mallOrderInfoVO.cloneFromOrderAndEmloyee(mallOrderDO, userEmployeeDO);
            mallOrderInfoVOS.add(mallOrderInfoVO);
        }
        return JSON.toJSONString(mallOrderInfoVOS);
    }

    private void checkOrderOperationState(int state) throws BIZException {
        if(state < OrderEnum.CONFIRM.getState() || state > OrderEnum.REFUSED.getState()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_TYPE_UNEXISTS);
        }
    }

    /**
     * 检验操作的订单是否是本公司下的订单
     * @param   orderList  订单list
     * @param   companyId  公司id
     * @author  cjm
     * @date  2018/10/16
     */
    private void checkOrderLimit(List<MallOrderDO> orderList, int companyId) throws BIZException {
        List<Integer> companyIdList = orderList.stream().map(MallOrderDO::getCompany_id).distinct().collect(Collectors.toList());
        for (Integer id : companyIdList) {
            if(id != companyId){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_OPERATION_LIMIT);
            }
        }
    }

    private UserEmployeeDO getUserEmployeeById(int employeeId) throws BIZException {
        UserEmployeeDO userEmployeeDO = userEmployeeDao.getEmployeeById(employeeId);
        if(userEmployeeDO == null){
            userEmployeeDO = historyUserEmployeeDao.getUserEmployeeById(employeeId);
            if(userEmployeeDO == null){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.USER_NOTEXIST);
            }
        }
        return userEmployeeDO;
    }
}
