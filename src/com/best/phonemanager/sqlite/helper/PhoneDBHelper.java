package com.best.phonemanager.sqlite.helper;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.best.phonemanager.entity.Blacklist;
import com.best.phonemanager.entity.InterceptCall;
import com.best.phonemanager.entity.InterceptSms;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * @author zhangshuaiqi
 */
public class PhoneDBHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "PhoneManagerDB";
	private static final int DATABASE_VERSION = 1;

	public PhoneDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTable(connectionSource, InterceptSms.class);
			TableUtils.createTable(connectionSource, Blacklist.class);
			TableUtils.createTable(connectionSource, InterceptCall.class);
		} catch (SQLException e) {
			Log.e(PhoneDBHelper.class.getName(), "创建数据库失败", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		try {
			TableUtils.dropTable(connectionSource, InterceptSms.class, true);
			TableUtils.dropTable(connectionSource, InterceptCall.class, true);
			TableUtils.dropTable(connectionSource, Blacklist.class, true);
			onCreate(arg0, connectionSource);
		} catch (SQLException e) {
			Log.e(PhoneDBHelper.class.getName(), "更新数据库失败", e);
			e.printStackTrace();
		}
	}

}
