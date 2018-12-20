package com.karan.virtualcr;

import android.provider.BaseColumns;

public class Groupdatabase {
    private Groupdatabase(){}
    public  static class Grouptabledata implements BaseColumns
    {

        public static final String TABLE_NAME = "Groups_detail";
        public static final String COLOUMN1 = "Groups_Name";
        public static final String COLOUMN2 = "Groups_Logo_url";
        public static final String COLOUMN3 = "Groups_Last_chat";
        public static final String COLOUMN4 = "Groups_Last_chat_time";


    }
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Grouptabledata.TABLE_NAME + " (" +
                    Grouptabledata._ID + " INTEGER PRIMARY KEY," +
                    Grouptabledata.COLOUMN1 + " TEXT," +
                    Grouptabledata.COLOUMN2 + " TEXT," +
                    Grouptabledata.COLOUMN3 + " TEXT," +
                    Grouptabledata.COLOUMN4 + " TEXT )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Grouptabledata.TABLE_NAME;
}
