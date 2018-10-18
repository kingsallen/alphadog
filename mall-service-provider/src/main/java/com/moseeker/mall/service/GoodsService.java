package com.moseeker.mall.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.malldb.MallGoodsInfoDao;
import com.moseeker.baseorm.db.malldb.tables.records.MallGoodsInfoRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.mall.annotation.OnlyEmployee;
import com.moseeker.mall.annotation.OnlySuperAccount;
import com.moseeker.mall.utils.PaginationUtils;
import com.moseeker.mall.vo.MallGoodsInfoVO;
import com.moseeker.mall.constant.GoodsEnum;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.mall.struct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-10-12 16:24
 **/
@Service
public class GoodsService {

    private Logger logger = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    private MallGoodsInfoDao mallGoodsInfoDao;

    private static final int TRY_TIMES = 3;

    /**
     * 获取商品详情
     * @param mallGoodsIdForm 商品id
     * @author  cjm
     * @date  2018/10/14
     * @return 商品信息
     */
    public MallGoodsInfoVO getGoodDetail(MallGoodsIdForm mallGoodsIdForm) throws BIZException {
        MallGoodsInfoDO mallGoodsInfoDO = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(mallGoodsIdForm.getGood_id(), mallGoodsIdForm.getCompany_id());
        checkGoodsExist(mallGoodsInfoDO);
        MallGoodsInfoVO mallGoodsInfoVO = new MallGoodsInfoVO();
        BeanUtils.copyProperties(mallGoodsInfoDO, mallGoodsInfoVO);
        return mallGoodsInfoVO;
    }

    @OnlySuperAccount
    @Transactional(rollbackFor = Exception.class)
    public int updateGoodStock(MallGoodsStockForm mallGoodsStockForm) throws BIZException {
        MallGoodsInfoDO mallGoodsInfoDO = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(mallGoodsStockForm.getGood_id(), mallGoodsStockForm.getCompany_id());
        return updateGoodStockByLock(mallGoodsInfoDO, mallGoodsStockForm.getStock(), 1);
    }

    @OnlySuperAccount
    @Transactional(rollbackFor = Exception.class)
    public List<Integer> updateGoodState(MallGoodsStateForm mallGoodsStateForm) throws BIZException {
        return updateGoodStateByIds(mallGoodsStateForm);
    }

