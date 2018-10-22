package com.moseeker.mall.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.malldb.MallGoodsInfoDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.mall.annotation.OnlySuperAccount;
import com.moseeker.mall.utils.DbUtils;
import com.moseeker.mall.utils.HtmlFilterUtils;
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

    /**
     * 获取商品详情
     * @param mallGoodsIdForm 商品id
     * @author  cjm
     * @date  2018/10/14
     * @return 商品信息
     */
    public MallGoodsInfoVO getGoodDetail(MallGoodsIdForm mallGoodsIdForm) throws BIZException {
        logger.info("=================mallGoodsIdForm:{}", mallGoodsIdForm);
        MallGoodsInfoDO mallGoodsInfoDO = getGoodDetailByGoodIdAndCompanyId(mallGoodsIdForm.getGood_id(), mallGoodsIdForm.getCompany_id());
        checkGoodsExist(mallGoodsInfoDO);
        MallGoodsInfoVO mallGoodsInfoVO = new MallGoodsInfoVO();
        BeanUtils.copyProperties(mallGoodsInfoDO, mallGoodsInfoVO);
        return mallGoodsInfoVO;
    }

    @OnlySuperAccount
    @Transactional(rollbackFor = Exception.class)
    public int updateGoodStock(MallGoodsStockForm mallGoodsStockForm) throws BIZException {
        logger.info("=================mallGoodsStockForm:{}", mallGoodsStockForm);
        return updateGoodStockOnDownshelfState(mallGoodsStockForm.getGood_id(), mallGoodsStockForm.getCompany_id(), mallGoodsStockForm.getStock());
    }

    @OnlySuperAccount
    @Transactional(rollbackFor = Exception.class)
    public List<Integer> updateGoodState(MallGoodsStateForm mallGoodsStateForm) throws BIZException {
        logger.info("=================mallGoodsStateForm:{}", mallGoodsStateForm);
        return updateGoodStateByIds(mallGoodsStateForm);
    }

    @OnlySuperAccount
    @Transactional(rollbackFor = Exception.class)
    public void editGood(MallGoodsInfoForm mallGoodsInfoForm) throws BIZException {
        logger.info("=================mallGoodsInfoForm:{}", mallGoodsInfoForm);
        mallGoodsInfoForm.setDetail(HtmlFilterUtils.filterSafe(mallGoodsInfoForm.getDetail()));
        MallGoodsInfoDO mallGoodsInfoDO = new MallGoodsInfoDO();
        BeanUtils.copyProperties(mallGoodsInfoForm, mallGoodsInfoDO);
        checkGoodsState(mallGoodsInfoDO, GoodsEnum.DOWNSHELF.getState());
        int row = mallGoodsInfoDao.editGood(mallGoodsInfoDO);
        if(row == 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_GOODS_UNEXISTS);
        }
    }

    @OnlySuperAccount
    @Transactional(rollbackFor = Exception.class)
    public void addGood(MallGoodsInfoForm mallGoodsInfoForm){
        logger.info("=================mallGoodsInfoForm:{}", mallGoodsInfoForm);
        mallGoodsInfoForm.setDetail(HtmlFilterUtils.filterSafe(mallGoodsInfoForm.getDetail()));
        MallGoodsInfoDO mallGoodsInfoDO = new MallGoodsInfoDO();
        BeanUtils.copyProperties(mallGoodsInfoForm, mallGoodsInfoDO);
        mallGoodsInfoDao.addData(mallGoodsInfoDO);
    }

    public Map<String,String> getGoodsList(GoodSearchForm goodSearchForm) {
        logger.info("=================goodSearchForm:{}", goodSearchForm);
        int goodState = goodSearchForm.getState();
        if(GoodsEnum.ALL.getState() == goodState){
            return getAllGoodsMap(goodSearchForm);
        }else {
            return getSpecificStateGoodsMap(goodSearchForm);
        }
    }

    public int updateGoodStateByCompanyId(int companyId, int state) {
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
     * 在下架状态下更新库存
     * @param goodId 商品id
     * @param companyId 公司id
     * @param updateStock 要更新的库存
     * @author  cjm
     * @date  2018/10/19
     * @return 返回当前商品剩余库存
     */
    private int updateGoodStockOnDownshelfState(int goodId, int companyId, int updateStock) throws BIZException {
        return updateGoodStockByLock(goodId, companyId, updateStock, GoodsEnum.DOWNSHELF.getState(), 1);
    }

    /**
     * 乐观锁更新商品库存
     * @param goodId 商品id
     * @param companyId 公司id
     * @param updateStock 要更新的库存
     * @param retryTimes 重试次数 不大于三次
     * @author  cjm
     * @date  2018/10/18
     * @return 返回当前商品剩余库存
     */
    public int updateGoodStockByLock(int goodId, int companyId, int updateStock, int state, int retryTimes) throws BIZException {
        DbUtils.checkRetryTimes(retryTimes);
        MallGoodsInfoDO mallGoodsInfoDO = getGoodById(goodId, companyId, state);
        int row = mallGoodsInfoDao.updateGoodStock(mallGoodsInfoDO, updateStock);
        if(row == 0){
            return updateGoodStockByLock(goodId, companyId, updateStock, state, ++retryTimes);
        }else {
            MallGoodsInfoDO updatedGoods = getGoodDetailByGoodIdAndCompanyId(mallGoodsInfoDO.getId(), mallGoodsInfoDO.getCompany_id());
            // 商品库存在0~99999之间
            if(updatedGoods.getStock() < 0 || updatedGoods.getStock() > 99999){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_STOCK_LIMIT);
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
        if(goodIds.size() != mallGoodsInfoDOS.size()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
        int rows = mallGoodsInfoDao.updateGoodStateByIds(goodIds, mallGoodsStateForm.getCompany_id(), mallGoodsStateForm.getState());
        if(rows != goodIds.size()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
        }else {
            return goodIds;
        }
    }

    private void checkGoodsState(MallGoodsInfoDO mallGoodsInfoDO, int state) throws BIZException {
        if(mallGoodsInfoDO.getState() != state){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_GOODS_NEED_DOWNSHELF);
        }
    }

    /**
     * 获取有效的商品
     * @param goodId 商品id
     * @param companyId 公司id
     * @author  cjm
     * @date  2018/10/18
     * @return 商品信息实体
     */
    public MallGoodsInfoDO getUpshelfGoodById(int goodId, int companyId) throws BIZException {
        return getGoodById(goodId, companyId, GoodsEnum.UPSHELF.getState());
    }

    /**
     * 获取指定状态的商品
     * @param goodId 商品id
     * @param companyId 公司id
     * @param state 商品上下架状态
     * @author  cjm
     * @date  2018/10/18
     * @return 商品信息实体
     */
    private MallGoodsInfoDO getGoodById(int goodId, int companyId, int state) throws BIZException {
        MallGoodsInfoDO mallGoodsInfoDO = getGoodDetailByGoodIdAndCompanyId(goodId, companyId);
        checkGoodsExist(mallGoodsInfoDO);
        checkGoodsState(mallGoodsInfoDO, state);
        return mallGoodsInfoDO;
    }

    private MallGoodsInfoDO getGoodDetailByGoodIdAndCompanyId(int goodId, int companyId) {
        return mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(goodId, companyId);
    }

    public void updateStockAndExchangeNumByLock(MallGoodsInfoDO mallGoodsInfoDO, int count, int state, int retryTimes) throws BIZException {
        DbUtils.checkRetryTimes(retryTimes);
        int row = mallGoodsInfoDao.updateStockAndExchangeNum(mallGoodsInfoDO, count, state);
        if(row == 0){
            mallGoodsInfoDO = mallGoodsInfoDao.getGoodDetailByGoodIdAndCompanyId(mallGoodsInfoDO.getId(), mallGoodsInfoDO.getCompany_id());
            updateStockAndExchangeNumByLock(mallGoodsInfoDO, count, state, ++retryTimes);
        }
    }
}
