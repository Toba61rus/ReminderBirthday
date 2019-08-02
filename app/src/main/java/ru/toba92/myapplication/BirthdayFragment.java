package ru.toba92.myapplication;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;


public class BirthdayFragment extends Fragment {

//    private ImageButton mDeleteUser;
    private Birthday mBirthday;
    private Button mButtonDate;
    private ImageView mAvatarUser;//Будет использоваться в дальнейшем!
    private EditText mInformationConguration;
    private CheckBox mReceiveNotify;
    private static final String ARG_ID_BIRTHDAY ="birthday_id";
    private static final String DIALOG_DATE="DialogDate";
    private static final int REQUEST_DATE=0;


    public static BirthdayFragment newInstance(UUID birthdayId){
        Bundle args=new Bundle();
        args.putSerializable(ARG_ID_BIRTHDAY,birthdayId);

        BirthdayFragment fragment=new BirthdayFragment();
        fragment.setArguments(args);
        return fragment;

    }




    @Override
    public void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        mBirthday=new Birthday();
        UUID birthdayID=(UUID) getArguments().getSerializable(ARG_ID_BIRTHDAY);
        mBirthday=BirthdayLab.get(getActivity()).getBirthday(birthdayID);
    }

//    Обновление экарана при создании записи в базе данных.
    @Override
    public void onPause(){
        super.onPause();

        BirthdayLab.get(getActivity()).updateBirthday(mBirthday);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_birthday, container, false);

        mButtonDate=(Button)view.findViewById(R.id.button_date);
            updateDate();
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm=getFragmentManager();
                DatePickerFragment dialog=DatePickerFragment.newInstance(mBirthday.getDate());
                dialog.setTargetFragment(BirthdayFragment.this,REQUEST_DATE);
                dialog.show(fm,DIALOG_DATE);

            }
        });


        mInformationConguration =(EditText)view.findViewById(R.id.information_congratulated);
        mInformationConguration.setText(mBirthday.getInformation());
        mInformationConguration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//              Не использвуется.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 mBirthday.setInformation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
//                не используется.
            }
        });
        mReceiveNotify=(CheckBox) view.findViewById(R.id.check_box_notify);
        mReceiveNotify.setChecked(mBirthday.isReceiveNotify());
        mReceiveNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBirthday.setReceiveNotify(isChecked);
//Если чекбокс не нажат,то сервис не будет активирован, в дальнейшем будет использоваться для включения сервиса с помощью широковещательного приёмника.
                if (isChecked == true) {
                    boolean shouldStartAlarm = !ReminderService.isServiceAlarmOn(getActivity());
                    ReminderService.setServiceAlarm(getActivity(), shouldStartAlarm);
                    Toast.makeText(getActivity(),getString(R.string.notify_on), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.notify_off), Toast.LENGTH_SHORT).show();
                    ReminderService.setServiceAlarm(getActivity(),false);
                }

            }
        });
//        mDeleteUser =(ImageButton) view.findViewById(R.id.delete_user_image_button);
//        mDeleteUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BirthdayLab.get(getActivity()).deleteBirthday(mBirthday);
//
//
//            }
//        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode==REQUEST_DATE){
            Date date =(Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBirthday.setDate(date);
                updateDate();
        }

    }
        private void updateDate(){
        String date=new SimpleDateFormat("d MMMM yyyy").format(mBirthday.getDate());//Созданный свой стиль отображения даты.
        mButtonDate.setText(date);

        }
    }
