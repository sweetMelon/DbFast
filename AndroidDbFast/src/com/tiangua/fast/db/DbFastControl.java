package com.tiangua.fast.db;

import java.util.List;

import android.content.Context;

import com.tiangua.fast.db.util.DBConfig;

public class DbFastControl implements IDbFast {
	private static DbFastControl instance = null;

	protected DbFastControl() {

	}

	public static DbFastControl getDbFast() {
		if (instance == null) {
			synchronized (DbFastControl.class) {
				instance = new DbFastControl();
			}
		}
		return instance;
	}

	/**
	 * 初始化 dbFast
	 * 
	 * @param context
	 * @param dbConfiguration
	 *            数据库配置对象
	 * @param clz_list
	 *            要创建的表的集合
	 */
	public void init(Context context, DBConfig dbConfiguration,
			List<Class<?>> clz_list) {
		DbFast.getDbFast().init(context, dbConfiguration, clz_list);
	}

	/**
	 * insert data to db<br>
	 * ex:<br>
	 * List<Class<?>> clz_list = new Arraylist<Class<?>>();<br>
	 * <br>
	 * User u = new User();<br>
	 * u.setUid("100");<br>
	 * u.setUName("fengyagang");<br>
	 * clz_list.add(u);<br>
	 * <br>
	 * User u2 = new User();<br>
	 * u2.setUid("100");<br>
	 * u2.setUName("nidaye");<br>
	 * clz_list.add(u2);<br>
	 * <br>
	 * int count = DbFastControl.getDbFast().insert(clz_list);<br>
	 * if(count > 0)<br>
	 * {<br>
	 * &nbsp;&nbsp;insert success<br>
	 * }<br>
	 * else<br>
	 * {<br>
	 * &nbsp;&nbsp;insert failed<br>
	 * }<br>
	 * 
	 * @param clzs
	 *            要添加的对象的集合
	 * @return
	 */
	public long insertList(List<Object> clzs) {
		return DbFast.getDbFast().insertList(clzs);
	}

	/**
	 * insert data to db<br>
	 * ex:<br>
	 * User u = new User();<br>
	 * u2.setUid("100");<br>
	 * u2.setUName("nidaye");<br>
	 * clz_list.add(u2);<br>
	 * int count = DbFastControl.getDbFast().insert(u);<br>
	 * if(count > 0)<br>
	 * {<br>
	 * &nbsp;&nbsp;insert success<br>
	 * }<br>
	 * else<br>
	 * {<br>
	 * &nbsp;&nbsp;insert failed<br>
	 * }<br>
	 * 
	 * @param clz
	 *            要添加的对象的集合
	 * @return
	 */
	public long insert(Object clz) {
		return DbFast.getDbFast().insert(clz);
	}

	/**
	 * delete clz info<br>
	 * ex:<br>
	 * int count = DbFastControl.getDbFast().delete(User.class,"uid = ?",new
	 * String[]{uid});<br>
	 * if(count > 0)<br>
	 * {<br>
	 * &nbsp;&nbsp;delete success<br>
	 * }<br>
	 * else<br>
	 * {<br>
	 * &nbsp;&nbsp;delete failed<br>
	 * }<br>
	 * 
	 * @param clz
	 * @param where
	 * @param whereArgs
	 * @return
	 */
	public int delete(Class<?> clz, String where, String[] whereArgs) {
		return DbFast.getDbFast().delete(clz, where, whereArgs);
	}

	/**
	 * update clz info<br>
	 * ex:<br>
	 * User u = new User();<br>
	 * u.setUid("100");<br>
	 * u.setUName("fengyagang");<br>
	 * <br>
	 * DbFastControl.getDbFast().update(u,"uid = ?",new String[]{"100"})<br>
	 * 
	 * @param clzs
	 * @return
	 */
	public int update(Object clzs, String where, String[] whereArgs) {
		return DbFast.getDbFast().update(clzs, where, whereArgs);
	}

	/**
	 * update clz info<br>
	 * ex:<br>
	 * User u = new User();<br>
	 * u.setUid("100");<br>
	 * u.setUName("fengyagang");<br>
	 * <br>
	 * DbFastControl.getDbFast().update(u,"uid = ?",new String[]{"100"})<br>
	 * 
	 * @param clzs
	 * @return
	 */
	public int update(List<Object> clzs, String where, String[] whereArgs) {
		return DbFast.getDbFast().update(clzs, where, whereArgs);
	}

/**
     * query from db with clz<br>
     * ex:<br>
     * List<'Object> list_obj = DbFastControl.getDbFast().query(User.class,"uid=? and uname=?",new String[]{"1","feng"})
     * <br>
     * if(list_obj != null && list_obj.size() > 0)<br>&nbsp;&nbsp;&nbsp;&nbsp;
     * User user = (User)list_obj.get(0);
     * @param clz
     * @param where
     * @param whereArgs
     * @return null if no data or the List with data
     */
	public List<Object> query(Class<?> clz, String where, String[] whereArgs) {
		return DbFast.getDbFast().query(clz, where, whereArgs);
	}

/**
     * query all data from db with clz<br>
     * ex:<br>
     * List<'Object> list_obj = DbFastControl.getDbFast().query(User.class);
     * <br>
     * if(list_obj != null && list_obj.size() > 0)<br>&nbsp;&nbsp;&nbsp;&nbsp;
     * List<User> user = (User)list_obj.get(0);
     * @param clz
     * @param where
     * @param whereArgs
     * @return null if no data or the List with data
     */
	public List<Object> queryAll(Class<?> clz) {
		return DbFast.getDbFast().queryAll(clz);
	}

	/**
	 * clear all info from table clz<br>
	 * ex:<br>
	 * DbFastControl.getDbFast().clear(User.class);
	 * 
	 * @param clz
	 * @return
	 */
	public long clear(Class<?> clz) {
		return DbFast.getDbFast().clear(clz);
	}
}
