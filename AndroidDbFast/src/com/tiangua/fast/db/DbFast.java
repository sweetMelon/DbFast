package com.tiangua.fast.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tiangua.fast.db.core.DbFasterSqlite;
import com.tiangua.fast.db.util.DBConfig;
import com.tiangua.fast.db.util.FasterLog;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbFast implements IDbFast {

    private String db_name = "DBFast.db";
    private int db_version = 1;

    private Context context = null;

    private List<Class<?>> clz_list = null;

    public static DBConfig dbConfig = DBConfig.getDBConfig();

    private SQLiteDatabase db = null;

    private static DbFast instance = null;

    protected DbFast() {

    }

    public static DbFast getDbFast() {
        if (instance == null) {
            synchronized (DbFast.class) {
                if (instance == null) {
                    instance = new DbFast();
                }
            }
        }
        return instance;
    }

    public void init(Context ctx, DBConfig dbConfiguration,
    		List<Class<?>> list) {
        this.context = ctx;
        this.clz_list = list;
        if (dbConfig == null) {
            dbConfig.DB_NAME = db_name;
            dbConfig.DB_VERSION = db_version;
        } else {
            dbConfig = dbConfiguration;
        }
        DbFasterSqlite.getInstance(ctx, clz_list);
    }

    @Override
    public long insert(Object clz) {
        long count = -1;
        db = null;
        try {
            db = DbFasterSqlite.getInstance(context, clz_list).getWritableDatabase();
            if (clz != null && db != null && db.isOpen()
                    && !db.isDbLockedByCurrentThread()) {
                Class<?> c = clz.getClass();
                count = db.insert(c.getSimpleName(), null,
                        createContentValue(clz));
            }
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            if (db != null) {
                db.close();
                db = null;
            }
            count = -1;
        }
        return count;
    }

    @Override
    public long clear(Class<?> clz) {
        long count = -1;
        db = null;
        try {
            db = DbFasterSqlite.getInstance(context, clz_list).getWritableDatabase();
            if (clz != null && db != null && db.isOpen()
                    && !db.isDbLockedByCurrentThread()) {
                count = db.delete(clz.getSimpleName(), null, null);
            }
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            if (db != null) {
                db.close();
                db = null;
            }
            count = -1;
        }
        return count;
    }

    @Override
    public synchronized long insertList(List<Object> clzs) {
        long count = -1;
        db = null;
        try {
            db = DbFasterSqlite.getInstance(context, clz_list).getWritableDatabase();
            if (clzs != null && db != null && db.isOpen()
                    && !db.isDbLockedByCurrentThread()) {
                db.beginTransaction();
                int size = clzs.size();
                for (int i = 0; i < size; i++) {
                    Object clz = clzs.get(i);
                    Class<?> c = clz.getClass();
                    count = db.insert(c.getSimpleName(), null,
                            createContentValue(clz));
                }
                FasterLog.d("count:" + count);
                db.setTransactionSuccessful();
                db.endTransaction();
            }
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            if (db != null) {
                db.close();
                db = null;
            }
            count = -1;
        }
        return count;
    }

    @Override
    public synchronized List<Object> query(Class<?> clz, String where,
                                           String[] whereArgs) {
        List<Object> object = null;
        try {
            db = DbFasterSqlite.getInstance(context, clz_list).getReadableDatabase();
            if (clz != null && db != null && db.isOpen()
                    && !db.isDbLockedByCurrentThread()) {
                Cursor cursor = db.query(clz.getSimpleName(), null, where,
                        whereArgs, null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    object = convertCursor(clz, cursor);
                }
            }
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            object = null;
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return object;
    }

    @Override
    public synchronized List<Object> queryAll(Class<?> clz) {
        List<Object> list = null;
        try {
            db = DbFasterSqlite.getInstance(context, clz_list).getReadableDatabase();
            if (clz != null && db != null && db.isOpen()
                    && !db.isDbLockedByCurrentThread()) {
                // ��ѯ������Ҫ��ȡobject��clssֱ��ʹ��ǿת���ɣ���Ϊ�����Ҳ��һ��.class
                Cursor cursor = db.query(clz.getSimpleName(), null, null, null,
                        null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    list = convertCursor(clz, cursor);
                }
            }
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            list = null;
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return list;
    }

    @Override
    public synchronized int update(Object clzs, String where, String[] whereArgs) {
        int count = -1;
        db = null;
        try {
            db = DbFasterSqlite.getInstance(context, clz_list).getWritableDatabase();

            if (clzs != null && db != null && db.isOpen()
                    && !db.isDbLockedByCurrentThread())

            {
                // �����Ӻ��޸����ݿ⣬����clz.getClass�õ���
                // ����ǲ�ѯ��ɾ������Ҫֱ��ǿת
                Class<?> c = clzs.getClass();
                count = db.update(c.getSimpleName(), createContentValue(clzs),
                        where, whereArgs);
            }

        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            if (db != null) {
                db.close();
                db = null;
            }
            count = -1;
        }
        return count;
    }

    @Override
    public synchronized int update(List<Object> clzs, String where, String[] whereArgs) {
        int count = -1;
        db = null;
        try {
            db = DbFasterSqlite.getInstance(context, clz_list).getWritableDatabase();

            if (clzs != null && db != null && db.isOpen()
                    && !db.isDbLockedByCurrentThread())

            {
                db.beginTransaction();
                int size = clzs.size();
                for (int i = 0; i < size; i++) {
                    // �����Ӻ��޸����ݿ⣬����clz.getClass�õ���
                    // ����ǲ�ѯ��ɾ������Ҫֱ��ǿת
                    Class<?> c = clzs.getClass();
                    count = db.update(c.getSimpleName(), createContentValue(clzs),
                            where, whereArgs);
                }
                FasterLog.d("count:" + count);
                db.setTransactionSuccessful();
                db.endTransaction();

            }

        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            if (db != null) {
                db.close();
                db = null;
            }
            count = -1;
        }
        return count;
    }

    @Override
    public synchronized int delete(Class<?> clz, String where, String[] whereArgs) {
        int count = -1;
        db = null;
        try {
            db = DbFasterSqlite.getInstance(context, clz_list).getWritableDatabase();
            if (clz != null && db != null && db.isOpen()
                    && !db.isDbLockedByCurrentThread()) {
                //���߼�ɾ�����߼�ɾ���ɹ���������ɾ��
//                int logicDelete = logicDelete(clz, where, whereArgs);
//                if(logicDelete > 0)
//                {
                // �����Ӻ��޸����ݿ⣬����clz.getClass�õ���
                // ����ǲ�ѯ��ɾ������Ҫֱ��ǿת
                Class<?> c = (Class<?>) clz;
                count = db.delete(c.getSimpleName(), where, whereArgs);
//                }
            }
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            count = -1;
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return count;
    }

    private int logicDelete(Object clz, String where, String[] whereArgs) {
        int count = -1;
        db = null;
        try {
            db = DbFasterSqlite.getInstance(context, clz_list).getWritableDatabase();
            if (clz != null && db != null && db.isOpen()
                    && !db.isDbLockedByCurrentThread()) {
                setObjDelete(clz);
                // �����Ӻ��޸����ݿ⣬����clz.getClass�õ���
                // ����ǲ�ѯ��ɾ������Ҫֱ��ǿת
                Class<?> c = (Class<?>) clz;
                count = db.update(c.getSimpleName(), createContentValue(clz),
                        where, whereArgs);
            }
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            count = -1;
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return count;
    }


    private ContentValues createContentValue(Object clz) {
        ContentValues cv = new ContentValues();
        try {
            Class<?> c = clz.getClass();
            Field[] fields = c.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    // get field author
                    int t_interview_author = f.getModifiers();
                    if (t_interview_author == Modifier.PRIVATE) {
                        // ��������Ա�����
                        f.setAccessible(true);
                        // get ���Ե� name
                        String t_name = f.getName();
                        // get ���Ե�ֵ
                        Object value = f.get(clz);
                        // get ���Ե�����
                        String t_type = f.getType().getSimpleName();

                        //���ĳ�����ĳ����������������,��ô��������ת��Ϊlist
                        //����insertList���������������е����ж�����ӽ����ݿ�(��Ϊ�˶��������ݿ��е������ӳ��)
                        if (f.getType().isArray()) {
                            Object[] arrayValue = (Object[]) f.get(clz);
                            List<Object> list = Arrays.asList(arrayValue);
                            insertList(list);
                            continue;
                        }
                        //���ĳ�����ĳ��������List����
                        //����insertList����������list�е����ж�����ӽ����ݿ�(��Ϊ�˶��������ݿ��е������ӳ��)
                        if (f.getType().isAssignableFrom(List.class))
                        {
                            List<Object> list = (List<Object>)f.get(clz);
                            insertList(list);
                            continue;
                        }

                        if (t_type != null && t_type.trim().length() > 0) {
                            if (t_type.equalsIgnoreCase("string")) {
                                String v = (String) value;
                                cv.put(t_name, v);
                            } else if (t_type.equalsIgnoreCase("float")) {
                                float float_v = (Float) value;
                                cv.put(t_name, float_v);
                            } else if (t_type.equalsIgnoreCase("long")) {
                                long long_v = (Long) value;
                                cv.put(t_name, long_v);
                            } else if (t_type.equalsIgnoreCase("int")
                                    || t_type.equalsIgnoreCase("integer")) {
                                if (t_name.equals("_id"))
                                    continue;
                                String v = String.valueOf(value);
                                int int_v = Integer.valueOf(v);
                                cv.put(t_name, int_v);
                            } else if (t_type.equalsIgnoreCase("double")) {
                                double double_v = (Double) value;
                                cv.put(t_name, double_v);
                            } else if (t_type.equalsIgnoreCase("boolean")) {
                                boolean boolean_v = (Boolean) value;
                                cv.put(t_name, boolean_v);
                            } else if (t_type.equalsIgnoreCase("byte")) {
                                byte byte_v = (Byte) value;
                                cv.put(t_name, byte_v);
                            } else if (t_type.equalsIgnoreCase("byte[]")) {
                                byte[] bytes_v = (byte[]) value;
                                cv.put(t_name, bytes_v);
                            } else {
                                cv.put(t_name, (String) value);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            cv = null;
        }
        return cv;
    }

    /**
     * ����ѯ����cursorת��Ϊ��ѯ���ʵ�弯��
     *
     * @param clz    Ҫ��ѯ�ı�(��class��ʾ)
     * @param cursor
     * @return
     */
    private List<Object> convertCursor(Object clz, Cursor cursor) {
        List<Object> list = null;
        Object newInstance = null;
        try {
            list = new ArrayList<Object>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // ��objectת��Ϊ class
                // ����ǲ�ѯ��صģ�ֱ��ǿת����Ϊ��ʱclzҲ��һ��.class����
                Class<?> c = (Class<?>) clz;
                // ����һ��clz��Ķ���
                newInstance = Class.forName(c.getCanonicalName()).newInstance();
                // ��ȡ�����������е�����
                Field[] fields = c.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (int i = 0; i < fields.length; i++) {
                        Field f = fields[i];
                        // ��ȡ���Ե�Ȩ�ޣ�ֻҪprivate˽�е�
                        int t_interview_author = f.getModifiers();
                        if (t_interview_author == Modifier.PRIVATE) {
                            // ��������Ա�����
                            f.setAccessible(true);
                            // get ���Ե� name
                            String t_name = f.getName();
                            // get ���Ե�����
                            String t_type = f.getType().getSimpleName();

                            if (t_type != null && t_type.trim().length() > 0) {
                                if (t_type.equalsIgnoreCase("string")) {
                                    String value = cursor.getString(cursor.getColumnIndex(t_name));
                                    if (value != null) {
                                        f.set(newInstance, value);
                                    }
                                } else if (t_type.equalsIgnoreCase("float")) {
                                    float value = cursor.getFloat(cursor.getColumnIndex(t_name));
                                    f.setFloat(newInstance, value);
                                } else if (t_type.equalsIgnoreCase("long")) {
                                    long value = cursor.getLong(cursor.getColumnIndex(t_name));
                                    f.setLong(newInstance, value);
                                } else if (t_type.equalsIgnoreCase("int")
                                        || t_type.equalsIgnoreCase(
                                        "integer")) {
                                    if (t_name.equals("_id"))
                                        continue;
                                    int value = cursor.getInt(cursor.getColumnIndex(t_name));
                                    f.setInt(newInstance, value);
                                } else if (t_type.equalsIgnoreCase("double")) {
                                    double value = cursor.getDouble(cursor.getColumnIndex(t_name));
                                    f.setDouble(newInstance, value);
                                } else if (t_type.equalsIgnoreCase("boolean")) {
                                    int value = cursor.getInt(cursor.getColumnIndex(t_name));
                                    // ��Ϊcursor��û�в���ֵ�����������ٲ������ݵ�ʱ��
                                    // ������ֵת��Ϊ���� 0 �� 1
                                    // true Ϊ1
                                    // false Ϊ0
                                    if (value == 1) {
                                        f.setBoolean(newInstance, true);
                                    } else {
                                        f.setBoolean(newInstance, false);
                                    }
                                } else if (t_type.equalsIgnoreCase("byte[]")) {
                                    byte[] value = cursor.getBlob(cursor.getColumnIndex(t_name));
                                    f.set(newInstance, value);
                                } else {
                                    String value = cursor.getString(cursor.getColumnIndex(t_name));
                                    f.set(newInstance, value);
                                }
                            }
                        }
                    }
                }
                list.add(newInstance);
                cursor.moveToNext();
            }
            cursor.close();
            cursor = null;
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);
            list = null;
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return list;

    }

    /**
     * �߼�ɾ������ĳ������ʱ���Ƚ������delete���Է�������Ϊ1��Ȼ�����޸Ĵ˶��������ݿ��б��ֶ�deleteֵΪ1
     *
     * @param clz
     */
    private void setObjDelete(Object clz) {

        Object newInstance = null;
        try {
            // ��objectת��Ϊ class
            // ����ǲ�ѯ��صģ�ֱ��ǿת����Ϊ��ʱclzҲ��һ��.class����
            Class<?> c = (Class<?>) clz;
            // ����һ��clz��Ķ���
            newInstance = Class.forName(c.getCanonicalName()).newInstance();
            // ��ȡ�����������е�����
            Field field = c.getDeclaredField("deleteTag");
            if (field != null) {

                // ��ȡ���Ե�Ȩ�ޣ�ֻҪprivate˽�е�
                int t_interview_author = field.getModifiers();
                if (t_interview_author == Modifier.PRIVATE) {
                    // ��������Ա�����
                    field.setAccessible(true);
                    field.setInt(newInstance, 1);
                }
            }
        } catch (Exception e) {
            FasterLog.E(DbFast.class, e);

        }

    }

    /**
     * ȥ�ز��ϲ�2������
     *
     * @param a
     * @param b
     * @return
     */
    private String[] concat(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

}
