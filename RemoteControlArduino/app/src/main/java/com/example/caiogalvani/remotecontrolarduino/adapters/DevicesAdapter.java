package com.example.caiogalvani.remotecontrolarduino.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.caiogalvani.remotecontrolarduino.R;
import com.example.caiogalvani.remotecontrolarduino.adapters.view_holders.DevicesViewHolder;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesViewHolder> {
    private List<String> mListDevices;

    public DevicesAdapter(List<String> listDevices)
    {
        mListDevices = listDevices;
    }

    @Override
    public DevicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_devices, parent, false);

        DevicesViewHolder devicesViewHolder = new DevicesViewHolder(view);

        return devicesViewHolder;
    }

    @Override
    public void onBindViewHolder(DevicesViewHolder holder, int position) {
        String itemDevice = mListDevices.get(position);

        holder.textViewNameDevice.setText(itemDevice);

        holder.itemView.setTag(itemDevice);
    }

    @Override
    public int getItemCount() {
        return mListDevices != null ? mListDevices.size() : 0;
    }
}