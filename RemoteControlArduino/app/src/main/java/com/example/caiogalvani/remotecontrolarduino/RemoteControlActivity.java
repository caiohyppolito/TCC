package com.example.caiogalvani.remotecontrolarduino;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.caiogalvani.remotecontrolarduino.bluetooth.ConnectionBluetoothThread;

import java.io.IOException;

public class RemoteControlActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

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

        imageButtonTop.setOnTouchListener(this);
        imageButtonDown.setOnTouchListener(this);
        imageButtonLeft.setOnTouchListener(this);
        imageButtonRight.setOnTouchListener(this);

        buttonStop.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        String message = null;

        switch (view.getId()) {
            case R.id.imageButtonTop:
                message = "f";
                break;

            case R.id.imageButtonDown:
                message = "t";
                break;

            case R.id.imageButtonLeft:
                message = "e";
                break;

            case R.id.imageButtonRight:
                message = "d";
                break;
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            message = "p"; // TIROU O DEDO DO BOTÃO. PARAR O ROBO
        }

        try {
            mConnectionBluetoothThread.writeMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
