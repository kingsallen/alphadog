package com.moseeker.search;

/**
 * Created by zztaiwll on 18/1/5.
 */
public class Test {
    public static void main(String[] args) {
        String value="你好///kjadkajla////kkladjakl or OR";
        if(value.contains("/")){
            value=value.replaceAll("/","");
        }
        if(value.toLowerCase().contains("or")){
            value=value.toLowerCase().replaceAll("or","");
        }
        if(value.toLowerCase().contains("and")){
            value=value.toLowerCase().replaceAll("and","");
        }
        System.out.println(value);
    }
}
