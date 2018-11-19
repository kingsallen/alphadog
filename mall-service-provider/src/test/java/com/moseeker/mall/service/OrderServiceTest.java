package com.moseeker.mall.service;

import com.moseeker.common.thread.ThreadPool;
import com.moseeker.mall.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.mall.struct.OrderForm;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 订单服务测试
 *
 * @author cjm
 * @date 2018-10-23 15:21
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    ThreadPool pool = ThreadPool.Instance;

    @Test
    public void testConfirmOrder() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i = 0; i < 100; i++){
            new Thread(){

                @Override
                public void run() {
                    OrderForm orderForm = new OrderForm();
                    orderForm.setCompany_id(4);
                    orderForm.setCount(1);
                    orderForm.setEmployee_id(677771);
                    orderForm.setGoods_id(10);
                    try {
                        countDownLatch.await();
                        orderService.confirmOrder(orderForm);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BIZException e) {
                        e.printStackTrace();
                    } catch (TException e) {
                        e.printStackTrace();
                    }
                }


            }.start();

        }
        countDownLatch.countDown();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConfirmOrder1() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1000);
        for(int i = 0; i < 1000; i++){
            new Thread(){
                @Override
                public void run() {
                    OrderForm orderForm = new OrderForm();
                    orderForm.setCompany_id(4);
                    orderForm.setCount(1);
                    orderForm.setEmployee_id(677771);
                    orderForm.setGoods_id(10);
                    try {
                        cyclicBarrier.await();
                        orderService.confirmOrder(orderForm);
                    } catch (BIZException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (TException e) {
                        e.printStackTrace();
                    }
                }


            }.start();

        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
