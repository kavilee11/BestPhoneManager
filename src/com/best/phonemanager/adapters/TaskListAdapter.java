package com.best.phonemanager.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.best.phonemanager.R;
import com.best.phonemanager.TaskActivity;
import com.best.phonemanager.entity.ProcessInfo;

/**
 * @author zhangshuaiqi
 * @date 2013-2-21 下午4:42:15
 */
public class TaskListAdapter extends BaseAdapter {

	List<ProcessInfo> list;
	Context context;
	List<ProcessInfo> checkedList;

	public TaskListAdapter(List<ProcessInfo> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		checkedList = new ArrayList<ProcessInfo>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public ProcessInfo getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		arg1 = LayoutInflater.from(context).inflate(
				R.layout.activity_task_list_item, null);
		holder = new ViewHolder();
		holder.checkBox = (CheckBox) arg1.findViewById(R.id.checkBox1);
		holder.icon = (ImageView) arg1.findViewById(R.id.iv_icon);
		holder.tv1 = (TextView) arg1.findViewById(R.id.tv1);
		holder.tv2 = (TextView) arg1.findViewById(R.id.tv2);
		if(checkedList.contains(getItem(arg0))){
			holder.checkBox.setChecked(true);
		}
		else{
			holder.checkBox.setChecked(false);
		}
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					checkedList.add(getItem(arg0));
				}
				else{
					checkedList.remove(getItem(arg0));
				}
				TaskActivity.handler.sendEmptyMessage(TaskActivity.MSG_WHAT_REFRESH_KILL_BUTTON);
			}
		});
		holder.icon.setImageDrawable(getItem(arg0).getIcon());
		holder.tv1.setText(getItem(arg0).getName());
		holder.tv2.setText(getItem(arg0).getMemorySize()+"");
		return arg1;
	}

	public List<ProcessInfo> getCheckedList() {
		return checkedList;
	}

	public void setCheckedList(List<ProcessInfo> checkedList) {
		this.checkedList = checkedList;
	}
	
	public class ViewHolder{
		ImageView icon;
		TextView tv1, tv2;
		CheckBox checkBox;
	}
	
}
