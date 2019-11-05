package com.moseeker.mall.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.historydb.HistoryUserEmployeeDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.malldb.MallGoodsOrderDao;
import com.moseeker.baseorm.dao.malldb.MallMailAddressDao;
import com.moseeker.baseorm.dao.malldb.MallOrderOperationDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsDao;
import com.moseeker.baseorm.db.dictdb.tables.pojos.DictCity;
import com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.mall.annotation.OnlyEmployee;
import com.moseeker.mall.annotation.OnlySuperAccount;
import com.moseeker.mall.constant.GoodsEnum;
import com.moseeker.mall.constant.OrderEnum;
import com.moseeker.mall.utils.DbUtils;
import com.moseeker.mall.utils.PaginationUtils;
import com.moseeker.mall.vo.MallOrderInfoVO;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderOperationDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.mall.struct.*;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cjm
 * @date 2018-10-12 16:24
 **/
@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final MallGoodsOrderDao orderDao;

    private final UserEmployeeDao userEmployeeDao;

    private final HistoryUserEmployeeDao historyUserEmployeeDao;

    private final GoodsService goodsService;

    private final MallOrderOperationDao orderOperationDao;

    private final UserEmployeePointsDao userEmployeePointsDao;

    private final HrCompanyDao hrCompanyDao;

    private final MallMailAddressDao addressDao;

    private final DictCityDao dictCityDao;

    private final TemplateService templateService;

    private final Environment environment;

    private final SearchengineEntity searchengineEntity;

    ThreadPool pool = ThreadPool.Instance;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    private static final String CONSUME_REMARK = "点击查看详细兑换记录";
    private static final String REFUSE_REMARK = "点击查看积分明细";

    public OrderService(MallGoodsOrderDao orderDao, UserEmployeeDao userEmployeeDao, HistoryUserEmployeeDao historyUserEmployeeDao, GoodsService goodsService, MallOrderOperationDao orderOperationDao, UserEmployeePointsDao userEmployeePointsDao, HrCompanyDao hrCompanyDao, MallMailAddressDao addressDao, DictCityDao dictCityDao, TemplateService templateService, Environment environment, SearchengineEntity searchengineEntity) {
        this.orderDao = orderDao;
        this.userEmployeeDao = userEmployeeDao;
        this.historyUserEmployeeDao = historyUserEmployeeDao;
        this.goodsService = goodsService;
        this.orderOperationDao = orderOperationDao;
        this.userEmployeePointsDao = userEmployeePointsDao;
        this.hrCompanyDao = hrCompanyDao;
        this.addressDao = addressDao;
        this.dictCityDao = dictCityDao;
        this.templateService = templateService;
        this.environment = environment;
        this.searchengineEntity = searchengineEntity;
    }

    /**
     * hr获取公司下的订单
     *
     * @param orderSearchForm 订单搜索实体
     * @return 返回订单list和总行数
     * @author cjm
     * @date 2018/10/16
     */
    @OnlySuperAccount
    public Map<String, String> getCompanyOrderList(OrderSearchForm orderSearchForm) {
        int state = orderSearchForm.getState();
        List<MallOrderDO> orderList;
        Map<String, String> resultMap = new HashMap<>(1 >> 4);
        String keyWord = orderSearchForm.getKeyword();
        int totalRows;
        if (StringUtils.isNullOrEmpty(keyWord)) {
            if (state == OrderEnum.All.getState()) {
                totalRows = orderDao.getTotalRowsByCompanyId(orderSearchForm.getCompany_id());
                int startIndex = PaginationUtils.getStartIndex(orderSearchForm.getPage_size(), orderSearchForm.getPage_number(), totalRows);
                orderList = orderDao.getOrdersListByPage(orderSearchForm.getCompany_id(), startIndex, orderSearchForm.getPage_size());
            } else {
                totalRows = orderDao.getTotalRowsByCompanyIdAndState(orderSearchForm.getCompany_id(), orderSearchForm.getState());
                int startIndex = PaginationUtils.getStartIndex(orderSearchForm.getPage_size(), orderSearchForm.getPage_number(), totalRows);
                orderList = orderDao.getOrdersListByPageAndState(orderSearchForm.getCompany_id(), orderSearchForm.getState(), startIndex, orderSearchForm.getPage_size());
            }
        } else {
            orderSearchForm.setKeyword("%" + orderSearchForm.getKeyword() + "%");
            keyWord = orderSearchForm.getKeyword();
            if (state == OrderEnum.All.getState()) {
                totalRows = orderDao.getTotalRowsByCompanyIdAndKeyword(orderSearchForm.getCompany_id(), keyWord);
                int startIndex = PaginationUtils.getStartIndex(orderSearchForm.getPage_size(), orderSearchForm.getPage_number(), totalRows);
                orderList = orderDao.getOrdersListByPageAndKeyword(orderSearchForm, startIndex);
            } else {
                totalRows = orderDao.getTotalRowsByCompanyIdAndStateAndKeyword(orderSearchForm.getCompany_id(), orderSearchForm.getState(), keyWord);
                int startIndex = PaginationUtils.getStartIndex(orderSearchForm.getPage_size(), orderSearchForm.getPage_number(), totalRows);
                orderList = orderDao.getOrdersListByPageAndStateAndKeyword(orderSearchForm, startIndex);
            }
        }
        Map<Integer, List<MallOrderDO>> employeeOrderMap = getEmployeeOrderMap(orderList);
        List<Integer> employeeIds = orderList.stream().map(MallOrderDO::getEmployee_id).distinct().collect(Collectors.toList());
        List<UserEmployeeDO> employeeDOS = userEmployeeDao.getEmployeeByIds(employeeIds);
        List<UserEmployeeDO> historyEmployeeDOS = new ArrayList<>();
        if (employeeDOS.size() != employeeIds.size()) {
            historyEmployeeDOS = historyUserEmployeeDao.getHistoryEmployeeByIds(employeeIds);
        }
        List<MallOrderInfoVO> mallOrderInfoVOS = getMallOrderInfoVOS(employeeOrderMap, employeeDOS, historyEmployeeDOS, employeeIds);
        resultMap.put("total_row", totalRows + "");
        resultMap.put("list", JSON.toJSONString(mallOrderInfoVOS));
        return resultMap;
    }


    /**
     * 员工获取积分兑换记录
     *
     * @param baseMallForm 基础form
     * @return 兑换记录list
     * @author cjm
     * @date 2018/10/16
     */
    @OnlyEmployee
    public String getEmployeeOrderList(BaseMallForm baseMallForm) throws BIZException {
        List<MallOrderDO> orderList = orderDao.getOrdersListByEmployeeId(baseMallForm.getEmployee_id());
        UserEmployeeDO userEmployeeDO = getUserEmployeeById(baseMallForm.getEmployee_id());
        UserEmployeeDO historyUserEmployeeDO = null;
        if (userEmployeeDO == null) {
            historyUserEmployeeDO = historyUserEmployeeDao.getUserEmployeeById(baseMallForm.getEmployee_id());
        }
        List<MallOrderInfoVO> mallOrderInfoVOS = new ArrayList<>();
        for (MallOrderDO mallOrderDO : orderList) {
            MallOrderInfoVO mallOrderInfoVO = new MallOrderInfoVO();
            mallOrderInfoVO.cloneFromOrderAndEmloyee(mallOrderDO, userEmployeeDO, historyUserEmployeeDO);
            mallOrderInfoVOS.add(mallOrderInfoVO);
        }
        return JSON.toJSONString(mallOrderInfoVOS);
    }

    /**
     * 确认兑换
     *
     * @param orderForm 确定兑换请求实体
     * @author cjm
     * @date 2018/10/16
     */
    @OnlyEmployee
    public void confirmOrder(OrderForm orderForm) throws TException {
        try {
            handleOrder(orderForm);
        } catch (Exception e) {
            delOrderRedisLock(orderForm);
            throw e;
        }
    }

    /**
     * 确认发放或不发放
     *
     * @param updateForm 订单更新实体
     * @author cjm
     * @date 2018/10/16
     */
    @OnlySuperAccount
    public void updateOrder(MallGoodsOrderUpdateForm updateForm) throws TException {
        try {
            List<Integer> employeeIds = handleUpdatedOrder(updateForm);
            logger.info("==========employeeids:{}", employeeIds);
            // 更新ES中的user_employee数据，以便积分排行实时更新
            pool.startTast(() -> searchengineEntity.updateEmployeeAwards(employeeIds, true));
        } catch (Exception e) {
            batchDelOrderRedisLock(updateForm.getIds(), updateForm.getHr_id());
            throw e;
        }
    }


    @OnlySuperAccount
    public String exportOrder(BaseMallForm baseMallForm) {
        List<MallOrderDO> orderList = orderDao.getAllOrderByCompanyId(baseMallForm.getCompany_id());
        //获取所有的邮寄地址id
        List<Integer> mailIdList = orderList.stream().map(MallOrderDO::getMailId).collect(Collectors.toList());
        //再根据地址id获取所有邮寄信息
        List<MallMailAddress> addresses = addressDao.getAddressByIdList(mailIdList);
        Map<Integer,MallMailAddress> addressMap = addresses.stream().collect(Collectors.toMap(MallMailAddress::getId,address->address));

        Map<Integer, List<MallOrderDO>> employeeOrderMap = getEmployeeOrderMap(orderList);
        List<Integer> employeeIds = orderList.stream().map(MallOrderDO::getEmployee_id).distinct().collect(Collectors.toList());
        List<UserEmployeeDO> employeeDOS = userEmployeeDao.getEmployeeByIds(employeeIds);
        List<UserEmployeeDO> historyEmployeeDOS = new ArrayList<>();
        if (employeeDOS.size() != employeeIds.size()) {
            historyEmployeeDOS = historyUserEmployeeDao.getHistoryEmployeeByIds(employeeIds);
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<MallOrderInfoVO> mallOrderInfoVOS = getMallOrderInfoVOS(employeeOrderMap, employeeDOS, historyEmployeeDOS, employeeIds, sdf);

        //将邮寄地址信息插入到导出列表中
        mallOrderInfoVOS.stream().forEach(mallOrderInfoVO -> {
            mallOrderInfoVO.setAddress(addressMap.get(mallOrderInfoVO.getMailId()));
        });
        return JSON.toJSONString(mallOrderInfoVOS);
    }

    private List<MallOrderInfoVO> getMallOrderInfoVOS(Map<Integer, List<MallOrderDO>> employeeOrderMap, List<UserEmployeeDO> employeeDOS,
                                                      List<UserEmployeeDO> historyEmployeeDOS, List<Integer> employeeIds, DateFormat dateFormat) {
        List<MallOrderInfoVO> mallOrderInfoVOS = new ArrayList<>();
        Map<Integer, UserEmployeeDO> idEmployeeMap = employeeDOS.stream().collect(Collectors.toMap(UserEmployeeDO::getId, userEmployeeDO -> userEmployeeDO));
        Map<Integer, UserEmployeeDO> historyIdEmployeeMap = historyEmployeeDOS.stream().collect(Collectors.toMap(UserEmployeeDO::getId, userEmployeeDO -> userEmployeeDO));

        for (Integer employeeId : employeeIds) {
            List<MallOrderDO> tempList = employeeOrderMap.get(employeeId);
            for (MallOrderDO mallOrderDO : tempList) {
                MallOrderInfoVO mallOrderInfoVO = new MallOrderInfoVO();
                UserEmployeeDO userEmployeeDO = idEmployeeMap.get(employeeId);
                UserEmployeeDO historyIdEmployee = null;
                if (userEmployeeDO == null) {
                    historyIdEmployee = historyIdEmployeeMap.get(employeeId);
                }
                mallOrderInfoVO.cloneFromOrderAndEmloyee(mallOrderDO, userEmployeeDO, historyIdEmployee, dateFormat);
                mallOrderInfoVOS.add(mallOrderInfoVO);
            }
        }
        return mallOrderInfoVOS;
    }

    /**
     * 组装订单记录数据
     *
     * @param employeeOrderMap   员工ID-订单map
     * @param employeeDOS        员工dos
     * @param historyEmployeeDOS 历史表员工dos
     * @param employeeIds        员工IDS
     * @return mallOrderInfoVOS
     * @author cjm
     * @date 2018/10/16
     */
    private List<MallOrderInfoVO> getMallOrderInfoVOS(Map<Integer, List<MallOrderDO>> employeeOrderMap, List<UserEmployeeDO> employeeDOS, List<UserEmployeeDO> historyEmployeeDOS, List<Integer> employeeIds) {
        return getMallOrderInfoVOS(employeeOrderMap, employeeDOS, historyEmployeeDOS, employeeIds, null);

    }

    /**
     * @param orderList 订单list
     * @return 员工ID-订单 map
     * @author cjm
     * @date 2018/10/16
     */
    private Map<Integer, List<MallOrderDO>> getEmployeeOrderMap(List<MallOrderDO> orderList) {
        Map<Integer, List<MallOrderDO>> employeeOrderMap = new HashMap<>(1 >> 4);
        for (MallOrderDO mallOrderDO : orderList) {
            if (employeeOrderMap.get(mallOrderDO.getEmployee_id()) == null) {
                List<MallOrderDO> tempList = new ArrayList<>();
                tempList.add(mallOrderDO);
                employeeOrderMap.put(mallOrderDO.getEmployee_id(), tempList);
            } else {
                List<MallOrderDO> tempList = employeeOrderMap.get(mallOrderDO.getEmployee_id());
                tempList.add(mallOrderDO);
            }
        }
        return employeeOrderMap;
    }

    private void handleOrder(OrderForm orderForm) throws TException {
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
        // 更改订单业务更新，发送模板消息
        UserEmployeePointsRecordDO userEmployeePointsDO = handleOrderDbUpdate(orderForm, userEmployeeDO, mallGoodsInfoDO, payCredit);
        // 更新ES中的user_employee数据，以便积分排行实时更新
//        searchengineEntity.updateEmployeeAwards(userEmployeeDO.getId(), userEmployeePointsDO.getId());
        List<Integer> employeeIds = new ArrayList<>();
        employeeIds.add((int)userEmployeePointsDO.getEmployeeId());
        searchengineEntity.updateEmployeeAwards(employeeIds, true);
        // 删除redis锁
        delOrderRedisLock(orderForm);
    }

    @Transactional(rollbackFor = Exception.class)
    protected UserEmployeePointsRecordDO handleOrderDbUpdate(OrderForm orderForm, UserEmployeeDO userEmployeeDO, MallGoodsInfoDO mallGoodsInfoDO, int payCredit) throws BIZException {
        //插入地址信息
        if(orderForm.getUserId()==0&&!orderForm.isSetUserId()){
            orderForm.setUserId(userEmployeeDO.getSysuserId());
        }
        MallMailAddress address = null;
        if(orderForm.getDeliverType()==2){
            logger.info("OrderService handleOrderDbUpdate inserMailAddr:{}",orderForm);
            address = inserMailAddr(orderForm);
        }
        logger.info("OrderService handleOrderDbUpdate address:{}",address);
        // 插入订单记录
        MallOrderDO mallOrderDO = insertOrder(mallGoodsInfoDO, userEmployeeDO, orderForm,address);
        // 乐观锁减库存
        minusStockByLock(mallGoodsInfoDO, orderForm);
        // 乐观锁减积分
        updateAward(userEmployeeDO, -payCredit);
        // 插入积分明细
        UserEmployeePointsRecordDO userEmployeePointsDO = insertAwardRecord(mallOrderDO, OrderEnum.CONFIRM.getState());
        // 插入订单操作记录
        insertOperationRecord(mallOrderDO.getId(), userEmployeePointsDO.getId());
        // 发送消息模板
        sendAwardTemplate(orderForm.getCompany_id(), mallOrderDO.getCount() * mallOrderDO.getCredit(), userEmployeeDO.getSysuserId(), mallGoodsInfoDO.getTitle());
        return userEmployeePointsDO;
    }

    private MallMailAddress inserMailAddr(OrderForm orderForm) {
        MallMailAddress address = new MallMailAddress();
        address.setAddress(orderForm.getAddress());
        address.setAddressee(orderForm.getAddressee());
        address.setCity(orderForm.getCity());
        address.setMobile(orderForm.getMobile());
        address.setProvince(orderForm.getProvince());
        address.setRegion(orderForm.getRegion());
        address.setUserId(orderForm.getUserId());
        return addressDao.save(address);
    }

    private void sendAwardTemplate(int companyId, int credit, int sysUserId, String goodTitle) {
        DateTime dateTime = DateTime.now();
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String current = dateFormat.format(dateTime.toDate());
        String templateTile = "您已成功兑换【" + goodTitle + "】";
        String url = getTemplateJumpUrlByKey("mall.charge.template.url");
        HrCompanyDO hrCompanyDO = hrCompanyDao.getCompanyById(companyId);
        String shopName = hrCompanyDO.getName() + "积分商城";
        templateService.sendAwardTemplate(sysUserId, companyId, Constant.TEMPLATES_AWARD_CONSUME_NOTICE_TPL, templateTile,
                current, "0", credit + "积分", shopName, CONSUME_REMARK, url);
    }

    /**
     * 获取消息模板的跳转页面url
     *
     * @param key key
     * @return value
     * @author cjm
     * @date 2018/11/1
     */
    private String getTemplateJumpUrlByKey(String key) {
        String value = environment.getProperty(key);
        return value != null ? value : "";
    }


    private void delOrderRedisLock(OrderForm orderForm) {
        redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER.toString(),
                String.valueOf(orderForm.getGoods_id()), String.valueOf(orderForm.getEmployee_id()));
    }

    private void checkEmployeeDuplicateCommit(int goodId, int employeeId) throws BIZException {
        long flag = redisClient.setnx(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER.toString(),
                String.valueOf(goodId), String.valueOf(employeeId), "1");
        System.out.println("flag:" + flag);
        if (flag == 0) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_REPEATED_COMMIT);
        }
    }

    private void checkHrDuplicateCommit(int orderId, int hrId) throws BIZException {
        long flag = redisClient.setnx(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_OPERATION.toString(),
                String.valueOf(orderId), String.valueOf(hrId), "1");
        if (flag == 0) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_REPEATED_COMMIT);
        }
    }

    private void checkRemainStock(int count, int stock) throws BIZException {
        if (count > stock) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_STOCK_LACK);
        }
    }

    private int checkRemainAward(int goodCredit, int count, int remainAward) throws BIZException {
        int payCredit = goodCredit * count;
        if (payCredit > remainAward) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_AWARD_LACK);
        }
        return payCredit;
    }

    /**
     * 插入订单操作记录
     *
     * @param orderId       订单id
     * @param pointRecordId 积分明细id
     * @author cjm
     * @date 2018/10/23
     */
    private void insertOperationRecord(int orderId, int pointRecordId) {
        MallOrderOperationDO mallOrderOperationDO = initOperationRecord(pointRecordId, orderId, 0);
        orderOperationDao.addData(mallOrderOperationDO);
    }

    /**
     * 初始化订单操作记录DO
     *
     * @param pointRecordId 积分明细id
     * @param orderId       订单id
     * @param state         订单状态
     * @return 返回订单操作记录DO
     * @author cjm
     * @date 2018/10/23
     */
    private MallOrderOperationDO initOperationRecord(int pointRecordId, int orderId, int state) {
        return initOperationRecord(pointRecordId, orderId, 0, state);
    }

    private MallOrderOperationDO initOperationRecord(int pointRecordId, int orderId, int hrId, int state) {
        MallOrderOperationDO mallOrderOperationDO = new MallOrderOperationDO();
        mallOrderOperationDO.setOrder_id(orderId);
        mallOrderOperationDO.setHr_id(hrId);
        mallOrderOperationDO.setPoint_record_id(pointRecordId);
        mallOrderOperationDO.setOperation_state((byte) state);
        return mallOrderOperationDO;
    }

    private Map<Integer, UserEmployeePointsRecordDO> batchInsertAwardRecord(List<MallOrderDO> orderList, int orderState) throws BIZException {
        Map<Integer, UserEmployeePointsRecordDO> map = new HashMap<>(1 >> 4);
        for (MallOrderDO mallOrderDO : orderList) {
            UserEmployeePointsRecordDO userEmployeePointsDO = new UserEmployeePointsRecordDO();
            if (orderState == OrderEnum.REFUSED.getState()) {
                userEmployeePointsDO.setAward(mallOrderDO.getCount() * mallOrderDO.getCredit());
                userEmployeePointsDO.setEmployeeId(mallOrderDO.getEmployee_id());
                userEmployeePointsDO.setReason("兑换商品-" + mallOrderDO.getTitle() + "-数量：" + mallOrderDO.getCount());
                userEmployeePointsDO = userEmployeePointsDao.addData(userEmployeePointsDO);
                map.put(mallOrderDO.getId(), userEmployeePointsDO);
            } else if (orderState != OrderEnum.CONFIRM.getState()) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_UNSUPPORTED_STATE);
            }
        }
        return map;
    }

    private UserEmployeePointsRecordDO insertAwardRecord(MallOrderDO mallOrderDO, int orderState) throws BIZException {
        UserEmployeePointsRecordDO userEmployeePointsDO = new UserEmployeePointsRecordDO();
        if (orderState == OrderEnum.CONFIRM.getState() || orderState == OrderEnum.REFUSED.getState()) {
            int award = mallOrderDO.getCredit() * mallOrderDO.getCount();
            userEmployeePointsDO.setAward(orderState == OrderEnum.CONFIRM.getState() ? -award : award);
            userEmployeePointsDO.setEmployeeId(mallOrderDO.getEmployee_id());
            String confirmReason = "兑换商品-" + mallOrderDO.getTitle() + "-数量：" + mallOrderDO.getCount();
            String refuseReason = "退回积分-" + mallOrderDO.getTitle() + "-数量：" + mallOrderDO.getCount();
            userEmployeePointsDO.setReason(orderState == OrderEnum.CONFIRM.getState() ? confirmReason : refuseReason);
        } else {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_UNSUPPORTED_STATE);
        }

        return userEmployeePointsDao.addData(userEmployeePointsDO);
    }

    /**
     * 乐观锁减库存
     *
     * @param mallGoodsInfoDO 商品信息
     * @param orderForm       订单扣减信息
     * @author cjm
     * @date 2018/10/19
     */
    private void minusStockByLock(MallGoodsInfoDO mallGoodsInfoDO, OrderForm orderForm) throws BIZException {
        goodsService.updateStockAndExchangeNumByLock(mallGoodsInfoDO, -orderForm.getCount(), GoodsEnum.UPSHELF.getState(), 1);
    }

    private MallOrderDO insertOrder(MallGoodsInfoDO mallGoodsInfoDO, UserEmployeeDO userEmployeeDO, OrderForm orderForm,
                                    MallMailAddress address) {
        MallOrderDO mallOrderDO = initOrderDO(mallGoodsInfoDO, userEmployeeDO, orderForm,address);
        return orderDao.addData(mallOrderDO);
    }

    private MallOrderDO initOrderDO(MallGoodsInfoDO mallGoodsInfoDO, UserEmployeeDO userEmployeeDO, OrderForm orderForm,
                                    MallMailAddress address) {
        MallOrderDO mallOrderDO = new MallOrderDO();
        String orderId = createOrderId();
        mallOrderDO.setOrder_id(orderId);
        mallOrderDO.setEmployee_id(userEmployeeDO.getId());
        mallOrderDO.setName(userEmployeeDO.getCname());
        mallOrderDO.setGoods_id(mallGoodsInfoDO.getId());
        mallOrderDO.setCompany_id(mallGoodsInfoDO.getCompany_id());
        mallOrderDO.setCredit(mallGoodsInfoDO.getCredit());
        mallOrderDO.setTitle(mallGoodsInfoDO.getTitle());
        mallOrderDO.setPic_url(mallGoodsInfoDO.getPic_url());
        mallOrderDO.setCount(orderForm.getCount());
        if(address!=null&&address.getId()!=null){
            mallOrderDO.setMailId(address.getId());
        }
        logger.info("mallOrderDO:{}", mallOrderDO);
        return mallOrderDO;
    }

    /**
     * 计算订单id
     *
     * @return 返回订单id
     * @author cjm
     * @date 2018/10/19
     */
    private String createOrderId() {
        DateTime dateTime = DateTime.now();
        DateTime allDay = dateTime.millisOfDay().withMaximumValue();
        long expireTime = new Duration(dateTime, allDay).getStandardSeconds();
        String current = redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_ID.toString(), null);
        if (StringUtils.isNullOrEmpty(current)) {
            current = redisClient.incrIfNotExist(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_ID.toString(), null) + "";
            redisClient.expire(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_ID.toString(), null, (int) expireTime);
        } else {
            current = redisClient.incrIfNotExist(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_ID.toString(), null) + "";
        }
        int year = dateTime.getYear() % 100;
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        logger.info("allDay:{}, expireTime:{}", allDay, expireTime);
        return String.valueOf((((year * 100) + month) * 100 + day) * 100000L + Long.parseLong(current));
    }

    @Transactional(rollbackFor = Exception.class)
    protected List<Integer> handleUpdatedOrder(MallGoodsOrderUpdateForm updateForm) throws TException {
        // 检验重复提交
        batchCheckHrDuplicateCommit(updateForm);
        // 检验需要修改的订单状态是否是合法状态
        checkOrderOperationState(updateForm.getState());
        // 获取所有订单
        List<MallOrderDO> orderList = orderDao.getOrdersByIds(updateForm.getIds());
        // 检验订单修改前的状态和要修改的状态
        checkOrderLimit(orderList, updateForm);
        // 更新订单状态
        int rows = orderDao.updateOrderStateByIdAndCompanyId(updateForm);
        if (rows != updateForm.getIds().size()) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
        }
        // 处理不发放订单
        Map<Integer, UserEmployeePointsRecordDO> recordDOMap = handleRefuseOrder(updateForm, orderList);
        // 插入hr操作记录
        batchInsertOperationRecord(recordDOMap, updateForm);
        // 删除redis锁
        batchDelOrderRedisLock(updateForm.getIds(), updateForm.getHr_id());
        // 获取员工employeeIds
        return getEmployeeIdsByPointsRecord(recordDOMap);
    }

    private List<Integer> getEmployeeIdsByPointsRecord(Map<Integer, UserEmployeePointsRecordDO> recordDOMap) {
        List<Integer> employeeIds = new ArrayList<>();
        Set<Integer> keyset = recordDOMap.keySet();
        for (Integer orderId : keyset) {
            employeeIds.add((int) recordDOMap.get(orderId).getEmployeeId());
        }
        return employeeIds;
    }

    private Map<Integer, UserEmployeePointsRecordDO> handleRefuseOrder(MallGoodsOrderUpdateForm updateForm, List<MallOrderDO> orderList) throws TException {
        Map<Integer, UserEmployeePointsRecordDO> recordDOMap = new HashMap<>(1 >> 4);
        if (updateForm.getState() == OrderEnum.REFUSED.getState()) {
            // 商品信息修改，例如兑换次数，兑换数量
            goodsService.batchUpdateGoodInfo(orderList, 1);
            // 返还积分
            recordDOMap = batchUpdateAward(orderList);
        } else if (updateForm.getState() != OrderEnum.CONFIRM.getState()) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_UNSUPPORTED_STATE);
        }
        return recordDOMap;
    }

    private void batchInsertOperationRecord(Map<Integer, UserEmployeePointsRecordDO> recordDOS, MallGoodsOrderUpdateForm updateForm) {
        // 批量确认订单
        if (recordDOS.size() == 0) {
            List<MallOrderOperationDO> orderOperationDOS = new ArrayList<>();
            updateForm.getIds().forEach(id -> orderOperationDOS.add(initOperationRecord(0, id, updateForm.getHr_id(), updateForm.getState())));
            orderOperationDao.addAllData(orderOperationDOS);
            return;
        }
        // 批量拒绝订单
        List<MallOrderOperationDO> orderOperationDOS = new ArrayList<>();
        updateForm.getIds().forEach(id -> orderOperationDOS.add(initOperationRecord(recordDOS.get(id).getId(), id, updateForm.getHr_id(), updateForm.getState())));
        orderOperationDao.addAllData(orderOperationDOS);
    }

    /**
     * todo 由于目前只支持单个不发放，所以这样写暂时不会有效率问题，目前打算使用create.insert().set().newRecord()解决batchinsert回填主键的问题，暂时没有时间做
     *
     * @param orderList 订单信息
     * @author cjm
     * @date 2018/10/22
     */
    private Map<Integer, UserEmployeePointsRecordDO> batchUpdateAward(List<MallOrderDO> orderList) throws TException {
        Map<Integer, UserEmployeePointsRecordDO> map = new HashMap<>(1 >> 4);
        List<Integer> employeeIds = orderList.stream().map(MallOrderDO::getEmployee_id).collect(Collectors.toList());
        // 获取历史库和员工库的所有员工信息
        List<UserEmployeeDO> userEmployeeDOS = userEmployeeDao.getEmployeeByIds(employeeIds);
        List<UserEmployeeDO> historyUserEmployeeDOS = new ArrayList<>();
        if (employeeIds.size() != userEmployeeDOS.size()) {
            historyUserEmployeeDOS = historyUserEmployeeDao.getHistoryEmployeeByIds(employeeIds);
        }
        Map<Integer, UserEmployeeDO> userEmployeeDOMap = getIdEmployeeMap(userEmployeeDOS);
        Map<Integer, UserEmployeeDO> historyEmployeeDOMap = getIdEmployeeMap(historyUserEmployeeDOS);
        for (MallOrderDO orderDO : orderList) {
            UserEmployeeDO userEmployeeDO = userEmployeeDOMap.get(orderDO.getEmployee_id());
            if (userEmployeeDO == null) {
                userEmployeeDO = historyEmployeeDOMap.get(orderDO.getEmployee_id());
                if (userEmployeeDO == null) {
                    continue;
                }
            }
            UserEmployeeDO tempEmployee = userEmployeeDO;
            UserEmployeePointsRecordDO userEmployeePointsDO = insertAwardRecord(orderDO, OrderEnum.REFUSED.getState());
            map.put(orderDO.getId(), userEmployeePointsDO);
            updateAwardByLock(tempEmployee, orderDO.getCount() * orderDO.getCredit(), 1);
            // 发送积分变动消息模板
            String templateTile = "您兑换的【" + orderDO.getTitle() + "】未成功发放，积分已退还到您的账户";
            String url = getTemplateJumpUrlByKey("mall.refund.template.url");
            templateService.sendAwardTemplate(tempEmployee.getSysuserId(), tempEmployee.getCompanyId(), Constant.TEMPLATES_AWARD_RETURN_NOTICE_TPL, templateTile,
                    "0", orderDO.getCount() * orderDO.getCredit() + "积分", "0",
                    tempEmployee.getAward() + orderDO.getCount() * orderDO.getCredit() + "积分", REFUSE_REMARK, url);
        }
        return map;
    }

    private void updateAward(UserEmployeeDO userEmployeeDO, int payCredit) throws BIZException {
        updateAwardByLock(userEmployeeDO, payCredit, 1);
    }

    private UserEmployeeDO updateAwardByLock(UserEmployeeDO userEmployeeDO, int payCredit, int retryTimes) throws BIZException {
        DbUtils.checkRetryTimes(retryTimes);
        int employeeId = userEmployeeDO.getId();
        int oldAward = userEmployeeDO.getAward();
        int row = userEmployeeDao.addAward(employeeId, oldAward + payCredit, oldAward);
        if (row == 0) {
            userEmployeeDO = userEmployeeDao.getEmployeeById(employeeId);
            return updateAwardByLock(userEmployeeDO, payCredit, ++retryTimes);
        }
        return userEmployeeDO;
    }

    private Map<Integer, UserEmployeeDO> getIdEmployeeMap(List<UserEmployeeDO> userEmployeeDOS) {
        Map<Integer, UserEmployeeDO> idEmployeeMap = new HashMap<>(1 >> 4);
        for (UserEmployeeDO userEmployeeDO : userEmployeeDOS) {
            // record.id是主键，不会重复
            idEmployeeMap.put(userEmployeeDO.getId(), userEmployeeDO);
        }
        return idEmployeeMap;
    }

    private void batchDelOrderRedisLock(List<Integer> orderIds, int hrId) {
        for (Integer orderId : orderIds) {
            redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MALL_ORDER_OPERATION.toString(),
                    String.valueOf(orderId), String.valueOf(hrId));
        }
    }

    private void batchCheckHrDuplicateCommit(MallGoodsOrderUpdateForm updateForm) throws BIZException {
        for (int orderId : updateForm.getIds()) {
            checkHrDuplicateCommit(orderId, updateForm.getHr_id());
        }
    }

    private void checkOrderOperationState(int state) throws BIZException {
        if (state < OrderEnum.CONFIRM.getState() || state > OrderEnum.REFUSED.getState()) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_TYPE_UNEXISTS);
        }
    }

    /**
     * 检验操作的订单是否是本公司下的订单
     *
     * @param orderList  订单list
     * @param updateForm 发放、不发放请求提交信息
     * @author cjm
     * @date 2018/10/16
     */
    private void checkOrderLimit(List<MallOrderDO> orderList, MallGoodsOrderUpdateForm updateForm) throws BIZException {
        List<Integer> companyIdList = new ArrayList<>();
        for (MallOrderDO mallOrderDO : orderList) {
            companyIdList.add(mallOrderDO.getCompany_id());
            // 如果订单状态不是未确认，hr不能操作
            if (mallOrderDO.getState() != OrderEnum.UNCONFIRM.getState()) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_UNSUPPORTED_STATE);
            }
        }
        int companyId = updateForm.getCompany_id();
        for (Integer id : companyIdList) {
            if (id != companyId) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_ORDER_OPERATION_LIMIT);
            }
        }
    }

    private UserEmployeeDO getUserEmployeeById(int employeeId) throws BIZException {
        UserEmployeeDO userEmployeeDO = userEmployeeDao.getEmployeeById(employeeId);
        if (userEmployeeDO == null) {
            userEmployeeDO = historyUserEmployeeDao.getUserEmployeeById(employeeId);
            if (userEmployeeDO == null) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.USER_NOTEXIST);
            }
        }
        return userEmployeeDO;
    }

    public MallMailAddressForm getAddressById(Integer id){
        MallMailAddress address = addressDao.fetchOneById(id);
        MallMailAddressForm form = new MallMailAddressForm();

        //根据城市code获取城市信息
        List<Integer> codes = Lists.newArrayList(address.getProvince(),address.getCity(),address.getRegion());
        List<DictCity> cities = dictCityDao.getDictCitiesByCodes(codes);
        Map<Integer,DictCity> citiesMap =
                cities.stream().collect(Collectors.toMap(DictCity::getCode,city->city));
        BeanUtils.copyProperties(address,form);

        if(cities!=null&&cities.size()>0){
            DictCity province = citiesMap.get(form.getProvince());
            DictCity city = citiesMap.get(form.getCity());
            DictCity region = citiesMap.get(form.getRegion());
            form.setProvinceName(province!=null?province.getName():null);
            form.setCityName(city!=null?city.getName():null);
            form.setRegionName(region!=null?region.getName():null);
        }

        return form;
    }

}
