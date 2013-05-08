package com.best.phonemanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.best.phonemanager.adapters.MainListAdapter;
import com.best.phonemanager.entity.MainListItem;
import com.best.phonemanager.util.CpuManager;

/**
 * @author ZhangShuaiQi
 * @date 2013-2-21 下午5:32:57
 */
public class AccelerateActivity extends BaseActivity implements OnItemClickListener{

	MainListAdapter adapter;
	ListView listview;
	List<MainListItem> list;
	TextView tv_ram;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerate);
		listview = (ListView) findViewById(R.id.list1);
		tv_ram = (TextView) findViewById(R.id.tv_ram);
		CpuManager manager = new CpuManager(this);
		int yiyong = manager.getPercentageUsedRam();
		tv_ram.setText(String.format("内存：%s", yiyong+"%"));
		MainListItem item1 = new MainListItem("结束进程", "内存已用"+yiyong+"%", R.drawable.main_icon_task);
		MainListItem item2 = new MainListItem("开机加速", "有12个开机启动软件,10个建议禁止", R.drawable.main_icon_task_accelerate);
		MainListItem item3 = new MainListItem("缓存清理", "定期清理,释放手机空间", R.drawable.main_icon_cache);
		list = new ArrayList<MainListItem>();
		list.add(item1);
		list.add(item2);
		list.add(item3);
		adapter = new MainListAdapter(list, this);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = null;
		switch (arg2) {
		case 0:
			intent = new Intent(this, TaskActivity.class);
			startActivity(intent);
			break;
		case 1:
			
			break;
		case 2:
			intent = new Intent(this, ClearCacheActivity.class);
			startActivity(intent);
			break;
		}
	}

}
