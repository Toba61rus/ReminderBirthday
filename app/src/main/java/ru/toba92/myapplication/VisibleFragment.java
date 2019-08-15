package ru.toba92.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class VisibleFragment extends Fragment {

    private static final String TAG="VisibleFragment";

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter(ReminderService.ACTION_SHOW_NOTIFICATION);
        getActivity().registerReceiver(mOnShowNotification,intentFilter);
    }
    @Override
    public void onStop(){
        super.onStop();
        getActivity().unregisterReceiver(mOnShowNotification);
    }
    private BroadcastReceiver mOnShowNotification =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getActivity(),"Goat a broadcast"+intent.getAction(),Toast.LENGTH_SHORT).show();
        }
    };
}
