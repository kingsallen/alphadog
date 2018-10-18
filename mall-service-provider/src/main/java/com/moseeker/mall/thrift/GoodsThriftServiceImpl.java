package com.moseeker.mall.thrift;

import com.alibaba.fastjson.JSON;
import com.moseeker.mall.vo.MallGoodsInfoVO;
import com.moseeker.mall.service.GoodsService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.mall.service.GoodsService.Iface;
import com.moseeker.thrift.gen.mall.struct.*;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-10-12 16:21
 **/
@Service
public class GoodsThriftServiceImpl implements Iface {

    private final GoodsService goodsService;

    @Autowired
    public GoodsThriftServiceImpl(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public Map<String, String> getGoodsList(GoodSearchForm goodSearchForm) throws TException {
        try {
            return goodsService.getGoodsList(goodSearchForm);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void addGood(MallGoodsInfoForm mallGoodsInfoForm) throws TException {
        try {
            goodsService.addGood(mallGoodsInfoForm);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void editGood(MallGoodsInfoForm mallGoodsInfoForm) throws TException {
        try {
            goodsService.editGood(mallGoodsInfoForm);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Integer> updateGoodState(MallGoodsStateForm mallGoodsStateForm) throws TException {
        try {
            return goodsService.updateGoodState(mallGoodsStateForm);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public int updateGoodStock(MallGoodsStockForm mallGoodsStockForm) throws TException {
        try {
            return goodsService.updateGoodStock(mallGoodsStockForm);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String getGoodDetail(MallGoodsIdForm mallGoodsIdForm) throws TException {
        try {
            MallGoodsInfoVO mallGoodsInfoVO = goodsService.getGoodDetail(mallGoodsIdForm);
            return JSON.toJSONString(mallGoodsInfoVO);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
}
