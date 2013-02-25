package com.best.phonemanager.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.best.phonemanager.R;
import com.best.phonemanager.entity.MainListItem;
import com.best.phonemanager.entity.ProcessInfo;

/**
 * @author zhangshuaiqi
 * @date 2013-2-21 下午4:42:15
 */
public class TaskListAdapter extends BaseAdapter {

	List<ProcessInfo> list;
	Context context;

	public TaskListAdapter(List<ProcessInfo> list, Context context) {
		super();
		this.list = list;
		this.context = context;
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.activity_task_list_item, null);
		ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);
		TextView tv1, tv2;
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		icon.setImageDrawable(getItem(arg0).getIcon());
		tv1.setText(getItem(arg0).getName());
		tv2.setText(getItem(arg0).getMemorySize()+"");
		return view;
	}

}
