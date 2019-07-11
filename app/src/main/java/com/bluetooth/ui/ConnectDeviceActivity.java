package com.bluetooth.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bluetooth.R;
import com.bluetooth.model.BluetoothHandler;

import java.io.IOException;
import java.util.List;

public class ConnectDeviceActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;

    private BluetoothHandler handler = BluetoothHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_device);

        turnBluetoothOn();
    }

    private void turnBluetoothOn() {

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                initView();
            }
            else {
                showConnectionError(R.string.bluetoothDenied);
            }
        }
    }

    private void initView() {

        List<BluetoothDevice> devices = handler.getDevices();

        RecyclerView listOfDevices = findViewById(R.id.listOfDevices);

        listOfDevices.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listOfDevices.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new ConnectDeviceAdapter(this, devices);
        listOfDevices.setAdapter(mAdapter);
    }

    public void connect(BluetoothDevice device) {

        Intent intent = new Intent(this, MainActivity.class);

        Handler connectionHandler = new Handler();

        Thread connectionThread = new Thread(() -> {
            try {
                handler.connect(device);
                startActivity(intent);
            }
            catch (IOException e) {

                connectionHandler.post(() -> showConnectionError(R.string.connectionErrorText));
            }
        });
        connectionThread.start();
    }

    private void showConnectionError(int resId) {

        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }
}
