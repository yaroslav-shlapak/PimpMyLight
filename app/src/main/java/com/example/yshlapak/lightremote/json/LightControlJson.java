package com.example.yshlapak.lightremote.json;

import org.omg.CORBA.*;

import java.lang.Object;

/**
 * Created by y.shlapak on Feb 02, 2015.
 */
public class LightControlJson {
    public LightControlJson(boolean lightEnabled, int lightLevel) {
        this.lightEnabled = lightEnabled;
        this.lightLevel = lightLevel;
    }

    private boolean lightEnabled;

    public boolean isLightEnabled() {
        return lightEnabled;
    }

    public void setLightEnabled(boolean lightEnabled) {
        this.lightEnabled = lightEnabled;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
    }

    private int lightLevel;
    LightControlJson() {

    }

    @Override
    public boolean equals(Object object) {
        LightControlJson obj = (LightControlJson) object;
        if(obj.lightEnabled == this.lightEnabled && obj.lightLevel == this.lightLevel) {
            return true;
        } else {
            return false;
        }


    }


}
