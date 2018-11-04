package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.wintercoding.wintercodingcalendar.listener.CustomGesture;
import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

public abstract class CalendarView extends View {
    int width = 0;
    float center = 0;
    float hinterval = 0;
    float vinterval = 0;

    final String[] DAYS = {"일", "월", "화", "수", "목", "금", "토"};
    protected Calendar today;
    protected Calendar selected;
    List<Schedule> schedules;
    final int[] numOfSchedules = new int[31];

    private GestureDetectorCompat gestureDetectorCompat = null;

    public CalendarView(Context context) {
        super(context);
        init();
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        today = Calendar.getInstance();
        selected = Calendar.getInstance();
        schedules = new ArrayList<>();
    }

    public abstract void movePrevious();

    public abstract void moveNext();

    public abstract boolean select(float x, float y);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /* UTILS */

    /**
     * @param year  년 입력
     * @param month 월 입력 (0-11)
     * @return 이번 달 1일의 요일을 반환
     */
    int getFirstDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @param year  년 입력
     * @param month 월 입력 (0-11)
     * @return 이번 달 마지막 날을 반환
     */
    int getLastDateOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, false);
        return calendar.get(Calendar.DATE);
    }

    /**
     * @param year  년 입력
     * @param month 현재 월 입력 (0-11)
     * @return 저번 달 마지막 날을 반환
     */
    int getLastDateOfLastMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.roll(Calendar.MONTH, false);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, false);
        return calendar.get(Calendar.DATE);
    }

    protected float dipToPx(float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getContext().getResources().getDisplayMetrics());
    }

    /* GETTER */
    public Calendar getSelected() {
        return selected;
    }

    public abstract void addSchedule(Schedule schedule);

    public void updateSchedules(List<Schedule> schedules) {
        this.schedules = schedules;

        for (int i = 0; i < 31; i++)
            numOfSchedules[i] = 0;

        for (Schedule schedule : schedules) {
            numOfSchedules[schedule.getDate() - 1]++;
        }
    }

    public void removeSchedule(Schedule schedule) {
        for (Schedule s : schedules) {
            if (s.getId() == schedule.getId()) {
                // 일정 삭제
                schedules.remove(s);
                numOfSchedules[schedule.getDate() - 1]--;
                break;
            }
        }
    }

    public void setOnSwipeGestureListener(CustomGesture listener) {
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }

}
