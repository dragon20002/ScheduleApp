package kr.co.wintercoding.wintercodingcalendar.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.List;

import kr.co.wintercoding.wintercodingcalendar.R;
import kr.co.wintercoding.wintercodingcalendar.listener.BaseOnSwipeGestureListener;
import kr.co.wintercoding.wintercodingcalendar.listener.CustomGesture;
import kr.co.wintercoding.wintercodingcalendar.model.Schedule;
import kr.co.wintercoding.wintercodingcalendar.view.CalendarView;

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int MONTHLY = 0;
    private static final int WEEKLY = 1;
    private static final int DAILY = 2;

    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        // 새로운 프래그먼트 생성 및 페이지번호(0,1,2) 전달
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 각 탭에 대한 뷰 생성
        View rootView;
        CalendarView view;
        BaseOnSwipeGestureListener listener;

        int sectionNumber = (getArguments() != null) ? getArguments().getInt(ARG_SECTION_NUMBER) : 0;
        switch (sectionNumber) {
            case MONTHLY:
            default:
                rootView = inflater.inflate(R.layout.fragment_calendar_monthly, container, false);
                view = rootView.findViewById(R.id.monthly_calendar_view);
                listener = new MonthlyOnSwipeGestureListener(view);
                break;
            case WEEKLY:
                rootView = inflater.inflate(R.layout.fragment_calendar_weekly, container, false);
                view = rootView.findViewById(R.id.weekly_calendar_view);
                listener = new WeeklyOnSwipeGestureListener(view);
                break;
            case DAILY:
                rootView = inflater.inflate(R.layout.fragment_calendar_daily, container, false);
                view = rootView.findViewById(R.id.daily_calendar_view);
                listener = new DailyOnSwipeGestureListener(view);
                break;
        }
        view.setOnSwipeGestureListener(new CustomGesture(listener));
        new ScheduleUpdateTask(view).execute(sectionNumber);
        return rootView;
    }

    /* LISTENERS */
    private class MonthlyOnSwipeGestureListener extends BaseOnSwipeGestureListener {

        private MonthlyOnSwipeGestureListener(CalendarView view) {
            super(view);
        }

        @Override
        protected void updateSchedules() {
            new ScheduleUpdateTask(view).execute(MONTHLY);
        }
    }

    private class WeeklyOnSwipeGestureListener extends BaseOnSwipeGestureListener {

        private WeeklyOnSwipeGestureListener(CalendarView view) {
            super(view);
        }

        @Override
        protected void updateSchedules() {
            new ScheduleUpdateTask(view).execute(WEEKLY);
        }
    }

    private class DailyOnSwipeGestureListener extends BaseOnSwipeGestureListener {

        private DailyOnSwipeGestureListener(CalendarView view) {
            super(view);
        }

        @Override
        protected void updateSchedules() {
            new ScheduleUpdateTask(view).execute(DAILY);
        }
    }

    /* THREAD */
    @SuppressLint("StaticFieldLeak")
    private class ScheduleUpdateTask extends AsyncTask<Integer, Void, List<Schedule>> {
        private CalendarView view;

        private ScheduleUpdateTask(CalendarView view) {
            this.view = view;
        }

        @Override
        protected List<Schedule> doInBackground(Integer... integers) {
            int sectionNumber = integers[0];

            // 탭에 넣을 데이터
            Calendar cal = Calendar.getInstance();
            int year, month, week, date;
            List<Schedule> schedules;

            if (sectionNumber == MONTHLY) {
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                schedules = CalendarActivity.scheduleDao.getMonthlySchedules(year, month);
            } else if (sectionNumber == WEEKLY) {
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                week = cal.get(Calendar.WEEK_OF_MONTH);
                schedules = CalendarActivity.scheduleDao.getWeeklySchedules(year, month, week);
            } else {
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                date = cal.get(Calendar.DATE);
                schedules = CalendarActivity.scheduleDao.getDailySchedules(year, month, date);
            }
            return schedules;
        }

        @Override
        protected void onPostExecute(List<Schedule> schedules) {
            view.updateSchedules(schedules);
            view.invalidate();
        }
    }
}