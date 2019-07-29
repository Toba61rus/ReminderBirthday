package ru.toba92.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.toba92.myapplication.database.BirthdayBaseHelper;
import ru.toba92.myapplication.database.BirthdayCursorWrapper;
import ru.toba92.myapplication.database.BirthdayDbSchema;

public class BirthdayLab {
/*Класс синглет, предназначен для сохранения дней рождений, в независимости от жизненныъ циклов активити, но до выгрузки приложения ОП.

 */
    private static BirthdayLab mBirthdayLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //Вставка добавленной записи.
    public void addBirthday(Birthday birthday){
        ContentValues values= getContentValues(birthday);
        mDatabase.insert(BirthdayDbSchema.BirthdayTable.NAME,null,values);
    }
//    Метод удаления дня рождения из базы данных
    public void deleteBirthday(Birthday birthday){

        String uuidString=birthday.getId().toString();
        mDatabase.delete(BirthdayDbSchema.BirthdayTable.NAME, "UUID =? ", new String[]{uuidString});
    }



    private BirthdayLab(Context context) {
        mContext=context.getApplicationContext();//объявление контекста для обращения к БД.
        mDatabase=new BirthdayBaseHelper(mContext).getWritableDatabase();//Обращение для чтения или создания нашей БД.

    }
//    КОнструктор, для обращения к данным дня рождения при помощи get.
    public static BirthdayLab get(Context context){
        if (mBirthdayLab==null){
            mBirthdayLab=new BirthdayLab(context);
        }
        return mBirthdayLab;
    }
    public List<Birthday> getBirthdays(){
        List<Birthday> birthdays=new ArrayList<>();

        BirthdayCursorWrapper cursor= queryBirthday(null,null);

        try {cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                birthdays.add(cursor.getBirthday());
                cursor.moveToNext();
            }

        }finally {
            cursor.close();
        }
                return birthdays;
    }

    public static BirthdayLab getmBirthdayLab() {
        return mBirthdayLab;
    }

    public static void setmBirthdayLab(BirthdayLab mBirthdayLab) {
        BirthdayLab.mBirthdayLab = mBirthdayLab;
    }
    public Birthday getBirthday(UUID id){
        BirthdayCursorWrapper cursor= queryBirthday(BirthdayDbSchema.BirthdayTable.Cols.UUID + "=?",
                new String[]{id.toString()});

        try {
            if (cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getBirthday();
        }
        finally {
            cursor.close();
        }
    }



//    Методя для добавления записи в базу данных.
    private static ContentValues getContentValues(Birthday birthday){
        ContentValues values=new ContentValues();
        values.put(BirthdayDbSchema.BirthdayTable.Cols.UUID,birthday.getId().toString());
        values.put(BirthdayDbSchema.BirthdayTable.Cols.DATE, birthday.getDate().getTime());
        values.put(BirthdayDbSchema.BirthdayTable.Cols.INFORMATION,birthday.getInformation());
        values.put(BirthdayDbSchema.BirthdayTable.Cols.RECEIVENOTIFY,birthday.isReceiveNotify()?1:0);
        return values;
    }
    ////Обновление записи
    public void updateBirthday(Birthday birthday){String uuidString=birthday.getId().toString();
        ContentValues values= getContentValues(birthday);

        mDatabase.update(BirthdayDbSchema.BirthdayTable.NAME,values, BirthdayDbSchema.BirthdayTable.Cols.UUID +"=?",new String[]{uuidString});
    }

    private BirthdayCursorWrapper queryBirthday(String whereClause, String[] whereArgs){
        Cursor cursor=mDatabase.query(
                BirthdayDbSchema.BirthdayTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new BirthdayCursorWrapper(cursor);

    }
}
