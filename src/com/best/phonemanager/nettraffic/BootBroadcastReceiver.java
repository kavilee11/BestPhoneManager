package com.best.phonemanager.nettraffic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

/**
 * 开机启动服务
 * 
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

	public BootBroadcastReceiver() {
		super();
	}

	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, ViewService.class);
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			boolean isAutoStart = PreferenceManager.getDefaultSharedPreferences(
					context).getBoolean("auto_start", true);
			if (isAutoStart) {
				context.startService(service);
			}
		}
	}
}
