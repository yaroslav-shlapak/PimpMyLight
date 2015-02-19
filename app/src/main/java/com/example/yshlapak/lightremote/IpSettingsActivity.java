package com.example.yshlapak.lightremote;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.house.smart.remote.database.ProtocolValue;
import com.house.smart.remote.database.ProtocolValueDataSource;

public class IpSettingsActivity extends Activity {
	private ProtocolValueDataSource protocolValueDataSource;
	private EditText etIp, etPort;
    private RadioGroup radioGroupProtocol;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ip_settings);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		etIp = (EditText) findViewById(R.id.ip_address);
		etPort = (EditText) findViewById(R.id.port_address);
        radioGroupProtocol = (RadioGroup) findViewById(R.id.radioGroupProtocol);

        protocolValueDataSource = new ProtocolValueDataSource(this);
        protocolValueDataSource.open();

        Log.v("IpSettings", "database opened");
		String textIp = protocolValueDataSource.getValue(1).getIp();
		String textPort = protocolValueDataSource.getValue(1).getPort();
        String protocolName = protocolValueDataSource.getValue(1).getProtocolType();
        setRadioGroup(protocolName);

        Log.v("IpSettings", "database read");
		
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
        protocolValueDataSource.close();
    }
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_undo:
                protocolValueDataSource.close();
	        	finish();
	            return true;
	        case R.id.action_accept:
                String protocolName = getProtocolName(radioGroupProtocol.getCheckedRadioButtonId());
                ProtocolValue protocolValue = new ProtocolValue(1, etIp.getText().toString(), etPort.getText().toString(), protocolName);
                protocolValueDataSource.updateValue(protocolValue);
                protocolValueDataSource.close();
	    		finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

    private void setRadioGroup(String protocolName) {
        switch (protocolName) {
            case Constants.PROTOCOL_UDP:
                radioGroupProtocol.check(R.id.radioButtonUdp);
                break;
            case Constants.PROTOCOL_TCP:
                radioGroupProtocol.check(R.id.radioButtonTcp);
                break;
        }
    }

    private String getProtocolName(int checkedId) {
        String protocolName;
        switch (checkedId) {
            case R.id.radioButtonUdp:
                protocolName = Constants.PROTOCOL_UDP;
                break;
            case R.id.radioButtonTcp:
                protocolName = Constants.PROTOCOL_TCP;
                break;
            default:
                protocolName = Constants.DEFAULT_PROTOCOL_TYPE;
        }
        return protocolName;
    }
}
