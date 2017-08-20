package com.example.caiogalvani.remotecontrolarduino.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.caiogalvani.remotecontrolarduino.R;

public class DevicesViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewNameDevice;

    public DevicesViewHolder(View itemView) {
        super(itemView);

        textViewNameDevice = (TextView) itemView.findViewById(R.id.textViewNameDevice);
    }
}
