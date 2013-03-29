package com.best.phonemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author zhangshuaiqi
 * @date 2013-3-1 下午2:56:48
 */
public class InterceptActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intercept);
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
		}
		startActivity(intent);
	}

}
