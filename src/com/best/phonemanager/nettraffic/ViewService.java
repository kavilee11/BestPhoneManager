package com.best.phonemanager.nettraffic;

import java.io.File;
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.WindowManager;

import com.best.phonemanager.R;

/**
 * 悬浮窗Service 该服务会在后台一直运行悬浮窗
 * 
 */
public class ViewService extends Service {
	public static Handler handler = new Handler();
	public static WindowManager wm = null;
	public static FloatTextView FTV = null;
	private boolean isAdded = false;// 窗体是否已经显示
	private long mobileRxBytes;// gprs接收字节
	private long mobileTxBytes;// gprs发送字节
	private long mobileRx_TxBytes;// gprs接受和发送字节
	private long wifiRxBytes;// wifi接收字节
	private long wifiTxBytes;// wifi发送字节
	private long wifiRx_TxBytes;// wifi接收和发送字节
	private DecimalFormat decimalFormat = new DecimalFormat("0.0");// 格式输出
	private SharedPreferences sp;
	private static final int SINGLE = 1;
	private static final int DOUBLE_RT = 2;
	private static final int DOUBLE_TR = 3;
	private int viewMode = SINGLE;
	private Editor editor;

	public void onCreate() {
		super.onCreate();
		sp = this.getSharedPreferences(FloatTextView.PREFERENCES_NAME,
				Activity.MODE_PRIVATE);
		editor = sp.edit();
		// 初始化显示模式 默认1:总网速
		String mode = PreferenceManager.getDefaultSharedPreferences(
				getBaseContext()).getString("view_mode", "1");
		if (mode.equals("1")) {
			viewMode = SINGLE;
		} else if (mode.equals("2")) {
			viewMode = DOUBLE_RT;
		} else if (mode.equals("3")) {
			viewMode = DOUBLE_TR;
		}
		// 初始化悬浮窗
		createView();
//		setForeground(true);
		// 监听关屏
		registerScreenOnBoradcastReceiver();
		// 监听开屏
		redisterScreenOnBroadcastReceiver();
		// 监听网络变化
		registerNetWorkReceiver();

	}

	/**
	 * 创建悬浮窗
	 * 
	 */
	private void createView() {
		wm = (WindowManager) getApplicationContext().getSystemService(
				WINDOW_SERVICE);
		WindowManager.LayoutParams params = FloatTextView.params;
		// 设置params的各个参数
		// params.type =WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		// 能放到状态栏之上
		params.flags = 327976;
		//宽度
		params.width = 260;
//		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// 设置窗体格式 透明
		params.format = PixelFormat.RGBA_8888;
		// 最开始在左上角
		params.gravity = Gravity.LEFT | Gravity.TOP;
		// 读取保存的坐标值
		int x = sp.getInt("x", 100);
		int y = sp.getInt("y", 5);
		// 设置悬浮窗位置
		params.x = x;
		params.y = y;
		Context context = getApplicationContext();
		FTV = new FloatTextView(context);

		// 设置背景
		setViewBackground();
		// 文字大小
		setViewTextSize();
		// 设置文字颜色
		setViewTextColor();
		// 居中
//		FTV.setGravity(Gravity.CENTER);
		//居右
		FTV.setGravity(Gravity.RIGHT);
		// 边距
		FTV.setPadding(3, 0, 3, 0);
		FTV.setText("欢迎使用");
		refreshView();
	}

	/**
	 * 设置悬浮窗背景 默认透明
	 */
	private void setViewBackground() {
		String bgStr = PreferenceManager.getDefaultSharedPreferences(
				getBaseContext()).getString("view_bg", "bg");
		String bgString[] = { "bg", "white", "yellow", "black", "pink",
				"green", "other" };
		int bgResource[] = { R.drawable.bg, R.drawable.white,
				R.drawable.yellow, R.drawable.black, R.drawable.pink,
				R.drawable.green };
		for (int i = 0; i < bgString.length; i++) {
			if (bgStr.equals("other")) {
				String imgPath = sp.getString("path", null);
				if (imgPath != null) {
					File imgFile = new File(imgPath);
					if (imgFile.exists()) {
						FTV.setBackgroundDrawable(Drawable
								.createFromPath(imgFile.getAbsolutePath()));
					} else {
						FTV.setBackgroundResource(bgResource[0]);
						Editor edt = PreferenceManager
								.getDefaultSharedPreferences(getBaseContext())
								.edit();
						edt.putString("view_bg", "bg");
						edt.commit();
					}
				}
			} else if (bgStr.equals(bgString[i])) {
				FTV.setBackgroundResource(bgResource[i]);
				editor.putString("path", null);
				editor.commit();
				break;
			}
		}
	}

