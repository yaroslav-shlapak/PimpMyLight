package com.example.yshlapak.lightremote.database;

/**
 * Created by y.shlapak on Dec 16, 2014.
 */
public class ProtocolValue {
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

    public ProtocolValue(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    private int id;

    public ProtocolValue(int id, String ip, String port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public ProtocolValue() {
    }

    private String ip;
    private String port;

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public ProtocolValue(int id, String ip, String port, String protocolType) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.protocolType = protocolType;
    }

    private String protocolType;
}
