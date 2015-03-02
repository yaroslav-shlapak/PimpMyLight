package com.example.yshlapak.lightremote.auxilary;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GestureHelper implements View.OnTouchListener {

    private final GestureDetector mGestureDetector;
    private static View view;

    public GestureHelper(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureListener(this));
    }

    public void onSwipeRight() {
    };

    public void onSwipeLeft() {
    };

    public void onSwipeTop(float diff) {
    };

    public void onSwipeBottom(float diff) {
    };

    public void onDoubleTap() {
    };

    public void onClick(View v) {
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        view = v;
        return mGestureDetector.onTouchEvent(event);
    }

    private static final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 10;
        private static final int SWIPE_VELOCITY_THRESHOLD = 2;
        private GestureHelper mHelper;

        public GestureListener(GestureHelper helper) {
            mHelper = helper;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            mHelper.onClick(view);
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mHelper.onDoubleTap();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            mHelper.onSwipeRight();
                        } else {
                            mHelper.onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            mHelper.onSwipeBottom(diffY);
                        } else {
                            mHelper.onSwipeTop(diffY);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return result;
        }
    }

}
