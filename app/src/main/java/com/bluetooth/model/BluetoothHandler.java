package com.bluetooth.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothHandler {

    private static final UUID UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static BluetoothHandler instance;

    private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    private BluetoothSocket connection;

    private BluetoothHandler() {

    }

    public static BluetoothHandler getInstance() {

        if(instance == null) {
            instance = new BluetoothHandler();
        }
        return instance;
    }

    public void connect(BluetoothDevice device) throws IOException {

        if(adapter != null && adapter.isEnabled()) {

            connection = device.createRfcommSocketToServiceRecord(UUID);
            connection.connect();
        }
    }

    /**
     * Sends the value to the connected bluetooth device.
     *
     * @param value of water requirements
     * @throws IOException
     */
    public synchronized void send(WaterRequirements value) throws IOException {

        if(connection != null) {
            connection.getOutputStream().write(value.getValueToSend().getBytes());
        }
    }

    public void disconnect() throws IOException {

        if(connection != null && connection.isConnected()) {
            connection.close();
        }
    }

    public List<BluetoothDevice> getDevices() {

        return new ArrayList<>(adapter.getBondedDevices());
    }
}
