package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Calendar;
import java.util.Locale;

import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

public class WeeklyCalendarView extends CalendarView {
    private final int[] dates = new int[7];

    public WeeklyCalendarView(Context context) {
        super(context);
    }

    public WeeklyCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeeklyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void movePrevious() {
        if (selected.get(Calendar.WEEK_OF_MONTH) == 1) {
            selected.roll(Calendar.MONTH, false);
            selected.set(Calendar.DATE, 1);
            if (selected.get(Calendar.MONTH) == 11)
                selected.roll(Calendar.YEAR, false);
        }
        selected.roll(Calendar.WEEK_OF_MONTH, false);
    }

    @Override
    public void moveNext() {
        selected.roll(Calendar.WEEK_OF_MONTH, true);
        if (selected.get(Calendar.WEEK_OF_MONTH) == 1) {
            selected.roll(Calendar.MONTH, true);
            if (selected.get(Calendar.MONTH) == 0)
                selected.roll(Calendar.YEAR, true);
        }
    }

    @Override
    public boolean select(float x, float y) {
        // x := center + (j - 3) * hinterval
        // y := (4 + i) * vinterval
        int i = Math.round(y / vinterval - 4);
        int j = Math.round((x - center) / hinterval + 3);
        if (i >= 0 && i < 1 && j >= 0 && j < 7) {
            int dateDiff = Math.abs(dates[j] - selected.get(Calendar.DATE));
            if (dateDiff > 0 && dateDiff < 8 && dates[j] != 0) {
                selected.set(Calendar.DATE, dates[j]);
                return true;
            }
        }
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
        for (int i = 0; i < 7; i++)
            dates[i] = 0;

        // month
        int year = selected.get(Calendar.YEAR);
        int month = selected.get(Calendar.MONTH); //0-11
        int week = selected.get(Calendar.WEEK_OF_MONTH);
        int day = selected.get(Calendar.DAY_OF_WEEK);
        canvas.drawText(String.format(Locale.KOREA, "%d 년 %02d 월 %d 주", year, month + 1, week), center, 1.5f * vinterval, largeTextPaint);

        // 7 days
        canvas.drawText(DAYS[0], center - 3 * hinterval, 3 * vinterval, redTextPaint);
        for (int i = 1; i < 6; i++)
            canvas.drawText(DAYS[i], center + (i - 3) * hinterval, 3 * vinterval, normalTextPaint);
        canvas.drawText(DAYS[6], center + 3 * hinterval, 3 * vinterval, blueTextPaint);

        // date
        int firstDayOfMonth = getFirstDayOfMonth(year, month);
        int lastDateOfMonth = getLastDateOfMonth(year, month);
        int numOfWeeks = (lastDateOfMonth + firstDayOfMonth - 2) / 7 + 1;
        int lastDateOfLastMonth = getLastDateOfLastMonth(year, month);
        int selDate = selected.get(Calendar.DATE);
        if (week == 1) { //현재 날짜가 첫 주
            for (int i = 0; i < day - selDate; i++) {
                dates[i] = lastDateOfLastMonth - firstDayOfMonth + i + 2;
            }
            int date = 1;
            for (int i = day - selDate; i < 7; i++) {
                dates[i] = date++;
            }
        } else if (week == numOfWeeks) { //현재 날짜가 마지막 주
            int date = selected.get(Calendar.DATE);
            int i = 0;
            while (date - day + 1 <= lastDateOfMonth) {
                dates[i++] = date - day + 1;
                date++;
            }
            int nextDate = 1;
            while (i < 7) {
                dates[i++] = nextDate++;
            }
        } else { //현재 날짜가 중간 주
            int date = selected.get(Calendar.DATE);
            for (int i = 0; i < 7; i++) {
                dates[i] = date - day + 1;
                date++;
            }
        }

        // draw
        for (int i = 0; i < 7; i++)
            drawDate(canvas, year, month, dates[i], i, center + (i - 3) * hinterval, 4 * vinterval);
    }

    public void addSchedule(Schedule schedule) {
        int selYear = selected.get(Calendar.YEAR);
        int selMonth = selected.get(Calendar.MONTH);
        int selWeek = selected.get(Calendar.WEEK_OF_MONTH);
        if (selYear == schedule.getYear() && selMonth == schedule.getMonth() && selWeek == schedule.getWeek()) {
            numOfSchedules[schedule.getDate() - 1]++;
            schedules.add(schedule);
        }
    }
}
