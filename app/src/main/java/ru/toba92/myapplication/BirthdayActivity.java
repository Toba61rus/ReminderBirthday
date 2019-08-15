package ru.toba92.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class BirthdayActivity extends SingeFragmentActivity{


    private static final String EXTRA_ID_BIRTHDAY ="ru.toba92.myapplication.birthday_id";//Дополнение, для сохранения ID указанного пользователя.

//    public static Intent newIntent(Context packageContext, UUID id){
//
//        Intent intent=new Intent(packageContext,BirthdayActivity.class);
//        intent.putExtra(EXTRA_ID_BIRTHDAY,id);
//        return intent;
//    }
//      Метод для передачи ID выбранного пользователя из списка в фрагмент детализации пользователя.
    @Override
    protected Fragment createFragment() {
        UUID birthdayId=(UUID)getIntent().getSerializableExtra(EXTRA_ID_BIRTHDAY);
        return BirthdayFragment.newInstance(birthdayId);
    }
}
