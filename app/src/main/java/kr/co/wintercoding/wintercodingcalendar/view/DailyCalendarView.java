package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Calendar;
import java.util.Locale;

import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

public class DailyCalendarView extends CalendarView {

    public DailyCalendarView(Context context) {
        super(context);
    }

    public DailyCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DailyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void movePrevious() {
        if (selected.get(Calendar.DATE) == 1) {
            selected.roll(Calendar.MONTH, false);
            if (selected.get(Calendar.MONTH) == 11)
                selected.roll(Calendar.YEAR, false);
        }
        selected.roll(Calendar.DATE, false);
    }

    @Override
    public void moveNext() {
        selected.roll(Calendar.DATE, true);
        if (selected.get(Calendar.DATE) == 1) {
            selected.roll(Calendar.MONTH, true);
            if (selected.get(Calendar.MONTH) == 0)
                selected.roll(Calendar.YEAR, true);
        }
    }

    @Override
    public boolean select(float x, float y) {
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (width == 0) {
            width = getWidth();
            center = width / 2;
            hinterval = width / 7;
            vinterval = width / 7;
        }

        // month
        int year = selected.get(Calendar.YEAR);
        int month = selected.get(Calendar.MONTH); //0-11
        int date = selected.get(Calendar.DATE);
        int day = selected.get(Calendar.DAY_OF_WEEK);
        canvas.drawText(String.format(Locale.KOREA, "%d 년 %02d 월", year, month + 1), center, 1.5f * vinterval, largeTextPaint);

        drawDate(canvas, year, month, date, day - 1, center, 3 * vinterval);
    }

    public void addSchedule(Schedule schedule) {
        int selYear = selected.get(Calendar.YEAR);
        int selMonth = selected.get(Calendar.MONTH);
        int selDate = selected.get(Calendar.DATE);
        if (selYear == schedule.getYear() && selMonth == schedule.getMonth() && selDate == schedule.getDate()) {
            numOfSchedules[selDate - 1]++;
            schedules.add(schedule);
        }
    }
}
