package com.best.phonemanager.entity;

import android.graphics.drawable.Drawable;

/**
 * @author fanshuo
 * @date 2013-2-28 下午3:43:40
 */
public class CacheInfo {

	private String name;
	private String cacheSize;
	private Drawable icon;
	private long cacheSizeL;
	private String packageName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public long getCacheSizeL() {
		return cacheSizeL;
	}

	public void setCacheSizeL(long cacheSizeL) {
		this.cacheSizeL = cacheSizeL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(String cacheSize) {
		this.cacheSize = cacheSize;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

}
