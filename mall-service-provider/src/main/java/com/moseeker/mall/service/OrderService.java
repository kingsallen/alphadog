package com.moseeker.mall.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.historydb.HistoryUserEmployeeDao;
import com.moseeker.baseorm.dao.malldb.MallGoodsOrderDao;
import com.moseeker.baseorm.dao.malldb.MallOrderOperationDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.mall.annotation.OnlyEmployee;
import com.moseeker.mall.annotation.OnlySuperAccount;
import com.moseeker.mall.constant.GoodsEnum;
import com.moseeker.mall.constant.OrderEnum;
import com.moseeker.mall.utils.DbUtils;
import com.moseeker.mall.utils.PaginationUtils;
import com.moseeker.mall.vo.MallOrderInfoVO;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderOperationDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.mall.struct.BaseMallForm;
import com.moseeker.thrift.gen.mall.struct.MallGoodsOrderUpdateForm;
import com.moseeker.thrift.gen.mall.struct.OrderForm;
import com.moseeker.thrift.gen.mall.struct.OrderSearchForm;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private MallGoodsOrderDao orderDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private HistoryUserEmployeeDao historyUserEmployeeDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MallOrderOperationDao orderOperationDao;

    @Autowired
    private UserEmployeePointsDao userEmployeePointsDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    private static final String CONFIRM_REASON = "积分商城兑换商品消费积分";
    private static final String REFUSE_REASON = "积分商城拒绝兑换商品返还积分";

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

    /**
     * 确认兑换
     * @param orderForm 确定兑换请求实体
     * @author  cjm
     * @date  2018/10/16
     */
    @OnlyEmployee
    @Transactional(rollbackFor = Exception.class)
    public void confirmOrder(OrderForm orderForm) throws BIZException {
        try{
            handleOrder(orderForm);
        }catch (Exception e){
            delOrderRedisLock(orderForm);
            throw e;
        }

    }

    /**
     * 确认发放或不发放
     * @param updateForm 订单更新实体
     * @author  cjm
     * @date  2018/10/16
     */
    @Transactional(rollbackFor = Exception.class)
    @OnlySuperAccount
    public void updateOrder(MallGoodsOrderUpdateForm updateForm) throws BIZException {
        try{
            handleUpdatedOrder(updateForm);
        }catch (Exception e){
            batchDelOrderRedisLock(updateForm.getIds(), updateForm.getHr_id());
            throw e;
        }
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

    private void handleOrder(OrderForm orderForm) throws BIZException {
        // redis防止重复提交
        checkEmployeeDuplicateCommit(orderForm.getGoods_id(), orderForm.getEmployee_id());
        // 获取员工信息
        UserEmployeeDO userEmployeeDO = userEmployeeDao.getUserEmployeeForUpdate(orderForm.getEmployee_id());
        // 获取商品信息
        MallGoodsInfoDO mallGoodsInfoDO = goodsService.getUpshelfGoodById(orderForm.getGoods_id(), orderForm.getCompany_id());
        // 检验剩余积分，返回待支付积分
        int payCredit = checkRemainAward(mallGoodsInfoDO.getCredit(), orderForm.getCount(), userEmployeeDO.getAward());
        // 检验剩余库存
        checkRemainStock(orderForm.getCount(), mallGoodsInfoDO.getStock());
        // 插入订单记录
        MallOrderDO mallOrderDO = insertOrder(mallGoodsInfoDO, userEmployeeDO, orderForm);
        // 乐观锁减库存
        minusStockByLock(mallGoodsInfoDO, orderForm);
        // 乐观锁减积分
        updateAward(userEmployeeDO, -payCredit);
        // 插入积分明细
        UserEmployeePointsRecordDO userEmployeePointsDO = insertAwardRecord(mallOrderDO, OrderEnum.CONFIRM.getState());
        // 插入订单操作记录
        insertOperationRecord(mallOrderDO.getId(), orderForm.getEmployee_id(), userEmployeePointsDO.getId());
        // todo 发送消息模板
//        sendAwardTemplate();
        //  删除redis锁
        delOrderRedisLock(orderForm);
    }

    private void delOrderRedisLock(OrderForm orderForm) {
        redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER.toString(),
                String.valueOf(orderForm.getGoods_id()), String.valueOf(orderForm.getEmployee_id()));
    }

    private void checkEmployeeDuplicateCommit(int goodId, int employeeId) throws BIZException {
        long flag = redisClient.setnx(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER.toString(),
                String.valueOf(goodId), String.valueOf(employeeId),  "1");
        if(flag == 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_REPEATED_COMMIT);
        }
    }

    private void checkHrDuplicateCommit(int orderId, int hrId) throws BIZException {
        long flag = redisClient.setnx(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_OPERATION.toString(),
                String.valueOf(orderId), String.valueOf(hrId),  "1");
        if(flag == 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_REPEATED_COMMIT);
        }
    }

    private void checkRemainStock(int count, int stock) throws BIZException {
        if(count > stock){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_STOCK_LACK);
        }
    }

    private int checkRemainAward(int goodCredit, int count, int remainAward) throws BIZException {
        int payCredit = goodCredit * count;
        if(payCredit > remainAward){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_AWARD_LACK);
        }
        return payCredit;
    }

    private void insertOperationRecord(int orderId, int employeeId, int pointRecordId) {
        MallOrderOperationDO mallOrderOperationDO = new MallOrderOperationDO();
        mallOrderOperationDO.setEmployee_id(employeeId);
        mallOrderOperationDO.setOrder_id(orderId);
        mallOrderOperationDO.setPoint_record_id(pointRecordId);
        orderOperationDao.addData(mallOrderOperationDO);
    }

    private List<UserEmployeePointsRecordDO> batchInsertAwardRecord(List<MallOrderDO> orderList, int orderState) throws BIZException {
        List<UserEmployeePointsRecordDO> recordDOS = new ArrayList<>();
        for(MallOrderDO mallOrderDO : orderList){
            UserEmployeePointsRecordDO userEmployeePointsDO = new UserEmployeePointsRecordDO();
            if(orderState == OrderEnum.CONFIRM.getState() || orderState == OrderEnum.REFUSED.getState()){
                userEmployeePointsDO.setAward(orderState == OrderEnum.CONFIRM.getState() ? mallOrderDO.getCredit() : -mallOrderDO.getCredit());
                userEmployeePointsDO.setEmployeeId(mallOrderDO.getEmployee_id());
                userEmployeePointsDO.setReason(orderState == OrderEnum.CONFIRM.getState() ? CONFIRM_REASON : REFUSE_REASON);
                recordDOS.add(userEmployeePointsDO);
            }else {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_UNSUPPORTED_STATE);
            }
        }
        recordDOS = userEmployeePointsDao.addAllData(recordDOS);
        if(recordDOS.size() != orderList.size()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
        }
        return recordDOS;
    }

    private UserEmployeePointsRecordDO insertAwardRecord(MallOrderDO mallOrderDO, int orderState) throws BIZException {
        List<MallOrderDO> orderList = new ArrayList<>();
        orderList.add(mallOrderDO);
        List<UserEmployeePointsRecordDO> recordDOS = batchInsertAwardRecord(orderList, orderState);
        return recordDOS.get(0);
    }


    private void updateAward(UserEmployeeDO userEmployeeDO, int payCredit) throws BIZException {
        updateAwardByLock(userEmployeeDO.getId(), userEmployeeDO.getAward(), payCredit, 1);
    }

    private void updateAwardByLock(int employeeId, int oldAward, int payCredit, int retryTimes) throws BIZException {
        DbUtils.checkRetryTimes(retryTimes);
        int row = userEmployeeDao.addAward(employeeId, oldAward + payCredit, oldAward);
        if(row == 0){
            UserEmployeeDO userEmployeeDO = userEmployeeDao.getUserEmployeeForUpdate(employeeId);
            updateAwardByLock(userEmployeeDO.getId(), userEmployeeDO.getAward(), payCredit, ++retryTimes);
        }
    }

    /**
     * 乐观锁减库存
     * @param mallGoodsInfoDO 商品信息
     * @param orderForm 订单扣减信息
     * @author  cjm
     * @date  2018/10/19
     */
    private void minusStockByLock(MallGoodsInfoDO mallGoodsInfoDO, OrderForm orderForm) throws BIZException {
        goodsService.updateStockAndExchangeNumByLock(mallGoodsInfoDO, -orderForm.getCount(), GoodsEnum.UPSHELF.getState(), 1);
    }

    private MallOrderDO insertOrder(MallGoodsInfoDO mallGoodsInfoDO, UserEmployeeDO userEmployeeDO, OrderForm orderForm) {
        MallOrderDO mallOrderDO = initOrderDO(mallGoodsInfoDO, userEmployeeDO, orderForm);
        return orderDao.addData(mallOrderDO);
    }

    private MallOrderDO initOrderDO(MallGoodsInfoDO mallGoodsInfoDO, UserEmployeeDO userEmployeeDO, OrderForm orderForm) {
        MallOrderDO mallOrderDO = new MallOrderDO();
        String orderId = createOrderId();
        mallOrderDO.setOrder_id(orderId);
        mallOrderDO.setEmployee_id(userEmployeeDO.getId());
        mallOrderDO.setGoods_id(mallGoodsInfoDO.getId());
        mallOrderDO.setCompany_id(mallGoodsInfoDO.getCompany_id());
        mallOrderDO.setCredit(mallGoodsInfoDO.getCredit());
        mallOrderDO.setTitle(mallGoodsInfoDO.getTitle());
        mallOrderDO.setPic_url(mallGoodsInfoDO.getPic_url());
        mallOrderDO.setCount(orderForm.getCount());
        logger.info("mallOrderDO:{}", mallOrderDO);
        return mallOrderDO;
    }

    /**
     * 计算订单id
     * @author  cjm
     * @date  2018/10/19
     * @return 返回订单id
     */
    private String createOrderId() {
        DateTime dateTime = DateTime.now();
        DateTime allDay  = dateTime.millisOfDay().withMaximumValue();
        long expireTime = new Duration(dateTime, allDay).getStandardSeconds();
        String current = redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_ID.toString(), null);
        if(StringUtils.isNullOrEmpty(current)){
            current = redisClient.incr(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_ID.toString(), null) + "";
            redisClient.expire(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_ID.toString(), null, (int)expireTime);
        }else {
            current = redisClient.incr(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_ID.toString(), null) + "";
        }
        int year = dateTime.getYear() % 100;
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        logger.info("allDay:{}, expireTime:{}", allDay, expireTime);
        return String.valueOf((((year * 100) + month) * 100 + day) * 10000L + Long.parseLong(current));
    }

    private void handleUpdatedOrder(MallGoodsOrderUpdateForm updateForm) throws BIZException {
        batchCheckHrDuplicateCommit(updateForm);
        checkOrderOperationState(updateForm.getState());
        List<MallOrderDO> orderList = orderDao.getOrdersByIds(updateForm.getIds());
        checkOrderLimit(orderList, updateForm.getCompany_id());
        if(updateForm.getState() == OrderEnum.CONFIRM.getState()){
            int rows = orderDao.updateOrderStateByIdAndCompanyId(updateForm.getIds(), updateForm.getCompany_id(), updateForm.getState());
            if(rows != updateForm.getIds().size()){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
            }
        }else if(updateForm.getState() == OrderEnum.REFUSED.getState()){
            // 插入积分变更记录
            List<UserEmployeePointsRecordDO> recordDOS = batchInsertAwardRecord(orderList, OrderEnum.REFUSED.getState());
            // 返还积分
            batchUpdateAward(orderList);
            // 商品信息修改，例如兑换次数，兑换数量
            batchUpdateGoodInfo(orderList);
            // 发送消息模板
            // sendAwardTemplate();
        }else {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_UNSUPPORTED_STATE);
        }
        // 插入hr操作记录
//        batchInsertOperationRecord();
        batchDelOrderRedisLock(updateForm.getIds(), updateForm.getHr_id());
    }

    private void batchUpdateGoodInfo(List<MallOrderDO> orderList) {

    }

    private void batchUpdateAward(List<MallOrderDO> orderList) throws BIZException {
        // todo 暂时循环处理
        for(MallOrderDO orderDO : orderList){
            UserEmployeeDO userEmployeeDO = userEmployeeDao.getUserEmployeeForUpdate(orderDO.getEmployee_id());
            updateAwardByLock(orderDO.getEmployee_id(), userEmployeeDO.getAward(), orderDO.getCredit(), 1);
        }
    }

    private void batchDelOrderRedisLock(List<Integer> orderIds, int hrId) {
        for(Integer orderId : orderIds){
            redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_OPERATION.toString(),
                    String.valueOf(orderId), String.valueOf(hrId));
        }
    }

    private void batchCheckHrDuplicateCommit(MallGoodsOrderUpdateForm updateForm) throws BIZException {
        for(int orderId : updateForm.getIds()){
            checkHrDuplicateCommit(orderId, updateForm.getHr_id());
        }
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
