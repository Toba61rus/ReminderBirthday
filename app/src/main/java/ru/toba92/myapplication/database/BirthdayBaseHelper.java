package ru.toba92.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static ru.toba92.myapplication.database.BirthdayDbSchema.BirthdayTable.NAME;

public class BirthdayBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="birthdayBse.db";

    public BirthdayBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

//    Создание таблицы
    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL("create table " + NAME +"("+
                "_id integer primary key autoincrement," + BirthdayDbSchema.BirthdayTable.Cols.UUID+
                "," + BirthdayDbSchema.BirthdayTable.Cols.DATE+
                "," +BirthdayDbSchema.BirthdayTable.Cols.INFORMATION+
                "," + BirthdayDbSchema.BirthdayTable.Cols.RECEIVENOTIFY +
                ")");

    }
//Обновление таблицы.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE  IF EXISTS "+NAME);

    }
}
