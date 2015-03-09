package com.example.yshlapak.lightremote.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yshlapak.lightremote.R;
import com.example.yshlapak.lightremote.auxilary.GestureHelper;
import com.example.yshlapak.lightremote.auxilary.LightLevel;
import com.example.yshlapak.lightremote.json.LightControlJson;
import com.example.yshlapak.lightremote.tcp.Client;

/**
 * Created by Void on 11-Feb-15.
 */
public class LightImageButton {
    public static final int bulbOnImg = R.drawable.bulb_on;
    public static final int bulbOffImg = R.drawable.bulb_off;
    Client client;
    GestureHelper gestureHelper;
    private int state;
    private LightLevel level;
    private int currentImage;
    private ImageButton imageButton;
    TextView textView;

    public LightImageButton(Context context, ImageButton imageButton, int state, int level, Client client, TextView textView) {
        update(context, imageButton, state, level, client, textView);

        imageButton.setImageResource(getCurrentImage());
        imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageButton.setOnTouchListener(gestureHelper);
        setMainLayoutBackgroundColor();
    }

    private void update(Context context, ImageButton imageButton, int state, int level, Client client, TextView textView) {
        this.textView = textView;
        this.state = state;
        this.client = client;
        this.level = new LightLevel(level);
        if (state == 1) {
            setCurrentImage(bulbOnImg);
        } else {
            setCurrentImage(bulbOffImg);
        }
        this.imageButton = imageButton;
        gestureHelper = new MyGestureHelper(context);
    }

    public int getLevel() {
        return level.getLevel();
    }

    public void setLevel(int level) {
        this.level.setLevel(level);
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(int currentImage) {
        this.currentImage = currentImage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private void setMainLayoutBackgroundColor() {
        if (state == 0) {
            setColor("#FF000000");
        } else {
            int step = 100;
            String baseString = Integer.toHexString(step + level.getLevel());
            String hexValue = "#FF" + baseString + baseString + baseString;
            setColor(hexValue);
        }
    }

    private void setColor(String color) {
        imageButton.setBackgroundColor(Color.parseColor(color));
        textView.setBackgroundColor(Color.parseColor(color));
    }

    private class MyGestureHelper extends GestureHelper {

        public MyGestureHelper(Context context) {
            super(context);
        }

        @Override
        public void onScroll(float distanceX, float distanceY) {
            super.onScroll(distanceX, distanceY);
            if (state == 1) {
                Log.v("xy", Double.toString(distanceY));
                level.setLevel(level.getLevel() + (int) distanceY / 3);
                LightControlJson json = new LightControlJson(state != 0 ? true : false, level.getLevel());
                client.send(json);
                setMainLayoutBackgroundColor();
                textView.setText(Integer.toString(level.getLevel()) + "%");
            }
        }

        @Override
        public void onClick() {
            super.onClick();
            switch (state) {
                case 0:
                    setCurrentImage(bulbOnImg);
                    state = 1;
                    break;
                case 1:
                    setCurrentImage(bulbOffImg);
                    state = 0;
                    break;
            }
            imageButton.setImageResource(getCurrentImage());
            imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            setMainLayoutBackgroundColor();
            LightControlJson json = new LightControlJson(state != 0 ? true : false, level.getLevel());
            client.send(json);
        }
    }
}

