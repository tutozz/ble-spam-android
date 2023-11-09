package com.tutozz.blespam;

public class EasySetupDevice {
    public enum type { BUDS, WATCH }
    private final String value;
    private final String name;
    private final type deviceType;

    public EasySetupDevice(String value, String name, type deviceType) {
        this.value = value;
        this.name = name;
        this.deviceType = deviceType;
    }

    public String getValue() {
        if(deviceType == type.WATCH){
            return "010002000101FF000043" + value.substring(2);
        }else if(deviceType == type.BUDS){
            return "42098102141503210109" + value.substring(2,6) + "01" + value.substring(6) + "063C948E00000000C700";
        }else{
            return value;
        }
    }

    public String getName() { return name; }
    public type getDeviceType(){
        return deviceType;
    }
}

