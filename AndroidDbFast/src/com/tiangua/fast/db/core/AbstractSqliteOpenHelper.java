
package com.tiangua.fast.db.core;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tiangua.fast.db.util.DBConfig;

public abstract class AbstractSqliteOpenHelper extends SQLiteOpenHelper {

    public AbstractSqliteOpenHelper(Context context) {
        super(context, DBConfig.getDBConfig().DB_NAME, null, DBConfig.getDBConfig().DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<String> table_list = getCreateTable();
        db.beginTransaction();
        for (int i = 0; i < table_list.size(); i++) {
            db.execSQL(table_list.get(i));
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // delete table
        List<String> table_delete_list = getDeleteTable();
        for (int i = 0; i < table_delete_list.size(); i++) {
            db.execSQL(table_delete_list.get(i));
        }

        // create table
        List<String> table_list = getCreateTable();
        for (int i = 0; i < table_list.size(); i++) {
            db.execSQL(table_list.get(i));
        }
    }

    public abstract List<String> getCreateTable();

    public abstract List<String> getDeleteTable();
}
