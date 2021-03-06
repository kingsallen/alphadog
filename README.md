# README

## 背景
本项目为MoSeeker基础服务，旨在为面向C端的项目提供业务接口实现，具体包括：

* mobile 
	* 企业号 - WeixinReferrer
	* 聚合号 - moseeker_bagging
* pc
	* 企业profile
	* 公司profile
	* jd
	* ...

*hr和sysplat暂时不在考虑范围。*

## 技术架构

面向服务的架构:

![基础架构图](http://wiki.moseeker.com/download/16)

* zookeeper - 服务治理
* thrift - RPC调用协议
* jooq - 数据库查询语言
* mycat - 数据库分布式处理系统
	* mysql
* redis
* springMVC - HTTP调用接口
* gradle - 项目打包

目前主要使用语言为java(1.8)，由于服务调用使用thrift，所以服务也可使用其他为thrift支持的语言来实现。

## 项目结构

```
.
├── commom // 公共库, redis日志、邮件/短信报警通知，数据库连接池，校验
├── service-manager // war, HTTP调用接口，use springMVC，"client"
├── rpc-center // 提供连接zookeeper及注册服务的通用类
├── thrift-jooq // thrfit接口定义文件(IDL)，jooq配置文件
├── demo-service-provider // jar, 编写一个服务的demo
└── oo-service-provider // 具体的service
└── xx-service-provider // 具体的service
```
## ServiceManager

ServiceManager 项目为基础服务对外提供符合restful风格的http协议接口服务。ServiceManager主要包括两个功能：1.http协议的解析和回复；2.获取和调用RPC服务，并返回结果。

#### http协议解析和回复

ServiceManager采用SpringMVC用于解析和处理restful风格的http请求，并返回相应结果。SpringMVC可以很方便的扩展其他功能,比如后续可以非常方便的添加一些黑名单，权限校验等拦截器。

对于查询资源的接口，存在一些通用参数，比如：当前页码，每页显示数量，关键词等。ServiceManager 有一个类ParamUtils，显示调用initParam方法，可以将rquest Parameters中的通用参数存入到定义好的数据结构中。

#### RPC服务获取和调用
ServiceManager为了让用户更方便地开发功能，对获取RPC服务做了特别封装。利用thrfit命令生成对应Java文件，并将生成好的类传给ServiceUtil工具，ServiceUtil将会帮助我们找到一个RPC服务。我们调用本地接口，本地接口会调用RPC服务的对应方法，并将执行结果返回给我们。

## 运行项目

在命令行启动项目：

1. 安装[zookeeper](https://zookeeper.apache.org/)最新版本
2. 安装[redis集群](https://redis.io/)
3. 下载alphadog项目`git clone http://git.moseeker.com/git/zhaozh/alphadog.git`
4. 运行依赖服务
    * 启动zookeeper服务 `zkServer.sh start some_conf_file`
    * 启动redis集群
    * 启动数据库
5. 修改服务配置
    * /src/main/resources/common.properties
        * redis.{通配符}.host reids 集群地址-IP地址
        * redis.{通配符}.port reids 集群地址-端口号
        * redis.elk.host log4j日志（不必要）
        * rabbitmq. rebbitmq地址（部分服务需要）
        *  mycat. 数据库配置
    * /src/main/resources/es.properties （部分服务需要）
        * es elasticsearch服务地址 
    * service.properties 
        * zookeeper.ZKIP zokeeper IP 地址
        * zookeeper.ZKport zokeeper 端口号
    * log4j.properties
        * log4j.appender.D.File 文件日志输出地址  
6. 运行一个服务
	* cd 到一个具体的服务下, 比如alpahdog/profile-service-provider。
	* 依次执行 `gradle clean` `gradle build -x test --parallel`
	* cd 到打包的目标目录，比如 build/libs，找到声称好的jar包，执行: `java -jar ./build/libs/xxx.jar`
	* 也可以在IDE中，直接run /src/main/java/{root package}/{service}Server.java，比如profile-service-provider服务中，com.moseeker.profile.ProfileServer.java 直接执行main方法。
7. run the "client"
	* `cd service-manager`
	* `gradle jetty run`

在IntelliJ中启动项目：

1. 在根目录及各子目录 run `gradle idea`
2. ...

在eclipse中启动项目：

1. 在根目录及各子目录 run `gradle eclipse`
2. ...

## 上线流程

1. `service-manager` -> `gradle war -Penv=production` -> war
2. `oo-service-provider` ->  `gradle build -Penv=production` -> jar

`env`表示对应的环境

## TODO

lots of todos


