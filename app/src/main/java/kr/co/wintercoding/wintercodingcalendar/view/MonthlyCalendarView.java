package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Calendar;

public class MonthlyCalendarView extends CalendarView {

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
    }

    @Override
    public void moveNext() {
        selected.roll(Calendar.MONTH, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int center = width / 2;
        canvas.drawText("1", center, center, smallTextPaint);
        canvas.drawCircle(center, center, center, primaryColorPaint);
    }
}
