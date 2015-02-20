package com.example.yshlapak.lightremote.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.yshlapak.lightremote.R;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValueDataSource;
import com.example.yshlapak.lightremote.database.ProtocolSettingsValue;

public class ProtocolSettingsActivity extends Activity {
	private ProtocolSettingsValueDataSource protocolSettingsValueDataSource;
	private EditText etIp, etPort;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_protocol_settings);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		etIp = (EditText) findViewById(R.id.ip_address);
		etPort = (EditText) findViewById(R.id.port_address);

        protocolSettingsValueDataSource = new ProtocolSettingsValueDataSource(this);
        protocolSettingsValueDataSource.open();

		String textIp = protocolSettingsValueDataSource.getValue(1).getIp();
		String textPort = protocolSettingsValueDataSource.getValue(1).getPort();

		etIp.setText(textIp);
		etPort.setText(textPort);
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_ip_settings, menu);
		return super.onCreateOptionsMenu(menu);
	}

    @Override
    protected void onPause() {
        super.onPause();
        protocolSettingsValueDataSource.close();
    }
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_undo:
                protocolSettingsValueDataSource.close();
	        	finish();
	            return true;
	        case R.id.action_accept:
                ProtocolSettingsValue protocolSettingsValue = new ProtocolSettingsValue(1, etIp.getText().toString(), etPort.getText().toString());
                protocolSettingsValueDataSource.updateValue(protocolSettingsValue);
                protocolSettingsValueDataSource.close();
	    		finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
