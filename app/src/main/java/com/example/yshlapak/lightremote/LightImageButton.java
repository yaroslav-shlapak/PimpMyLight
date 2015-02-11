package com.example.yshlapak.lightremote;

import android.content.Context;
import android.view.View;
import android.widget.Button;

/**
 * Created by Void on 11-Feb-15.
 */
public class LightImageButton {
    private static final int bulbOnImg = R.drawable.bulb_on;
    private static final int bulbOffImg = R.drawable.bulb_off;
    private final View.OnClickListener onClickListener;
    private boolean state;
    private Context context;
    private int currentImage;

    public LightImageButton(boolean state, Context context) {
        this.state = state;
        this.context = context;
        onClickListener = new MyOnclickListener();
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

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    class MyOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            if (state) {
                currentImage = bulbOnImg;
            } else {
                currentImage = bulbOffImg;
            }
            state = !state;
            btn.setBackground(context.getResources().getDrawable(currentImage));
        }
    }
}

