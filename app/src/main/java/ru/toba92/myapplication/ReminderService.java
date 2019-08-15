package ru.toba92.myapplication;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ReminderService extends IntentService {

    private static final String TAG="ReminderService";
    private static final int DEFAULT_YEAR = 0;
    private static final long REMINDER_INTERVAL = TimeUnit.MINUTES.toMillis(1);
    public static final String ACTION_SHOW_NOTIFICATION ="ru.toba92.myapplication.SHOW_NOTIFICATION" ;
    private static final String CHANEL_ID ="Димкин дом!" ;
    private static final String EXTRA_BIRTHDAY_ID ="ru.toba92.myapplication.extra_id" ;
    private int NOTIFICATION_ID=1;
    private Date mDate;
    private Birthday mBirthday;

    public  static Intent newIntent(Context context){
        return new Intent(context, ReminderService.class);
    }
//Статический интент для получения ID пользователя из которого получаем дату.
    public static Intent newIntentIDUser(Context packageContext, UUID birthdayID){
        Intent intent=new Intent(packageContext,ReminderService.class);
        intent.putExtra(EXTRA_BIRTHDAY_ID,birthdayID);
        return intent;
    }

    //Интент для приёма данных дня рождения пользователя


    public ReminderService() {
        super(TAG);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onHandleIntent( Intent intent) {

        mBirthday = new Birthday();

        //Наш ID полученный из метода onCreate в классе BirthdayListFragment,для установки даты определённого пользователя
//        UUID birthdayID =(UUID)intent.getSerializableExtra(EXTRA_BIRTHDAY_ID);//Пока не работает.
//        mBirthday=BirthdayLab.get(this).getBirthday(birthdayID);
        mDate=new Date();

        Intent i = BirthdayListActivity.newIntent(this);//Интент для перехода в приложения по нажатию на уведомление.
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        Resources resources = getResources();
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

//        Создание канала уведомелния ( для Андрода 8 и выше)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID, "DimKer Chanel", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("My chanel description");
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(notificationChannel);
//Интенты, которые будут выполняться по нажатию кнопок в уведомлении
            Intent intentWriteMessage = new Intent(Intent.ACTION_SENDTO);
            Intent intentCall = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);//Будет реализовано в дальнейшем.
            Intent intentCancel = new Intent();
//ПендингИнтенты, которые дают разрешение и вызывают соотвествующие им интенты.
            PendingIntent piCall = PendingIntent.getActivity(this, 0, intentCall, 0);
            PendingIntent piWriteMessage = PendingIntent.getActivity(this, 1, intentWriteMessage, 0);
            PendingIntent piCancel = PendingIntent.getActivity(this, 2, intentCancel, 0);
//Создание самого уведомления.
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANEL_ID)
                    .setTicker(resources.getString(R.string.new_event_title))
                    .setSmallIcon((R.drawable.ic_event_note_black_24dp))
                    .setContentTitle(resources.getString(R.string.new_event_title))
                    .setContentText(resources.getString(R.string.content_text_notify))
                    .setContentIntent(pi)
                    .setColor(Color.GREEN)
                    .setWhen(System.currentTimeMillis())
                    .addAction(R.mipmap.ic_launcher_round, getString(R.string.call).toString(), piCall)
                    .addAction(R.mipmap.ic_launcher_round, getString(R.string.write).toString(), piWriteMessage)
                    .addAction(R.mipmap.ic_launcher_round, getString(R.string.cancel).toString(), piCancel)
                    .setAutoCancel(true);

            GregorianCalendar calendar=new GregorianCalendar();
            calendar.setTime(mDate);
            int year=calendar.get(DEFAULT_YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);

            GregorianCalendar calendar2=new GregorianCalendar();
            calendar2.setTime(mBirthday.getDate());
            int year2=calendar2.get(DEFAULT_YEAR);
            int month2=calendar2.get(Calendar.MONTH);
            int day2=calendar2.get(Calendar.DAY_OF_MONTH);


            //Создадим свой формат даты длясравненения дат.
            DateFormat df=new SimpleDateFormat("d MMMM");
//Создади условие, при сравнение текущей даты с датой пользователя,при совпадении дат,проявляется уведомление.
            if (df.format(calendar.getTime()).compareTo(df.format(calendar2.getTime()))==0){
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION));
                Log.d(TAG, "сегодня"+calendar.getTime()+ " день рождения"+ calendar2.getTime());

            }
        }

    }


//Метод для вызова сервивса при помощи PendingIntent и включение повтора вызова Service.
    public static void setServiceAlarm(Context context,boolean isOn) {
        Intent i = ReminderService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), REMINDER_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
        QueryPreferences.setAlarmOn(context,isOn);
    }
//    Метод для проверки сигнала PendingIntent.
    public static boolean isServiceAlarmOn(Context context){
        Intent i=ReminderService.newIntent(context);
        PendingIntent pi=PendingIntent.getService(context,0,i,PendingIntent.FLAG_NO_CREATE);
        return pi !=null;
        }
    }
