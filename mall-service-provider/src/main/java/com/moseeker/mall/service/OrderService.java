package com.moseeker.mall.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.malldb.MallGoodsOrderDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.mall.constant.OrderEnum;
import com.moseeker.mall.utils.PaginationUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import com.moseeker.thrift.gen.mall.struct.OrderForm;
import com.moseeker.thrift.gen.mall.struct.OrderSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    /**
     * hr获取公司下的订单 todo 关键词搜索还没做，并且需要数据组装
     * @param  orderSearchForm 订单搜索实体
     * @author  cjm
     * @date  2018/10/16
     * @return 返回订单list和总行数
     */
    public Map<String,String> getCompanyOrderList(OrderSearchForm orderSearchForm) {
        int totalRows = orderDao.getTotalRowsByCompanyId(orderSearchForm.getCompany_id());
        int startIndex = PaginationUtils.getStartIndex(orderSearchForm.getPage_size(), orderSearchForm.getPage_number(), totalRows);
        List<MallOrderDO> orderList = orderDao.getOrdersListByPage(orderSearchForm.getCompany_id(), startIndex, orderSearchForm.getPage_size());
        Map<String, String> resultMap = new HashMap<>(1 >> 4);
        resultMap.put("total_row", totalRows + "");
        resultMap.put("orders", JSON.toJSONString(orderList));
        return resultMap;
    }

    /**
     * 确认兑换 todo 做防止重复提交限制 消息模板
     * @param orderForm 确定兑换请求实体
     * @author  cjm
     * @date  2018/10/16
     */
    public void confirmOrder(OrderForm orderForm) throws BIZException {
        // todo redis防止重复提交

    }

    /**
     * 确认发放或不发放 todo 做防止重复提交限制 消息模板
     * @param ids 订单ids
     * @param companyId 公司id
     * @param state 需要修改的订单状态
     * @author  cjm
     * @date  2018/10/16
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(List<Integer> ids, int companyId, int state) throws BIZException {
        // todo redis防止重复提交
        checkOrderOperationState(state);
        List<MallOrderDO> orderList = orderDao.getOrdersByIds(ids);
        checkOrderLimit(orderList, companyId);
        if(state == OrderEnum.CONFIRM.getState()){
            int rows = orderDao.updateOrderStateByIdAndCompanyId(ids, companyId, state);
            if(rows != ids.size()){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
            }
        }else {
            // todo 不发放请求，需要返回积分

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

    /**
     * 员工获取积分兑换记录 todo 需要数据组装
     * @param  employeeId 员工id
     * @author  cjm
     * @date  2018/10/16
     * @return  兑换记录list
     */
    public String getEmployeeOrderList(int employeeId) {
        List<MallOrderDO> orderList = orderDao.getOrdersListByEmployeeId(employeeId);
        return JSON.toJSONString(orderList);
    }
}
