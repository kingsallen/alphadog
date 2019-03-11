package com.moseeker.mq.service.sms;

/**
 * @author ltf
 * 阿里大鱼-短信模板
 * 2017年3月13日
 */
public enum SmsTemplate {
	
	EMPLOYEE_MERGE_ACCOUNT_SMS("SMS_5735115", "感谢您访问仟寻，为方便您再次访问并使用仟寻招聘，我们已免费为您开通雇主权限，账号%s，密码为%s，请及时登录仟寻官网修改您的登录密码，保障账号安全。"),
	
	RANDOM_SMS("SMS_5755096", "您的验证码是：%s。请不要把验证码泄露给其他人。"),
	
	RANDOM_PWD_SMS("SMS_5855001", "感谢您访问仟寻并下载行业报告，为方便您再次访问并使用仟寻招聘，我们已免费为您开通雇主权限，账号%s，密码为%s，请及时登录hr.moseeker.com修改您的登录密码，保障账号安全。"),
	
	POSITION_FAV_SMS("SMS_5895237", "感谢您使用仟寻，为方便您再次访问并了解求职进展，我们已为您开通个人账号：账号为%s，密码为%s，请及时到个人中心账号设置中修改您的登录密码，保障账号安全。"),
	
	NEW_APPLICATION_TO_HR_SMS("SMS_5715100", "您发布的职位%s获得一封新简历，请登录仟寻招聘管理平台查看哦！"),
	
	NEW_APPLIACATION_TO_APPLIER_SMS("SMS_5740044", "您好！%s已收到您对%s职位的求职简历。关注微信公众号'仟寻'，实时查看您的求职进度。"),
	
	APPLICATION_IS_VIEW_SMS("SMS_5690213", "您好！%s的HR已查看您的简历，距离您理想的工作又进了一步。关注微信公众号'仟寻'，实时查看您的求职进度。"),
	
	APPLICATION_REJECT_SMS("SMS_14211719", "您应聘的%s-%s职位，HR认为您暂不匹配。关注微信公众号'仟寻',及时获取更多公司职位信息。"),
	
	APPLICATION_CANCEL_REJECT_SMS("SMS_5685040", "您应聘的%s-%s职位，HR正在重新考虑，将您置为%s状态。关注微信公众号'仟寻'，实时查看您的求职进度。"),
	
	APPLICATION_APPROVED_SMS("SMS_5945001", "您的简历已通过%s-%s职位的评审。恭喜您！离您理想的工作又进了一步。关注微信公众号'仟寻'，实时查看您的求职进度。"),
	
	APPLICATION_INTERVIEW_SMS("SMS_5930002", "您的简历已通过%s-%s职位的面试。恭喜您！离您理想的工作又进了一步。关注微信公众号'仟寻'，实时查看您的求职进度。"),
	
	APPLICATION_ENTRY_SMS("SMS_5885016", "恭喜您成功加入%s！关注微信公众号'仟寻'，推荐更多的小伙伴加入你的队伍吧~~"),
	
	UPDATE_SYSUSER_SMS("SMS_6350435", "感谢使用仟寻，您已可以使用账号%s，密码%s登录仟寻，了解求职进展。请及时到个人中心修改密码"),
	
	REGISTERED_THREE_DAYS_SMS("SMS_5675088", "感谢您注册仟寻招聘，记得登录hr.moseeker.com来发职位哦~微信关注'仟寻招聘助手'，使用移动招聘功能，助您一臂之力。"),
	
	APPLIER_REMIND_EMAIL_ATTACHMENT_SMS("SMS_5735203", "您应聘%s的%s尚未上传简历。请在仟寻发送给您的邮件中上传简历附件。关注微信公众号'仟寻'，实时查看您的求职进度。"),
	
	APPLIER_REMIND_EMAIL_ATTACHMENT_COM_SMS("SMS_5695256", "您好！您尚未上传简历，请在仟寻发送给您的邮件中上传简历附件，关注微信公众号'仟寻'，实时查看您的求职进度。"),
	
