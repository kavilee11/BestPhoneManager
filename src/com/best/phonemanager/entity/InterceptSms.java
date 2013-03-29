package com.best.phonemanager.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author zhangshuaiqi
 * @date 2013-3-1 下午4:03:40
 */
@DatabaseTable(tableName="InterceptSms")
public class InterceptSms {

	public static final String FIELD_NAME_ID = "_id";
	public static final String FIELD_NAME_DATE = "date";
	public static final String FIELD_NAME_PHONE_NUM = "phoneNum";
	public static final String FIELD_NAME_BODY = "body";
	
	@DatabaseField(columnName=FIELD_NAME_ID)
	private int id;
	@DatabaseField(columnName=FIELD_NAME_DATE)
	private Long date;
	@DatabaseField(columnName=FIELD_NAME_PHONE_NUM)
	private String phoneNum;
	@DatabaseField(columnName=FIELD_NAME_BODY)
	private String body;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "InterceptSms [date=" + date + ", phoneNum=" + phoneNum
				+ ", body=" + body + "]";
	}

}
