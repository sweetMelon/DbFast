package com.tiangua.fast.db.core;

import android.content.Context;

import com.tiangua.fast.db.util.FasterLog;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbFasterSqlite extends AbstractSqliteOpenHelper {

	List<Class<?>> clz_list = null;

    private static DbFasterSqlite instance = null;

    protected DbFasterSqlite(Context context, List<Class<?>> clz_list) {
        super(context);
        this.clz_list = clz_list;
    }

    public static DbFasterSqlite getInstance(Context context,
                                             List<Class<?>> clz_list) {
        if (instance == null) {
            synchronized (DbFasterSqlite.class) {
                instance = new DbFasterSqlite(context, clz_list);
            }
        }
        return instance;
    }

    @Override
    public List<String> getCreateTable() {
        List<String> table_create_list = new ArrayList<String>();
        for (int i = 0; i < clz_list.size(); i++) {
            table_sql_map = createTable((Class<?>)clz_list.get(i), table_sql_map);
            if (table_sql_map != null && table_sql_map.size() > 0) {
                for (Map.Entry<String, String> entry : table_sql_map.entrySet()) {
                    String tableSql = table_sql_map.get(entry.getKey());
                    table_create_list.add(tableSql);
                }
            }
        }
        return table_create_list;
    }

    @Override
    public List<String> getDeleteTable() {
        List<String> table_delete_list = new ArrayList<String>();
        for (int i = 0; i < clz_list.size(); i++) {
            table_delete_list.add(deleteTable(clz_list.get(i)));
        }
        return table_delete_list;
    }

    Map<String, String> table_sql_map = new HashMap<String, String>();

    private Map<String, String> createTable(Class<?> clz, Map<String, String> table_map) {
        try {
            StringBuilder tableCreate = new StringBuilder();
            String table_name = clz.getSimpleName();
            tableCreate.append("CREATE TABLE IF NOT EXISTS " + table_name + "(");
            // 返回所有成员变量
            Field[] fields = clz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    // get field interview author
                    int t_interview_author = f.getModifiers();
                    if (t_interview_author == Modifier.PRIVATE) {
                        f.setAccessible(true);
                        // get field name
                        String t_name = f.getName();
                        // get field type
                        Class<?> t_class = f.getType();
                        String t_type = t_class.getSimpleName();
                        if (t_type != null) {
                            if (t_name != null && t_name.equals("_id")) {
                                continue;
                            }
                            //如果这个此成员变量为数组类型，那么递归调用createTable方法获取此变量类型的sql
                            if(t_class.isArray())
                            {
                                table_map = createTable(t_class.getComponentType(),table_map);
                                continue;
                            }
                            if(t_class.isAssignableFrom(List.class))
                            {
                                Type type = f.getGenericType();
                                ParameterizedType pt = (ParameterizedType)type;
                                Class<?> listClz = (Class<?>)pt.getActualTypeArguments()[0];
                                table_map = createTable(listClz, table_map);
                                continue;
                            }
                            if (t_type.equalsIgnoreCase("String")) {
                                t_type = "varchar";
                            } else if (t_type.equalsIgnoreCase("long")) {
                                t_type = "NUMERIC";
                            } else if (t_type.equalsIgnoreCase("double") || t_type.equalsIgnoreCase("Decimal") || t_type.equalsIgnoreCase("float")) {
                                t_type = "float";
                            } else if (t_type.equalsIgnoreCase("byte[]")) {
                                t_type = "blob";
                            } else if (t_type.equalsIgnoreCase("int") || t_type.equalsIgnoreCase("integer")) {
                                t_type = "integer";
                            } else if (t_type.equalsIgnoreCase("enum")) {
                                t_type = "integer";
                            } else if (t_type.equalsIgnoreCase("boolean")) {
                            	t_type = "integer";
                            }
                            tableCreate.append(t_name + " " + t_type + ",");
                        }
                    }

                }
                tableCreate.deleteCharAt(tableCreate.length() - 1);
                tableCreate.append(");");
            }
            if (!table_map.containsKey(table_name))
                table_map.put(table_name, tableCreate.toString());
            FasterLog.d("table:" + table_map);
        } catch (Exception e) {
            FasterLog.E(DbFasterSqlite.class, e);
            table_map = null;
        }
        return table_map;
    }

    private String deleteTable(Object clz) {
        String result = null;
        StringBuilder tableCreate = new StringBuilder();
        try {
            Class<?> c = (Class<?>) clz;
            String table_name = c.getSimpleName();
            tableCreate.append("DELETE TABLE IF EXISTS " + table_name + ";");
            result = tableCreate.toString();
            FasterLog.d("table:" + result);
        } catch (Exception e) {
            FasterLog.E(DbFasterSqlite.class, e);
            result = null;
        }
        return result;
    }

    // public synchronized SQLiteDatabase getWritableDatabase()
    // {
    // SQLiteDatabase db = instance.getWritableDatabase();
    // return db;
    // }
    //
    // public synchronized SQLiteDatabase getReadableDatabase()
    // {
    // SQLiteDatabase db = instance.getReadableDatabase();
    // return db;
    // }
}
