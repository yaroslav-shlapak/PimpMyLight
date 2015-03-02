package com.example.yshlapak.lightremote.auxilary;

import com.example.yshlapak.lightremote.json.LightControlJson;

/**
 * Created by y.shlapak on Mar 02, 2015.
 */
public class LightLevel {
    private static final int hi = 100;
    private static final int lo = 0;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(level >= hi) {
            this.level = hi;
        } else if(level <= lo) {
            this.level = lo;
        } else {
            this.level = level;
        }
    }

    private int level;

    public LightLevel() {
        level = lo;
    }


}
