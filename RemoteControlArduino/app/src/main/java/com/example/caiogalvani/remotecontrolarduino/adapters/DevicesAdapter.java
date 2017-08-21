package com.example.caiogalvani.remotecontrolarduino.adapters;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.caiogalvani.remotecontrolarduino.R;
import com.example.caiogalvani.remotecontrolarduino.adapters.view_holders.DevicesViewHolder;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesViewHolder> {
    public interface IDevicesAdapter{
        void OnClickDeviceListener(BluetoothDevice device);
    }

    private List<BluetoothDevice> mListDevices;
    private IDevicesAdapter mDevicesAdapter;

    public DevicesAdapter(List<BluetoothDevice> listDevices, IDevicesAdapter devicesAdapter)
    {
        mListDevices = listDevices;
        mDevicesAdapter = devicesAdapter;
    }

    @Override
    public DevicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_devices, parent, false);

        DevicesViewHolder devicesViewHolder = new DevicesViewHolder(view);

        devicesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDevicesAdapter != null)
                {
                    BluetoothDevice device = (BluetoothDevice) view.getTag();

                    mDevicesAdapter.OnClickDeviceListener(device);
                }
            }
        });

        return devicesViewHolder;
    }

    @Override
    public void onBindViewHolder(DevicesViewHolder holder, int position) {
        BluetoothDevice itemDevice = mListDevices.get(position);

        holder.textViewNameDevice.setText(itemDevice.getName());

        holder.itemView.setTag(itemDevice);
    }

    @Override
    public int getItemCount() {
        return mListDevices != null ? mListDevices.size() : 0;
    }
}