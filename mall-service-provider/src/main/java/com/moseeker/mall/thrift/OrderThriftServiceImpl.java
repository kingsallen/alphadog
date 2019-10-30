package com.moseeker.mall.thrift;

import com.moseeker.mall.service.OrderService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.mall.service.OrderService.Iface;
import com.moseeker.thrift.gen.mall.struct.BaseMallForm;
import com.moseeker.thrift.gen.mall.struct.MallGoodsOrderUpdateForm;
import com.moseeker.thrift.gen.mall.struct.OrderForm;
import com.moseeker.thrift.gen.mall.struct.OrderSearchForm;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-10-12 16:21
 **/
@Service
public class OrderThriftServiceImpl implements Iface{

    private static Logger logger = LoggerFactory.getLogger(OrderThriftServiceImpl.class);

    private final OrderService orderService;

    @Autowired
    public OrderThriftServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Map<String, String> getCompanyOrderList(OrderSearchForm orderSearchForm) throws TException {
        try {
            return orderService.getCompanyOrderList(orderSearchForm);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String getEmployeeOrderList(BaseMallForm baseMallForm) throws TException {
        try {
            return orderService.getEmployeeOrderList(baseMallForm);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void confirmOrder(OrderForm orderForm) throws BIZException, TException {
        try {
            orderService.confirmOrder(orderForm);
        }catch (BIZException e){
            logger.error(e.getMessage(),e);
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void updateOrder(MallGoodsOrderUpdateForm updateForm) throws BIZException, TException {
        try {
            orderService.updateOrder(updateForm);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String exportOrder(BaseMallForm baseMallForm) throws BIZException, TException {
        try {
            return orderService.exportOrder(baseMallForm);
        }catch (Exception e){
            throw e;
        }
    }
}
