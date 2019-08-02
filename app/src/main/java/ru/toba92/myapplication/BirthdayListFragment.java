package ru.toba92.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


//Контроллер представления списка пользователей.
public class BirthdayListFragment extends Fragment {

    private static final int REQUEST_ID_DELETE =0;
    private RecyclerView mRecyclerViewListBirthday;
    private BirthdayAdapter mBirthdayAdapter;
    private static final String DIALOG_DELETE_USER="DialogDeleteUser";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_user:
                Birthday birthday=new Birthday();
                BirthdayLab.get(getActivity()).addBirthday(birthday);
                Intent intent=BirthdayViewPager.newIntent(getActivity(),birthday.getId());
                startActivity(intent);
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_birthday_list,container,false);

        //Инициилизация вью элементов.
        mRecyclerViewListBirthday=(RecyclerView) view.findViewById(R.id.recycler_view_list_birthday);
        mRecyclerViewListBirthday.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
//    Метод возобновления жизненного цикла фрагмента,испольщуется для обновления списка при переходе из фрагмента детализации списка(при помощи метода updateUI())/
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
//Создание меню в тулбаре.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_list_birthday,menu);
    }
//Метод обновления представления на экране вывода списка.
    public  void updateUI() {
        BirthdayLab birthdayLab=BirthdayLab.get(getActivity());
        List<Birthday> birthdays=birthdayLab.getBirthdays();
        if (mBirthdayAdapter==null){

        mBirthdayAdapter=new BirthdayAdapter(birthdays);
        mRecyclerViewListBirthday.setAdapter(mBirthdayAdapter);}
        else {
            mBirthdayAdapter.setBirthday(birthdays);
            mBirthdayAdapter.notifyDataSetChanged();
        }
    }

    private class BirthdayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        Объявление вью элемнтов.
        private Birthday mBirthday;
        private TextView mDateTextView;
        private TextView mInformationTextView;
        private ImageView mNotifyImageView;
        private ImageView mPersonImageView;//Будет использоваться в дальнейшем.


//       Метод привязки информации модели к нашим вью эллементам.
        public void bind(Birthday birthday){
            mBirthday=birthday;
            mInformationTextView.setText(mBirthday.getInformation());

            String date= new SimpleDateFormat("d MMMM yyyy").format(mBirthday.getDate());//Созданный свой стиль отображения даты.
            mDateTextView.setText(date);

            mNotifyImageView.setVisibility(mBirthday.isReceiveNotify() ? View.VISIBLE:View.GONE);
//            mPersonImageView.setImageBitmap(); Будет использоваться в дальнейшем.
        }

        public BirthdayViewHolder(LayoutInflater inflater,ViewGroup container){
            super(inflater.inflate(R.layout.list_item_person_birthday,container,false));//Раздувание представления.

//         Инициилизация вью элементов представлениея.
            mDateTextView=(TextView)itemView.findViewById(R.id.item_date_text_view);
            mInformationTextView=(TextView) itemView.findViewById(R.id.ite_information_text_view);
            mPersonImageView=(ImageView)itemView.findViewById(R.id.item_avatar_person);
            mNotifyImageView =(ImageView)itemView.findViewById(R.id.notifiImageView);
            itemView.setOnClickListener(this);
//Метод для удаления указанного пользователя из списка(БД).
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentDeleteUser fragmentDeleteUser=FragmentDeleteUser.newInstance(mBirthday.getId());
                    fragmentDeleteUser.setTargetFragment(BirthdayListFragment.this,REQUEST_ID_DELETE);
                    fragmentDeleteUser.show(fragmentManager,DIALOG_DELETE_USER);

//                    BirthdayLab.get(getActivity()).deleteBirthday(mBirthday);
//                    updateUI();

                    return true;
                }
            });
        }


        @Override
        public void onClick(View v) {
//          Отработка нажатий на элементы списка с последующим переходов детализации данного пользователя списка.
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
        public void setBirthday(List<Birthday>  birthday){
            mBirthdays=birthday;
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent idUserDelete){
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode==REQUEST_ID_DELETE){

            UUID id=(UUID) idUserDelete.getSerializableExtra(FragmentDeleteUser.EXTRA_ID_USER_DELETE);
            Birthday birthday=BirthdayLab.get(getActivity()).getBirthday(id);
            BirthdayLab.get(getActivity()).deleteBirthday(birthday);

            updateUI();
        }
    }
}
