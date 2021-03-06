package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.job58.vo.Job58PositionForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author cjm
 * @date 2018-11-27 14:17
 **/
@Component
public class Job58TransferCheck extends AbstractTransferCheck<Job58PositionForm> {

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    private Pattern chinese = Pattern.compile("[\u4e00-\u9fa5]");
    private Pattern email = Pattern.compile(REGEX_EMAIL);
    private Pattern mobile = Pattern.compile(REGEX_MOBILE);
    private Pattern titleLimit = Pattern.compile(REGEX_TITLE);

    private static final String REGEX_EMAIL = "([a-z0-9_.-]+)@([\\da-z.-]+)\\.([a-z]{2,6})";
    private static final String REGEX_MOBILE = "1[3|4|5|6|7|8|9][0-9]{9}";
    private static final String REGEX_TITLE = "[^\u4e00-\u9fa5_a-zA-Z0-9()（）\\-—*]";

    private static final int TITLE_MIN_LENGTH = 2;
    private static final int TITLE_MAX_LENGTH = 12;
    private static final int USERNAME_MIN_LENGTH = 2;
    private static final int USERNAME_MAX_LENGTH = 6;
    private static final int RECRUIT_MAX_NUMBER = 999;

    private static final String TITLE_NOT_EMPTY = "标题不为空!";
    private static final String TITLE_LENGTH_LIMIT = "职位标题长度范围为2~12!";
    private static final String TITLE_NEED_CONTAINS_CHINESE = "职位标题需要包含中文!";
    private static final String TITLE_LIMIT = "职位标题中特殊符号支持中英文括号，短横线，下划线，星号!";
    private static final String CONTENT_RULE_LIMIT = "工作内容和职位要求不能填写电话、QQ等联系方式!";
    private static final String RECRUIT_NUMBER_LIMIT = "招聘人数为1～3位整数!";
    private static final String SALARY_NOT_EMPTY = "薪资不能为空!";
    private static final String SALARY_AMOUT_LIMIT = "薪资上限不能超过下限的两倍!";
    private static final String OCCUPATION_NOT_EMPTY = "职能不能为空!";
    private static final String HR_ACCOUNT_DISABLE = "账号已停用!";
    private static final String USERNAME_LENGTH_LIMIT = "职位联系人姓名长度范围2-6个字!";

    @Override
    public Class<Job58PositionForm> getFormClass() {
        return Job58PositionForm.class;
    }

    @Override
    public List<String> getError(Job58PositionForm job58PositionForm, JobPositionDO moseekerPosition) {
        List<String> errorMsg = new ArrayList<>();
        String title = moseekerPosition.getTitle();
        if (StringUtils.isNullOrEmpty(title)) {
            errorMsg.add(TITLE_NOT_EMPTY);
        } else {
            // 长度判断
            if (title.length() < TITLE_MIN_LENGTH || title.length() > TITLE_MAX_LENGTH) {
                errorMsg.add(TITLE_LENGTH_LIMIT);
            }
            // 是否包含中文
            boolean containChinese = chinese.matcher(title).find();
            if (!containChinese) {
                errorMsg.add(TITLE_NEED_CONTAINS_CHINESE);
            }
            // 是否包含除了中英文，数字，括号，短横线，下划线，星号之外的特殊字符
            boolean containSpecialChar = titleLimit.matcher(title).find();
            if(containSpecialChar){
                errorMsg.add(TITLE_LIMIT);
            }
            // 职位描述长度限制
            String content = "工作内容：" + moseekerPosition.getAccountabilities() + "</br>职位要求：" + moseekerPosition.getRequirement();
            // 职位详情不能包括手机和qq
            boolean containMobile = mobile.matcher(content).find();
            boolean containEmail = email.matcher(content).find();
            if (containMobile || containEmail) {
                errorMsg.add(CONTENT_RULE_LIMIT);
            }
            // 招聘人数限制
            if (moseekerPosition.getCount() > RECRUIT_MAX_NUMBER) {
                errorMsg.add(RECRUIT_NUMBER_LIMIT);
            }
            // 薪资必须不为空，如果都为0，则是面议
            if (job58PositionForm.getSalaryTop() == null || job58PositionForm.getSalaryBottom() == null) {
                errorMsg.add(SALARY_NOT_EMPTY);
            }else {
                if(job58PositionForm.getSalaryTop() > job58PositionForm.getSalaryBottom() * 2){
                    errorMsg.add(SALARY_AMOUT_LIMIT);
                }
            }

            // 职能不能为空
            if (job58PositionForm.getOccupation() == null || job58PositionForm.getOccupation().size() == 0) {
                errorMsg.add(OCCUPATION_NOT_EMPTY);
            }
            // 检验职位联系人姓名长度
            UserHrAccountDO userHrAccountDO = userHrAccountDao.getValidAccount(moseekerPosition.getPublisher());
            if (userHrAccountDO == null) {
                errorMsg.add(HR_ACCOUNT_DISABLE);
            } else {
                String username = userHrAccountDO.getUsername();
                if(StringUtils.isNullOrEmpty(username)){
                    errorMsg.add(USERNAME_LENGTH_LIMIT);
                }else {
                    if(username.length() > USERNAME_MAX_LENGTH || username.length() < USERNAME_MIN_LENGTH){
                        errorMsg.add(USERNAME_LENGTH_LIMIT);
                    }
                }
            }

        }
        return errorMsg;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB58;
    }

}
