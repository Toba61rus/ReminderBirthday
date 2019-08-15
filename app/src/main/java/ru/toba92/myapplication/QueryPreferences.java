package ru.toba92.myapplication;

import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPreferences {
    private static final String PREFF_IS_ALARM_ON="isAlarmOn";

    public static boolean isAlarmOn(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(PREFF_IS_ALARM_ON,false);
    }
    public static void setAlarmOn(Context context,boolean isOn){
        PreferenceManager.getDefaultSharedPreferences(context).
                edit()
                .putBoolean(PREFF_IS_ALARM_ON,isOn)
                .apply();
    }
}
