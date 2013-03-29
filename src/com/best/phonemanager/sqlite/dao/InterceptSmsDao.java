package com.best.phonemanager.sqlite.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.best.phonemanager.entity.InterceptSms;
import com.best.phonemanager.sqlite.helper.PhoneDBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * @author zhangshuaiqi
 */
public class InterceptSmsDao {

	private Dao<InterceptSms, Integer> dao = null;
	private Context context = null;
	PhoneDBHelper helper = null;

	public InterceptSmsDao(Context context) {
		super();
		this.context = context;
		helper = new PhoneDBHelper(context);
		try {
			dao = helper.getDao(InterceptSms.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	public InterceptSms getById(Long id) {
		List<InterceptSms> list = new ArrayList<InterceptSms>();
		try {
			QueryBuilder<InterceptSms, Integer> builder = dao.queryBuilder();
			builder.where().eq(InterceptSms.FIELD_NAME_ID, id);
			PreparedQuery<InterceptSms> prepareQuery = builder.orderBy(InterceptSms.FIELD_NAME_ID, true).prepare();
			list = dao.query(prepareQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取所有
	 * 
	 * @param orderByFiledName
	 *            排序所依据的字段
	 * @param ascending
	 *            是否升序排列
	 * @return
	 */
	public List<InterceptSms> getAll(String orderByFiledName,
			boolean ascending) {
		List<InterceptSms> list = new ArrayList<InterceptSms>();
		try {
			QueryBuilder<InterceptSms, Integer> builder = dao.queryBuilder();
			PreparedQuery<InterceptSms> prepareQuery = builder.orderBy(orderByFiledName, ascending).prepare();
			list = dao.query(prepareQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * 插入一个
	 * 
	 * @param sms
	 * @return 成功返回1; 异常返回-1
	 */
	public int addOne(InterceptSms sms) {
		try {
			return dao.create(sms);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		OpenHelperManager.releaseHelper();
		helper = null;
		super.finalize();
	}
}
