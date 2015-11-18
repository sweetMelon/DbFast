package com.tiangua.fast.db;

import java.util.List;

public interface IDbFast
{

    /**
     * query from clz<br>
     * ex:<br>
     * query(User.class,"uid=? and uname=?",new String[]{"1","feng"})
     * 
     * @param clz
     * @param where
     * @param whereArgs
     * @return
     */
    public List<Object> query(Class<?> clz, String where, String[] whereArgs);

    /**
     * query all info from table clz<br>
     * ex:<br>
     * query(User.class)
     * 
     * @param clz
     * @return
     */
    public List<?> queryAll(Class<?> clz);

    /**
     * update clz info<br>
     * ex:<br>
     * User u = new User();<br>
     * u.setUid("100");<br>
     * u.setUName("fengyagang");<br>
     * <br>
     * update(u,"uid = ?",new String[]{"100"})<br>
     * 
     * @param clzs
     * @return
     */
    public int update(Object clzs, String where, String[] whereArgs);
    
    /**
     * update clz info<br>
     * ex:<br>
     * User u = new User();<br>
     * u.setUid("100");<br>
     * u.setUName("fengyagang");<br>
     * <br>
     * update(u,"uid = ?",new String[]{"100"})<br>
     * 
     * @param clzs
     * @return
     */
    public int update(List<Object> clzs, String where, String[] whereArgs);

    /**
     * delete clz info<br>
     * ex:<br>
     * int count = delete(User.class,"uid = ?",new String[]{uid});<br>
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
    public int delete(Class<?> clz, String where, String[] whereArgs);

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
     * int count = insert(clz_list);<br>
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
     * @return
     */
    public long insertList(List<Object> clzs);

    /**
     * insert data to db<br>
     * ex:<br>
     * 
     * User u = new User();<br>
     * u.setUid("100");<br>
     * u.setUName("fengyagang");<br>
     * clz_list.add(u);<br>
     * <br>
     * 
     * <br>
     * int count = insert(u);<br>
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
     * @return
     */
    public long insert(Object clzs);

    public long clear(Class<?> clz);
    
}
