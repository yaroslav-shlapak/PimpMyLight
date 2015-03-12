package com.example.yshlapak.lightremote.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.yshlapak.lightremote.R;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValue;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValueDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Void on 12-Mar-15.
 */
public class WifiSettingsActivity extends Activity {
    private WifiManager mWifiManager;
    ArrayAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_settings);

        mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        mWifiManager.startScan();
        ListView listview = (ListView) findViewById(R.id.wifiListView);
        List<ScanResult> mScanResults = mWifiManager.getScanResults();
        List<String> namesList = new ArrayList<>();
        for(ScanResult e : mScanResults) {
            namesList.add(e.SSID.toString());
        }

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namesList);
        listview.setAdapter(mAdapter);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ip_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_undo:

                return true;
            case R.id.action_accept:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction() == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
                List<ScanResult> mScanResults = mWifiManager.getScanResults();
/*                mAdapter = new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_1, null,
                        mScanResults, toViews, 0);
                setListAdapter(mAdapter);*/
            }
        }
    };
}
