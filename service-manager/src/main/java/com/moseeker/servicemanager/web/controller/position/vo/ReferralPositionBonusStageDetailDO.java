
package com.moseeker.servicemanager.web.controller.position.vo;

import java.io.Serializable;

public class ReferralPositionBonusStageDetailDO implements Serializable {


  public String stage_bonus ="0"; // optional
  public int stage_proportion; // optional
  public int stage_type; // optional

  public String getStage_bonus() {
    return stage_bonus;
  }

  public void setStage_bonus(String stage_bonus) {
    this.stage_bonus = stage_bonus;
  }

  public int getStage_proportion() {
    return stage_proportion;
  }

  public void setStage_proportion(int stage_proportion) {
    this.stage_proportion = stage_proportion;
  }

  public int getStage_type() {
    return stage_type;
  }

  public void setStage_type(int stage_type) {
    this.stage_type = stage_type;
  }
}

