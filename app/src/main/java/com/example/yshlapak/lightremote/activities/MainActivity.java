package com.example.yshlapak.lightremote.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.yshlapak.lightremote.R;
import com.example.yshlapak.lightremote.database.MainScreenValue;
import com.example.yshlapak.lightremote.database.MainScreenValueDataSource;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValue;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValueDataSource;
import com.example.yshlapak.lightremote.entities.Constants;
import com.example.yshlapak.lightremote.json.LightControlJson;
import com.example.yshlapak.lightremote.tcp.Client;
import com.example.yshlapak.lightremote.ui.LightImageButton;


public class MainActivity extends Activity {

    ProtocolSettingsValueDataSource protocolSettingsValueDataSource;
    MainScreenValueDataSource mainScreenValueDataSource;
    LightImageButton lightImageButton;
    LightButtonOnClickListener lightButtonOnClickListener;
    Client client;
    int state;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    protected void onResume() {
        super.onResume();

        initializeDB();
        initializeInstance();
        initializeUI();
        initializeTcp();
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

        mainScreenValueDataSource.updateValue(new MainScreenValue(1, state, level));
        protocolSettingsValueDataSource.close();
        mainScreenValueDataSource.close();

    }

    private void initializeUI() {
        lightImageButton = new LightImageButton(state == 1 ? true : false);
        lightButtonOnClickListener = new LightButtonOnClickListener();
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setImageResource(lightImageButton.getCurrentImage());
        imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageButton.setOnClickListener(lightButtonOnClickListener);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new LightButtonOnSeekBarChangeListener());
        seekBar.setProgress(level);
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

    private class LightButtonOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            ImageButton btn = (ImageButton) v;
            //int tempState = state;
            switch(state) {
                case 0:
                    lightImageButton.setCurrentImage(lightImageButton.bulbOnImg);
                    state = 1;
                    break;
                case 1:
                    lightImageButton.setCurrentImage(lightImageButton.bulbOffImg);
                    state = 0;
                    break;
            }
            lightImageButton.setState(!lightImageButton.isState());
            btn.setImageResource(lightImageButton.getCurrentImage());
            btn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            LightControlJson json = new LightControlJson(state != 0 ? true : false, level);
            client.send(json);
        }
    }

    private class LightButtonOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
                level = seekBar.getProgress();
                LightControlJson json = new LightControlJson(state != 0 ? true : false, level);
                client.send(json);
        }
    }
}
