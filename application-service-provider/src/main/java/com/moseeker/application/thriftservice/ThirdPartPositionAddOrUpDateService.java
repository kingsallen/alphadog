package com.moseeker.application.thriftservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.application.service.OperatorThirdPartService;

public class ThirdPartPositionAddOrUpDateService {
	public static  ExecutorService executorService;
	@Autowired
	private OperatorThirdPartService operatorThirdPartService;
	public ThirdPartPositionAddOrUpDateService(){
		if(this.executorService==null){
			this.executorService=new ThreadPoolExecutor(3, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		}
	}
	public void addPositionToOrm(){
			executorService.submit(()->{
				operatorThirdPartService.SynOrUpThirdPartAccount(1);
			});
	}
	public void updatePositionToOrm(){
			executorService.submit(()->{
				operatorThirdPartService.SynOrUpThirdPartAccount(1);
			});
	}
	public void start(){
//		new Thread(new Runnable(){
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(true){
//					addPositionToOrm();
//				}
//				
//			}
//		}).start();
//		new Thread(new Runnable(){
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(true){
//					updatePositionToOrm();
//				}
//			}
//		}).start();
	}
}
