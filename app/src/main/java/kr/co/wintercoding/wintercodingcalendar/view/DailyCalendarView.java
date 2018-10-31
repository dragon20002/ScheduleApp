package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Calendar;

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
        selected.roll(Calendar.DATE, false);
    }

    @Override
    public void moveNext() {
        selected.roll(Calendar.DATE, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int center = width / 2;
        canvas.drawText("1", center, center, largeTextPaint);
        canvas.drawCircle(center, center, center, accentColorPaint);
    }
}
