package ru.toba92.myapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BirthdayLab {

    private static BirthdayLab mBirthdayLab;
    private List<Birthday> mBirthdays;

    private BirthdayLab(Context context) {
        mBirthdays=new ArrayList<>();

        for (int i=0;i<100;i++){

        Birthday birthday=new Birthday();
        birthday.setInformation("Пользователь "+i);
        birthday.setReceiveNotify(i==0);//Все чеки буду отключены по умолчанию.
        mBirthdays.add(birthday);
        }
    }

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
