package com.best.phonemanager.nettraffic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class FloatTextView extends TextView {
	public static int TOOL_BAR_HIGH = 0;// 状态栏高度
	public static WindowManager.LayoutParams params = new WindowManager.LayoutParams();
	private float startX;
	private float startY;
	private float x;
	private float y;
	public static String PREFERENCES_NAME = "setting";
	private SharedPreferences sp;
	private WindowManager wm = (WindowManager) getContext()
			.getApplicationContext().getSystemService(
					Context.WINDOW_SERVICE);

	public FloatTextView(Context context) {
		super(context);
		sp = context.getSharedPreferences(
				PREFERENCES_NAME, Activity.MODE_PRIVATE);
	}

	/**
	 * 悬浮窗移动
	 * 
	 */
	public boolean onTouchEvent(MotionEvent event) {
		boolean viewLock = PreferenceManager.getDefaultSharedPreferences(
				getContext()).getBoolean("view_lock", false);
		Editor editor= sp.edit();
		if (!viewLock) {
			params.gravity = Gravity.LEFT | Gravity.TOP;
			// 触摸点相对于屏幕左上角坐标
			x = event.getRawX();
			y = event.getRawY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				updatePosition();
				break;
			case MotionEvent.ACTION_UP:
				updatePosition();
				startX = startY = 0;
				//使用一个数组获得悬浮窗当前位置绝对值(相对于左上角)				
				int[] location = new  int[2] ;
				//获取在整个屏幕内的绝对坐标
				this.getLocationOnScreen(location);
				//保存当前坐标值
				editor.putInt("x", location[0] );
				editor.putInt("y", location[1]);
				editor.commit();
				break;
			}
		}
		return true;
	}

	/**
	 * 更新悬浮窗位置
	 * 
	 */
	private void updatePosition() {
		// 悬浮窗的当前位置
		params.x = (int) (x - startX);
		params.y = (int) (y - startY);
		wm.updateViewLayout(this, params);
	}
}
