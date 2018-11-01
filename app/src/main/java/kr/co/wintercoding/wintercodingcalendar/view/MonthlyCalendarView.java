package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Calendar;
import java.util.Locale;

public class MonthlyCalendarView extends CalendarView {
    private int[][] dates = new int[6][7];

    public MonthlyCalendarView(Context context) {
        super(context);
    }

    public MonthlyCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MonthlyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void movePrevious() {
        selected.roll(Calendar.MONTH, false);
        if (selected.get(Calendar.MONTH) == 11)
            selected.roll(Calendar.YEAR, false);
    }

    @Override
    public void moveNext() {
        selected.roll(Calendar.MONTH, true);
        if (selected.get(Calendar.MONTH) == 0)
            selected.roll(Calendar.YEAR, true);
    }

    @Override
    public boolean select(float x, float y) {
        // x := center + (j - 3) * hinterval
        // y := (4 + i) * vinterval
        int i = Math.round(y / vinterval - 4);
        int j = Math.round((x - center) / hinterval + 3);
        if (i >= 0 && i < 6 && j >= 0 && j < 7) {
            if (dates[i][j] != selected.get(Calendar.DATE) && dates[i][j] != 0) {
                selected.set(Calendar.DATE, dates[i][j]);
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
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                dates[i][j] = 0;

        // month
        int year = selected.get(Calendar.YEAR);
        int month = selected.get(Calendar.MONTH); //0-11
        canvas.drawText(String.format(Locale.KOREA, "%d 년 %02d 월", year, month + 1), center, 1.5f * vinterval, largeTextPaint);

        // 7 days
        canvas.drawText(DAYS[0], center - 3 * hinterval, 3 * vinterval, redTextPaint);
        for (int i = 1; i < 6; i++)
            canvas.drawText(DAYS[i], center + (i - 3) * hinterval, 3 * vinterval, normalTextPaint);
        canvas.drawText(DAYS[6], center + 3 * hinterval, 3 * vinterval, blueTextPaint);

        // date
        int firstDayOfMonth = getFirstDayOfMonth(year, month);
        int lastDateOfMonth = getLastDateOfMonth(year, month);
        int numOfWeeks = (lastDateOfMonth + firstDayOfMonth - 2) / 7 + 1;
        int date = 1;
        // first week
        for (int day = firstDayOfMonth - 1; day < 7; day++)
            dates[0][day] = date++;
        // mid week
        for (int week = 1; week < numOfWeeks - 1; week++)
            for (int day = 0; day < 7; day++)
                dates[week][day] = date++;
        // last week
        for (int day = 0; date <= lastDateOfMonth; day++)
            dates[numOfWeeks - 1][day] = date++;

        // draw
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                if (dates[i][j] != 0)
                    drawDate(canvas, year, month, dates[i][j], j, center + (j - 3) * hinterval, (4 + i) * vinterval);
    }
}
