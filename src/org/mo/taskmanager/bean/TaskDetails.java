package org.mo.taskmanager.bean;

import java.io.Serializable;

public class TaskDetails implements Serializable{
	private int _id;
	private String content;
	private int cycle;
	private String date;
	private String startTime;
	private String endTime;
	private int reminder;
	private int type;
	private int time;
	private String updateTime;
	private String expireDate;
	
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	private String reminderDate;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getReminderDate() {
		return reminderDate;
	}
	public void setReminderDate(String reminderDate) {
		this.reminderDate = reminderDate;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getReminder() {
		return reminder;
	}
	public void setReminder(int reminder) {
		this.reminder = reminder;
	}
	@Override
	public String toString() {
		return "TaskDetails [_id=" + _id + ", content=" + content + ", cycle="
				+ cycle + ", date=" + date + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", reminder=" + reminder
				+ ", reminderDate=" + reminderDate + "]";
	}
	
}
