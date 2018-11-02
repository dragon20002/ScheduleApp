package kr.co.wintercoding.wintercodingcalendar.listener;

import kr.co.wintercoding.wintercodingcalendar.view.CalendarView;

public abstract class BaseOnSwipeGestureListener implements CustomGesture.OnSwipeGestureListener {
    protected CalendarView view;

    public BaseOnSwipeGestureListener(CalendarView view) {
        this.view = view;
    }

    protected abstract void updateSchedules();

    @Override
    public void swipeUp() {
        view.moveNext();
        updateSchedules();
    }

    @Override
    public void swipeDown() {
        view.movePrevious();
        updateSchedules();
    }

    @Override
    public void tap(float x, float y) {
        if (view.select(x, y)) {
            updateSchedules();
        }
    }
}
