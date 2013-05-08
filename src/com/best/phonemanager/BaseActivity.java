package com.best.phonemanager;

import android.app.Activity;
import android.view.View;

public class BaseActivity extends Activity{
	
	public void onBackButtonClick(View v){
		onBackPressed();
	}

}
