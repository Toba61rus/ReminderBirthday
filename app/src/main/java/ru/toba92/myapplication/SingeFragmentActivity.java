package ru.toba92.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


//Абстрактный класс, использующийся для переходов из активити во фрагменты.

public  abstract class SingeFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);

        if (fragment==null){

            fragment=createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
}
