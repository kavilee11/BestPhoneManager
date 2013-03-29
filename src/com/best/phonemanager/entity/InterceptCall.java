package com.best.phonemanager.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author zhangshuaiqi
 * @date 2013-3-29 上午10:30:45
 */
@DatabaseTable(tableName = "InterceptCall")
public class InterceptCall {

	public static final String FIELD_NAME_ID = "_id";
	public static final String FIELD_NAME_DATE = "date";
	public static final String FIELD_NAME_PHONE_NUM = "phoneNum";

	@DatabaseField(columnName = FIELD_NAME_ID)
	private int id;
	@DatabaseField(columnName = FIELD_NAME_DATE)
	private Long date;
	@DatabaseField(columnName = FIELD_NAME_PHONE_NUM)
	private String phoneNum;

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

}
