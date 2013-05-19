package com.best.phonemanager.receiver;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.best.phonemanager.entity.Blacklist;
import com.best.phonemanager.entity.InterceptSms;
import com.best.phonemanager.sqlite.dao.BlackListDao;
import com.best.phonemanager.sqlite.dao.InterceptSmsDao;
import com.best.phonemanager.util.ActivityUtils;

/**
 * @author zhangshuaiqi
 * @date 2013-3-4 上午10:05:14
 */
public class InterceptSmsReceiver extends BroadcastReceiver {

	BlackListDao blacklistDao;
	InterceptSmsDao smsDao;
	@Override
	public void onReceive(Context context, Intent intent) {
		blacklistDao = new BlackListDao(context);
		smsDao = new InterceptSmsDao(context);
		List<Blacklist> blackList = blacklistDao.getAll(Blacklist.FIELD_NAME_DATE, false);
		List<String> blackListNumList = new ArrayList<String>();
		for (Blacklist item : blackList) {
			blackListNumList.add(item.getNumber());
		}
		// 第一步、获取短信的内容和发件人
		StringBuilder body = new StringBuilder();// 短信内容
		StringBuilder number = new StringBuilder();// 短信发件人
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Object[] _pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] message = new SmsMessage[_pdus.length];
			for (int i = 0; i < _pdus.length; i++) {
				message[i] = SmsMessage.createFromPdu((byte[]) _pdus[i]);
			}
			number.append(message[0].getDisplayOriginatingAddress());
			for (SmsMessage currentMessage : message) {
				body.append(currentMessage.getDisplayMessageBody());
			}
			String smsBody = body.toString();
			String smsNumber = number.toString();
			if (smsNumber.contains("+86")) {
				smsNumber = smsNumber.substring(3);
			}
			// 第二步:确认该短信内容是否满足过滤条件
			boolean flags_filter = false;
			if (blackListNumList.contains(smsNumber)) {// 屏蔽黑名单发来的短信
				flags_filter = true;
			}
			// 第三步:取消并添加到自己的数据库
			if (flags_filter) {
				ActivityUtils.showCenterToast(context, "拦截到短信：" + number + "-" + smsBody, Toast.LENGTH_SHORT);
				InterceptSms sms = new InterceptSms();
				sms.setDate(System.currentTimeMillis());
				sms.setPhoneNum(smsNumber);
				sms.setBody(smsBody);
				smsDao.addOne(sms);
				this.abortBroadcast();
			}
		}
	}

}
