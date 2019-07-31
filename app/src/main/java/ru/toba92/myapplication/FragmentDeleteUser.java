package ru.toba92.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.UUID;

public class FragmentDeleteUser extends DialogFragment {

    private static final String ARG_ID_USER_DELETE ="idUserDelete";
    public static final String EXTRA_ID_USER_DELETE="ru.toba92.myapplication.id_user_delete";
    private Birthday mUserDelete;
    private FragmentDeleteUser mFragmentDeleteUser;

//Мето получения арумента ID данного пользователя,для дальнейшего его удаления.

    public static FragmentDeleteUser newInstance(UUID idUserDelete){
        Bundle arg=new Bundle();
        arg.putSerializable(ARG_ID_USER_DELETE,idUserDelete);

        FragmentDeleteUser fragmentDeleteUser=new FragmentDeleteUser();
        fragmentDeleteUser.setArguments(arg);
        return fragmentDeleteUser;

    }
//   Cоздание диалогово окна.
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.delete_user ).setIcon(R.drawable.ic_warning_black_24dp).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                UUID id=(UUID)getArguments().getSerializable(ARG_ID_USER_DELETE);//Получения ID из аргумента переданного из BirthdayListFragment, и перердачи его в виде интента при нажатии кнопки ОК.
                senResult(Activity.RESULT_OK,id);
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).create();

    }
//   Метод создания интента и заворачивании его с кодом проверки для передачи целевому фрагменту.
    private void  senResult(int resultCode,UUID idUserDelete){
        if (getTargetFragment()==null){
            return;
        }
        Intent intent=new Intent();
        intent.putExtra(EXTRA_ID_USER_DELETE,idUserDelete);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
