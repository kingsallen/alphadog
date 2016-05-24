package test.com.moseeker.rpccenter.demo;

import org.apache.thrift.TException;

/**
 * Created by zzh on 16/3/29.
 */
public class EchoServiceImpl implements test.com.moseeker.rpccenter.gen.EchoService.Iface {

    @Override
    public String echo(String msg) throws TException {

        try {
            System.out.println(msg);
            Thread.sleep(Integer.valueOf(msg) * 10000);
//            Thread.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "hello " + msg;
    }
}
