package com.example.yshlapak.lightremote.auxilary;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GestureHelper implements View.OnTouchListener {

    private final GestureDetector mGestureDetector;

    public GestureHelper(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureListener(this));
    }


    public void onScroll(float distanceX, float distanceY) {

    }


    public void onClick() {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private static final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private GestureHelper mHelper;

        public GestureListener(GestureHelper helper) {
            mHelper = helper;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mHelper.onScroll(distanceX, distanceY);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            mHelper.onClick();
            return true;
        }


    }

}
