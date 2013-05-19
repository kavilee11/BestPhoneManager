package com.best.phonemanager.nettraffic;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.best.phonemanager.R;

public class MyAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Bitmap mIcon4;
	private List<String> items;
	private List<String> paths;

	public MyAdapter(Context context, List<String> it, List<String> pa) {
		mInflater = LayoutInflater.from(context);
		items = it;
		paths = pa;
		mIcon4 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.doc);
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.file_row, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.text);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		File f = new File(paths.get(position).toString());
		
		holder.text.setText(f.getName());
		holder.icon.setImageBitmap(mIcon4);
		return convertView;
	}

	private class ViewHolder {
		TextView text;
		ImageView icon;
	}
}
