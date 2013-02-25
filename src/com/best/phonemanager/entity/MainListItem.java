package com.best.phonemanager.entity;

/**
 * @author zhangshuaiqi
 * @date 2013-2-21 下午4:40:56
 */
public class MainListItem {

	private String str1;
	private String str2;
	private int iconId;

	public MainListItem(String str1, String str2, int iconId) {
		super();
		this.str1 = str1;
		this.str2 = str2;
		this.iconId = iconId;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

}
