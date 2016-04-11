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

![基础架构图](http://wiki.moseeker.com/download/14)

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
## 运行项目

在命令行启动项目：

1. 安装[zookeeper](https://zookeeper.apache.org/)最新版本
2. `git clone http://git.moseeker.com/git/zhaozh/alphadog.git`
3. run zookeeper
	* `zkServer.sh start some_conf_file`
4. run a service-provider
	* `cd path/oo-service-provider`
	* `gradle build`
	* `java -jar ./build/libs/xxx.jar`
5. run the "client"
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

