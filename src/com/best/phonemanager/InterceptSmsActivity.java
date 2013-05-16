package com.best.phonemanager;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.best.phonemanager.adapters.SmsAdapter;
import com.best.phonemanager.entity.InterceptSms;
import com.best.phonemanager.sqlite.dao.InterceptSmsDao;

/**
 * @author zhangshuaiqi
 * @date 2013-3-1 下午2:56:48
 */
public class InterceptSmsActivity extends BaseActivity{

	ListView listview;
	SmsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intercept_sms);
		listview = (ListView) findViewById(R.id.list);
		InterceptSmsDao dao = new InterceptSmsDao(this);
		List<InterceptSms> list = dao.getAll(InterceptSms.FIELD_NAME_ID, true);
		adapter = new SmsAdapter(this, list);
		listview.setAdapter(adapter);
	}
}
