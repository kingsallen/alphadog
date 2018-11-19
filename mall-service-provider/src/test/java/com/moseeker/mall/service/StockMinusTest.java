package com.moseeker.mall.service;

import com.moseeker.common.thread.ThreadPool;
import com.moseeker.mall.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author cjm
 * @date 2018-10-19 11:11
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class StockMinusTest {

    @Autowired
    private GoodsService goodsService;

    ThreadPool pool = ThreadPool.Instance;

    @Test
    @Rollback
    public void testStock() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i = 0; i < 10; i++){
            pool.startTast(() ->
                    {
                        countDownLatch.await();
                        int stock = goodsService.updateGoodStockByLock(11, 4, -150, 2, 1);
                        return 1;
                    });
        }
        countDownLatch.countDown();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
