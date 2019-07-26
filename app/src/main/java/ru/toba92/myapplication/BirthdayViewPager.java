package ru.toba92.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class BirthdayViewPager extends AppCompatActivity {

    private List<Birthday> mBirthdays;
    private ViewPager mViewPager;
    private static final String EXTRA_BIRTHDAY_ID="ru.toba92.myapplication.birthday_id";

    public static final Intent newIntent(Context packageContext, UUID id){
        Intent intent=new Intent(packageContext,BirthdayViewPager.class);
        intent.putExtra(EXTRA_BIRTHDAY_ID,id);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_view_pager);

    UUID birthdayId=(UUID) getIntent().getSerializableExtra(EXTRA_BIRTHDAY_ID);


    mViewPager=(ViewPager) findViewById(R.id.birthday_view_pager);
    mBirthdays=BirthdayLab.get(this).getBirthdays();
        FragmentManager fragmentManager=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                Birthday birthday=mBirthdays.get(i);
                return BirthdayFragment.newInstance(birthday.getId());
            }

            @Override
            public int getCount() {
                return mBirthdays.size();
            }
        });
        for (int i = 0; i < mBirthdays.size(); i++) {
            if (mBirthdays.get(i).getId().equals(birthdayId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
