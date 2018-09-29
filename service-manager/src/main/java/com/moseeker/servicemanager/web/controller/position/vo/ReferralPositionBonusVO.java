
package com.moseeker.servicemanager.web.controller.position.vo;


import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

public class ReferralPositionBonusVO implements Serializable {

  private static final long serialVersionUID = 5946700670085060295L;

  public ReferralPositionBonusDO position_bonus = new ReferralPositionBonusDO(); // required
  public java.util.List<ReferralPositionBonusStageDetailDO> bonus_details = Lists.newArrayList(); // required

  public ReferralPositionBonusDO getPosition_bonus() {
    return position_bonus;
  }

  public void setPosition_bonus(ReferralPositionBonusDO position_bonus) {
    this.position_bonus = position_bonus;
  }

  public List<ReferralPositionBonusStageDetailDO> getBonus_details() {
    return bonus_details;
  }

  public void setBonus_details(List<ReferralPositionBonusStageDetailDO> bonus_details) {
    this.bonus_details = bonus_details;
  }
}

