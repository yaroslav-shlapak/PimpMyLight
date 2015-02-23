package com.example.yshlapak.lightremote.database;

/**
 * Created by y.shlapak on Dec 16, 2014.
 */
public class ProtocolSettingsValue {
    private int id;
    private String ip;
    private int port;

    public ProtocolSettingsValue(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public ProtocolSettingsValue(int id, String ip, int port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public ProtocolSettingsValue() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
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
