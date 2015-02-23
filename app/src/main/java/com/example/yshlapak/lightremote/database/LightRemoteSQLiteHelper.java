package com.example.yshlapak.lightremote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LightRemoteSQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "lightremote.db";
    private static final int DATABASE_VERSION = 1;
    public static final String MAIN_SCREEN_TABLE_NAME = "main_screen_table";
    public static final String PROTOCOL_SETTINGS_TABLE_NAME = "protocol_settings_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MAIN_SCREEN_BULB_STATE = "bulb_state";
    public static final String COLUMN_MAIN_SCREEN_BULB_LEVEL = "bulb_level";
    public static final String COLUMN_IP = "ip";
    public static final String COLUMN_PORT = "port";


    // Database creation sql statement
    private static final String MAIN_SCREEN_TABLE_CREATE = "create table "
            + MAIN_SCREEN_TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key, " + COLUMN_MAIN_SCREEN_BULB_STATE + " integer not null," + COLUMN_MAIN_SCREEN_BULB_LEVEL
            + " integer not null " + ");";

    private static final String PROTOCOL_SETTINGS_CREATE = "create table "
            + PROTOCOL_SETTINGS_TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key, " + COLUMN_IP + " text not null, " + COLUMN_PORT +  " integer not null" + ");";


    public LightRemoteSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(MAIN_SCREEN_TABLE_CREATE);
        database.execSQL(PROTOCOL_SETTINGS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LightRemoteSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + MAIN_SCREEN_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROTOCOL_SETTINGS_TABLE_NAME);
        onCreate(db);
    }

}