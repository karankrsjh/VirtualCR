package com.karan.virtualcr;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDatabase extends SQLiteOpenHelper {
    public static final String SQLDatabaseName="VRDatabase.db";
    public static final int  SQLDatabaseVersion=1;
    public SQLDatabase(Context context) {
        super(context, SQLDatabaseName, null, SQLDatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Groupdatabase.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Groupdatabase.SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}