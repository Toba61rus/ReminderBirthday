package ru.toba92.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BirthdayListFragment extends Fragment {

    private RecyclerView mRecyclerViewListBirthday;
    private BirthdayAdapter mBirthdayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_birthday_list,container,false);

        mRecyclerViewListBirthday=(RecyclerView) view.findViewById(R.id.recycler_view_list_birthday);
        mRecyclerViewListBirthday.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        BirthdayLab birthdayLab=BirthdayLab.get(getActivity());
        List<Birthday> birthdays=birthdayLab.getBirthdays();
        if (mBirthdayAdapter==null){

        mBirthdayAdapter=new BirthdayAdapter(birthdays);
        mRecyclerViewListBirthday.setAdapter(mBirthdayAdapter);}
        else {
            mBirthdayAdapter.notifyDataSetChanged();
        }
    }

    private class BirthdayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Birthday mBirthday;
        private TextView mDateTextView;
        private TextView mInformationTextView;
        private ImageView mNotifiImageView;
        private ImageView mPersonImageView;//Будет использоваться в дальнейшем.

        public void bind(Birthday birthday){
            mBirthday=birthday;
            mInformationTextView.setText(mBirthday.getInformation());
            mDateTextView.setText(mBirthday.getDate().toString());
            mNotifiImageView.setVisibility(mBirthday.isReceiveNotify() ? View.VISIBLE:View.GONE);
//            mPersonImageView.setImageBitmap(); Будет использоваться в дальнейшем.
        }

        public BirthdayViewHolder(LayoutInflater inflater,ViewGroup container){
            super(inflater.inflate(R.layout.list_item_person_birthday,container,false));

            mDateTextView=(TextView)itemView.findViewById(R.id.item_date_text_view);
            mInformationTextView=(TextView) itemView.findViewById(R.id.ite_information_text_view);
            mPersonImageView=(ImageView)itemView.findViewById(R.id.item_avatar_person);
            mNotifiImageView=(ImageView)itemView.findViewById(R.id.notifiImageView);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {


            Intent intent=BirthdayViewPager.newIntent(getActivity(),mBirthday.getId());
            startActivity(intent);

        }
    }
    private class BirthdayAdapter extends RecyclerView.Adapter<BirthdayViewHolder>{
        private List<Birthday> mBirthdays;

        public BirthdayAdapter(List<Birthday> birthdays){
            mBirthdays=birthdays;
        }

        @NonNull
        @Override
        public BirthdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            return new BirthdayViewHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BirthdayViewHolder birthdayViewHolder, int i) {

            Birthday birthday=mBirthdays.get(i);
            birthdayViewHolder.bind(birthday);

        }

        @Override
        public int getItemCount() {
            return mBirthdays.size();
        }
    }
}
