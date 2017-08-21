package com.example.caiogalvani.remotecontrolarduino;

import android.bluetooth.BluetoothDevice;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.caiogalvani.remotecontrolarduino.bluetooth.ConnectionBluetoothThread;

import java.io.IOException;

public class RemoteControlActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PUT_EXTRA_DEVICE = "put_extra_device";

    private BluetoothDevice mBluetoothDevice;

    private ConnectionBluetoothThread mConnectionBluetoothThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        initialize();
    }

    private void initialize() {

        mBluetoothDevice = getIntent().getParcelableExtra(PUT_EXTRA_DEVICE);

        if(mBluetoothDevice == null){
            finish();

            return;
        }

        try {
            mConnectionBluetoothThread = new ConnectionBluetoothThread(mBluetoothDevice);
        } catch (IOException e) {
            e.printStackTrace();

            finish();
        }

        ImageButton imageButtonTop = (ImageButton) findViewById(R.id.imageButtonTop);
        ImageButton imageButtonDown = (ImageButton) findViewById(R.id.imageButtonDown);
        ImageButton imageButtonLeft = (ImageButton) findViewById(R.id.imageButtonLeft);
        ImageButton imageButtonRight = (ImageButton) findViewById(R.id.imageButtonRight);

        Button buttonStop = (Button) findViewById(R.id.buttonStop);

        imageButtonTop.setOnClickListener(this);
        imageButtonDown.setOnClickListener(this);
        imageButtonLeft.setOnClickListener(this);
        imageButtonRight.setOnClickListener(this);

        buttonStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButtonTop:
                try {
                    mConnectionBluetoothThread.writeMessage("f");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.imageButtonDown:
                try {
                    mConnectionBluetoothThread.writeMessage("t");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.imageButtonLeft:
                try {
                    mConnectionBluetoothThread.writeMessage("e");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.imageButtonRight:
                try {
                    mConnectionBluetoothThread.writeMessage("d");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.buttonStop:
                try {
                    mConnectionBluetoothThread.writeMessage("p");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
