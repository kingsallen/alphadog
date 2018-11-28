package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.job58.vo.Job58PositionForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
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

    private Pattern chinese = Pattern.compile("[\u4e00-\u9fa5]");
    private Pattern email = Pattern.compile(REGEX_EMAIL);
    private Pattern mobile = Pattern.compile(REGEX_MOBILE);
    private static final String ENGLISH = ".*[a-zA-z].*";
    private static final String NUMBER = ".*[0-9].*";
    private static final String REGEX_EMAIL = "([a-z0-9_.-]+)@([\\da-z.-]+)\\.([a-z]{2,6})";
    private static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,35-8])|(18[0-9])|166|198|199|(147))\\\\d{8}$";

    private static final int TITLE_MIN_LENGTH = 2;
    private static final int TITLE_MAX_LENGTH = 12;
    private static final int CONTENT_MAX_LENGTH = 2000;
    private static final int RECRUIT_MAX_NUMBER = 999;

    private static final String TITLE_NOT_EMPTY = "标题不为空!";
    private static final String TITLE_LENGTH_LIMIT = "职位标题长度范围为2~12!";
    private static final String TITLE_NEED_CONTAINS_CHINESE = "职位标题需要包含中文!";
    private static final String TITLE_NEED_CONTAINS = "只支持汉字字母和数字!";
    private static final String CONTENT_LENGTH_LIMIT = "58职位描述字数限制为20-2000!";
    private static final String CONTENT_RULE_LIMIT = "工作内容和职位要求不能填写电话、QQ等联系方式!";
    private static final String RECRUIT_NUMBER_LIMIT = "招聘人数为1～3位整数!";
    private static final String SALARY_NOT_EMPTY = "薪资不能为空!";

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
            // 是否包含英语，数字，中文
            boolean containEnglish = title.matches(ENGLISH);
            boolean containNumber = title.matches(NUMBER);
            boolean containChinese = chinese.matcher(title).find();
            if (!containChinese) {
                errorMsg.add(TITLE_NEED_CONTAINS_CHINESE);
            }
            if (!containEnglish && !containChinese && !containNumber) {
                errorMsg.add(TITLE_NEED_CONTAINS);
            }
            // 职位描述长度限制
            String content = "职位描述：</br>" + moseekerPosition.getAccountabilities() + "</br>职位要求：</br>" + moseekerPosition.getRequirement();
            if (content.length() > CONTENT_MAX_LENGTH) {
                errorMsg.add(CONTENT_LENGTH_LIMIT);
            }
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
            if (job58PositionForm.getSalaryTop() != null && job58PositionForm.getSalaryBottom() != null) {
                if(job58PositionForm.getSalaryTop() == 0 && job58PositionForm.getSalaryBottom() == 0){
                    job58PositionForm.setSalaryDiscuss((byte)1);
                }else {
                    job58PositionForm.setSalaryDiscuss((byte)0);
                }
            }else {
                errorMsg.add(SALARY_NOT_EMPTY);
            }

        }
        return errorMsg;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB58;
    }

}
