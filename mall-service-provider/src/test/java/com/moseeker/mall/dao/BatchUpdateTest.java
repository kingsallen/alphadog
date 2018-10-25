package com.moseeker.mall.dao;

import com.moseeker.baseorm.dao.malldb.MallGoodsInfoDao;
import com.moseeker.mall.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author cjm
 * @date 2018-10-22 14:57
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class BatchUpdateTest {


    @Autowired
    private MallGoodsInfoDao mallGoodsInfoDao;

    @Test
    public void test(){
//        int[] a = mallGoodsInfoDao.batchUpdate();
//        for(int i: a){
//            System.out.println(i);
//        }
    }

}
