package com.best.phonemanager.nettraffic;

import java.io.File;
import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.best.phonemanager.R;

public class UserSetting extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置保存位置 不设置 则默认/data/data/you_package_name
		addPreferencesFromResource(R.xml.setting);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		boolean isOpen = prefs.getBoolean("toggle", true);
		// 开启服务
		toggleService(isOpen);
		// CheckBoxPreference组件
		CheckBoxPreference toggle = (CheckBoxPreference) findPreference("toggle");
		// 添加事件
		toggle.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference arg0, Object newValue) {
				// 点击toggle开启服务
				toggleService((Boolean) newValue);
				return true;
			}
		});

		// 创建文件夹
		createFolder(Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator
				+ "ts.nettraffic"
				+ File.separator);
		String string = "test";
		string +=string.toUpperCase();
	}

	/**
	 * 开关服务
	 */
	public void toggleService(boolean isOpen) {
		Intent intent = new Intent();
		intent.setClass(this, ViewService.class);
		if (isOpen) {
			startService(intent);
		} else {
			stopService(intent);
		}
	}

	/**
	 * 当用户点击改变文字大小颜色和背景时做出改变
	 */
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Intent intent = new Intent();
		intent.setClass(this, ViewService.class);
		if (!key.equals("toggle")) {
			// 服务是否工作
			if (isWorked()) {
				// 判断用户点击的key
				if (key.equals("text_size") || key.equals("text_color")
						|| key.equals("view_bg") || key.equals("view_mode")) {
					stopService(intent);// 关闭服务

					try {
						Thread.sleep(100);// 休眠100毫秒
						startService(intent);// 开启服务
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				Toast.makeText(this, R.string.open_service, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/**
	 * 判断服务是否运行
	 */
	public boolean isWorked() {
		ActivityManager myManager = (ActivityManager) this
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals("ts.NetTrafficLite.ViewService")) {
				return true;
			}
		}
		return false;
	}

	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	private long exitTime = 0;// 记录按下时间


	/**
	 * 对话框 i资源文件 设置标题 j字符串 设置要显示的内容资源文件
	 */
	public void showDialog(int i, int j) {
		final Dialog dialog = new Dialog(this, R.style.WindowStyle);
		dialog.setContentView(R.layout.dialog);
		dialog.setTitle(i);
		TextView dialogTextView = (TextView) dialog
				.findViewById(R.id.dialogTextView);
		dialogTextView.setText(j);
		Button button = (Button) dialog.findViewById(R.id.dialogButton);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				dialog.cancel();
			}
		});
		dialog.show();
	}

	/**
	 * 创建新文件夹
	 */
	private void createFolder(String folderPath) {
		String filePath = folderPath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		try {
			if (myFilePath.isDirectory()) {
			} else {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
