package com.best.phonemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.best.phonemanager.services.InterceptService;

/**
 * @author zhangshuaiqi
 * @date 2013-3-1 下午2:56:48
 */
public class InterceptActivity extends BaseActivity implements OnClickListener {

	Button open;
	boolean isServiceRunning = false;
	SharedPreferences pre;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intercept);
		findViewById(R.id.sms).setOnClickListener(this);
		findViewById(R.id.call).setOnClickListener(this);
		findViewById(R.id.blacklist).setOnClickListener(this);
		open = (Button) findViewById(R.id.open);
		open.setOnClickListener(this);
		pre = getSharedPreferences("phone_manager", 0);
		isServiceRunning  = pre.getBoolean("isInterceptServiceRunning", false);
		if(isServiceRunning){
			open.setText("拦截已开启，点击关闭");
		}
		else{
			open.setText("拦截已关闭，点击开启");
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.sms:
			intent.setClass(this, InterceptSmsActivity.class);
			break;
		case R.id.call:
			intent.setClass(this, InterceptCallActivity.class);
			break;
		case R.id.blacklist:
			intent.setClass(this, BlackListActivity.class);
			break;
		case R.id.open:
			if(isServiceRunning){
				stopService(new Intent(this, InterceptService.class));
				Editor editor = pre.edit();
				editor.putBoolean("isInterceptServiceRunning", false);
				editor.commit();
				isServiceRunning = false;
				open.setText("拦截已关闭，点击开启");
			}else{
				startService(new Intent(this, InterceptService.class));
				Editor editor = pre.edit();
				editor.putBoolean("isInterceptServiceRunning", true);
				editor.commit();
				isServiceRunning = true;
				open.setText("拦截已开启，点击关闭");
			}
			return;
		}
		startActivity(intent);
	}

}
