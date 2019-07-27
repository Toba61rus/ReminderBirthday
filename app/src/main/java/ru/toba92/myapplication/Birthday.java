package ru.toba92.myapplication;

import java.util.Date;
import java.util.UUID;

//Класс-модель для нашего приложения.

public class Birthday {

    private UUID mId;
    private Date mDate;

    public boolean isReceiveNotify() {
        return mReceiveNotify;
    }

    public void setReceiveNotify(boolean receiveNotify) {
        mReceiveNotify = receiveNotify;
    }

    private boolean mReceiveNotify;

    public UUID getId() {
        return mId;
    }



    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getInformation() {
        return mInformation;
    }

    public void setInformation(String information) {
        mInformation = information;
    }

    private String mInformation;

    public Birthday(){

        mId=UUID.randomUUID();
        mDate=new Date();
    }

}
