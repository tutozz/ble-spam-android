package com.tutozz.blespam;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;

public class BluetoothAdvertiser {
    private final BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private AdvertiseSettings settings;
    private AdvertiseCallback advertiseCallback;
    private boolean ready;

    public BluetoothAdvertiser() {
        // Initialize BluetoothAdapter and BluetoothLeAdvertiser
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();

        // Check if Bluetooth is supported and enabled
        if(!mBluetoothAdapter.isEnabled()){
            // Handle the case where Bluetooth id not enabled
            return;
        }

        // Create AdvertiseSettings
        settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .setConnectable(false)
                .setTimeout(0)
                .build();

        // Create AdvertiseCallback
        advertiseCallback = new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                super.onStartSuccess(settingsInEffect);
                // Advertising started successfully

            }

            @Override
            public void onStartFailure(int errorCode) {
                super.onStartFailure(errorCode);
                // Handle advertising start failure

            }
        };
        ready = true;
    }



    @SuppressLint("MissingPermission")
    public void advertise(AdvertiseData data, AdvertiseData scanResponse) {
        if(ready){
            mBluetoothLeAdvertiser.startAdvertising(settings, data, scanResponse, advertiseCallback);
        }else{
            System.out.println("Failed: Not Ready");
        }
    }

    @SuppressLint("MissingPermission")
    public void stopAdvertising() {
        if (mBluetoothLeAdvertiser != null && advertiseCallback != null) {
            mBluetoothLeAdvertiser.stopAdvertising(advertiseCallback);
            System.out.println("Advertising stopped");
        }
    }


}