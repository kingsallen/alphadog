
package com.moseeker.profile.pojo.resume;

import java.util.List;


public class Tags {

    private List<Industries> industries;
    private List<PosTypes> pos_types;
    private List<PosTags> pos_tags;
    private List<SkillsTags> skills_tags;

    public void setIndustries(List<Industries> industries) {
        this.industries = industries;
    }

    public List<Industries> getIndustries() {
        return industries;
    }

    public void setPos_types(List<PosTypes> pos_types) {
        this.pos_types = pos_types;
    }

    public List<PosTypes> getPos_types() {
        return pos_types;
    }

    public void setPos_tags(List<PosTags> pos_tags) {
        this.pos_tags = pos_tags;
    }

    public List<PosTags> getPos_tags() {
        return pos_tags;
    }

    public void setSkills_tags(List<SkillsTags> skills_tags) {
        this.skills_tags = skills_tags;
    }

    public List<SkillsTags> getSkills_tags() {
        return skills_tags;
    }

}