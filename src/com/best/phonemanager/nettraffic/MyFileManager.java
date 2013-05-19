package com.best.phonemanager.nettraffic;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.best.phonemanager.R;

public class MyFileManager extends ListActivity {
	private List<String> items = null;
	private List<String> paths = null;
	private String rootPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator + "ts.nettraffic";
	private TextView mPath;
	public static String IMG_PATH = null;

	private SharedPreferences sp;
	private Editor editor;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		sp = this.getSharedPreferences(
				FloatTextView.PREFERENCES_NAME, Activity.MODE_PRIVATE);
		editor = sp.edit();	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fileselect);
		mPath = (TextView) findViewById(R.id.mPath);
		Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
		buttonConfirm.setOnClickListener(new OnClickListener() {
			//点击确定
			public void onClick(View v) {
				//保存文件路径
				editor.putString("path", IMG_PATH);
				editor.commit();
				Intent intent = new Intent(MyFileManager.this,
						ViewService.class);
				stopService(intent);
				try {
					Thread.sleep(100);
					startService(intent);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
		});
		Button buttonCancle = (Button) findViewById(R.id.buttonCancle);
		//点击取消
		buttonCancle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		getFileDir(rootPath);
	}

	private void getFileDir(String filePath) {
		mPath.setText(filePath);
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles(new FilenameFilter() {
			//过滤显示文件类型
			public boolean accept(File dir, String filename) {
				// TODO Auto-generated method stub
				if (filename.endsWith(".png") || filename.endsWith(".jpg")
						|| filename.endsWith(".gif")
						|| filename.endsWith(".jpeg")
						|| filename.endsWith(".bmp")) {
					return true;
				} else {
					return false;
				}
			}
		});

		if (!filePath.equals(rootPath)) {
			items.add("b1");
			paths.add(rootPath);
			items.add("b2");
			paths.add(f.getParent());
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			items.add(file.getName());
			paths.add(file.getPath());
		}
		setListAdapter(new MyAdapter(this, items, paths));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File file = new File(paths.get(position));
		IMG_PATH = file.getPath();//获得点击文件的路径
	}
}