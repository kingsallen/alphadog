package com.moseeker.servicemanager.web.controller.referral.form;

import java.io.Serializable;
import java.util.List;

/**
 * @Date: 2018/9/25
 * @Author: JackYang
 */
public class ReferralBonusForm implements Serializable{

    private static final long serialVersionUID = -2266728614120415854L;

        private int appid;
        private int total_bonus;
        private String position_id;
        private List<Stage_data> stage_data;

        public void setAppid(int appid) {
            this.appid = appid;
        }
        public int getAppid() {
            return appid;
        }

        public void setTotal_bonus(int total_bonus) {
            this.total_bonus = total_bonus;
        }
        public int getTotal_bonus() {
            return total_bonus;
        }

        public void setPosition_id(String position_id) {
            this.position_id = position_id;
        }
        public String getPosition_id() {
            return position_id;
        }

        public void setStage_data(List<Stage_data> stage_data) {
            this.stage_data = stage_data;
        }
        public List<Stage_data> getStage_data() {
            return stage_data;
        }

}
     class Stage_data {

        private int bonus;
        private int stage_type;
        private int proportion;
        public void setBonus(int bonus) {
            this.bonus = bonus;
        }
        public int getBonus() {
            return bonus;
        }

        public void setStage_type(int stage_type) {
            this.stage_type = stage_type;
        }
        public int getStage_type() {
            return stage_type;
        }

        public void setProportion(int proportion) {
            this.proportion = proportion;
        }
        public int getProportion() {
            return proportion;
        }

    }