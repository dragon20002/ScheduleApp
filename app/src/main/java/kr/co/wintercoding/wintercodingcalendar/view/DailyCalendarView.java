package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Calendar;
import java.util.Locale;

import kr.co.wintercoding.wintercodingcalendar.R;
import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

public class DailyCalendarView extends CalendarView {
    private final Paint smallTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint normalTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint boldTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint redTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint smallRedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint blueTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint smallBlueTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint largeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint primaryColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint primaryDarkColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint accentColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint notifyColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DailyCalendarView(Context context) {
        super(context);
        init();
    }

    public DailyCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DailyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void initTextPaint(Paint paint, float size, int color, Typeface typeface) {
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);
        if (color != -1)
            paint.setColor(color);
        if (typeface != null)
            paint.setTypeface(typeface);
    }

    private void initColorPaint(Paint paint, int color) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dipToPx(3f));
        paint.setColor(color);
    }

    private void init() {
        initTextPaint(smallTextPaint, dipToPx(15f), Color.BLACK, null);
        initTextPaint(normalTextPaint, dipToPx(30f), -1, null);
        initTextPaint(boldTextPaint, dipToPx(125f), -1, Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        initTextPaint(redTextPaint, dipToPx(120f), Color.RED, null);
        initTextPaint(smallRedTextPaint, dipToPx(15f), Color.RED, null);
        initTextPaint(blueTextPaint, dipToPx(120f), Color.BLUE, null);
        initTextPaint(smallBlueTextPaint, dipToPx(15f), Color.BLUE, null);
        initTextPaint(largeTextPaint, dipToPx(120f), -1, null);
        initColorPaint(primaryColorPaint, getResources().getColor(R.color.colorPrimary));
        initColorPaint(primaryDarkColorPaint, getResources().getColor(R.color.colorPrimaryDark));
        initColorPaint(accentColorPaint, getResources().getColor(R.color.colorAccent));
        notifyColorPaint.setStyle(Paint.Style.FILL);
        notifyColorPaint.setColor(getResources().getColor(R.color.colorAccent));
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

    void drawDate(Canvas canvas, int year, int month, int date, int day, float x, float y) {
        if (today.get(Calendar.YEAR) == year && today.get(Calendar.MONTH) == month && today.get(Calendar.DATE) == date)
            canvas.drawText(String.valueOf(date), x, y, boldTextPaint);
        else {
            if (day == 0)
                canvas.drawText(String.valueOf(date), x, y, redTextPaint);
            else if (day == 6)
                canvas.drawText(String.valueOf(date), x, y, blueTextPaint);
            else
                canvas.drawText(String.valueOf(date), x, y, largeTextPaint);
        }
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
        canvas.drawText(String.format(Locale.KOREA, "%d 년 %02d 월", year, month + 1), center, 1.5f * vinterval, normalTextPaint);

        drawDate(canvas, year, month, date, day - 1, center, 4 * vinterval);
        if (day == 1)
            canvas.drawText(DAYS[day-1] + "요일", center + 2 * hinterval, 4 * vinterval, smallRedTextPaint);
        else if (day == 7)
            canvas.drawText(DAYS[day-1] + "요일", center + 2 * hinterval, 4 * vinterval, smallBlueTextPaint);
        else
            canvas.drawText(DAYS[day-1] + "요일", center + 2 * hinterval, 4 * vinterval, smallTextPaint);
    }

    public void addSchedule(Schedule schedule) {
        int selYear = selected.get(Calendar.YEAR);
        int selMonth = selected.get(Calendar.MONTH);
        int selDate = selected.get(Calendar.DATE);
        if (selYear == schedule.getYear() && selMonth == schedule.getMonth() && selDate == schedule.getDate()) {
            for (Schedule s : schedules) {
                if (s.getId() == schedule.getId()) {
                    // 기존 일정 업데이트
                    s.setContent(schedule.getContent());
                    s.setYear(schedule.getYear());
                    s.setMonth(schedule.getMonth());
                    s.setWeek(schedule.getWeek());
                    s.setDate(schedule.getDate());
                    return;
                }
            }
            numOfSchedules[selDate - 1]++;
            schedules.add(schedule);
        }
    }
}
