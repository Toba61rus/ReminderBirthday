package ru.toba92.myapplication;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.UUID;


public class BirthdayFragment extends Fragment {

    private Birthday mBirthday;
    private Button mButtonDate;
    private ImageView mAvatarUser;//Будет использоваться в дальнейшем!
    private EditText mInformationConguration;
    private CheckBox mReceiveNotify;
    private static final String ARG_ID_BIRTHDAY ="birthday_id";


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_birthday, container, false);

        mButtonDate=(Button)view.findViewById(R.id.button_date);
        mButtonDate.setText(mBirthday.getDate().toString());
        mButtonDate.setEnabled(false);
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
//                Будет реализованно позже! Будет включать уведомления о предстоящем дне рождении.
            mBirthday.setReceiveNotify(isChecked);

            }
        });

        return view;
    }



}
