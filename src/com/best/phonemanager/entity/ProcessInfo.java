package com.best.phonemanager.entity;

import android.graphics.drawable.Drawable;

/**
 * @author ZhangShuaiQi
 * @date 2013-2-22 下午2:02:01
 */
public class ProcessInfo {

	private int pid;
	private int uid;
	private int memorySize;
	private String name;
	private String packageName;
	private Drawable icon;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String toString() {
		return "ProcessInfo [pid=" + pid + ", uid=" + uid + ", memorySize="
				+ memorySize + ", name=" + name + ", packageName="
				+ packageName + ", icon=" + icon + "]";
	}

}
