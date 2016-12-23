package com.moseeker.apps.constants;

public enum TemplateMs {
	
	TOSEEKER(9) {
		@Override
		public MsInfo processStatus(int status, Object...args) {
			MsInfo mi = null;
			switch (status) {
				case 12:
			        mi = new MsInfo("恭喜您成功入职", "成功入职", "入职愉快！");
			        break;
				case 13:
				    mi = new MsInfo("您好！感谢对我司的关注！但您与我司需求匹配度上存在一定差异，现将您的资料纳入公司人才储备库中，后续有机会再与您联系。", "纳入人才库", "试试看其它职位吧！加油！");
				    break;
				case 4:
				    mi = new MsInfo("您好，您的简历已被查阅", "已查看", "请耐心等待");
				    break;
				case 7:
				    mi = new MsInfo("恭喜你，你的简历已通过评审！", "通过评审", "请耐心等待后续通知");
				    break;
				case 10:
					mi = new MsInfo("恭喜你，面试成功！", "面试成功", "请耐心等待公司后续通知");
			}
			return mi;
		}
	},
	
	TORECOM(9) {
		@Override
		public MsInfo processStatus(int status, Object ...args) {
			MsInfo mi = null;
			switch (status) {
				case 12:
			        mi = new MsInfo(String.format("您推荐的{0}已经成功入职", args), "成功入职", "感谢您对公司人才招聘的贡献！");
			        break;
				case 13:
				    mi = new MsInfo(String.format("您推荐的【{0}】经评定与我司需求匹配度上存在一定差异，现将其资料纳入公司人才库中，后续有机会再与其联系。", args), "纳入人才库", "感谢您对公司人才招聘的贡献！");
				    break;
			}
			return mi;
		}
	};
	
	private int systemlateId;
	
	TemplateMs(int systemlateId) {
		this.systemlateId = systemlateId;
	}
	
	public int getSystemlateId() {
		return systemlateId;
	}

	public void setSystemlateId(int systemlateId) {
		this.systemlateId = systemlateId;
	}

	public abstract MsInfo processStatus(int status, Object ...args);
	
	public class MsInfo {
		private String result;
		private String statusDesc; 
		private String remark;
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
		public MsInfo(String result, String statusDesc, String remark) {
			super();
			this.result = result;
			this.statusDesc = statusDesc;
			this.remark = remark;
		}
		public MsInfo() {}
	}
}
