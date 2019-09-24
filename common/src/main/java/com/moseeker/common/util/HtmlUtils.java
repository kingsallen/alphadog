package com.moseeker.common.util;

import org.jsoup.Jsoup;

/**
 * Created by qiancheng on 2019/9/21.
 */
public class HtmlUtils {


    public static boolean isHtml(String html){
        // "<html></html>".length() : 12
        if(html == null || (html=html.trim()).length() < 12){
            return false ;
        }

        // 如果不是以'<'开头，跳过
        // 如果有注释，跳过注释
        for(String prefix : new String[]{"<html","<!DOCTYPE html"}){
            if( StringUtils.startsWithIgnoreCase(html,prefix)) return true ;
        }
        return false ;
    }

    public static String getTitle(String html){
        return Jsoup.parse(html).select("title").text() ;
    }
}
