package com.example.yshlapak.lightremote.database;

/**
 * Created by y.shlapak on Dec 16, 2014.
 */
public class ProtocolSettingsValue {
    private int id;
    private String ip;
    private String port;

    public ProtocolSettingsValue(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }


    public ProtocolSettingsValue(int id, String ip, String port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public ProtocolSettingsValue() {
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
