package com.moseeker.common.thrift;

import org.apache.thrift.TException;

import com.moseeker.common.thrift.Calculator.Iface;

public class CalculatorHandler implements Iface {

	@Override
	public SharedStruct getStruct(int key) throws TException {
		System.out.println("key:"+key);
		SharedStruct struct = new SharedStruct();
		struct.setKey(key);
		struct.setValue("value");
		return struct;
	}

	@Override
	public void ping() throws TException {
		System.out.println("CalculatorHandler ping");
	}

	@Override
	public int add(int num1, int num2) throws TException {
		System.out.println("add function num1:"+num1 +"+ num2:"+num1 +" = "+num1+num2);
		return num1 + num2;
	}

	@Override
	public int calculate(int logid, Work w) throws InvalidOperation, TException {
		int result = 0;
		System.out.println("comment:"+w.getComment());
		System.out.println("logid:"+logid);
		if(w != null) {
			switch(w.op) {
				case ADD: result = w.getNum1() + w.getNum2(); break;
				case SUBTRACT: result = w.getNum1() - w.getNum2(); break;
				case MULTIPLY: result = w.getNum1() * w.getNum2(); break;
				case DIVIDE: 
					if(w.getNum2() != 0) {
						result = w.getNum1() / w.getNum2();
					}
					break;
				default:
			}
		}
		return result;
	}

	@Override
	public void zip() throws TException {
		System.out.println("zip");
	}

}