    @OnlySuperAccount
    @Transactional(rollbackFor = Exception.class)
    public void editGood(MallGoodsInfoForm mallGoodsInfoForm) throws BIZException {
        MallGoodsInfoDO mallGoodsInfoDO = new MallGoodsInfoDO();
        BeanUtils.copyProperties(mallGoodsInfoForm, mallGoodsInfoDO);
        int row = mallGoodsInfoDao.editGood(mallGoodsInfoDO);
        if(row == 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_GOODS_UNEXISTS);
        }
    }

    @OnlySuperAccount
    @Transactional(rollbackFor = Exception.class)
    @OnlyEmployee
    public void addGood(MallGoodsInfoForm mallGoodsInfoForm) {
        MallGoodsInfoRecord record = new MallGoodsInfoRecord();
        BeanUtils.copyProperties(mallGoodsInfoForm, record);
        logger.info("record:{}",record);
        mallGoodsInfoDao.addRecord(record);
    }

    public Map<String,String> getGoodsList(GoodSearchForm goodSearchForm) {
        int goodState = goodSearchForm.getState();
        if(GoodsEnum.ALL.getState() == goodState){
            return getAllGoodsMap(goodSearchForm);
        }else {
            return getSpecificStateGoodsMap(goodSearchForm);
        }
    }

    public int updateGoodStateByCompanyId(int companyId, int state) throws BIZException {
        return mallGoodsInfoDao.updateStateByCompanyId(companyId, (byte)state);
    }

    /**
     * 获取指定状态下的商品列表
     * @param   goodSearchForm 商品列表搜索信息
     * @author  cjm
     * @date  2018/10/18
     * @return   商品列表和总行数
     */
    private Map<String, String> getSpecificStateGoodsMap(GoodSearchForm goodSearchForm) {
        int totalRows = mallGoodsInfoDao.getTotalRowsByCompanyIdAndState(goodSearchForm.getCompany_id(), goodSearchForm.getState());
        int startIndex = PaginationUtils.getStartIndex(goodSearchForm.getPage_size(), goodSearchForm.getPage_number(), totalRows);
        List<MallGoodsInfoDO> goodsList = mallGoodsInfoDao.getGoodsListByPageAndState(goodSearchForm.getCompany_id(),
                startIndex, goodSearchForm.getPage_size(), goodSearchForm.getState());
        Map<String, String> resultMap = new HashMap<>(1 >> 4);
        resultMap.put("total_row", totalRows + "");
        resultMap.put("goods_list", JSON.toJSONString(goodsList));
        return resultMap;
    }

    /**
     * 获取所有商品状态的商品列表
     * @param   goodSearchForm 商品列表搜索信息
     * @author  cjm
     * @date  2018/10/18
     * @return   商品列表和总行数
     */
    private Map<String, String> getAllGoodsMap(GoodSearchForm goodSearchForm) {
        int totalRows = mallGoodsInfoDao.getTotalRowsByCompanyId(goodSearchForm.getCompany_id());
        int startIndex = PaginationUtils.getStartIndex(goodSearchForm.getPage_size(), goodSearchForm.getPage_number(), totalRows);
        List<MallGoodsInfoDO> goodsList = mallGoodsInfoDao.getGoodsListByPage(goodSearchForm.getCompany_id(), startIndex, goodSearchForm.getPage_size());
        Map<String, String> resultMap = new HashMap<>(1 >> 4);
        resultMap.put("total_row", totalRows + "");
        resultMap.put("goods_list", JSON.toJSONString(goodsList));
        return resultMap;
    }

    private void checkGoodsExist(MallGoodsInfoDO mallGoodsInfoDO) throws BIZException {
        if(mallGoodsInfoDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_GOODS_UNEXISTS);
        }
    }

    /**
     * 乐观锁更新商品库存
     * @param mallGoodsInfoDO 当前商品信息
     * @param updatedStock 要更新的库存数
     * @param retryTimes 重试次数 不大于三次
     * @author  cjm
     * @date  2018/10/18
     * @return 返回当前商品剩余库存
     */
    private int updateGoodStockByLock(MallGoodsInfoDO mallGoodsInfoDO, int updatedStock, int retryTimes) throws BIZException {
        checkRetryTimes(retryTimes);
        checkGoodsExist(mallGoodsInfoDO);
        checkGoodsState(mallGoodsInfoDO);
        int row = mallGoodsInfoDao.updateGoodStock(mallGoodsInfoDO, updatedStock);
        if(row == 0){
            mallGoodsInfoDO = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(mallGoodsInfoDO.getId(), mallGoodsInfoDO.getCompany_id());
            return updateGoodStockByLock(mallGoodsInfoDO, updatedStock, ++retryTimes);
        }else {
            MallGoodsInfoDO updatedGoods = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(mallGoodsInfoDO.getId(), mallGoodsInfoDO.getCompany_id());
            if(updatedGoods.getStock() < 0){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_STOCK_CANNOT_MINUS);
            }
            return updatedGoods.getStock();
        }

    }

    /**
     * 批量更新商品上下架状态
     * @param  mallGoodsStateForm 商品修改状态信息
     * @author  cjm
     * @date  2018/10/18
     * @return   修改成功的商品ids
     */
    private List<Integer> updateGoodStateByIds(MallGoodsStateForm mallGoodsStateForm) throws BIZException {
        List<Integer> goodIds = mallGoodsStateForm.getIds();
        int companyId = mallGoodsStateForm.getCompany_id();
        List<MallGoodsInfoDO> mallGoodsInfoDOS = mallGoodsInfoDao.getGoodDetailByGoodsIdAndCompanyId(goodIds, companyId);
        for (MallGoodsInfoDO mallGoodsInfoDO : mallGoodsInfoDOS) {
            checkGoodsExist(mallGoodsInfoDO);
        }
        int rows = mallGoodsInfoDao.updateGoodStateByIds(goodIds, mallGoodsStateForm.getCompany_id(), mallGoodsStateForm.getState());
        if(rows != goodIds.size()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
        }else {
            return goodIds;
        }
    }

    private void checkGoodsState(MallGoodsInfoDO mallGoodsInfoDO) throws BIZException {
        if(mallGoodsInfoDO.getState() == GoodsEnum.UPSHELF.getState()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_GOODS_NEED_DOWNSHELF);
        }
    }

    private void checkRetryTimes(int retryTimes) throws BIZException {
        if(retryTimes >= TRY_TIMES){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
        }
    }
}
