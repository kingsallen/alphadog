package com.moseeker.rpccenter.common;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzh on 16/3/30.
 */
public class ServerNodeUtils {

    /** 默认权重 */
    private static final int DEFAULT_WEIGHT = 1;

    /**
     * private constructor
     */
    private ServerNodeUtils() {
        super();
    }

    /**
     * 服务地址转换为ServerNode列表
     * <p>
     *
     * @param address
     * @return {@link List<ServerNode>}
     */
    public static List<ServerNode> transfer(String address) {
        String[] hostname = address.split(":");
        int weight = DEFAULT_WEIGHT;
        if (hostname.length == 3) {
            weight = Integer.valueOf(hostname[2]);
        }
        String ip = hostname[0];
        Integer port = Integer.valueOf(hostname[1]);
        List<ServerNode> result = new ArrayList<ServerNode>();
        for (int i = 0; i < weight; i++) {
            result.add(new ServerNode(ip, port));
        }
        return result;
    }

    /**
     * 获取当前项目名称
     * <p>
     *
     * @param clazz
     *        provider server class
     * @return 项目名称
     */
    public static String getProjectName(Class clazz) {
        try {
            URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
            String filePath = URLDecoder.decode(url.getPath(), "utf-8");
            String[] file = filePath.split("/");
            return file[file.length - 1];
        }catch (Exception e){
            return clazz.getSimpleName();
        }
    }

    /**
     * 获取命令行输入的端口号
     * <p>
     *
     * @param args
     *        -port 19001
     * @return 端口号
     */
    public static int getPort(String[] args) throws Exception{
    	if(args != null) {
    		for(int i=0; i<args.length; i++) {
    			System.out.println(args[i]);
    			//throw new RuntimeException(args[i]);
    		}
    	}
        if ( args.length > 1 ){
            Integer port = Integer.valueOf(args[1]);
            if (args[0].equals("-port")){
                return Integer.valueOf(args[1]);
            }else{
                throw new RuntimeException("请输入端口号");
            }
        }else{
            throw new RuntimeException("usage : java -jar provider的jar文件 -port 19090");
        }
    }

    /**
     * 打印端口说明
     * <p>
     *
     * @return void
     */
    public static void providerUsagePrint(){
        System.out.println("usage : java -jar provider的jar文件 -port 19090");
        System.out.println("        -port 请输入在10000~30000之间的端口号");
        System.out.println(" ");
    }
}