	/**
	 * 设置悬浮窗文字颜色 默认白
	 */
	private void setViewTextColor() {
		String colorStr = PreferenceManager.getDefaultSharedPreferences(
				getBaseContext()).getString("text_color", "MAGENTA");
		String colorsString[] = { "WHITE", "BLACK", "BLUE", "GREEN", "RED",
				"YELLOW", "CYAN", "GRAY", "MAGENTA" };
		int intColor[] = { Color.WHITE, Color.BLACK, Color.BLUE, Color.GREEN,
				Color.RED, Color.YELLOW, Color.CYAN, Color.GRAY, Color.MAGENTA };
		for (int i = 0; i < intColor.length; i++) {
			if (colorStr.equals(colorsString[i])) {
				FTV.setTextColor(intColor[i]);
			}
		}
	}

	/**
	 * 设置悬浮窗文字大小 默认12
	 */
	public void setViewTextSize() {
		String sizeStr = PreferenceManager.getDefaultSharedPreferences(
				getBaseContext()).getString("text_size", "14");
		int sizeInt = Integer.valueOf(sizeStr).intValue();
		FTV.setTextSize(sizeInt);
	}

	/**
	 * 添加悬浮窗或者更新悬浮窗 如果悬浮窗还没添加则添加 如果已经添加则更新其位置
	 * 
	 */
	public void refreshView() {
		if (isAdded) {
			wm.updateViewLayout(FTV, FloatTextView.params);
		} else {
			wm.addView(FTV, FloatTextView.params);
			isAdded = true;
		}
	}

	/**
	 * 关闭悬浮窗
	 * 
	 */
	public void removeView() {
		if (isAdded) {
			wm.removeView(FTV);
			isAdded = false;
		}
	}

	/**
	 * wifi runnable---api
	 */
	public Runnable wifiRunnableApi = new Runnable() {
		@Override
		public void run() {
			// 获取当前接收流量
			long newWifiRxBytes = TrafficStats.getTotalRxBytes()
					- TrafficStats.getMobileRxBytes();
			// 获取当前上传流量
			long newWifiTxBytes = TrafficStats.getTotalTxBytes()
					- TrafficStats.getMobileTxBytes();
			// 总流量模式
			switch (viewMode) {
			case SINGLE:
				// 获得当前接收传送流量
				long newWifiRx_TxBytes = newWifiRxBytes + newWifiTxBytes;
				// 计算一秒流量
				long resultWifiRx_TxBytes = newWifiRx_TxBytes - wifiRx_TxBytes;
				// 设置显示内容 网速
				FTV.setText(netSpeedSec(resultWifiRx_TxBytes) + "/S");
				wifiRx_TxBytes = newWifiRx_TxBytes;// 赋值
				break;
			// 下载上传模式
			case DOUBLE_RT:
				// 计算一秒下载流量
				long resultWifiRxBytes1 = newWifiRxBytes - wifiRxBytes;
				// 计算一秒上传流量
				long resultWifiTxBytes1 = newWifiTxBytes - wifiTxBytes;
				// 设置显示内容 下载上传网速
				FTV.setText(netSpeedSec(resultWifiRxBytes1) + " l "
						+ netSpeedSec(resultWifiTxBytes1));
				wifiRxBytes = newWifiRxBytes;
				wifiTxBytes = newWifiTxBytes;
				break;
			// 上传下载模式
			case DOUBLE_TR:
				// 计算一秒下载流量
				long resultWifiRxBytes2 = newWifiRxBytes - wifiRxBytes;
				// 计算一秒上传流量
				long resultWifiTxBytes2 = newWifiTxBytes - wifiTxBytes;
				// 设置显示内容 上传 下载网速
				FTV.setText(netSpeedSec(resultWifiTxBytes2) + " l "
						+ netSpeedSec(resultWifiRxBytes2));
				wifiRxBytes = newWifiRxBytes;
				wifiTxBytes = newWifiTxBytes;
				break;
			default:
				break;
			}
			handler.postDelayed(wifiRunnableApi, 1000);
		}
	};

