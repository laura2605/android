package com.bluetooth.ui;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluetooth.R;

import java.util.List;

public class ConnectDeviceAdapter extends RecyclerView.Adapter<ConnectDeviceAdapter.ConnectDeviceViewHolder> {

    private ConnectDeviceActivity activity;

    private List<BluetoothDevice> devices;

    public ConnectDeviceAdapter(ConnectDeviceActivity activity, List<BluetoothDevice> devices) {
        this.activity = activity;
        this.devices = devices;
    }

    @NonNull
    @Override
    public ConnectDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_device_name, parent, false);
        ConnectDeviceViewHolder holder = new ConnectDeviceViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectDeviceViewHolder holder, int position) {
        holder.deviceName.setText(devices.get(position).getName());

        holder.view.setOnClickListener(view -> {

            activity.connect(devices.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ConnectDeviceViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView deviceName;

        public ConnectDeviceViewHolder(View view) {
            super(view);

            this.view = view;
            deviceName = view.findViewById(R.id.deviceName);
        }
    }
}
