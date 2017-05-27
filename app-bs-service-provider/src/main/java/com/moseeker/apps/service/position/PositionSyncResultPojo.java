package com.moseeker.apps.service.position;

/**
 * 同步结果
 * @author wjf
 *
 */
public class PositionSyncResultPojo {

	private int channel;
	private int sync_status;
	private String sync_time;
	private String sync_fail_reason;
	private int account_id;
	
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getSync_status() {
		return sync_status;
	}
	public void setSync_status(int sync_status) {
		this.sync_status = sync_status;
	}
	public String getSync_time() {
		return sync_time;
	}
	public void setSync_time(String sync_time) {
		this.sync_time = sync_time;
	}
	public String getSync_fail_reason() {
		return sync_fail_reason;
	}
	public void setSync_fail_reason(String sync_fail_reason) {
		this.sync_fail_reason = sync_fail_reason;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
}
