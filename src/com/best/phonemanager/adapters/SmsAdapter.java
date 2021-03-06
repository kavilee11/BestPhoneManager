package com.best.phonemanager.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.best.phonemanager.R;
import com.best.phonemanager.entity.InterceptSms;
import com.best.phonemanager.util.DateUtils;

/**
 * @author zhangshuaiqi
 * @date 2013-5-15 下午9:21:17
 */
public class SmsAdapter extends BaseAdapter {

	Context context;
	List<InterceptSms> list;

	public SmsAdapter(Context context, List<InterceptSms> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public InterceptSms getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.adapter_intercept_sms, null);
		TextView date = (TextView) convertView.findViewById(R.id.date);
		TextView phone = (TextView) convertView.findViewById(R.id.phone);
		TextView body = (TextView) convertView.findViewById(R.id.body);
		InterceptSms sms = getItem(position);
		date.setText(DateUtils.formatDate(new Date(sms.getDate())));
		phone.setText(sms.getPhoneNum());
		body.setText(sms.getBody());
		return convertView;
	}

}
