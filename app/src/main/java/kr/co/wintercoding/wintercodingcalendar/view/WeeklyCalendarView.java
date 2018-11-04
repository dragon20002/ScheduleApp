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

public class WeeklyCalendarView extends CalendarView {
    private final Paint smallTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint normalTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint boldTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint redTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint blueTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint largeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint primaryColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint secondColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint accentColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint notifyColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final int[] dates = new int[7];

    public WeeklyCalendarView(Context context) {
        super(context);
        init();
    }

    public WeeklyCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeeklyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        initTextPaint(smallTextPaint, dipToPx(10f), Color.argb(255, 250, 250, 250), null);
        initTextPaint(normalTextPaint, dipToPx(20f), -1, null);
        initTextPaint(boldTextPaint, dipToPx(22f), -1, Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        initTextPaint(redTextPaint, dipToPx(20f), Color.RED, null);
        initTextPaint(blueTextPaint, dipToPx(20f), Color.BLUE, null);
        initTextPaint(largeTextPaint, dipToPx(30f), -1, null);
        primaryColorPaint.setStyle(Paint.Style.FILL);
        primaryColorPaint.setColor(getResources().getColor(R.color.colorPrimary));
        initColorPaint(accentColorPaint, getResources().getColor(R.color.colorAccent));
        secondColorPaint.setStyle(Paint.Style.FILL);
        secondColorPaint.setColor(getResources().getColor(R.color.colorSecond));
        notifyColorPaint.setStyle(Paint.Style.FILL);
        notifyColorPaint.setColor(getResources().getColor(R.color.colorAccent));
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

    void drawDate(Canvas canvas, int year, int month, int date, int day, float x, float y) {
        if (selected.get(Calendar.DATE) == date)
            canvas.drawCircle(x, y - dipToPx(8f), dipToPx(15f), accentColorPaint);
        if (today.get(Calendar.YEAR) == year && today.get(Calendar.MONTH) == month && today.get(Calendar.DATE) == date)
            canvas.drawText(String.valueOf(date), x, y, boldTextPaint);
        else {
            if (day == 0)
                canvas.drawText(String.valueOf(date), x, y, redTextPaint);
            else if (day == 6)
                canvas.drawText(String.valueOf(date), x, y, blueTextPaint);
            else
                canvas.drawText(String.valueOf(date), x, y, normalTextPaint);
        }
        if (numOfSchedules[date - 1] > 0) {
            canvas.drawCircle(x + dipToPx(15f), y - dipToPx(15f), dipToPx(6f), notifyColorPaint);
            canvas.drawText(String.valueOf(numOfSchedules[date - 1]), x + dipToPx(15f), y - dipToPx(12f), smallTextPaint);
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
        for (int i = 0; i < 7; i++)
            dates[i] = 0;

        int year = selected.get(Calendar.YEAR);
        int month = selected.get(Calendar.MONTH); //0-11
        int week = selected.get(Calendar.WEEK_OF_MONTH);
        int day = selected.get(Calendar.DAY_OF_WEEK);
        int firstDayOfMonth = getFirstDayOfMonth(year, month);
        int lastDateOfMonth = getLastDateOfMonth(year, month);
        int numOfWeeks = (lastDateOfMonth + firstDayOfMonth - 2) / 7 + 1;
        int lastDateOfLastMonth = getLastDateOfLastMonth(year, month);
        int selDate = selected.get(Calendar.DATE);

        // month
        canvas.drawText(String.format(Locale.KOREA, "%d 년 %02d 월 %d 주", year, month + 1, week), center, 1.5f * vinterval, largeTextPaint);
        float bgLeft = center - 4 * hinterval, bgRight = center + 4 * hinterval;
        canvas.drawRect(bgLeft, 2.45f * vinterval, bgRight, 3.35f * vinterval, primaryColorPaint);
        canvas.drawRect(bgLeft, 3.45f * vinterval, bgRight, 4.35f * vinterval, secondColorPaint);

        // 7 days
        canvas.drawText(DAYS[0], center - 3 * hinterval, 3 * vinterval, redTextPaint);
        for (int i = 1; i < 6; i++)
            canvas.drawText(DAYS[i], center + (i - 3) * hinterval, 3 * vinterval, normalTextPaint);
        canvas.drawText(DAYS[6], center + 3 * hinterval, 3 * vinterval, blueTextPaint);

        // date
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
            numOfSchedules[schedule.getDate() - 1]++;
            schedules.add(schedule);
        }
    }
}