	/**
	 * mobile runnable---api
	 */
	public Runnable mobileRunnableApi = new Runnable() {
		@Override
		public void run() {
			// 获取当前接收流量
			long newMobileRxBytes = TrafficStats.getMobileRxBytes();
			// 获取当前上传流量
			long newMobileTxBytes = TrafficStats.getMobileTxBytes();
			switch (viewMode) {
			// 总流量模式
			case SINGLE:
				// 获得当前接收传送流量
				long newMoblieRx_TxBytes = newMobileRxBytes + newMobileTxBytes;
				// 计算一秒流量
				long resultMoblieRx_TxBytes = newMoblieRx_TxBytes
						- mobileRx_TxBytes;
				// 设置显示内容 网速
				FTV.setText(netSpeedSec(resultMoblieRx_TxBytes) + "/S");
				mobileRx_TxBytes = newMoblieRx_TxBytes;// 赋值
				break;
			// 下载上传模式
			case DOUBLE_RT:
				// 计算一秒下载流量
				long resultMobileRxBytes1 = newMobileRxBytes - mobileRxBytes;
				// 计算一秒上传流量
				long resultMobileTxBytes1 = newMobileTxBytes - mobileTxBytes;
				// 设置显示内容 下载网速 上传网速
				FTV.setText(netSpeedSec(resultMobileRxBytes1) + " l "
						+ netSpeedSec(resultMobileTxBytes1));
				// 赋值
				mobileRxBytes = newMobileRxBytes;
				mobileTxBytes = newMobileTxBytes;
				break;
			// 上传下载模式
			case DOUBLE_TR:
				// 计算一秒下载流量
				long resultMobileRxBytes2 = newMobileRxBytes - mobileRxBytes;
				// 计算一秒上传流量
				long resultMobileTxBytes2 = newMobileTxBytes - mobileTxBytes;
				// 设置显示内容 上传网速 下载网速
				FTV.setText(netSpeedSec(resultMobileTxBytes2) + " l "
						+ netSpeedSec(resultMobileRxBytes2));
				// 赋值
				mobileRxBytes = newMobileRxBytes;
				mobileTxBytes = newMobileTxBytes;
				break;
			default:
				break;
			}

			handler.postDelayed(mobileRunnableApi, 1000);
		}
	};

	/**
	 * 关屏 流量
	 * 
	 */
	public Runnable screeenOffRunnable = new Runnable() {
		@Override
		public void run() {
			handler.postDelayed(screeenOffRunnable, 10000);
		}
	};

