package ru.toba92.myapplication;

import android.support.v4.app.Fragment;

public class BirthdayListActivity extends SingeFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new BirthdayListFragment();
    }
}
