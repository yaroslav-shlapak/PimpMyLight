
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
public class ProtocolSettingsValueDataSource extends ValueDataSource{
    private String[] allColumns = {LightRemoteSQLiteHelper.COLUMN_ID, LightRemoteSQLiteHelper.COLUMN_IP, LightRemoteSQLiteHelper.COLUMN_PORT};
    public ProtocolSettingsValueDataSource(Context context) {
        super(context);
    }
    public void addValue(ProtocolSettingsValue protocolSettingsValue) {
        if (!isTableExisting(LightRemoteSQLiteHelper.PROTOCOL_SETTINGS_TABLE_NAME, 1)) {
            ContentValues values = new ContentValues();
            values.put(LightRemoteSQLiteHelper.COLUMN_ID, protocolSettingsValue.getId());
            values.put(LightRemoteSQLiteHelper.COLUMN_IP, protocolSettingsValue.getIp());
            values.put(LightRemoteSQLiteHelper.COLUMN_PORT, protocolSettingsValue.getPort());

            long insertId = db.insert(LightRemoteSQLiteHelper.PROTOCOL_SETTINGS_TABLE_NAME, null,
                    values);
            Cursor cursor = db.query(LightRemoteSQLiteHelper.PROTOCOL_SETTINGS_TABLE_NAME,
                    allColumns, LightRemoteSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            cursor.close();
            db.close();
            Log.v("debug", "IP and port was added");
        }
    }
    public ProtocolSettingsValue getValue(int id) {
        Cursor cursor = db.query(LightRemoteSQLiteHelper.PROTOCOL_SETTINGS_TABLE_NAME, allColumns,
                LightRemoteSQLiteHelper.COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        return new ProtocolSettingsValue(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
    }
    // Getting All Contacts
    public List<ProtocolSettingsValue> getAllValues() {
        List<ProtocolSettingsValue> protocolSettingsValuesList = new ArrayList<ProtocolSettingsValue>();
// Select All Query
        String selectQuery = "SELECT * FROM " + LightRemoteSQLiteHelper.PROTOCOL_SETTINGS_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProtocolSettingsValue protocolSettingsValue = new ProtocolSettingsValue();
                protocolSettingsValue.setId(Integer.parseInt(cursor.getString(0)));
                protocolSettingsValue.setIp(cursor.getString(1));
                protocolSettingsValue.setPort(cursor.getString(2));
// Adding contact to list
                protocolSettingsValuesList.add(protocolSettingsValue);
            } while (cursor.moveToNext());
        }
// return contact list
        return protocolSettingsValuesList;
    }
    public int getValuesCount() {
        String countQuery = "select * from" + LightRemoteSQLiteHelper.PROTOCOL_SETTINGS_TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public int updateValue(ProtocolSettingsValue protocolSettingsValue) {
        ContentValues values = new ContentValues();
        values.put(LightRemoteSQLiteHelper.COLUMN_IP, protocolSettingsValue.getIp());
        values.put(LightRemoteSQLiteHelper.COLUMN_PORT, protocolSettingsValue.getPort());
// updating row
        return db.update(LightRemoteSQLiteHelper.PROTOCOL_SETTINGS_TABLE_NAME, values, LightRemoteSQLiteHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(protocolSettingsValue.getId()) });
    }
    public void deleteValue(ProtocolSettingsValue protocolSettingsValue) {
        db.delete(LightRemoteSQLiteHelper.PROTOCOL_SETTINGS_TABLE_NAME, LightRemoteSQLiteHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(protocolSettingsValue.getId()) });
        db.close();
    }
}