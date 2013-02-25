package com.best.phonemanager;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.best.phonemanager.adapters.TaskListAdapter;
import com.best.phonemanager.entity.ProcessInfo;
import com.best.phonemanager.util.AppUtil;

/**
 * @author ZhangShuaiQi
 * @date 2013-2-22 下午2:29:24
 */
public class TaskActivity extends Activity {

	ListView listview;
	TaskListAdapter adapter;
	List<ProcessInfo> list;
	TextView tv1, tv2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		listview = (ListView) findViewById(R.id.list1);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);

		ActivityManager _ActivityManager = (ActivityManager) this
				.getSystemService(Context.ACTIVITY_SERVICE);

		ActivityManager.MemoryInfo minfo = new ActivityManager.MemoryInfo();
		_ActivityManager.getMemoryInfo(minfo);
		tv2.setText(String.format("剩余可用内存%s", minfo.availMem / (1024 * 1024)) + "MB");


		AppUtil util = new AppUtil(this);
		list = util.getRunningAppProcessInfo();
		tv1.setText(String.format("当前运行%s个进程", list.size()));
		adapter = new TaskListAdapter(list, this);
		listview.setAdapter(adapter);
	}
}
