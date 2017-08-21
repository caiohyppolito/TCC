package com.example.caiogalvani.remotecontrolarduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.caiogalvani.remotecontrolarduino.adapters.DevicesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements DevicesAdapter.IDevicesAdapter{
    private final int TAG_REQUEST_BLUETOOTH = 1;

    private List<BluetoothDevice> mListDevicesConnected;
    private List<BluetoothDevice> mListDevicesAvailable;

    private DevicesAdapter mDevicesAdapterConnected;
    private DevicesAdapter mDevicesAdapterAvailable;

    private BluetoothAdapter mBluetoothAdapter;

    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        mBluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        this.unregisterReceiver(mReceiver);
    }

    private void initialize() {
        mListDevicesAvailable = new ArrayList<>();
        mListDevicesConnected = new ArrayList<>();

        setupBroadcastReceiver();
        setupBluetooth();
        setupToolbar();
        setupRecyclerView();
    }

    private void setupBroadcastReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                switch (action) {
                    case BluetoothDevice.ACTION_FOUND:
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        // Apenas insere na lista os dispositivos que nao estao pareados

                        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                            mListDevicesAvailable.add(device);

                            mDevicesAdapterAvailable.notifyDataSetChanged();
                        }
                        break;

                    case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:

                        break;
                }
            }
        };

        this.registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        this.registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    }

    private void setupBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            // DISPOSITIVO NAO TEM BLUETOOTH

            finish();
        } else if (!mBluetoothAdapter.isEnabled()) {
            // SOLICITAR QUE O USUARIO LIGUE O BLUETOOH
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, TAG_REQUEST_BLUETOOTH);
        } else {
            // BLUETOOTH LIGADO
            loadBondedDevices();
        }
    }

    private void loadBondedDevices() {
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();

        for(BluetoothDevice device : devices)
        {
            mListDevicesConnected.add(device);
        }

        if (mDevicesAdapterConnected != null) {
            mDevicesAdapterConnected.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAG_REQUEST_BLUETOOTH:
                if (mBluetoothAdapter.isEnabled()) {
                    // BLUETOOTH FOI LIGADO
                    loadBondedDevices();
                }
                break;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        setupRecyclerViewConnected();
        setupRecyclerViewAvailable();
    }

    private void setupRecyclerViewConnected() {
        RecyclerView recyclerViewConnected = (RecyclerView) findViewById(R.id.recyclerViewConnectDevices);
        recyclerViewConnected.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewConnected.setHasFixedSize(true);

        setupAdapterConnected(recyclerViewConnected);
    }

    private void setupRecyclerViewAvailable() {
        RecyclerView recyclerViewAvailable = (RecyclerView) findViewById(R.id.recyclerViewAvailableDevices);
        recyclerViewAvailable.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewAvailable.setHasFixedSize(true);

        setupAdapterAvailable(recyclerViewAvailable);
    }

    private void setupAdapterConnected(RecyclerView recyclerView) {
        mDevicesAdapterConnected = new DevicesAdapter(mListDevicesConnected, this);

        recyclerView.setAdapter(mDevicesAdapterConnected);
    }

    private void setupAdapterAvailable(RecyclerView recyclerView) {
        mDevicesAdapterAvailable = new DevicesAdapter(mListDevicesAvailable, this);

        recyclerView.setAdapter(mDevicesAdapterAvailable);
    }

    @Override
    public void OnClickDeviceListener(BluetoothDevice device) {
        if(device != null)
        {
            Intent intent = new Intent(getApplicationContext(), RemoteControlActivity.class);
            intent.putExtra(RemoteControlActivity.PUT_EXTRA_DEVICE, device);
            startActivity(intent);
        }
    }
}
