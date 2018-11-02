package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.wintercoding.wintercodingcalendar.R;
import kr.co.wintercoding.wintercodingcalendar.listener.CustomGesture;
import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

public abstract class CalendarView extends View {
    protected final Paint normalTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint boldTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint redTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint blueTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint largeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected final Paint primaryColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint primaryDarkColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint accentColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected int width = 0;
    protected float center = 0;
    protected float hinterval = 0;
    protected float vinterval = 0;

    protected final String DAYS[] = {"일", "월", "화", "수", "목", "금", "토"};
    protected Calendar today, selected;
    protected List<Schedule> schedules;

    protected GestureDetectorCompat gestureDetectorCompat = null;

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

    private void initTextPaint(Paint paint, float size, int colorId, Typeface typeface) {
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);
        if (colorId != -1)
            paint.setColor(colorId);
        if (typeface != null)
            paint.setTypeface(typeface);
    }

    private void initColorPaint(Paint paint, int colorId) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dipToPx(3f));
        paint.setColor(getResources().getColor(colorId));
    }

    private void init() {
        initTextPaint(normalTextPaint, dipToPx(20f), -1, null);
        initTextPaint(boldTextPaint, dipToPx(22f), -1, Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        initTextPaint(redTextPaint, dipToPx(20f), Color.RED, null);
        initTextPaint(blueTextPaint, dipToPx(20f), Color.BLUE, null);
        initTextPaint(largeTextPaint, dipToPx(30f), -1, null);
        initColorPaint(primaryColorPaint, R.color.colorPrimary);
        initColorPaint(primaryDarkColorPaint, R.color.colorPrimaryDark);
        initColorPaint(accentColorPaint, R.color.colorAccent);

        today = Calendar.getInstance();
        selected = Calendar.getInstance();
        schedules = new ArrayList<>();
    }

    public abstract void movePrevious();

    public abstract void moveNext();

    public abstract boolean select(float x, float y);

    protected void drawDate(Canvas canvas, int year, int month, int date, int day, float x, float y) {
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

//        int minh = MeasureSpec.getSize(w) + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w), heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    /* UTILS */

    /**
     * @param year  년 입력
     * @param month 월 입력 (0-11)
     * @return 이번 달 1일의 요일을 반환
     */
    protected int getFirstDayOfMonth(int year, int month) {
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
    protected int getLastDateOfMonth(int year, int month) {
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
    protected int getLastDateOfLastMonth(int year, int month) {
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
    public Calendar getToday() {
        return today;
    }

    public Calendar getSelected() {
        return selected;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public void updateSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
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
