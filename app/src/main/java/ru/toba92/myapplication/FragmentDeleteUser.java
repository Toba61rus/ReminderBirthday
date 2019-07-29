package ru.toba92.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

public class FragmentDeleteUser extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.delete_user).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).create();

    }
}
