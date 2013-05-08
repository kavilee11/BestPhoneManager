package com.best.phonemanager;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.best.phonemanager.adapters.TaskListAdapter;
import com.best.phonemanager.entity.ProcessInfo;
import com.best.phonemanager.util.AppUtil;

/**
 * @author ZhangShuaiQi
 */
public class TaskActivity extends BaseActivity {

	public static final int MSG_WHAT_REFRESH_KILL_BUTTON = 100;
	
	ListView listview;
	TaskListAdapter adapter;
	List<ProcessInfo> list;
	TextView tv1, tv2;
	public static Handler handler;
	Button killButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		listview = (ListView) findViewById(R.id.list1);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		killButton = (Button) findViewById(R.id.button_kill);
		initData();
		initHandler();
	}
	
	private void initData(){
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
	
	public void kill(View view){
		List<ProcessInfo> checkedList = adapter.getCheckedList();
		for (ProcessInfo processInfo : checkedList) {
			ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
			activityManager.killBackgroundProcesses(processInfo.getPackageName());
		}
		Toast.makeText(this, "成功结束了"+checkedList.size()+"个程序", Toast.LENGTH_SHORT).show();  
		initData();
		handler.sendEmptyMessage(MSG_WHAT_REFRESH_KILL_BUTTON);
	}
	
	private void initHandler(){
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_WHAT_REFRESH_KILL_BUTTON:
					List<ProcessInfo> checkedList = adapter.getCheckedList();
					if(checkedList.size() > 0){
						killButton.setText(String.format("结束程序（%s）", checkedList.size()));
					}
					else{
						killButton.setText("结束程序");
					}
					break;
				}
			}
			
		};
	}
}
