package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Calendar;

public class WeeklyCalendarView extends CalendarView {

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
        selected.roll(Calendar.WEEK_OF_MONTH, false);
    }

    @Override
    public void moveNext() {
        selected.roll(Calendar.WEEK_OF_MONTH, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int center = width / 2;
        canvas.drawText("1", center, center, boldTextPaint);
        canvas.drawCircle(center, center, center, primaryDarkColorPaint);
    }
}
