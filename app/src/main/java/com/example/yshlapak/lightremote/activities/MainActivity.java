package com.example.yshlapak.lightremote.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.yshlapak.lightremote.R;
import com.example.yshlapak.lightremote.database.MainScreenValue;
import com.example.yshlapak.lightremote.database.MainScreenValueDataSource;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValue;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValueDataSource;
import com.example.yshlapak.lightremote.entities.Constants;
import com.example.yshlapak.lightremote.ui.LightImageButton;


public class MainActivity extends ActionBarActivity {

    ProtocolSettingsValueDataSource protocolSettingsValueDataSource;
    MainScreenValueDataSource mainScreenValueDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeDB();
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
            case R.id.action_settings:
                Intent intent = new Intent(this, ProtocolSettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onPause() {
        super.onPause();

        protocolSettingsValueDataSource.close();
        mainScreenValueDataSource.close();

    }

    private void initializeUI() {
        LightImageButton lightImageButton = new LightImageButton(false);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setImageResource(lightImageButton.getCurrentImage());
        imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageButton.setOnClickListener(lightImageButton);
    }

    private void initializeDB() {
        protocolSettingsValueDataSource = new ProtocolSettingsValueDataSource(this);
        mainScreenValueDataSource = new MainScreenValueDataSource(this);

        protocolSettingsValueDataSource.open();
        mainScreenValueDataSource.open();

        protocolSettingsValueDataSource.addValue(new ProtocolSettingsValue(1, Constants.DEFAULT_IP, Constants.DEFAULT_PORT));
        mainScreenValueDataSource.addValue(new MainScreenValue(1, 0, 0));
    }
}
