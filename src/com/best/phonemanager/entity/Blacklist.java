package com.best.phonemanager.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author zhangshuaiqi
 * @date 2013-3-28 下午6:15:31
 */
@DatabaseTable(tableName="Blacklist")
public class Blacklist {

	public static final String FIELD_NAME_ID = "_id";
	public static final String FIELD_NAME_DATE = "date";
	public static final String FIELD_NAME_NUM = "number";

	@DatabaseField(columnName = FIELD_NAME_ID, generatedId=true)
	private int id;
	@DatabaseField(columnName = FIELD_NAME_DATE)
	private Long date;
	@DatabaseField(columnName = FIELD_NAME_NUM)
	private String number;

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
