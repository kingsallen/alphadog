package com.moseeker.common.aop;

import java.text.MessageFormat;

import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;

@Service
public class CounterIfaceService {

	@CounterIface
	public void display(String name){
		System.out.println(MessageFormat.format("{0} entry display...", name));
	}
}