	/**
	 * 确定网速单位 resultLong 网速值返回String网速单位B...
	 */
	public String netSpeedSec(long resultLong) {
		String netSpeedSecStr = null;
		if (resultLong <= 0) {
			netSpeedSecStr = 0 + "B";
		} else if (resultLong < 1000) {
			netSpeedSecStr = resultLong + "B";
		} else if (resultLong < 1024000) {
			// 控制整数部分不超过三位数
			netSpeedSecStr = decimalFormat.format((float) resultLong / 1024)
					+ "K";
		} else {
			netSpeedSecStr = decimalFormat.format((float) resultLong / 1048576)
					+ "M";
		}
		return netSpeedSecStr;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	public void onDestroy() {
		super.onDestroy();
		// 解除广播监听器绑定
		this.unregisterReceiver(screenOffBroadcastReceiver);
		this.unregisterReceiver(screenOnBroadcastReceiver);
		this.unregisterReceiver(netWorkStateBroadcastReceiver);
		removeView();
		removeAllQueue();
	}

	/**
	 * 开屏广播接收器
	 */
	public BroadcastReceiver screenOnBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			removeAllQueue();
			refreshView();
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(CONNECTIVITY_SERVICE);
			boolean isMobile = connectivityManager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).isConnected();
			boolean isWifi = connectivityManager.getNetworkInfo(
					ConnectivityManager.TYPE_WIFI).isConnected();
			if (isWifi) {// 当前连接是否wifi 是
				wifiRxBytes = TrafficStats.getTotalRxBytes()
						- TrafficStats.getMobileRxBytes();
				wifiTxBytes = TrafficStats.getTotalTxBytes()
						- TrafficStats.getMobileTxBytes();
				wifiRx_TxBytes = wifiRxBytes + wifiTxBytes;
				handler.postDelayed(wifiRunnableApi, 1000);
			} else if (isMobile) {// 当前连接是否mobile 是
				mobileRxBytes = TrafficStats.getMobileRxBytes();
				mobileTxBytes = TrafficStats.getMobileTxBytes();
				mobileRx_TxBytes = mobileRxBytes + mobileTxBytes;
				handler.postDelayed(mobileRunnableApi, 1000);
			} else {// 判断网络是否断开 当前断开
				// 无网络是否显示
				boolean isHidden = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext())
						.getBoolean("view_hidden", false);
				if (isHidden) {
					removeView();
				} else {
					FTV.setText(R.string.no_network);
				}

			}
		}
	};

	/**
	 * 开屏接收器绑定
	 */
	public void redisterScreenOnBroadcastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		ViewService.this.registerReceiver(screenOnBroadcastReceiver,
				intentFilter);
	}

	/**
	 * 关屏广播接收器
	 */
	public BroadcastReceiver screenOffBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			removeView();
			removeAllQueue();
		}
	};

	/**
	 * 关屏接收器绑定
	 */
	public void registerScreenOnBoradcastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		ViewService.this.registerReceiver(screenOffBroadcastReceiver,
				intentFilter);
	}

	/**
	 * 移除所有可能的队列防止重复显示
	 */
	public void removeAllQueue() {
		handler.removeCallbacks(mobileRunnableApi);
		handler.removeCallbacks(wifiRunnableApi);
	}

	/**
	 * 网络变化监听器
	 */
	public BroadcastReceiver netWorkStateBroadcastReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			removeAllQueue();
			boolean isBreak = intent.getBooleanExtra(
					ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(CONNECTIVITY_SERVICE);
			boolean isMobile = connectivityManager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).isConnected();
			boolean isWifi = connectivityManager.getNetworkInfo(
					ConnectivityManager.TYPE_WIFI).isConnected();
			if (!isBreak) {// 判断网络是否断开 当前没断开
				refreshView();
				if (isWifi) {// 当前连接是否wifi 是
					wifiRxBytes = TrafficStats.getTotalRxBytes()
							- TrafficStats.getMobileRxBytes();
					wifiTxBytes = TrafficStats.getTotalTxBytes()
							- TrafficStats.getMobileTxBytes();
					wifiRx_TxBytes = wifiRxBytes + wifiTxBytes;
					handler.postDelayed(wifiRunnableApi, 1000);
				} else if (isMobile) {// 当前连接是否mobile 是
					mobileRxBytes = TrafficStats.getMobileRxBytes();
					mobileTxBytes = TrafficStats.getMobileTxBytes();
					mobileRx_TxBytes = mobileRxBytes + mobileTxBytes;
					handler.postDelayed(mobileRunnableApi, 1000);
				}
			} else {// 判断网络是否断开 当前断开
				// 无网络是否显示
				boolean isHidden = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext())
						.getBoolean("view_hidden", false);
				if (isHidden) {
					removeView();
				} else {
					FTV.setText(R.string.no_network);
				}
			}
		}
	};

	/**
	 * 网络变化接收器绑定
	 */
	public void registerNetWorkReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		intentFilter.setPriority(1000);
		ViewService.this.registerReceiver(netWorkStateBroadcastReceiver,
				intentFilter);
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}
}
