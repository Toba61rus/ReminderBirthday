package ru.toba92.myapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BirthdayLab {
/*Класс синглет, предназначен для сохранения дней рождений, в независимости от жизненныъ циклов активити, но до выгрузки приложения ОП.

 */
    private static BirthdayLab mBirthdayLab;
    private List<Birthday> mBirthdays;

    public void addBirthday(Birthday birthday){
        mBirthdays.add(birthday);
    }

    private BirthdayLab(Context context) {
        mBirthdays=new ArrayList<>();

    }
//    КОнструктор, для обращения к данным дня рождения при помощи get.
    public static BirthdayLab get(Context context){
        if (mBirthdayLab==null){
            mBirthdayLab=new BirthdayLab(context);
        }
        return mBirthdayLab;
    }

    public static BirthdayLab getmBirthdayLab() {
        return mBirthdayLab;
    }

    public static void setmBirthdayLab(BirthdayLab mBirthdayLab) {
        BirthdayLab.mBirthdayLab = mBirthdayLab;
    }

    public List<Birthday> getBirthdays() {
        return mBirthdays;
    }

    public void setBirthdays(List<Birthday> birthdays) {
        mBirthdays = birthdays;
    }

    public Birthday getBirthday(UUID id){
        for (Birthday birthday:mBirthdays){
            if (birthday.getId().equals(id)){
                return birthday;
            }
        }
        return null;
    }

}
