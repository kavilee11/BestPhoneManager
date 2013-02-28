package com.best.phonemanager.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.best.phonemanager.R;
import com.best.phonemanager.entity.CacheInfo;

/**
 * @author zhangshuaiqi
 * @date 2013-2-21 下午4:42:15
 */
public class ClearCacheListAdapter extends BaseAdapter {

	List<CacheInfo> list;
	Context context;

	public ClearCacheListAdapter(Context context, List<CacheInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public CacheInfo getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		final ViewHolder holder;
		arg1 = LayoutInflater.from(context).inflate(
				R.layout.activity_clear_cache_list_item, null);
		holder = new ViewHolder();
		holder.clearButton = (ImageButton) arg1.findViewById(R.id.clear);
		holder.icon = (ImageView) arg1.findViewById(R.id.iv_icon);
		holder.tv1 = (TextView) arg1.findViewById(R.id.tv1);
		holder.tv2 = (TextView) arg1.findViewById(R.id.tv2);
		holder.icon.setImageDrawable(getItem(arg0).getIcon());
		holder.tv1.setText(getItem(arg0).getName());
		holder.tv2.setText(getItem(arg0).getCacheSize());
		holder.clearButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CacheInfo info = getItem(arg0);
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		        Uri uri = Uri.fromParts("package", info.getPackageName(), null);
		        intent.setData(uri);
		        context.startActivity(intent);
			}
		});
		return arg1;
	}
	public class ViewHolder {
		ImageView icon;
		TextView tv1, tv2;
		ImageButton clearButton;
	}

}
