package com.best.phonemanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.best.phonemanager.adapters.MainListAdapter;
import com.best.phonemanager.entity.MainListItem;
import com.best.phonemanager.nettraffic.UserSetting;
import com.best.phonemanager.util.CpuManager;

public class MainActivity extends Activity implements OnItemClickListener{

	MainListAdapter adapter;
	ListView listview;
	List<MainListItem> list;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (ListView) findViewById(R.id.list1);
		CpuManager manager = new CpuManager(this);
		int yiyong = manager.getPercentageUsedRam();
		MainListItem item1 = new MainListItem("流量监控", "今日1.5M,未设置包月", R.drawable.home_network);
		MainListItem item2 = new MainListItem("骚扰拦截", "本月拦截信息3,电话0", R.drawable.home_filter);
		MainListItem item3 = new MainListItem("手机加速", "内存已用"+yiyong+"%", R.drawable.home_accelerate);
		list = new ArrayList<MainListItem>();
		list.add(item1);
		list.add(item2);
		list.add(item3);
		adapter = new MainListAdapter(list, this);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = null;
		switch (arg2) {
		//流量监控
		case 0:
			startActivity(new Intent(this, UserSetting.class));
			break;
		//骚扰拦截
		case 1:
			intent = new Intent(this, InterceptActivity.class);
			startActivity(intent);
			break;
		//手机加速
		case 2:
			intent = new Intent(this, AccelerateActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	private long exitTime = 0;// 记录按下时间

	/**
	 * 按两次退出
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(this, R.string.press_again_exit,
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
