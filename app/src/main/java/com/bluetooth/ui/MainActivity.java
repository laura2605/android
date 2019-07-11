package com.bluetooth.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.bluetooth.R;
import com.bluetooth.model.BluetoothHandler;
import com.bluetooth.model.WaterRequirements;

import java.io.IOException;

import static com.bluetooth.model.WaterRequirements.LITTLE;
import static com.bluetooth.model.WaterRequirements.MEDIUM;
import static com.bluetooth.model.WaterRequirements.MUCH;

public class MainActivity extends AppCompatActivity {

    private BluetoothHandler handler = BluetoothHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButton(R.id.fewWater, LITTLE);
        initButton(R.id.mediumWater, MEDIUM);
        initButton(R.id.muchWater, MUCH);
    }

    private void initButton(int id, WaterRequirements dataToSend) {

        Button button = this.findViewById(id);

        Handler sendDataHandler = new Handler();

        button.setOnClickListener(view -> {

            Thread sendDataThread = new Thread(() -> {
                try {
                    handler.send(dataToSend);
                }
                catch (IOException e) {
                    e.printStackTrace();

                    sendDataHandler.post(this::showConnectionError);
                }
            });
            sendDataThread.start();
        });
    }

    private void showConnectionError() {

        Toast.makeText(getApplicationContext(), R.string.connectionErrorText, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            handler.disconnect();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
