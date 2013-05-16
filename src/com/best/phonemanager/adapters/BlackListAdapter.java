package com.best.phonemanager.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.best.phonemanager.R;
import com.best.phonemanager.entity.Blacklist;

/**
 * @author zhangshuaiqi
 * @date 2013-5-15 下午9:21:17
 */
public class BlackListAdapter extends BaseAdapter {

	Context context;
	List<Blacklist> list;

	public BlackListAdapter(Context context, List<Blacklist> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Blacklist getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.adapter_blacklist, null);
		TextView phone = (TextView) convertView.findViewById(R.id.phone);
		Blacklist item = getItem(position);
		phone.setText(item.getNumber());
		return convertView;
	}

	public List<Blacklist> getList() {
		return list;
	}

	public void setList(List<Blacklist> list) {
		this.list = list;
	}

}
