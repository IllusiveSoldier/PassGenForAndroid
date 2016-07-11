package com.passgen.knack.passwordgeneratorandconnecttodatabase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class DB
{
    private DbHelper mDbHelper;
    private SQLiteDatabase db;

    Context context;

    public DB(Context c)
    {
        mDbHelper = new DbHelper(c);
        context = c;
    }

    // Возвращает список всех элементов
    public Cursor getAllItems()
    {
        db = mDbHelper.getReadableDatabase();
        return db.query(FeedEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    // Закрыть подключение
    public void Close()
    {
        if (mDbHelper != null) mDbHelper.close();
        if (db != null) db.close();
    }

    // Описание таблицы, и т.д.
    public static abstract class FeedEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "passwords";
        public static final String COLUMN_NAME_RESOURCE = "resource";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }

    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    // Запрос на создание таблицы
    public static final String CREATE_TABLE = "CREATE TABLE " + FeedEntry.TABLE_NAME +
            " (" + FeedEntry._ID + " INTEGER PRIMARY KEY, " + FeedEntry.COLUMN_NAME_RESOURCE +
            TEXT_TYPE + COMMA_SEP + FeedEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE + " )";
    // Запрос на удаление таблицы
    public static final  String DELETE_TABLE = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public void DeleteValueInDatabase(int id)
    {
        db = mDbHelper.getWritableDatabase();
        db.delete(FeedEntry.TABLE_NAME, FeedEntry._ID + " = " + id, null);
    }

    public void AddValueToDatabase(final String resource, final String password)
    {
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Определяем вставляемые значения
        ContentValues values = new ContentValues();
        values.put(DB.FeedEntry.COLUMN_NAME_RESOURCE, resource);
        values.put(DB.FeedEntry.COLUMN_NAME_PASSWORD, password);
        // Вставляем значения
        db.insert(DB.FeedEntry.TABLE_NAME, null, values);
    }

    public void DeleteAndRestoreTable(SQLiteDatabase db)
    {
        db.execSQL(DB.DELETE_TABLE);
        db.execSQL(DB.CREATE_TABLE);
    }
}