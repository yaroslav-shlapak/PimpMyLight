package com.example.yshlapak.lightremote;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Void on 11-Feb-15.
 */
public class LightImageButton implements View.OnClickListener{
    private static final int bulbOnImg = R.drawable.bulb_on;
    private static final int bulbOffImg = R.drawable.bulb_off;
    private boolean state;
    private Context context;
    private int currentImage;

    public LightImageButton(boolean state, Context context) {
        this.state = state;
        this.context = context;
        currentImage = bulbOffImg;
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(int currentImage) {
        this.currentImage = currentImage;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


        @Override
        public void onClick(View v) {
            ImageButton btn = (ImageButton) v;
            if (state) {
                currentImage = bulbOnImg;
            } else {
                currentImage = bulbOffImg;
            }
            state = !state;
            btn.setBackground(context.getResources().getDrawable(currentImage));
        }
}

