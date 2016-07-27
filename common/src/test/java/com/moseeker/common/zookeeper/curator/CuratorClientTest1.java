package com.moseeker.common.zookeeper.curator;

import java.nio.charset.Charset;
import java.util.concurrent.Callable;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryLoop;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.common.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuratorClientTest1 extends Thread {
	Logger logger = LoggerFactory.getLogger(CuratorClientTest1.class);
	protected final CuratorZookeeperClient zkClient;  
    protected String parent;  
  
    public static final Charset charset = Charset.forName("utf-8");  
  
    private ZNodeWatcher zNodeWatcher = new ZNodeWatcher();//自定义watcher  
  
    public CuratorClientTest1(String connectString, int sessionTimeout, String parent) throws Exception {  
        this.parent = parent;  
        zkClient = new CuratorZookeeperClient(connectString, sessionTimeout, sessionTimeout, zNodeWatcher, new ExponentialBackoffRetry(1000, Integer.MAX_VALUE));  
        zkClient.start();//must,but anytime before zookeeper operation  
        zkClient.blockUntilConnectedOrTimedOut(); //first connection should be successful  
    }  
  
  
    public boolean exist(String path,boolean watched) throws Exception{  
        return zkClient.getZooKeeper().exists(path,watched) == null ? false : true;  
    }  
  
    /** 
     * 此path必须存在，如果不存在则立即创建 
     * @param path 
     * @return 
     */  
    public boolean ensurePath(final String path) throws Exception{  
        PathUtils.validatePath(path);  
        return RetryLoop.callWithRetry(zkClient, new Callable<Boolean>(){  
            @Override  
            public Boolean call() throws Exception {  
                EnsurePath ensure = new EnsurePath(path);  
                ensure.ensure(zkClient);  
                return true;  
            }  
        });  
    }  
  
    /** 
     * 
     * @param path 
     * @param data 
     * @return   如果path已经存在或者创建成功，则返回true，否则返回false。 
     * @throws Exception 
     */  
    public boolean create(final String path, final String data) throws Exception {  
        PathUtils.validatePath(path);//if bad format,here will throw some Exception;  
        return RetryLoop.callWithRetry(zkClient, new Callable<Boolean>() {  
  
            @Override  
            public Boolean call() throws Exception {  
                int _current = 0;  
                while (_current < 3) {  
                    _current++;  
                    try {  
                        //zkClient.blockUntilConnectedOrTimedOut();  
                        //确保父节点存在  
                        EnsurePath ensure = new EnsurePath(path).excludingLast();  
                        //parent path should be existed.  
                        //EnsurePath: retry + block  
                        ensure.ensure(zkClient); //ugly API  
                        zkClient.getZooKeeper().create(path, data.getBytes(charset), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  
                        return true;  
                    } catch (KeeperException.NodeExistsException e) {  
                        return true;  
                    }  
                    //retry only for KeeperException，not for other runtimeException。  
                    //other exception will be thrown，and stop retry！！  
                    //if no Exception thrown,retry will be stopped and return successfully.  
                }  
                return false;  
            }  
        }) ;  
    }  
      
      
    public  class ZNodeWatcher implements Watcher{  
        @Override  
        public void process(WatchedEvent event) {  
            Event.KeeperState keeperState =  event.getState();  
            String path = event.getPath();  
            switch(event.getType()) {  
                case None:  
                    //connection Error：会自动重连  
                    logger.info("[Watcher],Connecting...");  
                    if(keeperState == Event.KeeperState.SyncConnected){  
                        logger.info("[Watcher],Connected...");  
                        //检测临时节点是否失效等。  
                    }  
                    break;  
                case NodeCreated:  
                    logger.info("[Watcher],NodeCreated:" + path);  
                    break;  
                case NodeDeleted:  
                    logger.info("[Watcher],NodeDeleted:" + path);  
                    break;  
                default:  
                    //  
            }  
        }  
    }
}
