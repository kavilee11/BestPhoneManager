package com.best.phonemanager;

import android.os.Bundle;
import android.widget.ListView;

import com.best.phonemanager.adapters.CallAdapter;
import com.best.phonemanager.entity.InterceptCall;
import com.best.phonemanager.sqlite.dao.InterceptCallDao;

/**
 * @author zhangshuaiqi
 * @date 2013-3-1 下午2:56:48
 */
public class InterceptCallActivity extends BaseActivity{

	CallAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intercept_call);
		InterceptCallDao dao = new InterceptCallDao(this);
		adapter = new CallAdapter(this, dao.getAll(InterceptCall.FIELD_NAME_DATE, true));
		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);
	}
}
