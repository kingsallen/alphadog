package com.moseeker.common.zookeeper.curator;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryLoop;
import org.apache.curator.TimeTrace;
import org.apache.curator.drivers.TracerDriver;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.ensemble.exhibitor.DefaultExhibitorRestClient;
import org.apache.curator.ensemble.exhibitor.ExhibitorEnsembleProvider;
import org.apache.curator.ensemble.exhibitor.ExhibitorRestClient;
import org.apache.curator.ensemble.exhibitor.Exhibitors;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.PathUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Transaction;
import org.apache.zookeeper.ZooDefs;


public class IZKClient {

    private final CuratorZookeeperClient zkClient;

    public IZKClient(String configLocation) throws Exception {

        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream(configLocation));
        EnsembleProvider provider = buildProvider(properties);
        String pTimeout = properties.getProperty("zk.timeout");
        Integer timeout = 30000;
        if (pTimeout != null) {
            timeout = Integer.valueOf(pTimeout);
        }
        zkClient = new CuratorZookeeperClient(provider, timeout, timeout, null, new ExponentialBackoffRetry(1000, Integer.MAX_VALUE));
        zkClient.setTracerDriver(new PrintTraceDrive());
        zkClient.start();//must,but anytime before zookeeper operation
        zkClient.blockUntilConnectedOrTimedOut(); //first connection should be successful
    }

    /**
     * build provider,all of params from config-file
     * @param properties
     * @return
     */
    private EnsembleProvider buildProvider(Properties properties) {
        //String servers = properties.getProperty("host.rest.servers");   //hosts.servers = 127.0.0.1,127.0.0.2
    	String servers = "192.168.31.10";   //hosts.servers = 127.0.0.1,127.0.0.2
        if (servers == null || servers.isEmpty()) {
            throw new IllegalArgumentException("host.servers cant be empty");
        }
        //List<String> hostnames = Arrays.asList(servers.split(","));
        List<String> hostnames = Arrays.asList("192.168.31.10");
        //String port = properties.getProperty("host.rest.port");
        String port = "2181";
        Integer restPort = 80;   //default
        if (port != null) {
            restPort = Integer.valueOf(port);
        }
        //final String backupAddress = properties.getProperty("host.backup");//127.0.0.1:2181
        final String backupAddress = "127.0.0.1:2181";//127.0.0.1:2181
        //if network is error,you should sepcify a backup zk-connectString
        Exhibitors exhibitors = new Exhibitors(hostnames, restPort, new Exhibitors.BackupConnectionStringProvider() {
            @Override
            public String getBackupConnectionString() throws Exception {
                return backupAddress;
            }
        });
        //rest,as meaning of getting fresh zk-connectString list.
        ExhibitorRestClient restClient = new DefaultExhibitorRestClient();
        //String restUriPath = properties.getProperty("host.rest.path");
        //String period = properties.getProperty("host.rest.period");
        String restUriPath = properties.getProperty("host.rest.path");
        String period = properties.getProperty("host.rest.period");
        Integer pollingMs = 180000; //3 min
        if (period != null) {
            pollingMs = Integer.valueOf(period);
        }
        return new ExhibitorEnsembleProvider(exhibitors, restClient, restUriPath, pollingMs, new RetryNTimes(10, 1000));
    }

    public CuratorZookeeperClient getZkClient() {
        return zkClient;
    }

    /**
     * how to use RtryLoop ,another style
     * if Znode has been existed,will delete it,and create it again.
     *
     */
    public boolean replace(final String path,final byte[] value){
        PathUtils.validatePath(path);
        boolean result = false;
        try{
            result = RetryLoop.callWithRetry(zkClient,new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    int _current = 0;
                    while(_current < 3){
                        _current++;
                        try{
                            zkClient.blockUntilConnectedOrTimedOut();
                            Transaction tx = zkClient.getZooKeeper().transaction();
                            tx.delete(path, -1);
                            tx.create(path,value,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                            tx.commit();
                            return true;
                        } catch (KeeperException.NoNodeException e){
                             //
                        } catch (KeeperException.NodeExistsException e){
                            //
                        }
                    }
                    return false;  //To change body of implemented methods use File | Settings | File Templates.
                }
            }) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //API : on for test
    public String createPath(String path, byte[] value) throws Exception {
        PathUtils.validatePath(path);//if bad format,here will throw some Exception;
        EnsurePath ensure = new EnsurePath(path).excludingLast();
        //parent path should be existed.
        //EnsurePath: retry + block
        ensure.ensure(zkClient); //ugly API
        return zkClient.getZooKeeper().create(path, value, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    //API: on for test
    public boolean createPath(String path, byte[] value,int blockTimes){
		if (!zkClient.isConnected() && blockTimes == 0) {
			return false;
		}
        TimeTrace trace = zkClient.startTracer("createPath:" + path);//log message
        try{
            EnsurePath ensure = new EnsurePath(path).excludingLast();
            ensure.ensure(zkClient);//only for persistent node
            RetryLoop loop = zkClient.newRetryLoop();
            int _current = 0;
            while(loop.shouldContinue()){
                 try{
                     if(_current >= blockTimes){
                         loop.markComplete(); //stop here.
                         continue;
                     }
                     //blocking
                     boolean isConnected = zkClient.blockUntilConnectedOrTimedOut();
                     if(!isConnected){
                         _current++;
                         continue;
                     }
                     zkClient.getZooKeeper().create(path, value, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                     loop.markComplete();
                 } catch (KeeperException.NodeExistsException e){
                     loop.markComplete();//exist ,stop here
                 } catch (Exception e){
                     loop.takeException(e);
                 }
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;  //cant create path
        } finally{
            trace.commit();
        }
        return true;
    }

    public byte[] getData(String path) throws Exception{
        PathUtils.validatePath(path);
        return zkClient.getZooKeeper().getData(path,false,null);
    }
	public void close(){
		zkClient.close();
	}
    class PrintTraceDrive implements TracerDriver {
        @Override
        public void addTrace(String name, long time, TimeUnit unit) {
            System.out.println("<Trace>" + name + ";time:" + TimeUnit.MILLISECONDS.convert(time, unit) + " ms");
        }

        @Override
        public void addCount(String name, int increment) {
        }
    }
}
