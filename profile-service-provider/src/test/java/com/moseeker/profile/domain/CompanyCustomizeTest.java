package com.moseeker.profile.domain;


import edu.emory.mathcs.backport.java.util.Collections;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class CompanyCustomizeTest {

    @BeforeClass
    public static void setupAll(){
        //CompanyCustomize.Baiji ;
    }

    @Test
    public void checkRepeatRecommendBenchmark() throws InterruptedException {
        //Baiji.checkRepeatRecommend(null,"18209212004","18209212003@qq.com");
        List<Long> times = Collections.synchronizedList(new ArrayList<>());
        int count = 20;
        CountDownLatch latch = new CountDownLatch(count);
        for(int i=0;i<20;i++){
            new Thread(()->{
                long start = System.currentTimeMillis();
                boolean result = CompanyCustomize.Baiji.checkRepeatRecommend(null,"18375331858","893207466@qq.com");
                long time = System.currentTimeMillis() - start;
                times.add(time);
                System.out.println(result);
                latch.countDown();
            }).start();
        }

        latch.await();
        long min = times.get(0) ,max = times.get(0),sum=0 ;
        for(long t : times){
            if( t < min) {
                min = t ;
            }else if(t > max) {
                max = t ;
            }
            sum += t ;
        }
        long average = sum / times.size();
        System.out.printf("min=%dms,max=%dms,average=%dms\n",min,max,average);
    }

}