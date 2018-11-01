package kr.co.wintercoding.wintercodingcalendar.view;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class CustomGesture extends GestureDetector.SimpleOnGestureListener {
    public interface OnSwipeGestureListener {
        void swipeUp();

        void swipeDown();

        void tap(float x, float y);
    }

    private OnSwipeGestureListener listener;

    public CustomGesture(OnSwipeGestureListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float dy = e1.getY() - e2.getY();

        float absdy = Math.abs(dy);
        if (absdy > 100 && absdy < 1000) {
            if (dy > 0)
                listener.swipeUp();
            else
                listener.swipeDown();
        }

        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        listener.tap(e.getX(), e.getY());
        return true;
    }
}
