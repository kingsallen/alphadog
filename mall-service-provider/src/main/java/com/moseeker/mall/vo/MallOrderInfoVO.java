package com.moseeker.mall.vo;

import com.moseeker.common.util.StringUtils;
import com.moseeker.mall.constant.OrderUserEmployeeEnum;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import java.text.DateFormat;

/**
 * 订单记录VO
 *
 * @author cjm
 * @date 2018-10-16 12:01
 **/
public class MallOrderInfoVO{
    private Integer id;
    private String order_id;
    private Integer good_id;
    private Integer credit;
    private String name;
    private String mobile;
    private String email;
    private String custom;
    private String title;
    private String pic_url;
    private Integer count;
    private Byte order_state;
    private Byte employee_state;
    private String assign_time;
    private String create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Integer getGood_id() {
        return good_id;
    }

    public void setGood_id(Integer good_id) {
        this.good_id = good_id;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getAssign_time() {
        return assign_time;
    }

    public void setAssign_time(String assign_time) {
        this.assign_time = assign_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Byte getOrder_state() {
        return order_state;
    }

    public void setOrder_state(Byte order_state) {
        this.order_state = order_state;
    }

    public Byte getEmployee_state() {
        return employee_state;
    }

    public void setEmployee_state(Byte employee_state) {
        this.employee_state = employee_state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void cloneFromOrderAndEmloyee(MallOrderDO mallOrderDO, UserEmployeeDO userEmployeeDO,
                                         UserEmployeeDO historyUserEmployeeDO, DateFormat dateFormat) {
        setId(mallOrderDO.getId());
        setCount(mallOrderDO.getCount());
        setCredit(mallOrderDO.getCredit());
        setGood_id(mallOrderDO.getGoods_id());
        setOrder_id(mallOrderDO.getOrder_id());
        setPic_url(mallOrderDO.getPic_url());
        setOrder_state(mallOrderDO.getState());
        setTitle(mallOrderDO.getTitle());
        if(dateFormat == null){
            setCreate_time(mallOrderDO.getCreateTime());
            setAssign_time(mallOrderDO.getAssign_time());
        }else {
            try {
                // 格式化时间格式
                setCreate_time(dateFormat.format(dateFormat.parse(mallOrderDO.getCreateTime())));
                if(!StringUtils.isNullOrEmpty(mallOrderDO.getAssign_time())){
                    setAssign_time(dateFormat.format(dateFormat.parse(mallOrderDO.getAssign_time())));
                }
            }catch (Exception e){
                setCreate_time(mallOrderDO.getCreateTime());
                setAssign_time(mallOrderDO.getAssign_time());
            }
        }
        if(userEmployeeDO != null){
            setEmployee_state(userEmployeeDO.getActivation() == 0 ?
                    (byte)OrderUserEmployeeEnum.VERTIFIED.getState() :
                    (byte)OrderUserEmployeeEnum.UNVERTIFIED.getState());
        }else {
            if(historyUserEmployeeDO == null){
                return;
            }
            userEmployeeDO = historyUserEmployeeDO;
            setEmployee_state((byte)OrderUserEmployeeEnum.DELETED.getState());
        }
        setMobile(userEmployeeDO.getMobile());
        setEmail(userEmployeeDO.getEmail());
        setCustom(userEmployeeDO.getCustomField());
        setName(userEmployeeDO.getCname());
    }
    /**
     * 根据订单信息，员工信息组装页面显示的订单信息
     * @param mallOrderDO 订单信息
     * @param userEmployeeDO 员工信息
     * @param historyUserEmployeeDO 历史表员工信息
     * @author  cjm
     * @date  2018/10/23
     */
    public void cloneFromOrderAndEmloyee(MallOrderDO mallOrderDO, UserEmployeeDO userEmployeeDO,
                                         UserEmployeeDO historyUserEmployeeDO){
        cloneFromOrderAndEmloyee(mallOrderDO, userEmployeeDO, historyUserEmployeeDO, null);
    }

    @Override
    public String toString() {
        return "MallOrderInfoVO{" +
                "id=" + id +
                ", order_id='" + order_id + '\'' +
                ", good_id=" + good_id +
                ", credit=" + credit +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", custom='" + custom + '\'' +
                ", title='" + title + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", count=" + count +
                ", order_state=" + order_state +
                ", employee_state=" + employee_state +
                ", assign_time='" + assign_time + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
