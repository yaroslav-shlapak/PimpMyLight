package com.example.yshlapak.lightremote.entities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.house.smart.remote.database.ButtonValue;
import com.house.smart.remote.database.ButtonValueDataSource;
import com.house.smart.remote.database.ProtocolValue;
import com.house.smart.remote.database.ProtocolValueDataSource;
import com.house.smart.remote.tcp.SendToByTcpActivity;
import com.house.smart.remote.udp.SendToUriActivity;
import com.house.smart.remote.ui.SmartHouseButtons;
import com.house.smart.remote.ui.SmartHouseButtonsAdapter;

public class MainActivity extends Activity {
    ProtocolValueDataSource protocolValueDataSource;
    ButtonValueDataSource buttonValueDataSource;
    private Context context;
    private SharedPreferences sharedPrefIp, sharedPrefPort;
    private Toast currentToast;
    private SmartHouseButtonsAdapter buttonsAdapter;

    private OnClickListener buttonOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

/*            if (!isWifiConnected()) {
                showShortToast(Constants.WIFI_DISCONNECTED_MESSAGE);
                return;
            }*/


            SmartHouseButtons btn = (SmartHouseButtons) v.getTag();
            protocolValueDataSource.open();
            String textIp = protocolValueDataSource.getValue(1).getIp();
            String textPort = protocolValueDataSource.getValue(1).getPort();
            String protocolType = protocolValueDataSource.getValue(1).getProtocolType();
            protocolValueDataSource.close();

            Log.v("OnClickListener", "before hex logic");

            buttonValueDataSource.open();
            Log.v("OnClickListener", "reopen database");
            String dataText = buttonValueDataSource.getButtonValue(btn.getId()).getButtonString();
            if(buttonValueDataSource.getButtonValue(btn.getId()).getButtonHexOption() == 1) {
                dataText += buttonValueDataSource.getButtonValue(btn.getId()).getButtonHexValue();
                String newDataText = "";

                for(int i = 0; i < (dataText.length() - 1); i++) {
                    if(dataText.charAt(i) == '\\') {
                        if(dataText.charAt(i+1) == 'r') {
                            newDataText += '\r';
                        } else if(dataText.charAt(i+1) == 'n') {
                            newDataText += '\n';
                        } else {
                            newDataText += '\\';
                        }
                        i++;
                    }
                    else {
                        newDataText += dataText.charAt(i);
                    }
                }
                dataText = newDataText;
            }

            Log.v("OnClickListener", "before database closing");
            buttonValueDataSource.close();

            Log.v("OnClickListener", "data was received from database");

            if (!textIp.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
                showShortToast(Constants.INVALID_IP_ERROR_MESSAGE);
                return;
            }

            if (!textPort.matches("^(6553[0-5]|655[0-2]\\d|65[0-4]\\d\\d|6[0-4]\\d{3}|[1-5]\\d{4}|[1-9]\\d{0,3}|0)$")) {
                showShortToast(Constants.INVALID_PORT_ERROR_MESSAGE);
                return;
            }

            String dataHex = "";
            if (dataText.length() < 1 && dataHex.length() < 2) {
                showShortToast(Constants.SENDING_CONTENT_ERROR_MESSAGE);
                return;
            }
            String uriString = "udp://" + textIp + ":" + textPort + "/";
            if (dataHex.length() >= 2) {
                uriString += Uri.encode("0x" + dataHex);
            } else {
                uriString += Uri.encode(dataText);
            }
            Uri uri = Uri.parse(uriString);



            switch (protocolType) {
                case Constants.PROTOCOL_UDP:
                    sendByUdp(uri);
                    break;
                case Constants.PROTOCOL_TCP:

                    sendByTcp(uri);
                    break;
                default:

            }


        }
    };

    private OnLongClickListener buttonOnLongClickListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub
            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            vibrator.vibrate(50);
            Intent intent = new Intent(getApplicationContext(), ButtonsSettingsActivity.class);
            SmartHouseButtons btn = (SmartHouseButtons) v.getTag();
            Log.v("onLongClick", "btn was initialized");
            intent.putExtra(Constants.BUTTON_ID, btn.getId());
            Log.v("onLongClick", "putExtra");
            startActivity(intent);
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();
        createButtons();
    }

    public void onResume() {
        super.onResume();
        buttonsAdapter.notifyDataSetChanged();
//        findViewById(R.id.grdButtons).invalidate();
    }

    @Override
    protected void onPause() {
        super.onPause();

        protocolValueDataSource.close();
        buttonValueDataSource.close();

    }

    private void initDatabase() {
        protocolValueDataSource = new ProtocolValueDataSource(this);
        buttonValueDataSource = new ButtonValueDataSource(this);

        protocolValueDataSource.open();
        buttonValueDataSource.open();


        for (SmartHouseButtons btn : SmartHouseButtons.values())
            buttonValueDataSource.addButtonValue(new ButtonValue(btn));

        protocolValueDataSource.addValue(new ProtocolValue(1, Constants.DEFAULT_IP, Constants.DEFAULT_PORT, Constants.DEFAULT_PROTOCOL_TYPE));

    }

    private void createButtons() {
        // TODO Auto-generated method stub
        buttonsAdapter = new SmartHouseButtonsAdapter(this);
        Log.v("MainActivity", "SmartHouseButtonsAdapter was created");
        GridView keypadGrid = (GridView) findViewById(R.id.grdButtons);
        Log.v("MainActivity", "keypadGrid was initialized");

        keypadGrid.setAdapter(buttonsAdapter);
        Log.v("MainActivity", "buttonsAdapter was set");

        for (SmartHouseButtons btn : SmartHouseButtons.values())
            buttonValueDataSource.getButtonValue(btn.getId()).getButtonName();

        Log.v("MainActivity", "Buttons was added");

        buttonsAdapter.setButtonOnClickListener(buttonOnClickListener);
        buttonsAdapter.setButtonOnLongClickListener(buttonOnLongClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.ip_settings:
                openIpSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openIpSettings() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, IpSettingsActivity.class);
        startActivity(intent);
    }

    private void sendByUdp(Uri uri) {
        Intent intent = new Intent(getApplicationContext(), SendToUriActivity.class);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        //intent.addCategory(Intent.CATEGORY_DEFAULT);
        Log.v("UDPsend", "before starting intent");
        startActivity(intent);
    }

    private boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return mWifi.isConnected();
    }

    void showShortToast(String text) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        currentToast.show();

    }

    private void sendByTcp(Uri uri) {
        Intent intent = new Intent(getApplicationContext(), SendToByTcpActivity.class);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        //intent.addCategory(Intent.CATEGORY_DEFAULT);
        Log.v("TCPsend", "before starting intent");
        startActivity(intent);
    }

}
