
package com.moseeker.servicemanager.web.controller.position.vo;

import java.io.Serializable;

public class ReferralPositionBonusDO implements Serializable {

  private static final long serialVersionUID = -2221144992412406654L;

  public int position_id; // optional
  public String total_bonus ="0"; // optional

  public int getPosition_id() {
    return position_id;
  }

  public void setPosition_id(int position_id) {
    this.position_id = position_id;
  }

  public String getTotal_bonus() {
    return total_bonus;
  }

  public void setTotal_bonus(String total_bonus) {
    this.total_bonus = total_bonus;
  }
}

