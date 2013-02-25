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

/**
 * @author zhangshuaiqi
 * @date 2013-2-21 下午4:42:15
 */
public class MainListAdapter extends BaseAdapter {

	List<MainListItem> list;
	Context context;

	public MainListAdapter(List<MainListItem> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MainListItem getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.activity_main_list_item, null);
		ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);
		TextView tv1, tv2;
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		icon.setImageResource(getItem(arg0).getIconId());
		tv1.setText(getItem(arg0).getStr1());
		tv2.setText(getItem(arg0).getStr2());
		return view;
	}

}
