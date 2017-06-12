package com.moseeker.common.proxy;

import org.junit.Test;

public class CGLibProxyTest {
	
	//@Test
	public void proxyTest() {
		Book book = new Book();
		book.setDescription("book description!");
		book.setName("The Song of Ice and Fire Series");
		IExecution execution = new IExecution();
		CGLibProxy proxy = new CGLibProxy(book, execution);
		book = (Book)proxy.proxy();
		System.out.println(book.getDescription());
		System.out.println(book.getName());
	}

}
