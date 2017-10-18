package com.moseeker.entity.pojo.resume;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * Created by jack on 20/09/2017.
 */
public class ResumeObjTest {

    @Test
    public void parse() {
        ResumeObj resumeObj = JSONObject.parseObject(test, ResumeObj.class);
        System.out.println(resumeObj.getResult().getSkills_objs());
    }
    
    private static String test = "{\"account\":{\"uid\":1707240,\"usage_limit\":60300,\"usage_remaining\":60128},\"eval\":{\"salary\":19900},\"result\":{\"age\":\"28\",\"birthday\":\"1989.09.12\",\"cert_objs\":[{\"langcert_lang\":\"英语\",\"langcert_name\":\"大学英语4级\"}],\"city\":\"上海\",\"cont_basic_info\":\"王景华   id:93901801n我目前不想换工作   15026572049      wjh756381193@qq.comn男|28 岁 (1989/09/12)|现居住上海|4年工作经验n最近工作n职位:   高级软件工程师n公司:   上海大岂网络科技有限公司n行业:   计算机软件n最高学历/学位n专业:   机械电子工程/机电一体化n学校:   青海大学(211工程重点建设院校)n学历/学位:   本科n个人信息n户口/国籍:   商丘n身高:   172cmn婚姻状况:   已婚n家庭地址:   上海市松江区 (邮编:201107)n政治面貌:   普通公民n目前年收入 24万元(包含基本工资、补贴、奖金、股权收益等)n基本工资:   21.6万元n补贴/津贴:   0.6万元n奖金/佣金:   1.8万元\",\"cont_expect_job\":\"个人标签:   精通php 熟悉linux 热爱技术n期望薪资:   20000-24999 元/月n地点:   上海n职能:   高级软件工程师 软件工程师 需求工程师n行业:   计算机软件 网络游戏 互联网/电子商务n到岗时间:   1个月内n工作类型:   全职\",\"cont_job_exp\":\"2016/8-至今   高级软件工程师n上海大岂网络科技有限公司 [1年1个月]n计算机软件|50-150人|民营公司n工作描述:   公司官网的开发,后台的搭建,后端架构的设计!n2015/4-2016/8   php高级工程师|平台产品部/环球工作室n上海游族信息技术有限公司 [1年4个月]n网络游戏|1000-5000人|上市公司n工作描述:   负责公司游戏官网,游戏活动,公司内部系统,游戏平台的开发和维护!n这份工作中对以下部分有了一些基本的认识和感悟:n1.大数据量并发的处理(如mysql的优化)n2.对服务器集群的架设的深入认识(如lvs服务器集群的架设)n3.对高性能网站开发的一些规范认识(如redis,mongodb,cdn的使用)n4.对一些框架的底层有了比较深入的了解(如:yii,ci)n5.对设计模式在实际开发过程中的应用有了一定的认识n2014/7-2015/4   php开发工程师|技术部n上海慧广科技有限公司 [9个月]n计算机软件|150-500人|民营公司n工作描述:   公司软件的开发n马尚网的开发和维护n地址:www.equexchina.comn未来餐厅微信功能的开发n好吃来项目的开发nm.haochilai.men好吃来微信端的开发n在这份工作中学会了以下的一些知识:n1.一些常用的框架的熟练使用n2.对mysql的调优有了初步的认识n3.能熟练的完成相应服务器的环境搭建(如lamp)n4.能基本独立的完成线上的代码的调试,并可在服务器环境上进行无障碍开发n5.对一些新兴的技术有了一些基本的认识(如nodejs.redis)n2013/2-2014/7   php开发工程师|技术部n上海吉群计算机网络技术有限公司 [1年5个月]n计算机软件|少于50人|外资(非欧美)n工作描述:   负责公司所接网站的系统的分析,设计!某些网站的运行的维护,在此期间取得了以下成绩:nhttp://bc.brainchildusa.com易扬製作與维护nhttp://fine080.com/快付通,台湾支付平台的制作nhttp://www.lojel.com/lojel (皇冠皮件)的制作nhttp://daily-life.tw/生活笔記的制作nhttp://theda.com.tw/夕达科技的制作nhttp://minyu.com/content/index.php明裕机械的修改nhttp://1page.070.com.tw公司新公版一頁式網站模板的制作nhttp://www.brolnk.com/深圳铂钛开发有限公司门户的制作nhttp://www.tht-tech.com/久鑫科技股份有限公司的门户制作nhttp://morr.com.tw沐悅的制作与修改nhttp://cafeliegeois.com.tw烈日咖啡的制作nhttp://hung-wei.com宏玮科技的制作n等案子的制作。n另外,还有公司服务器方面的小的维护等。n在这份工作中学到了以下的东西:n1.对php的工作原理有了一定的认识,可以使用php进行基本的开发n2.对js和html有了一定的认识能进行相关的前端工作的开发n3.对mysql数据库有了初步的认识,能熟练进行相关的数据库的ddl&dml等相关操作n4.对服务器的建设有了初步的了解,可以在服务器上进行基本的操作\",\"cont_my_desc\":\"思维活跃、对技术有追求、精通php、对数据库调优有一定的经验,对服务器架构和高性能网站开发有一定的认识\",\"education_objs\":[{\"edu_college\":\"青海大学\",\"edu_college_rank\":\"258\",\"edu_college_type\":\"2\",\"edu_degree\":\"本科\",\"edu_degree_norm\":\"本科\",\"edu_major\":\"机械电子工程\",\"end_date\":\"2013.06\",\"start_date\":\"2009.09\"}],\"email\":\"wjh756381193@qq.com\",\"expect_jlocation\":\"上海\",\"expect_jnature\":\"全职\",\"expect_job\":\"php,linux\",\"expect_salary\":\"20000~24999元/月\",\"gender\":\"男\",\"job_exp_objs\":[{\"end_date\":\"至今\",\"job_content\":\"工作描述:   公司官网的开发,后台的搭建,后端架构的设计!\",\"job_cpy\":\"上海大岂网络科技有限公司\",\"job_cpy_nature\":\"民营公司\",\"job_cpy_size\":\"50-150人\",\"job_industry\":\"计算机软件\",\"job_position\":\"高级软件工程师\",\"start_date\":\"2016.08\"},{\"end_date\":\"2016.08\",\"job_content\":\"上海游族信息技术有限公司 [1年4个月]n工作描述:   负责公司游戏官网,游戏活动,公司内部系统,游戏平台的开发和维护!n这份工作中对以下部分有了一些基本的认识和感悟:n1.大数据量并发的处理(如mysql的优化)n2.对服务器集群的架设的深入认识(如lvs服务器集群的架设)n3.对高性能网站开发的一些规范认识(如redis,mongodb,cdn的使用)n4.对一些框架的底层有了比较深入的了解(如:yii,ci)n5.对设计模式在实际开发过程中的应用有了一定的认识\",\"job_cpy\":\"工作室\",\"job_cpy_nature\":\"上市公司\",\"job_cpy_size\":\"1000-5000人\",\"job_industry\":\"网络游戏\",\"job_position\":\"php高级工程师\",\"start_date\":\"2015.04\"},{\"end_date\":\"2015.04\",\"job_content\":\"工作描述:   公司软件的开发n马尚网的开发和维护n未来餐厅微信功能的开发n好吃来项目的开发nm.haochilai.men好吃来微信端的开发n在这份工作中学会了以下的一些知识:n1.一些常用的框架的熟练使用n2.对mysql的调优有了初步的认识n3.能熟练的完成相应服务器的环境搭建(如lamp)n4.能基本独立的完成线上的代码的调试,并可在服务器环境上进行无障碍开发n5.对一些新兴的技术有了一些基本的认识(如nodejs.redis)\",\"job_cpy\":\"上海慧广科技有限公司\",\"job_cpy_nature\":\"民营公司\",\"job_cpy_size\":\"150-500人\",\"job_industry\":\"计算机软件\",\"job_location\":\"www.equexchina.com\",\"job_position\":\"php开发工程师\",\"start_date\":\"2014.07\"},{\"end_date\":\"2014.07\",\"job_content\":\"工作描述:   负责公司所接网站的系统的分析,设计!某些网站的运行的维护,在此期间取得了以下成绩:nhttp://bc.brainchildusa.com易扬製作與维护nhttp://fine080.com/快付通,台湾支付平台的制作nhttp://www.lojel.com/lojel (皇冠皮件)的制作nhttp://daily-life.tw/生活笔記的制作nhttp://theda.com.tw/夕达科技的制作nhttp://minyu.com/content/index.php明裕机械的修改nhttp://1page.070.com.tw公司新公版一頁式網站模板的制作nhttp://www.brolnk.com/深圳铂钛开发有限公司门户的制作nhttp://www.tht-tech.com/久鑫科技股份有限公司的门户制作nhttp://morr.com.tw沐悅的制作与修改nhttp://cafeliegeois.com.tw烈日咖啡的制作nhttp://hung-wei.com宏玮科技的制作n等案子的制作。n另外,还有公司服务器方面的小的维护等。n在这份工作中学到了以下的东西:n1.对php的工作原理有了一定的认识,可以使用php进行基本的开发n2.对js和html有了一定的认识能进行相关的前端工作的开发n3.对mysql数据库有了初步的认识,能熟练进行相关的数据库的ddl&dml等相关操作n4.对服务器的建设有了初步的了解,可以在服务器上进行基本的操作\",\"job_cpy\":\"上海吉群计算机网络技术有限公司\",\"job_cpy_size\":\"50人\",\"job_industry\":\"计算机软件\",\"job_position\":\"php开发工程师\",\"start_date\":\"2013.02\"}],\"lang_objs\":[{\"language_name\":\"英语\"}],\"living_address\":\"上海\",\"name\":\"王景华\",\"phone\":\"15026572049\",\"proj_exp_objs\":[{\"end_date\":\"至今\",\"proj_content\":\"项目描述:   phpstorm(开发工具) lamp(软件环境)公司收购bp后,为和bp平台实现用户的对接,开发了相应的游戏微端,使bp用户可以通关bp的相关的账号登录公司的游戏!\",\"proj_name\":\"bigpoint用户微端开发\",\"proj_resp\":\"责任描述:   这个项目中主要负责:n1.接口文档的制定n2.bp和微端之间数据的中转和存储n3.游戏的相关的接口的开发\",\"start_date\":\"2016.06\"},{\"end_date\":\"2016.06\",\"proj_content\":\"项目描述:   phpstorm(开发工具) lamp(软件环境)为缓解客服压力,建立mod系统--游戏内玩家自助的问答系统。 通过相应的建立机制,把相关的玩家的问题转送给特定身份的高级玩家的手里,来为玩家答疑的系统!\",\"proj_name\":\"游戏内mod系统的开发\",\"proj_resp\":\"责任描述:   完成了相关的mod系统的管理员管理模块,积分模块,玩家问答模块,客服回答模块等核心模块的开发。另外,完成了问题的定时推送功能!\",\"start_date\":\"2016.05\"},{\"end_date\":\"2016.04\",\"proj_content\":\"项目描述:   phpstorm(开发工具) lamp(软件环境)游戏的网站的相关的redis操作修改为相应的codis集群操作修改为使用redis集群的codis架构进行相关的程序处理!并兼容原本的redis集群\",\"proj_resp\":\"责任描述:   独立完成本次的功能的整合,对原有的redis相关操作进行了相关的整合优化!\",\"start_date\":\"2016.04\"},{\"end_date\":\"2016.04\",\"proj_content\":\"项目描述:   phpstorm(开发工具) lamp(软件环境)海外新游戏的官网的多语言版本的开发,包括德语法西葡语言的开发和大皇帝手游的繁体中文的开发\",\"proj_name\":\"海外游戏官网的开发\",\"proj_resp\":\"责任描述:   游戏官网多语言版本的开发。\",\"start_date\":\"2016.03\"},{\"end_date\":\"2016.02\",\"proj_content\":\"项目描述:   phpstorm(开发工具) lamp(软件环境)用户中心的相关的功能开发,包括用户的权限系统,以及用户的游戏内激活功能的完善。用户充值功能的查询,客服后台的用户查询,订单系统的查询功能。\",\"proj_name\":\"海外用户中心的开发\",\"proj_resp\":\"责任描述:   用户的相关的游戏内行为的统计,注册和登录的量的统计完善。用户充值信息的查询。\",\"start_date\":\"2016.01\"},{\"end_date\":\"2015.12\",\"proj_content\":\"项目描述:   phpstorm(开发工具) lamp(软件环境)整合环球工作室的客服系统并新增加相应的edm系统,主要是客服人员通过客服后台实现向游戏用户的批量的邮件发送,通过edm系统来统计相关的发送数据的转化率,点击率等相关功能!\",\"proj_name\":\"海外项目的edm开发\",\"proj_resp\":\"责任描述:   本项目完全由我独立开发,实现了用户的邮件群发操作和相应的主要信息的统计功能和报表导出功能!提升了客服人员的作业方便性和效率。增加了游戏的激活率。\",\"start_date\":\"2015.11\"},{\"end_date\":\"2015.10\",\"proj_content\":\"项目描述:   phpstorm(开发工具) lamp(软件环境)游戏官网,游戏活动,用户中心等相关系统的开发和维护!\",\"proj_name\":\"游族平台和游戏活动的开发\",\"proj_resp\":\"责任描述:   1.各新游戏的官网的开发n2.游戏内的各活动的需求的开发n3.用户中心相关的开发工作n4.相关的游戏接口的开发\",\"start_date\":\"2015.04\"},{\"end_date\":\"2015.04\",\"proj_content\":\"项目描述:   phpstorm(开发工具) lamp(软件环境)用户通过微信关注相应的网上餐厅,完成下单操作,在相应的订餐点,完成支付并在相应的取餐地点通过扫码实现取餐的系统!\",\"proj_name\":\"好吃来微信订餐系统\",\"proj_resp\":\"责任描述:   此项目的开发过程中,主要负责数据中心的搭建包括数据的存储和转发操作! 存储主要是订单的记录,转发分别是扫码过程通知机器开仓的操作和通知骑手接单的操作!\",\"start_date\":\"2014.10\"},{\"end_date\":\"2014.12\",\"proj_content\":\"项目描述:   phpstorm(开发工具) linux(硬件环境) lamp(软件环境)企业电子商务网站\",\"proj_resp\":\"责任描述:   网站的前台页面和后台的制作\",\"start_date\":\"2013.11\"},{\"end_date\":\"2014.09\",\"proj_content\":\"项目描述:   马尚官网是马尚(上海)投资管理有限公司的官方网站,主要包括了一些新闻资讯页面,订单页面功能!\",\"proj_name\":\"马尚官网\",\"proj_resp\":\"责任描述:   负责后台的会员模块以及相关的购票的活动的页面和后台权限系统的开发!独立完成了相关的微信消息推送的功能,微信订票的功能的制作!\",\"start_date\":\"2014.07\"},{\"end_date\":\"2014.07\",\"proj_content\":\"项目描述:   phpstorm(开发工具) lamp(软件环境)呈军企业门户网站是一个企业的门户网站\",\"proj_name\":\"呈军企业门户的开发\",\"proj_resp\":\"责任描述:   我的主要责任是网站的前台的页面的功能开发,后台的搭建\",\"start_date\":\"2014.06\"},{\"end_date\":\"2014.06\",\"proj_content\":\"项目描述:   phpstorm(开发工具) linux(硬件环境) lamp(软件环境)沐浴商城 是一个企业电子商城,服务与台湾的用户。\",\"proj_name\":\"沐悦网站的维护\",\"proj_resp\":\"责任描述:   企业的电子商城的设计与开发 包括前台页面的显示,后台的开发。\",\"start_date\":\"2014.03\"},{\"end_date\":\"2014.02\",\"proj_content\":\"项目描述:   phpstorm(开发工具) linux(硬件环境) lamp(软件环境)久畅科技是一个台湾的客户的企业门户网站主要是一个cms内容的管理系统!\",\"proj_resp\":\"责任描述:   网站的前后台的搭建以及各个功能模块的开发\",\"start_date\":\"2014.01\"},{\"end_date\":\"2013.11\",\"proj_content\":\"项目描述:   phpstorm(开发工具) linux(硬件环境) lamp(软件环境)企业门户网站\",\"proj_resp\":\"责任描述:   网站的制作\",\"start_date\":\"2013.10\"},{\"end_date\":\"2013.10\",\"proj_content\":\"项目描述:   phpstorm(开发工具) windows(硬件环境) lamp(软件环境)个人网站\",\"proj_name\":\"生活笔记\",\"proj_resp\":\"责任描述:   独立完成各模块的功能,同时负责客户的数据的采集模块的制作\",\"start_date\":\"2013.09\"},{\"end_date\":\"2013.09\",\"proj_content\":\"项目描述:   phpstorm(开发工具) windows(硬件环境) lamp(软件环境)企业门户网站,对企业的一些基本的宣传\",\"proj_name\":\"皇冠皮件的制作\",\"proj_resp\":\"责任描述:   网站的前后台的搭建\",\"start_date\":\"2013.08\"},{\"end_date\":\"2013.07\",\"proj_content\":\"项目描述:   phpstorm + mysql + linux(开发工具) lamp(软件环境)实现公司内部的系统的开发维护,主要包括报表功能,事件流功能,任务分配功能等!\",\"proj_name\":\"公司内部系统的开发\",\"proj_resp\":\"责任描述:   独立完成相关的事项的开发工作\",\"start_date\":\"2013.04\"},{\"end_date\":\"2013.04\",\"proj_content\":\"项目描述:   phpstorm(开发工具)nwindows(硬件环境)nlamp(软件环境)快付通,台湾支付平台的企业介绍网站n利用公司的公版进行的项目开发\",\"proj_name\":\"快付通\",\"proj_resp\":\"责任描述:   整个网站的搭建\",\"start_date\":\"2013.03\"},{\"end_date\":\"2013.03\",\"proj_content\":\"项目描述:   epp、dw;ps(开发工具) windows(硬件环境) wamp(软件环境)实现了新闻的基本功能及后台的管理工作,对mysql和linux、js有一定的认知。\",\"proj_name\":\"新闻管理系统\",\"proj_resp\":\"责任描述:   独立完成整个系统的开发\",\"start_date\":\"2013.01\"}],\"raw_text\":\"王景华   id:93901801n我目前不想换工作   15026572049      wjh756381193@qq.comn男|28 岁 (1989/09/12)|现居住上海|4年工作经验n最近工作n职位:   高级软件工程师n公司:   上海大岂网络科技有限公司n行业:   计算机软件n最高学历/学位n专业:   机械电子工程/机电一体化n学校:   青海大学(211工程重点建设院校)n学历/学位:   本科n个人信息n户口/国籍:   商丘n身高:   172cmn婚姻状况:   已婚n家庭地址:   上海市松江区 (邮编:201107)n政治面貌:   普通公民n目前年收入 24万元(包含基本工资、补贴、奖金、股权收益等)n基本工资:   21.6万元n补贴/津贴:   0.6万元n奖金/佣金:   1.8万元n求职意向n个人标签:   精通php 熟悉linux 热爱技术n期望薪资:   20000-24999 元/月n地点:   上海n职能:   高级软件工程师 软件工程师 需求工程师n行业:   计算机软件 网络游戏 互联网/电子商务n到岗时间:   1个月内n工作类型:   全职n自我评价:   思维活跃、对技术有追求、精通php、对数据库调优有一定的经验,对服务器架构和高性能网站开发有一定的认识。n工作经验n2016/8-至今   高级软件工程师n上海大岂网络科技有限公司 [1年1个月]n计算机软件|50-150人|民营公司n工作描述:   公司官网的开发,后台的搭建,后端架构的设计!n2015/4-2016/8   php高级工程师|平台产品部/环球工作室n上海游族信息技术有限公司 [1年4个月]n网络游戏|1000-5000人|上市公司n工作描述:   负责公司游戏官网,游戏活动,公司内部系统,游戏平台的开发和维护!n这份工作中对以下部分有了一些基本的认识和感悟:n1.大数据量并发的处理(如mysql的优化)n2.对服务器集群的架设的深入认识(如lvs服务器集群的架设)n3.对高性能网站开发的一些规范认识(如redis,mongodb,cdn的使用)n4.对一些框架的底层有了比较深入的了解(如:yii,ci)n5.对设计模式在实际开发过程中的应用有了一定的认识n2014/7-2015/4   php开发工程师|技术部n上海慧广科技有限公司 [9个月]n计算机软件|150-500人|民营公司n工作描述:   公司软件的开发n马尚网的开发和维护n地址:www.equexchina.comn未来餐厅微信功能的开发n好吃来项目的开发nm.haochilai.men好吃来微信端的开发n在这份工作中学会了以下的一些知识:n1.一些常用的框架的熟练使用n2.对mysql的调优有了初步的认识n3.能熟练的完成相应服务器的环境搭建(如lamp)n4.能基本独立的完成线上的代码的调试,并可在服务器环境上进行无障碍开发n5.对一些新兴的技术有了一些基本的认识(如nodejs.redis)n2013/2-2014/7   php开发工程师|技术部n上海吉群计算机网络技术有限公司 [1年5个月]n计算机软件|少于50人|外资(非欧美)n工作描述:   负责公司所接网站的系统的分析,设计!某些网站的运行的维护,在此期间取得了以下成绩:nhttp://bc.brainchildusa.com易扬製作與维护nhttp://fine080.com/快付通,台湾支付平台的制作nhttp://www.lojel.com/lojel (皇冠皮件)的制作nhttp://daily-life.tw/生活笔記的制作nhttp://theda.com.tw/夕达科技的制作nhttp://minyu.com/content/index.php明裕机械的修改nhttp://1page.070.com.tw公司新公版一頁式網站模板的制作nhttp://www.brolnk.com/深圳铂钛开发有限公司门户的制作nhttp://www.tht-tech.com/久鑫科技股份有限公司的门户制作nhttp://morr.com.tw沐悅的制作与修改nhttp://cafeliegeois.com.tw烈日咖啡的制作nhttp://hung-wei.com宏玮科技的制作n等案子的制作。n另外,还有公司服务器方面的小的维护等。n在这份工作中学到了以下的东西:n1.对php的工作原理有了一定的认识,可以使用php进行基本的开发n2.对js和html有了一定的认识能进行相关的前端工作的开发n3.对mysql数据库有了初步的认识,能熟练进行相关的数据库的ddl&dml等相关操作n4.对服务器的建设有了初步的了解,可以在服务器上进行基本的操作n项目经验n2016/6-至今   bigpoint用户微端开发n项目描述:   phpstorm(开发工具) lamp(软件环境)公司收购bp后,为和bp平台实现用户的对接,开发了相应的游戏微端,使bp用户可以通关bp的相关的账号登录公司的游戏!n责任描述:   这个项目中主要负责:n1.接口文档的制定n2.bp和微端之间数据的中转和存储n3.游戏的相关的接口的开发n2016/5-2016/6   游戏内mod系统的开发n项目描述:   phpstorm(开发工具) lamp(软件环境)为缓解客服压力,建立mod系统--游戏内玩家自助的问答系统。 通过相应的建立机制,把相关的玩家的问题转送给特定身份的高级玩家的手里,来为玩家答疑的系统!n责任描述:   完成了相关的mod系统的管理员管理模块,积分模块,玩家问答模块,客服回答模块等核心模块的开发。另外,完成了问题的定时推送功能!n2016/4-2016/4   网站架构redis转化为codis集群的修改n项目描述:   phpstorm(开发工具) lamp(软件环境)游戏的网站的相关的redis操作修改为相应的codis集群操作修改为使用redis集群的codis架构进行相关的程序处理!并兼容原本的redis集群n责任描述:   独立完成本次的功能的整合,对原有的redis相关操作进行了相关的整合优化!n2016/3-2016/4   海外游戏官网的开发n项目描述:   phpstorm(开发工具) lamp(软件环境)海外新游戏的官网的多语言版本的开发,包括德语法西葡语言的开发和大皇帝手游的繁体中文的开发n责任描述:   游戏官网多语言版本的开发。n2016/1-2016/2   海外用户中心的开发n项目描述:   phpstorm(开发工具) lamp(软件环境)用户中心的相关的功能开发,包括用户的权限系统,以及用户的游戏内激活功能的完善。用户充值功能的查询,客服后台的用户查询,订单系统的查询功能。n责任描述:   用户的相关的游戏内行为的统计,注册和登录的量的统计完善。用户充值信息的查询。n2015/11-2015/12   海外项目的edm开发n项目描述:   phpstorm(开发工具) lamp(软件环境)整合环球工作室的客服系统并新增加相应的edm系统,主要是客服人员通过客服后台实现向游戏用户的批量的邮件发送,通过edm系统来统计相关的发送数据的转化率,点击率等相关功能!n责任描述:   本项目完全由我独立开发,实现了用户的邮件群发操作和相应的主要信息的统计功能和报表导出功能!提升了客服人员的作业方便性和效率。增加了游戏的激活率。n2015/4-2015/10   游族平台和游戏活动的开发n项目描述:   phpstorm(开发工具) lamp(软件环境)游戏官网,游戏活动,用户中心等相关系统的开发和维护!n责任描述:   1.各新游戏的官网的开发n2.游戏内的各活动的需求的开发n3.用户中心相关的开发工作n4.相关的游戏接口的开发n2014/10-2015/4   好吃来微信订餐系统n项目描述:   phpstorm(开发工具) lamp(软件环境)用户通过微信关注相应的网上餐厅,完成下单操作,在相应的订餐点,完成支付并在相应的取餐地点通过扫码实现取餐的系统!n责任描述:   此项目的开发过程中,主要负责数据中心的搭建包括数据的存储和转发操作! 存储主要是订单的记录,转发分别是扫码过程通知机器开仓的操作和通知骑手接单的操作!n2014/7-2014/9   马尚官网n项目描述:   马尚官网是马尚(上海)投资管理有限公司的官方网站,主要包括了一些新闻资讯页面,订单页面功能!n责任描述:   负责后台的会员模块以及相关的购票的活动的页面和后台权限系统的开发!独立完成了相关的微信消息推送的功能,微信订票的功能的制作!n2014/6-2014/7   呈军企业门户的开发n项目描述:   phpstorm(开发工具) lamp(软件环境)呈军企业门户网站是一个企业的门户网站n责任描述:   我的主要责任是网站的前台的页面的功能开发,后台的搭建n2014/3-2014/6   沐悦网站的维护n项目描述:   phpstorm(开发工具) linux(硬件环境) lamp(软件环境)沐浴商城 是一个企业电子商城,服务与台湾的用户。n责任描述:   企业的电子商城的设计与开发 包括前台页面的显示,后台的开发。n2014/1-2014/2   久畅科技的制作n项目描述:   phpstorm(开发工具) linux(硬件环境) lamp(软件环境)久畅科技是一个台湾的客户的企业门户网站主要是一个cms内容的管理系统!n责任描述:   网站的前后台的搭建以及各个功能模块的开发n2013/11-2014/12   烈日咖啡的制作n项目描述:   phpstorm(开发工具) linux(硬件环境) lamp(软件环境)企业电子商务网站n责任描述:   网站的前台页面和后台的制作n2013/10-2013/11   夕达科技的制作n项目描述:   phpstorm(开发工具) linux(硬件环境) lamp(软件环境)企业门户网站n责任描述:   网站的制作n2013/9-2013/10   生活笔记n项目描述:   phpstorm(开发工具) windows(硬件环境) lamp(软件环境)个人网站n责任描述:   独立完成各模块的功能,同时负责客户的数据的采集模块的制作n2013/8-2013/9   皇冠皮件的制作n项目描述:   phpstorm(开发工具) windows(硬件环境) lamp(软件环境)企业门户网站,对企业的一些基本的宣传n责任描述:   网站的前后台的搭建n2013/4-2013/7   公司内部系统的开发n项目描述:   phpstorm + mysql + linux(开发工具) lamp(软件环境)实现公司内部的系统的开发维护,主要包括报表功能,事件流功能,任务分配功能等!n责任描述:   独立完成相关的事项的开发工作n2013/3-2013/4   快付通,台湾支付平台n项目描述:   phpstorm(开发工具)nwindows(硬件环境)nlamp(软件环境)快付通,台湾支付平台的企业介绍网站n利用公司的公版进行的项目开发n责任描述:   整个网站的搭建n2013/1-2013/3   新闻管理系统n项目描述:   epp、dw;ps(开发工具) windows(硬件环境) wamp(软件环境)实现了新闻的基本功能及后台的管理工作,对mysql和linux、js有一定的认知。n责任描述:   独立完成整个系统的开发n教育经历n2009/9-2013/6   青海大学(211工程重点建设院校)n本科|机械电子工程/机电一体化n专业描述:   在校期间,主修机电一体化专业。专业课程有微机原理与接口技术、机械系统微机控制、辅助专业c及汇编语言。同时,在校时还自学了c++以及php编程。n技能特长 (包括it技能、语言能力、证书、成绩、培训经历)n技能/语言n英语   良好nlinux   熟练njavascript   熟练nphp   精通nsql   熟练n证书n2009/12   大学英语四级 (429)\",\"resume_integrity\":\"87\",\"resume_type\":\"0\",\"skills_objs\":[{\"skills_name\":\"英语\"},{\"skills_level\":\"熟练\",\"skills_name\":\"linux\"},{\"skills_level\":\"熟练\",\"skills_name\":\"javascript\"},{\"skills_name\":\"php\"},{\"skills_level\":\"熟练\",\"skills_name\":\"sql\"},{\"skills_name\":\"数据库\"}],\"surname\":\"王\",\"training_objs\":[],\"work_company\":\"上海大岂网络科技有限公司\",\"work_position\":\"高级软件工程师\",\"work_start_time\":\"2013.02\",\"work_year\":\"4\"},\"status\":{\"code\":200,\"message\":\"success\"},\"tags\":{\"industries\":[{\"tag_name\":\"计算机软件\",\"tag_weight\":1.4},{\"tag_name\":\"互联网/电子商务\",\"tag_weight\":1.0},{\"tag_name\":\"专业服务(咨询、人力资源、财会)\",\"tag_weight\":0.8}],\"pos_tags\":[{\"tag_name\":\"php开发工程师\",\"tag_weight\":1.6067499727824057},{\"tag_name\":\"linux开发工程师\",\"tag_weight\":1.3902881112183063},{\"tag_name\":\"软件开发工程师\",\"tag_weight\":1.0000091537109153},{\"tag_name\":\"python开发工程师\",\"tag_weight\":0.7237236189708056},{\"tag_name\":\"后端开发工程师\",\"tag_weight\":0.5382724426165262},{\"tag_name\":\"java开发工程师\",\"tag_weight\":0.5193569268336687},{\"tag_name\":\"开发工程师\",\"tag_weight\":0.5029498706858783},{\"tag_name\":\"c开发工程师\",\"tag_weight\":0.49530964851379394},{\"tag_name\":\"服务器开发工程师\",\"tag_weight\":0.48082537683815085},{\"tag_name\":\"后台开发工程师\",\"tag_weight\":0.46838974786922516}],\"pos_types\":[{\"tag_name\":\"软件工程师\",\"tag_weight\":3.4897107742172935},{\"tag_name\":\"高级软件工程师\",\"tag_weight\":1.5823485993529924},{\"tag_name\":\"互联网软件开发工程师\",\"tag_weight\":1.3809385360766724},{\"tag_name\":\"嵌入式软件开发(linux/单片机/plc/dsp…)\",\"tag_weight\":0.1769457596096026},{\"tag_name\":\"其他\",\"tag_weight\":0.14788685422293324}],\"skills_tags\":[{\"tag_name\":\"php\",\"tag_weight\":1.269267908593541},{\"tag_name\":\"javascript\",\"tag_weight\":0.6982977819352775},{\"tag_name\":\"lamp\",\"tag_weight\":0.6574966126084725},{\"tag_name\":\"redis\",\"tag_weight\":0.44943170248648195},{\"tag_name\":\"设计\",\"tag_weight\":0.35624074713045506},{\"tag_name\":\"开发\",\"tag_weight\":0.3469500369186724},{\"tag_name\":\"mysql\",\"tag_weight\":0.3072476538050301},{\"tag_name\":\"bp\",\"tag_weight\":0.27180999045298754},{\"tag_name\":\"收购\",\"tag_weight\":0.26848990768885067},{\"tag_name\":\"通关\",\"tag_weight\":0.2497688434380983},{\"tag_name\":\"mongodb\",\"tag_weight\":0.22674557480104862},{\"tag_name\":\"yii\",\"tag_weight\":0.21685584497535815},{\"tag_name\":\"服务器集群\",\"tag_weight\":0.19399841324509898},{\"tag_name\":\"lvs\",\"tag_weight\":0.17708192136323025},{\"tag_name\":\"网站开发\",\"tag_weight\":0.1710326996231836},{\"tag_name\":\"游戏平台\",\"tag_weight\":0.1557232718674662},{\"tag_name\":\"cdn\",\"tag_weight\":0.15533423144346162},{\"tag_name\":\"客服\",\"tag_weight\":0.13118312216489258},{\"tag_name\":\"nodejs\",\"tag_weight\":0.11353081742155582}]}}";
}