package com.example.mydatasaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DataBaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
        dbHelper = new DataBaseHelper(context);
    }

    public void insert(String name, String phone) {
            database = dbHelper.getWritableDatabase();
            ContentValues contentValue = new ContentValues();
            contentValue.put(DataBaseHelper.NAME, name);
            contentValue.put(DataBaseHelper.PHONE, phone);
            database.insert(DataBaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToLast();
        }
        return cursor;
    }

}