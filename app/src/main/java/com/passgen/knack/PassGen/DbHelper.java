package com.passgen.knack.PassGen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Класс - помощник по работе с БД
public class DbHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "resources.db";

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DB.CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DB.DELETE_TABLE);
        onCreate(db);
    }

    /*
    public void onDowngrade(SQLiteDatabase db)
    {
        onUpgrade(db, oldVersion, newVercsion);
    }
    */
}
