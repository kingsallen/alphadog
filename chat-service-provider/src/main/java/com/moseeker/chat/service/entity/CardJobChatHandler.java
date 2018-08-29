package com.moseeker.chat.service.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.common.constants.ChatMsgType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.chat.struct.ChatVO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardJobChatHandler implements IOutputChatHandler,IBeforeSaveChatHandler {

    @Autowired
    ChatDao chatDao;

    @Autowired
    HrCompanyDao companyDao;

    @Autowired
    JobPositionCityDao positionCityDao;

    private SerializeConfig serializeConfig = new SerializeConfig();

    public CardJobChatHandler() {
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    /**
     * 职位名片类型的聊天内容
     * 需要根据content中保存的职位ID，获取职位数据，
     * 包装称{@link PositionCard}对象，最后以json字符串的形式再存入content中
     *
     * @param chat 处理前的聊天内容
     * @return 处理后的聊天内容
     */
    @Override
    public ChatVO outputHandler(ChatVO chat) {
        String pid = chat.getContent();
        JobPositionDO position = chatDao.getPositionById(Integer.parseInt(pid));

        PositionCard positionCard = new PositionCard();
        if (position != null) {
            // 设置公司名称
            HrCompanyDO companyDO = companyDao.getCompanyById(position.getCompanyId());
            if (StringUtils.isNotNullOrEmpty(companyDO.getAbbreviation())) {
                positionCard.setCompanyName(companyDO.getAbbreviation());
            } else {
                positionCard.setCompanyName(companyDO.getName());
            }

            // 设置城市
            List<DictCityDO> cityList = positionCityDao.getPositionCitys(position.getId());
            if (!StringUtils.isEmptyList(cityList)) {
                positionCard.setCity(cityList.stream().map(c -> c.getName()).collect(Collectors.toList()));
            }

            // 设置薪资
            if (position.getSalaryTop()==0 && position.getSalaryBottom() == 0){
                positionCard.setSalary("薪资面议");
            } else {
                StringBuilder strBuilder = new StringBuilder();
                positionCard.setSalary(strBuilder
                        .append(position.getSalaryBottom())
                        .append("K - ")
                        .append(position.getSalaryTop())
                        .append("K").toString());
            }

            positionCard.setId(position.getId());
            positionCard.setUpdate(position.getUpdateTime());
            positionCard.setTitle(position.getTitle());
            positionCard.setStatus(Double.valueOf(position.getStatus()).intValue());
            positionCard.setTeam(position.getDepartment());

        }
        chat.setCompoundContent(JSON.toJSONString(positionCard, serializeConfig));
        return chat;
    }

    @Override
    public ChatMsgType msgType() {
        return ChatMsgType.JOB;
    }

    /**
     * 前端传入的content是一个{@link PositionCard}类型的json字符串
     * 入库时只需要把职位ID存入content就行
     * @param chat 处理前的聊天内容
     * @return 处理后的聊天内容
     */
    @Override
    public HrWxHrChatDO beforeSave(HrWxHrChatDO chat) {
        PositionCard positionCard = JSON.parseObject(chat.getCompoundContent(),PositionCard.class);
        chat.setContent(String.valueOf(positionCard.getId()));
        return chat;
    }

    private static class PositionCard {
        private int id;
        private String update;
        private List<String> city;
        private String salary;
        private String title;
        private String companyName;
        private int status;
        private String team;

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public List<String> getCity() {
            return city;
        }

        public void setCity(List<String> city) {
            this.city = city;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}
