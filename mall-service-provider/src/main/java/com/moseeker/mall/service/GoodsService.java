package com.moseeker.mall.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.malldb.MallGoodsInfoDao;
import com.moseeker.baseorm.db.malldb.tables.records.MallGoodsInfoRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.mall.annotation.OnlyEmployee;
import com.moseeker.mall.utils.PaginationUtils;
import com.moseeker.mall.vo.MallGoodsInfoVO;
import com.moseeker.mall.constant.GoodsEnum;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.mall.struct.GoodSearchForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-10-12 16:24
 **/
@Service
public class GoodsService {

    @Autowired
    private MallGoodsInfoDao mallGoodsInfoDao;

    private static final int TRY_TIMES = 3;

    /**
     * 获取商品详情
     * @param goodId 商品id
     * @param companyId 公司id
     * @author  cjm
     * @date  2018/10/14
     * @return 商品信息
     */
    public MallGoodsInfoVO getGoodDetail(int goodId, int companyId) throws BIZException {
        MallGoodsInfoDO mallGoodsInfoDO = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(goodId, companyId);
        checkGoodsExist(mallGoodsInfoDO);
        MallGoodsInfoVO mallGoodsInfoVO = new MallGoodsInfoVO();
        BeanUtils.copyProperties(mallGoodsInfoDO, mallGoodsInfoVO);
        return mallGoodsInfoVO;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateGoodStock(int goodId, int companyId, int stock) throws BIZException {
        return updateGoodStockByLock(goodId, companyId, stock, 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateGoodState(int goodId, int companyId, int state) throws BIZException {
        return updateGoodStateByLock(goodId, companyId, state, 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editGood(MallGoodsInfoDO mallGoodsInfoDO) throws BIZException {
        int row = mallGoodsInfoDao.editGood(mallGoodsInfoDO);
        if(row == 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_GOODS_UNEXISTS);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @OnlyEmployee
    public void addGood(MallGoodsInfoDO mallGoodsInfoDO) {
        MallGoodsInfoRecord record = new MallGoodsInfoRecord();
        BeanUtils.copyProperties(mallGoodsInfoDO, record);
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

    private int updateGoodStockByLock(int goodId, int companyId, int stock, int retryTimes) throws BIZException {
        checkRetryTimes(retryTimes);
        MallGoodsInfoDO mallGoodsInfoDO = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(goodId, companyId);
        checkGoodsExist(mallGoodsInfoDO);
        checkGoodsState(mallGoodsInfoDO);
        int row = mallGoodsInfoDao.updateGoodStock(mallGoodsInfoDO, stock);
        if(row == 0){
            return updateGoodStockByLock(goodId, companyId, stock, ++retryTimes);
        }else {
            MallGoodsInfoDO updatedGoods = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(goodId, companyId);
            return updatedGoods.getStock();
        }

    }

    private int updateGoodStateByLock(int goodId, int companyId, int state, int retryTimes) throws BIZException {
        checkRetryTimes(retryTimes);
        MallGoodsInfoDO mallGoodsInfoDO = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(goodId, companyId);
        checkGoodsExist(mallGoodsInfoDO);
        checkGoodsState(mallGoodsInfoDO);
        int row = mallGoodsInfoDao.updateGoodState(mallGoodsInfoDO, state);
        if(row == 0){
            return updateGoodStateByLock(goodId, companyId, state, ++retryTimes);
        }else {
            MallGoodsInfoDO updatedGoods = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(goodId, companyId);
            return updatedGoods.getState();
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
