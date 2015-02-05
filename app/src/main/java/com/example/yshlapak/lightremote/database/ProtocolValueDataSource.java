
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
public class ProtocolValueDataSource extends ValueDataSource{
    private String[] allColumns = {SmartHouseSQLiteHelper.COLUMN_ID, SmartHouseSQLiteHelper.COLUMN_IP, SmartHouseSQLiteHelper.COLUMN_PORT, SmartHouseSQLiteHelper.COLUMN_PROTOCOL};
    public ProtocolValueDataSource(Context context) {
        super(context);
    }
    public void addValue(ProtocolValue protocolValue) {
        if (!isTableExisting(SmartHouseSQLiteHelper.UDP_TABLE_NAME, 1)) {
            ContentValues values = new ContentValues();
            values.put(SmartHouseSQLiteHelper.COLUMN_ID, protocolValue.getId());
            values.put(SmartHouseSQLiteHelper.COLUMN_IP, protocolValue.getIp());
            values.put(SmartHouseSQLiteHelper.COLUMN_PORT, protocolValue.getPort());
            values.put(SmartHouseSQLiteHelper.COLUMN_PROTOCOL, protocolValue.getProtocolType());

            long insertId = db.insert(SmartHouseSQLiteHelper.UDP_TABLE_NAME, null,
                    values);
            Cursor cursor = db.query(SmartHouseSQLiteHelper.UDP_TABLE_NAME,
                    allColumns, SmartHouseSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            cursor.close();
            db.close();
            Log.v("debug", "IP and port was added");
        }
    }
    public ProtocolValue getValue(int id) {
        Cursor cursor = db.query(SmartHouseSQLiteHelper.UDP_TABLE_NAME, allColumns,
                SmartHouseSQLiteHelper.COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        return new ProtocolValue(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
    }
    // Getting All Contacts
    public List<ProtocolValue> getAllValues() {
        List<ProtocolValue> protocolValuesList = new ArrayList<ProtocolValue>();
// Select All Query
        String selectQuery = "SELECT * FROM " + SmartHouseSQLiteHelper.COLUMN_BUTTON_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProtocolValue protocolValue = new ProtocolValue();
                protocolValue.setId(Integer.parseInt(cursor.getString(0)));
                protocolValue.setIp(cursor.getString(1));
                protocolValue.setPort(cursor.getString(2));
// Adding contact to list
                protocolValuesList.add(protocolValue);
            } while (cursor.moveToNext());
        }
// return contact list
        return protocolValuesList;
    }
    public int getValuesCount() {
        String countQuery = "select * from" + SmartHouseSQLiteHelper.BUTTONS_TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public int updateValue(ProtocolValue protocolValue) {
        ContentValues values = new ContentValues();
        values.put(SmartHouseSQLiteHelper.COLUMN_IP, protocolValue.getIp());
        values.put(SmartHouseSQLiteHelper.COLUMN_PORT, protocolValue.getPort());
        values.put(SmartHouseSQLiteHelper.COLUMN_PROTOCOL, protocolValue.getProtocolType());
// updating row
        return db.update(SmartHouseSQLiteHelper.UDP_TABLE_NAME, values, SmartHouseSQLiteHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(protocolValue.getId()) });
    }
    public void deleteValue(ProtocolValue protocolValue) {
        db.delete(SmartHouseSQLiteHelper.UDP_TABLE_NAME, SmartHouseSQLiteHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(protocolValue.getId()) });
        db.close();
    }
}