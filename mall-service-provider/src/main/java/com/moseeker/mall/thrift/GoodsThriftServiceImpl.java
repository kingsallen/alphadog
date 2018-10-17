package com.moseeker.mall.thrift;

import com.alibaba.fastjson.JSON;
import com.moseeker.mall.vo.MallGoodsInfoVO;
import com.moseeker.mall.service.GoodsService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.mall.service.GoodsService.Iface;
import com.moseeker.thrift.gen.mall.struct.GoodSearchForm;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addGood(MallGoodsInfoDO mallGoodsInfoDO) throws TException {
        try {
            goodsService.addGood(mallGoodsInfoDO);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void editGood(MallGoodsInfoDO mallGoodsInfoDO) throws TException {
        try {
            goodsService.editGood(mallGoodsInfoDO);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public int updateGoodState(int goodId, int companyId, int state) throws TException {
        try {
            return goodsService.updateGoodState(goodId, companyId, state);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public int updateGoodStock(int goodId, int companyId, int stock) throws TException {
        try {
            return goodsService.updateGoodStock(goodId, companyId, stock);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String getGoodDetail(int goodId, int companyId) throws TException {
        try {
            MallGoodsInfoVO mallGoodsInfoVO = goodsService.getGoodDetail(goodId, companyId);
            return JSON.toJSONString(mallGoodsInfoVO);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
}
