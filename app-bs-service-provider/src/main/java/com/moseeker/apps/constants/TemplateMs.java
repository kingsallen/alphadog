package com.moseeker.apps.constants;

import java.text.MessageFormat;
import java.util.Date;

public enum TemplateMs {

	TOSEEKER("求职者") {
		@Override
		public MsInfo processStatus(int status, Object...args) {
			MsInfo mi = null;
			switch (status) {
				case 12:
			        mi = new MsInfo(35,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}&from_template_message=35","您好，您已入职成功", "入职成功", "点击查看投递进度详情");
			        break;
				case 13:
				    mi = new MsInfo(36,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}&from_template_message=36", "您好，感谢您对我司的关注！目前我们会考虑与该职位更合适的候选人，现将您的简历纳入公司人才储备库中，后续有机会再与您联系。", "不合适", "点击查看投递进度详情");
				    break;
				case 4:
				    mi = new MsInfo(9,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}&from_template_message=9", "您好，您的简历已被查阅", "已查阅简历", "点击查看求职进度详情");
				    break;
				case 7:
				    mi = new MsInfo(32,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}&from_template_message=32","你好，你的简历已通过初筛", "初筛通过", "点击查看求职进度详情");
				    break;
				case 10:
					mi = new MsInfo(45,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}&from_template_message=45","您好，您已通过面试", "面试通过", "点击查看求职进度详情");
			}
			return mi;
		}
	},

    RESRT("撤回原操作") {
        @Override
        public MsInfo processStatus(int status, Object...args) {
            MsInfo mi = null;
            switch (status) {
                case 4:
                    mi = new MsInfo(37,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}&from_template_message=37","您好，HR重新评估了您的简历，更新状态如下：", "已查阅简历", "点击查看投递进度详情");
                    break;
                case 7:
                    mi = new MsInfo(37,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}&from_template_message=37", "您好，HR重新评估了您的简历，更新状态如下：", "初筛通过", "点击查看投递进度详情");
                    break;
                case 10:
                    mi = new MsInfo(37,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}&from_template_message=37", "您好，HR重新评估了您的简历，更新状态如下：", "面试通过", "点击查看求职进度详情");
                    break;
            }
            return mi;
        }
    },
	
	TORECOM("推荐者") {
		@Override
		public MsInfo processStatus(int status, Object ...args) {
			MsInfo mi = null;
			switch (status) {
			    case 12:
			        mi = new MsInfo(60,"{0}m/employee/referral/progress?wechat_signature={1}&from_template_message=60", MessageFormat.format("您好，您推荐的{0}已入职", args), "已入职", "感谢您对公司人才招聘的贡献，欢迎继续推荐");
			        break;
				case 13:
				    mi = new MsInfo(61,"{0}m/employee/referral/progress?wechat_signature={1}&from_template_message=61",MessageFormat.format("很遗憾，您推荐的候选人{0}和公司在招岗位不合适", args), "简历不合适", "感谢您对公司人才招聘的贡献，欢迎继续推荐");
				    break;
			}
			return mi;
		}
	},

    TORECOMSTATUS("推荐者2") {
        @Override
        public MsInfo processStatus(int status, Object ...args) {
            MsInfo mi = null;
            switch (status) {
                case 4:
                    mi = new MsInfo(75,"{0}m/employee/referral/progress?wechat_signature={1}&from_template_message=75", MessageFormat.format("您好，您推荐的候选人简历已被查看", args), "日期", "感谢您对公司人才招聘的贡献，欢迎继续推荐！");
                    break;
                case 7:
                    mi = new MsInfo(76,"{0}m/employee/referral/progress?wechat_signature={1}&from_template_message=76",MessageFormat.format("您好，您推荐的候选人简历已通过初筛", args), "日期", "感谢您对公司人才招聘的贡献，欢迎继续推荐！");
                    break;
                case 10:
                    mi = new MsInfo(77,"{0}m/employee/referral/progress?wechat_signature={1}&from_template_message=77", MessageFormat.format("您好，您推荐的候选人已通过面试", args), "日期", "感谢您对公司人才招聘的贡献，欢迎继续推荐！");
                    break;
            }
            return mi;
        }
    };

	private String type;
	
	TemplateMs(String type) {
		this.type = type;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract MsInfo processStatus(int status, Object ...args);
	
	public class MsInfo {
		private String result;
		private String statusDesc; 
		private String remark;
		private int config_id;
		private String url;

		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getStatusDesc() {
			return statusDesc;
		}
		public void setStatusDesc(String statusDesc) {
			this.statusDesc = statusDesc;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}

        public int getConfig_id() {
            return config_id;
        }

        public void setConfig_id(int config_id) {
            this.config_id = config_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public MsInfo(int config_id, String url, String result, String statusDesc, String remark) {
			super();
			this.config_id = config_id;
			this.url = url;
			this.result = result;
			this.statusDesc = statusDesc;
			this.remark = remark;
		}
		public MsInfo() {}
	}
	
}
