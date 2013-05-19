package com.best.phonemanager.receiver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.best.phonemanager.entity.Blacklist;
import com.best.phonemanager.entity.InterceptCall;
import com.best.phonemanager.sqlite.dao.BlackListDao;
import com.best.phonemanager.sqlite.dao.InterceptCallDao;
import com.best.phonemanager.util.ActivityUtils;

/**
 * @author zhangshuaiqi
 * @date 2013-3-29 上午10:04:43
 */
public class InterceptCallReceiver extends BroadcastReceiver {

	private final String TAG = InterceptCallReceiver.class.getSimpleName();

	public static final String ACTION_PHONE_STATE = TelephonyManager.ACTION_PHONE_STATE_CHANGED;

	BlackListDao blacklistDao;
	InterceptCallDao callDao;

	@Override
	public void onReceive(Context context, Intent intent) {
		blacklistDao = new BlackListDao(context);
		callDao = new InterceptCallDao(context);
		List<Blacklist> blackList = blacklistDao.getAll(
				Blacklist.FIELD_NAME_DATE, false);
		List<String> blackListNumList = new ArrayList<String>();
		for (Blacklist item : blackList) {
			blackListNumList.add(item.getNumber());
		}

		AudioManager mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		// 利用反射获取隐藏的endcall方法
		TelephonyManager mTelephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		ITelephony mITelephony = null;
		try {
			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("getITelephony", (Class[]) null);
			getITelephonyMethod.setAccessible(true);
			mITelephony = (ITelephony) getITelephonyMethod.invoke(
					mTelephonyManager, (Object[]) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		final String action = intent.getAction();
		final String state = intent
				.getStringExtra(TelephonyManager.EXTRA_STATE);
		if (ACTION_PHONE_STATE.equals(action)) {// 通话状态改变
			Log.e(TAG, "电话状态改变");
			if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {// 电话响铃RINGING
				final String number = intent
						.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
				Log.e(TAG, "Number：" + number);
				Log.e(TAG, "电话响铃中…");
				// 如果号码在黑名单中就拦截
				if (blackListNumList.contains(number)) {
					// 先静音处理
					mAudioManager
							.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					Log.e(TAG, "先静音处理");
					try {
						// 挂断电话
						mITelephony.endCall();
						ActivityUtils.showCenterToast(context, "拦截到电话：" + number, Toast.LENGTH_SHORT);
						Log.e(TAG, "挂断电话");
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 再恢复正常铃声
					mAudioManager
							.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					Log.e(TAG, "再恢复正常铃声");

					InterceptCall call = new InterceptCall();
					call.setDate(System.currentTimeMillis());
					call.setPhoneNum(number);
					callDao.addOne(call);
				}
			} else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {// 接电话OFFHOOK
				Log.e(TAG, "接听");
			} else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {// 挂电话IDLE
				Log.e(TAG, "挂断");
			}
		} else if (Intent.ACTION_NEW_OUTGOING_CALL.equals(action)) {// 拦截呼出电话
			final String phoneNumber = intent
					.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			Log.e(TAG, "电话呼出，呼出号码为：" + phoneNumber);
		}
	}
}
