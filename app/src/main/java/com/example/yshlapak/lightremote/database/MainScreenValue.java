package com.example.yshlapak.lightremote.database;

public class MainScreenValue {


    private int id;
    private int bulbState;
    private int bulbLevel;

    public MainScreenValue(int id, int bulbState, int bulbLevel) {
        this.id = id;
        this.bulbState = bulbState;
        this.bulbLevel = bulbLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBulbState() {
        return bulbState;
    }

    public void setBulbState(int bulbState) {
        this.bulbState = bulbState;
    }

    public int getBulbLevel() {
        return bulbLevel;
    }

    public void setBulbLevel(int bulbLevel) {
        this.bulbLevel = bulbLevel;
    }


}
