package com.moseeker.apps.constants;

import java.text.MessageFormat;

public enum TemplateMs {

	TOSEEKER("求职者") {
		@Override
		public MsInfo processStatus(int status, Object...args) {
			MsInfo mi = null;
			switch (status) {
				case 12:
			        mi = new MsInfo(35,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}","恭喜您成功入职", "成功入职", "入职愉快！");
			        break;
				case 13:
				    mi = new MsInfo(36,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}", "您好！感谢对我司的关注！但您与我司需求匹配度上存在一定差异，现将您的资料纳入公司人才储备库中，后续有机会再与您联系。", "纳入人才库", "试试看其它职位吧！加油！");
				    break;
				case 4:
				    mi = new MsInfo(9,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}", "您好，您的简历已被查阅", "已查阅简历", "点击查看求职进度详情");
				    break;
				case 7:
				    mi = new MsInfo(32,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}","恭喜你，你的简历已通过初审！", "初审通过", "点击查看求职进度详情");
				    break;
				case 10:
					mi = new MsInfo(45,"{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}","恭喜你，面试成功！", "面试成功", "请耐心等待公司后续通知");
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
			        mi = new MsInfo(60,"{0}m/app/employee/recommends?wechat_signature={1}", MessageFormat.format("您推荐的{0}已经成功入职", args), "成功入职", "感谢您对公司人才招聘的贡献，欢迎继续推荐");
			        break;
				case 13:
				    mi = new MsInfo(61,"{0}m/app/employee/recommends?wechat_signature={1}",MessageFormat.format("您推荐的【{0}】经评定与我司需求匹配度上存在一定差异，现将其资料纳入公司人才库中，后续有机会再与其联系。", args), "纳入人才库", "感谢您对公司人才招聘的贡献，欢迎继续推荐");
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
