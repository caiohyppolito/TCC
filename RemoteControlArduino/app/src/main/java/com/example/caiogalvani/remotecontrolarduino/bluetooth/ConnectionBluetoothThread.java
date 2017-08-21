package com.example.caiogalvani.remotecontrolarduino.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectionBluetoothThread extends Thread {
    private final static String UUID_CONNECTION = "00001101-0000-1000-8000-00805F9B34FB";

    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;

    private InputStream mInputStream;
    private OutputStream mOutputStream;

    public ConnectionBluetoothThread(){

    }

    public ConnectionBluetoothThread(BluetoothDevice device) throws IOException{
        mBluetoothDevice = device;

        setupConnection();
    }

    private void setDeviceConnection(BluetoothDevice device)throws IOException{
        mBluetoothDevice = device;

        setupConnection();
    }

    private void setupConnection() throws IOException{
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(UUID_CONNECTION));

            if (mBluetoothSocket != null) {
                mInputStream = mBluetoothSocket.getInputStream();
                mOutputStream = mBluetoothSocket.getOutputStream();
            }
    }

    @Override
    public void run() {

    }

    public void cancel() {
        try {
            mBluetoothSocket.close();
            mInputStream.close();
            mOutputStream.close();
        } catch (Exception e) {

        }
    }

    public void writeMessage(String message) throws IOException {
        if (message != null && mOutputStream != null && mBluetoothDevice != null) {
            mOutputStream.write(message.getBytes());
        }
    }
}