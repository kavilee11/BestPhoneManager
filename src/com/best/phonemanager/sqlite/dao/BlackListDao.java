package com.best.phonemanager.sqlite.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.best.phonemanager.entity.Blacklist;
import com.best.phonemanager.sqlite.helper.PhoneDBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * @author zhangshuaiqi
 * @date 2013-3-28 下午6:20:03
 */
public class BlackListDao {

	private Dao<Blacklist, Integer> dao = null;
	private Context context = null;
	PhoneDBHelper helper = null;

	public BlackListDao(Context context) {
		super();
		this.context = context;
		helper = new PhoneDBHelper(context);
		try {
			dao = helper.getDao(Blacklist.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	public List<Blacklist> getAll(String orderByFiledName,
			boolean ascending) {
		List<Blacklist> list = new ArrayList<Blacklist>();
		try {
			QueryBuilder<Blacklist, Integer> builder = dao.queryBuilder();
			PreparedQuery<Blacklist> prepareQuery = builder.orderBy(orderByFiledName, ascending).prepare();
			list = dao.query(prepareQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public void deleteOne(Blacklist item){
		try {
			dao.delete(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入一个
	 * @return 成功返回1; 异常返回-1
	 */
	public int addOne(Blacklist sms) {
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
