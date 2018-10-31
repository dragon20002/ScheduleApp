package kr.co.wintercoding.wintercodingcalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.wintercoding.wintercodingcalendar.R;

public abstract class CalendarView extends View {
    protected final Paint smallTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint normalTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint boldTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint largeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected final Paint primaryColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint primaryDarkColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint accentColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected Calendar today, selected;
    protected List<Calendar> schedules;

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

    private void initTextPaint(Paint paint, float size, Typeface typeface) {
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);
        if (typeface != null)
            paint.setTypeface(typeface);
    }

    private void initColorPaint(Paint paint, int colorId) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(100);
        paint.setColor(getResources().getColor(colorId));
    }

    private void init() {
        initTextPaint(smallTextPaint, 40f, null);
        initTextPaint(normalTextPaint, 60f, null);
        initTextPaint(boldTextPaint, 60f, Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        initTextPaint(largeTextPaint, 100f, null);
        initColorPaint(primaryColorPaint, R.color.colorPrimary);
        initColorPaint(primaryDarkColorPaint, R.color.colorPrimaryDark);
        initColorPaint(accentColorPaint, R.color.colorAccent);

        today = Calendar.getInstance();
        selected = Calendar.getInstance();
        schedules = new ArrayList<>();
    }

    public abstract void movePrevious();

    public abstract void moveNext();

    @Override
    protected abstract void onDraw(Canvas canvas);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        int minh = MeasureSpec.getSize(w) + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    public Calendar getToday() {
        return today;
    }

    public Calendar getSelected() {
        return selected;
    }

    public void addSchedule(Calendar calendar) {
        schedules.add(calendar);
    }

    public void addSchedules(List<Calendar> calendars) {
        schedules.addAll(calendars);
    }

}
