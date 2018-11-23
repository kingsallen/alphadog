package com.moseeker.mall.dao;

import com.moseeker.baseorm.dao.malldb.MallGoodsInfoDao;
import com.moseeker.baseorm.dao.malldb.MallGoodsOrderDao;
import com.moseeker.baseorm.db.malldb.tables.records.MallOrderRecord;
import com.moseeker.mall.config.AppConfig;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjm
 * @date 2018-10-22 14:57
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class BatchUpdateTest {


    @Autowired
    private MallGoodsInfoDao mallGoodsInfoDao;
    @Autowired
    private MallGoodsOrderDao mallGoodsOrderDao;

    @Test
    public void test(){
//        int[] a = mallGoodsInfoDao.batchUpdate();
//        for(int i: a){
//            System.out.println(i);
//        }
        List<MallOrderRecord> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            MallOrderRecord mallOrderRecord = new MallOrderRecord();
            mallOrderRecord.setCount(1);
            mallOrderRecord.setCompanyId(4);
            mallOrderRecord.setCredit(100);
            mallOrderRecord.setGoodsId(1);
            mallOrderRecord.setEmployeeId(677753);
            list.add(mallOrderRecord);
        }

        int[] rows = mallGoodsOrderDao.batchInsert(list);
    }

}
