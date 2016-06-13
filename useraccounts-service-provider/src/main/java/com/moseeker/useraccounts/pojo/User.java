package com.moseeker.useraccounts.pojo;

import java.sql.Timestamp;

/**
 * 用户Pojo实体
 * <p>
 *
 * Created by zzh on 16/5/28.
 */
public class User {
   public  int             id              ;  // 用户名，比如手机号、邮箱等
   public  String          username        ;  // 用户名，比如手机号、邮箱等
   public  long            mobile          ;  // user pass mobile registe
   public  String          password        ;  // 密码
   public  String          token           ;  //
   public  int             is_disable      ;  // 是否禁用，0：可用，1：禁用
   public  String          rank            ;  // 用户等级
   public  Timestamp       register_time   ;  // 注册时间
   public  String          register_ip     ;  // 注册IP
   public  Timestamp       last_login_time ;  // 最近登录时间
   public  String          last_login_ip   ;  // 最近登录IP
   public  int             login_count     ;  // 登录次数
   public  String          email           ;  // user pass email registe
   public  int             activation      ;  // is not activation 0:no 1:yes
   public  String          activation_code ;  // activation code
   public  String          name            ;  // 姓名或微信昵称
   public  String          headimg         ;  // 头像
   public  int             country_id      ;  // 国家字典表ID,
   public  int             wechat_id       ;  // 注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
   public  String          unionid         ;  // 存储仟寻服务号的unionid
   public  int             source          ;  // 来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录
   public  String          company         ;  // 点击我感兴趣时填写的公司
   public  String          position        ;  // 点击我感兴趣时填写的职位
   public  int             parentid        ;  // 合并到了新用户的id
}