	HR_INVITE_BYPASS_ACCOUNT_SMS("SMS_5935247", "您好, %s公司邀请您成为其子账号, 点击以下链接并同意即可开通账号: http://hr.moseeker.com/tu/%s"),
	
	HR_BYPASS_ACCOUNT_SMS("SMS_5920001", "您好, 您已成为%s公司子账号, 登录名为您的手机号, 密码为%s"),
	
	HR_BYPASS_ACCOUNT_OPEN_SMS("SMS_5910002", "您好, %s用户已经同意成为贵公司的子账号"),
	
	HR_BYPASS_ACCOUNT_REJECT_SMS("SMS_5900004", "您好, %s用户拒绝成为贵公司的子账号"),
	
	APPLIER_EMAIL_APP_SUC_SMS("SMS_5890259", "您应聘的%s公司%s职位已经投递成功。关注微信公众号'仟寻'，实时查看您的求职进度"),
	
	APPLIER_EMAIL_APP_NO_ATTACH_SMS("SMS_5875173", "您应聘的%s公司%s职位使用的Email简历投递未上传附件。请再次在仟寻发送给您的邮件中上传简历附件。关注微信公的众号'仟寻'，实时查看您的求职进度"),
	
	APPLIER_EMAIL_APP_ATTACH_ERROR_SMS("SMS_5885252", "您应聘的%s公司%s职位上传的Email简历附件格式不正确。请再次在仟寻发送给您的邮件中上传简历附件。关注微信公的众号'仟寻'，实时查看您的求职进度。"),
	
	APPLIER_EMAIL_APP_ATTACH_OVERSIZE_SMS("SMS_5910272", "您应聘的%s公司%s职位上传的Email简历附件过大。请再次在仟寻发送给您的邮件中上传简历附件。关注微信公的众号'仟寻'，实时查看您的求职进度"),
	
	APPLIER_EMAIL_APP_RESOLVE_FAIL_SMS("SMS_5930121", "您应聘的%s公司%s职位上传的Email简历解析失败。请再次在仟寻发送给您的邮件中上传简历附件。关注微信公的众号'仟寻'，实时查看您的求职进度。"),
	
	APPLIER_EMAIL_APP_ATTACH_RESOLVE_FAIL_SMS("SMS_5885253", "您应聘的%s公司%s职位上传的Email简历附件解析失败。请再次在仟寻发送给您的邮件中上传简历附件。关注微信公众号'仟寻'，实时查看您的求职进度"),
	
	APPLIER_APP_ATTACH_RESOLVE_SUC_SMS("SMS_5965103", "您好！您上传的附件简历解析成功，您已成功创建您的仟寻简历。关注微信公众号'仟寻'，实时查看您的求职进度"),
	
	APPLIER_APP_ATTACH_RESOLVE_FAIL_SMS("SMS_5930140", "您好! 您上传的附件简历获取失败，请在仟寻发送给您的邮件中再次上传简历附件。关注微信公众号'仟寻'，实时查看您的求职进度"),
	
	APPLIER_APP_ATTACH_RESOLVE_ERROR_SMS("SMS_5895263", "您好！您上传的附件简历格式不正确，请在仟寻发送给您的邮件中再次上传简历附件。关注微信公众号'仟寻'，实时查看您的求职进度"),
	
	PPLIER_APP_ATTACH_RESOLVE_OVERSIZE_SMS("SMS_5910306", "您好！您上传的附件简历过大，请在仟寻发送给您的邮件中再次上传简历附件。关注微信公众号'仟寻'，实时查看您的求进度"),
	
	APPLIER_APP_RESOLVE_FAIL_SMS("SMS_5910307", "您好！您上传的附件获取失败，请在仟寻发送给您的邮件中再次上传简历附件。关注微信公众号'仟寻'，实时查看您的求进度"),

	ALARM_SMS("SMS_5905208", "系统报警！项目%s，错误消息%s，发生频率%s");
	
	private String smsCode;
	
	private String msg;
	
	SmsTemplate (String smsCode, String msg) {
		this.smsCode = smsCode;
		this.msg = msg;
	}



	public String getSmsCode() {
		return smsCode;
	}

	public String getMsg() {
		return msg;
	}
}
