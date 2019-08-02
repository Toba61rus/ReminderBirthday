package ru.toba92.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class BirthdayListActivity extends SingeFragmentActivity {

    public static Intent newIntent(Context context){
        return new Intent(context,BirthdayListActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return new BirthdayListFragment();
    }
}
