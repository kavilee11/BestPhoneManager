package com.best.phonemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.best.phonemanager.adapters.BlackListAdapter;
import com.best.phonemanager.entity.Blacklist;
import com.best.phonemanager.sqlite.dao.BlackListDao;

/**
 * @author fanshuo
 * @date 2013-5-16 上午6:53:47
 */
public class BlackListActivity extends BaseActivity implements OnClickListener {

	ListView listview;
	BlackListAdapter adapter;
	BlackListDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blacklist);
		listview = (ListView) findViewById(R.id.list);
		dao = new BlackListDao(this);
		adapter = new BlackListAdapter(this, dao.getAll(Blacklist.FIELD_NAME_ID, true));
		listview.setAdapter(adapter);
		findViewById(R.id.add).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add:
			final EditText et = new EditText(this);
			new AlertDialog.Builder(this).setTitle("请输入").setIcon(
				     android.R.drawable.ic_dialog_info).setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Blacklist item = new Blacklist();
							item.setDate(System.currentTimeMillis());
							item.setNumber(et.getText().toString());
							dao.addOne(item);
							resresh();
						}
					})
				     .setNegativeButton("取消", null).show();
			break;
		}
	}
	
	private void resresh(){
		adapter.setList(dao.getAll(Blacklist.FIELD_NAME_ID, true));
		adapter.notifyDataSetChanged();
	}

}
