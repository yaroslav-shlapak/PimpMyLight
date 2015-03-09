package com.example.yshlapak.lightremote.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.yshlapak.lightremote.R;
import com.example.yshlapak.lightremote.database.MainScreenValue;
import com.example.yshlapak.lightremote.database.MainScreenValueDataSource;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValue;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValueDataSource;
import com.example.yshlapak.lightremote.entities.Constants;
import com.example.yshlapak.lightremote.tcp.Client;
import com.example.yshlapak.lightremote.ui.LightImageButton;


public class MainActivity extends Activity {

    ProtocolSettingsValueDataSource protocolSettingsValueDataSource;
    MainScreenValueDataSource mainScreenValueDataSource;
    LightImageButton lightImageButton;
    LinearLayout mainLayout;
    Client client;
    int state;
    int level;
    ImageButton imageButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onResume() {
        super.onResume();

        initializeDB();
        initializeInstance();
        initializeTcp();
        initializeUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.ip_settings:
                Intent intent = new Intent(this, ProtocolSettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onPause() {
        super.onPause();

        mainScreenValueDataSource.updateValue(new MainScreenValue(1, lightImageButton.getState(), lightImageButton.getLevel()));
        protocolSettingsValueDataSource.close();
        mainScreenValueDataSource.close();
    }

    private void initializeUI() {
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(Integer.toString(level) + "%");
        lightImageButton = new LightImageButton(this, imageButton, state, level, client, textView);
    }

    private void initializeDB() {
        protocolSettingsValueDataSource = new ProtocolSettingsValueDataSource(this);
        mainScreenValueDataSource = new MainScreenValueDataSource(this);

        protocolSettingsValueDataSource.open();
        mainScreenValueDataSource.open();

        protocolSettingsValueDataSource.addValue(new ProtocolSettingsValue(1, Constants.DEFAULT_IP, Integer.parseInt(Constants.DEFAULT_PORT)));
        mainScreenValueDataSource.addValue(new MainScreenValue(1, 0, 0));
    }

    private void initializeTcp() {
        client = new Client(protocolSettingsValueDataSource.getValue(1).getIp(), protocolSettingsValueDataSource.getValue(1).getPort());
        Log.v("initializeTcp", "host = " + client.hostName + ", port = " + Integer.toString(client.portNumber));
    }

    private void initializeInstance() {
        state = mainScreenValueDataSource.getValue(1).getBulbState();
        level = mainScreenValueDataSource.getValue(1).getBulbLevel();
    }
}

