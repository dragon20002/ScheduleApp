package kr.co.wintercoding.wintercodingcalendar.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import kr.co.wintercoding.wintercodingcalendar.R;
import kr.co.wintercoding.wintercodingcalendar.adapter.ScheduleAdapter;
import kr.co.wintercoding.wintercodingcalendar.dao.ScheduleDao;
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
        FloatingActionButton fab;
        RecyclerView recyclerView;
        TextView noTodoTextView;
        ScheduleAdapter adapter;
        CalendarView calendarView;
        BaseOnSwipeGestureListener onSwipeGestureListener;

        int sectionNumber = (getArguments() != null) ? getArguments().getInt(ARG_SECTION_NUMBER) : 0;
        switch (sectionNumber) {
            case MONTHLY:
            default:
                rootView = inflater.inflate(R.layout.fragment_calendar_monthly, container, false);
                // 월 달력뷰
                calendarView = rootView.findViewById(R.id.monthly_calendar_view);
                onSwipeGestureListener = new MonthlyOnSwipeGestureListener(calendarView);
                // 플로팅 액션 버튼
                fab = rootView.findViewById(R.id.fab);
                fab.setOnClickListener(new FabOnClickListener(calendarView));
                // 스케줄 업데이트
                new UpdateScheduleTask(calendarView, null, null).execute(sectionNumber);
                break;

            case WEEKLY:
                rootView = inflater.inflate(R.layout.fragment_calendar_weekly, container, false);
                // 리스트뷰
                recyclerView = rootView.findViewById(R.id.weekly_todo_recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new ScheduleAdapter();
                recyclerView.setAdapter(adapter);
                noTodoTextView = rootView.findViewById(R.id.weekly_no_todo);
                // 주 달력뷰
                calendarView = rootView.findViewById(R.id.weekly_calendar_view);
                onSwipeGestureListener = new WeeklyOnSwipeGestureListener(calendarView, adapter, noTodoTextView);
                // 스케줄 업데이트
                new UpdateScheduleTask(calendarView, adapter, noTodoTextView).execute(sectionNumber);
                break;

            case DAILY:
                rootView = inflater.inflate(R.layout.fragment_calendar_daily, container, false);
                // 리스트뷰
                recyclerView = rootView.findViewById(R.id.daily_todo_recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new ScheduleAdapter();
                recyclerView.setAdapter(adapter);
                noTodoTextView = rootView.findViewById(R.id.daily_no_todo);
                // 일 달력뷰
                calendarView = rootView.findViewById(R.id.daily_calendar_view);
                onSwipeGestureListener = new DailyOnSwipeGestureListener(calendarView, adapter, noTodoTextView);
                // 스케줄 업데이트
                new UpdateScheduleTask(calendarView, adapter, noTodoTextView).execute(sectionNumber);
                break;
        }

        calendarView.setOnSwipeGestureListener(new CustomGesture(onSwipeGestureListener));

        return rootView;
    }

    /* LISTENERS */
    private class MonthlyOnSwipeGestureListener extends BaseOnSwipeGestureListener {

        private MonthlyOnSwipeGestureListener(CalendarView view) {
            super(view);
        }

        @Override
        protected void updateSchedules() {
            new UpdateScheduleTask(view, null, null).execute(MONTHLY);
        }
    }

    private class WeeklyOnSwipeGestureListener extends BaseOnSwipeGestureListener {
        private final ScheduleAdapter adapter;
        private final TextView noTodoTextView;

        private WeeklyOnSwipeGestureListener(CalendarView view, ScheduleAdapter adapter, TextView noTodoTextView) {
            super(view);
            this.adapter = adapter;
            this.noTodoTextView = noTodoTextView;
        }

        @Override
        protected void updateSchedules() {
            new UpdateScheduleTask(view, adapter, noTodoTextView).execute(WEEKLY);
        }
    }

    private class DailyOnSwipeGestureListener extends BaseOnSwipeGestureListener {
        private final ScheduleAdapter adapter;
        private final TextView noTodoTextView;

        private DailyOnSwipeGestureListener(CalendarView view, ScheduleAdapter adapter, TextView noTodoTextView) {
            super(view);
            this.adapter = adapter;
            this.noTodoTextView = noTodoTextView;
        }

        @Override
        protected void updateSchedules() {
            new UpdateScheduleTask(view, adapter, noTodoTextView).execute(DAILY);
        }
    }

    private class FabOnClickListener implements View.OnClickListener {
        private final CalendarView calendarView;

        FabOnClickListener(CalendarView calendarView) {
            this.calendarView = calendarView;
        }

        @Override
        public void onClick(View v) {
            CalendarActivity activity = (CalendarActivity) getActivity();
            if (activity == null) return;

            Calendar sel = calendarView.getSelected();
            activity.startActivityForResult(
                    new Intent(activity, ManageScheduleActivity.class)
                            .putExtra("year", sel.get(Calendar.YEAR))
                            .putExtra("month", sel.get(Calendar.MONTH))
                            .putExtra("date", sel.get(Calendar.DATE)),
                    CalendarActivity.MANAGE_SCHEDULE_REQ_CODE);
        }
    }

    /* THREAD */
    @SuppressLint("StaticFieldLeak")
    private class UpdateScheduleTask extends AsyncTask<Integer, Void, List<Schedule>> {
        private final CalendarView calendarView;
        private final ScheduleAdapter adapter;
        private final TextView noTodoTextView;

        private UpdateScheduleTask(CalendarView view, ScheduleAdapter adapter, TextView noTodoTextView) {
            this.calendarView = view;
            this.adapter = adapter;
            this.noTodoTextView = noTodoTextView;
        }

        @Override
        protected List<Schedule> doInBackground(Integer... integers) {
            int sectionNumber = integers[0];

            CalendarActivity activity = (CalendarActivity) getActivity();
            if (activity == null)
                return null;
            ScheduleDao scheduleDao = activity.getScheduleDao();

            // 탭에 넣을 데이터
            Calendar sel = calendarView.getSelected();
            int year, month, week, date;
            List<Schedule> schedules;

            if (sectionNumber == MONTHLY) {
                year = sel.get(Calendar.YEAR);
                month = sel.get(Calendar.MONTH);
                schedules = scheduleDao.getMonthlySchedules(year, month);
            } else if (sectionNumber == WEEKLY) {
                year = sel.get(Calendar.YEAR);
                month = sel.get(Calendar.MONTH);
                week = sel.get(Calendar.WEEK_OF_MONTH);
                schedules = scheduleDao.getWeeklySchedules(year, month, week);
            } else {
                year = sel.get(Calendar.YEAR);
                month = sel.get(Calendar.MONTH);
                date = sel.get(Calendar.DATE);
                schedules = scheduleDao.getDailySchedules(year, month, date);
            }

            return schedules;
        }

        @Override
        protected void onPostExecute(List<Schedule> schedules) {
            if (adapter != null) {
                adapter.addAll(schedules);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() == 0) {
                    noTodoTextView.setVisibility(View.VISIBLE);
                } else {
                    noTodoTextView.setVisibility(View.GONE);
                }
            }
            calendarView.updateSchedules(schedules);
            calendarView.invalidate();
        }
    }
}