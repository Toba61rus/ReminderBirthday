package ru.toba92.myapplication.database;

public class BirthdayDbSchema {

    public static final class BirthdayTable{
        public static final String NAME="birthday";

        public static final class Cols{
            public static final String UUID="uuid";
            public static final String DATE="date";
            public static final String RECEIVENOTIFY ="receivenotify";
            public static final String INFORMATION="information";
        }
    }
}