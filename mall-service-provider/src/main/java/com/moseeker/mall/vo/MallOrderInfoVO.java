package com.moseeker.mall.vo;

import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

/**
 * 订单记录VO
 *
 * @author cjm
 * @date 2018-10-16 12:01
 **/
public class MallOrderInfoVO{
    private Integer id;
    private Integer order_id;
    private Integer good_id;
    private Integer credit;
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

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
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

    public MallOrderInfoVO cloneFromOrderAndEmloyee(MallOrderDO mallOrderDO, UserEmployeeDO userEmployeeDO){
        MallOrderInfoVO mallOrderInfoVO = new MallOrderInfoVO();
        mallOrderInfoVO.setId(mallOrderDO.getId());
        mallOrderInfoVO.setAssign_time(mallOrderDO.getAssign_time());
        mallOrderInfoVO.setCount(mallOrderDO.getCount());
        mallOrderInfoVO.setCreate_time(mallOrderDO.getCreateTime());
        mallOrderInfoVO.setCredit(mallOrderDO.getCredit());
        mallOrderInfoVO.setGood_id(mallOrderDO.getGoods_id());
        mallOrderInfoVO.setOrder_id(mallOrderDO.getOrder_id());
        mallOrderInfoVO.setPic_url(mallOrderDO.getPic_url());
        mallOrderInfoVO.setOrder_state(mallOrderDO.getState());
        mallOrderInfoVO.setEmployee_state((byte)userEmployeeDO.getStatus());
        mallOrderInfoVO.setTitle(mallOrderDO.getTitle());
        return mallOrderInfoVO;
    }
}
