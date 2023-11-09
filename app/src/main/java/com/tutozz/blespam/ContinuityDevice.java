package com.tutozz.blespam;

public class ContinuityDevice {
    public enum type {
        DEVICE, ACTION
    }
    private String value;
    private String name;
    private type deviceType;


    public ContinuityDevice(String value, String name, type deviceType) {
        this.value = value;
        this.name = name;
        this.deviceType = deviceType;
    }

    public String getValue() { return value; }
    public String getName() {
        return name;
    }
    public type getDeviceType(){ return deviceType; }
}
