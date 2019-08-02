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
import android.os.Messenger;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReminderService extends IntentService {

    private static final String TAG="ReminderService";
    private static final long REMINDER_INTERVAL = TimeUnit.MINUTES.toMillis(1);
    private static final String CHANEL_ID ="Димкин дом!" ;
    private int NOTIFICATION_ID=1;
    private Date mDate;
    private Birthday mBirthday;


    public  static Intent newIntent(Context context){
        return new Intent(context, ReminderService.class);
    }

    public ReminderService() {
        super(TAG);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onHandleIntent( Intent intent) {

        mDate=new Date();
        mBirthday=new Birthday();
        Log.i(TAG,"Received an intent "+ intent);


        Intent i=BirthdayListActivity.newIntent(this);//Интент для перехода в приложения по нажатию на уведомление.
        PendingIntent pi=PendingIntent.getActivity(this,0,i,0);
        Resources resources=getResources();
        NotificationManager notificationManager=getSystemService(NotificationManager.class);

//        Создание канала уведомелние ( для Андрода 8 и выше)
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
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
            PendingIntent piCall=PendingIntent.getActivity(this,0,intentCall,0);
            PendingIntent piWriteMessage=PendingIntent.getActivity(this,1,intentWriteMessage,0);
            PendingIntent piCancel=PendingIntent.getActivity(this,2,intentCancel,0);
//Создание самого уведомления.
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANEL_ID)
                    .setTicker(resources.getString(R.string.new_event_title))
                    .setSmallIcon((R.drawable.ic_event_note_black_24dp))
                    .setContentTitle(resources.getString(R.string.new_event_title))
                    .setContentText(resources.getString(R.string.content_text_notify))
                    .setContentIntent(pi)
                    .setColor(Color.GREEN)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .addAction(R.mipmap.ic_launcher_round,"Позвонить",piCall)
                    .addAction(R.mipmap.ic_launcher_round,"Написать",piWriteMessage)
                    .addAction(R.mipmap.ic_launcher_round,"Отменить",piCancel);

            Log.d(TAG, "Канал создан и уведомление сработало");
            NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

           }
    }


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
    }
    public static boolean isServiceAlarmOn(Context context){
        Intent i=ReminderService.newIntent(context);
        PendingIntent pi=PendingIntent.getService(context,0,i,PendingIntent.FLAG_NO_CREATE);
        return pi !=null;
        }
    }
