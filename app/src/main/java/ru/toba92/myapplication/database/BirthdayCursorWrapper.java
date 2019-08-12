package ru.toba92.myapplication.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Date;
import java.util.UUID;

import ru.toba92.myapplication.Birthday;

public class BirthdayCursorWrapper extends CursorWrapper {

    public BirthdayCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Birthday getBirthday(){
        String uuidString=getString(getColumnIndex(BirthdayDbSchema.BirthdayTable.Cols.UUID));
        String information=getString(getColumnIndex(BirthdayDbSchema.BirthdayTable.Cols.INFORMATION));
        long date=getLong(getColumnIndex(BirthdayDbSchema.BirthdayTable.Cols.DATE));
        int receivedNotify=getInt(getColumnIndex(BirthdayDbSchema.BirthdayTable.Cols.RECEIVENOTIFY));

        Birthday birthday=new Birthday(UUID.fromString(uuidString));
        birthday.setDate(new Date(date));
        birthday.setReceiveNotify(receivedNotify !=0);
        birthday.setInformation(information);

        return birthday;
    }
}
