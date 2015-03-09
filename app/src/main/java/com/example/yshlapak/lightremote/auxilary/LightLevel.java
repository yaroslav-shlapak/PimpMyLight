package com.example.yshlapak.lightremote.auxilary;

/**
 * Created by Void on 10-Mar-15.
 */
public class LightLevel {
    private static final int hi = 100;
    private static final int lo = 0;
    private int level;

    public LightLevel(int level) {
        setLevel(level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level >= hi) {
            this.level = hi;
        } else if (level <= lo) {
            this.level = lo;
        } else {
            this.level = level;
        }
    }

}