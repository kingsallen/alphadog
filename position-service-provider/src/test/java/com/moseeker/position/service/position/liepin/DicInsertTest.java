package com.moseeker.position.service.position.liepin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;

/**
 * @author cjm
 * @date 2018-06-01 16:11
 **/
public class DicInsertTest {

    public static void loadDb() throws Exception{
        File file = new File("D:\\sys_dq.dic");//Text文件
        BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
        String s = null;
        while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            System.out.println("insert into dict_city_liepin values(null, " + s + ");");
        }
        br.close();
    }

    public static void main(String[] args) throws Exception {
        loadDb();
    }


}
