package com.best.phonemanager.services;

import com.best.phonemanager.receiver.InterceptCallReceiver;
import com.best.phonemanager.receiver.InterceptSmsReceiver;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class InterceptService extends Service {

	InterceptCallReceiver callReceiver;
	InterceptSmsReceiver smsReceiver;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		callReceiver = new InterceptCallReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.PHONE_STATE");   //为BroadcastReceiver指定action，使之用于接收同action的广播
		intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
		intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
		registerReceiver(callReceiver, intentFilter);
		
		
		smsReceiver = new InterceptSmsReceiver();
		IntentFilter intentFilter2 = new IntentFilter();
		intentFilter2.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(smsReceiver, intentFilter2);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(callReceiver);
		unregisterReceiver(smsReceiver);
	}

}
