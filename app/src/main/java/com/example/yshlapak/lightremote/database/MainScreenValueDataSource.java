package com.example.yshlapak.lightremote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by y.shlapak on Dec 18, 2014.
 */
public class MainScreenValueDataSource extends ValueDataSource {

    public MainScreenValueDataSource(Context context) {
        super(context);
    }

    private final String[] allColumns = {LightRemoteSQLiteHelper.COLUMN_ID, LightRemoteSQLiteHelper.COLUMN_MAIN_SCREEN_BULB_STATE,
            LightRemoteSQLiteHelper.COLUMN_MAIN_SCREEN_BULB_LEVEL};

    public void addValue(MainScreenValue mainScreenValue) {

        if (!isTableExisting(LightRemoteSQLiteHelper.MAIN_SCREEN_TABLE_NAME, 1)) {
            ContentValues values = new ContentValues();
            values.put(LightRemoteSQLiteHelper.COLUMN_ID, mainScreenValue.getId());
            values.put(LightRemoteSQLiteHelper.COLUMN_MAIN_SCREEN_BULB_STATE, mainScreenValue.getBulbState());
            values.put(LightRemoteSQLiteHelper.COLUMN_MAIN_SCREEN_BULB_LEVEL, mainScreenValue.getBulbLevel());

            long insertId = db.insert(LightRemoteSQLiteHelper.MAIN_SCREEN_TABLE_NAME, null,
                    values);
            Cursor cursor = db.query(LightRemoteSQLiteHelper.MAIN_SCREEN_TABLE_NAME,
                    allColumns, LightRemoteSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            cursor.close();
        }
    }

    public MainScreenValue getValue(int id) {

        Cursor cursor = db.query(LightRemoteSQLiteHelper.MAIN_SCREEN_TABLE_NAME, allColumns,
                LightRemoteSQLiteHelper.COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        for(int i = 0; i < 4; i++)
        if (cursor != null) {
            return new MainScreenValue(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)));
        }
        return null;
    }

    // Getting All Contacts
    public List<MainScreenValue> getAllValues() {

        List<MainScreenValue> mainScreenValuesList = new ArrayList<MainScreenValue>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + LightRemoteSQLiteHelper.MAIN_SCREEN_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MainScreenValue mainScreenValue = new MainScreenValue(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                mainScreenValuesList.add(mainScreenValue);
            } while (cursor.moveToNext());
        }

        // return contact list
        return mainScreenValuesList;
    }

    public int getValuesCount() {
        String countQuery = "select * from" + LightRemoteSQLiteHelper.MAIN_SCREEN_TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateValue(MainScreenValue mainScreenValue) {

        ContentValues values = new ContentValues();
        values.put(LightRemoteSQLiteHelper.COLUMN_MAIN_SCREEN_BULB_STATE, mainScreenValue.getBulbState());
        values.put(LightRemoteSQLiteHelper.COLUMN_MAIN_SCREEN_BULB_LEVEL, mainScreenValue.getBulbLevel());

        // updating row
        return db.update(LightRemoteSQLiteHelper.MAIN_SCREEN_TABLE_NAME, values, LightRemoteSQLiteHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(mainScreenValue.getId()) });
    }

    public void deleteValue(MainScreenValue mainScreenValue) {
        db.delete(LightRemoteSQLiteHelper.MAIN_SCREEN_TABLE_NAME, LightRemoteSQLiteHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(mainScreenValue.getId()) });
        db.close();
    }

}
