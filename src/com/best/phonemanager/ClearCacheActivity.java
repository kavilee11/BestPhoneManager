package com.best.phonemanager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.best.phonemanager.adapters.ClearCacheListAdapter;
import com.best.phonemanager.entity.CacheInfo;

/**
 * @author fanshuo
 * @date 2013-2-28 下午2:24:50
 */
public class ClearCacheActivity extends Activity{

	public static final int MSG_WHAT_REFRESH_TEXT = 200;
	
	TextView tv1, tv2;
	Button button;
	ClearCacheListAdapter adapter;
	ListView listView;
	List<PackageInfo> list;
	List<CacheInfo> cacheList;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear_cache);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		button = (Button) findViewById(R.id.button_clear);
		
		listView = (ListView) findViewById(R.id.list1);
		cacheList = new ArrayList<CacheInfo>();
		adapter = new ClearCacheListAdapter(this, cacheList);
		listView.setAdapter(adapter);
		initList();
		initHanlder();
	}
	
	private void initHanlder(){
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_WHAT_REFRESH_TEXT:
					//更新文字
					tv1.setText(String.format("有%s款软件的缓存文件可清理", cacheList.size()));
					long totalSize = 0;
					for (CacheInfo item : cacheList) {
						totalSize += item.getCacheSizeL();
					}
					String totalSizeStr =  Formatter.formatFileSize(
							ClearCacheActivity.this, totalSize);
					button.setText(String.format("全部清除（%s）", totalSizeStr));
					tv2.setText(String.format("共%s", totalSizeStr));
					break;
				}
			}
			
		};
	}

	private void initList() {
		List<PackageInfo> list1 = getPackageManager().getInstalledPackages(0);
		list = new ArrayList<PackageInfo>();
		//过滤掉系统应用
		for (PackageInfo packageInfo : list1) {
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				// 非系统应用
				list.add(packageInfo);
			}
		}
		PackageManager pm = getPackageManager();
		try {
			Method getPackageSizeInfo = pm.getClass().getMethod(
					"getPackageSizeInfo", String.class,
					IPackageStatsObserver.class);
			for (PackageInfo packageInfo : list) {
				CacheInfo info = new CacheInfo();
				info.setName(packageInfo.applicationInfo.loadLabel(
						getPackageManager()).toString());
				info.setIcon(packageInfo.applicationInfo
						.loadIcon(getPackageManager()));
				info.setPackageName(packageInfo.packageName);
				getPackageSizeInfo.invoke(pm, packageInfo.packageName,
						new PkgSizeObserver(info));
			}
		} catch (Exception e) {
		}
	}

	class PkgSizeObserver extends IPackageStatsObserver.Stub {
		CacheInfo info;

		public PkgSizeObserver(CacheInfo info) {
			super();
			this.info = info;
		}

		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) {
			//过滤掉无缓存的程序
			if(pStats.cacheSize > 0){
				String formatCacheSize = Formatter.formatFileSize(
						ClearCacheActivity.this, pStats.cacheSize);
				info.setCacheSize(formatCacheSize);
				info.setCacheSizeL(pStats.cacheSize);
				cacheList.add(info);
				//刷新列表
				adapter.notifyDataSetChanged();
				handler.sendEmptyMessage(MSG_WHAT_REFRESH_TEXT);
			}
		}
	}
	
	public void clearAll(View view){
		
	}
}
