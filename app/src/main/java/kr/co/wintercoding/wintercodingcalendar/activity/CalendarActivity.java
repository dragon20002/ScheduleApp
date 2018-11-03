package kr.co.wintercoding.wintercodingcalendar.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import kr.co.wintercoding.wintercodingcalendar.R;
import kr.co.wintercoding.wintercodingcalendar.adapter.ScheduleAdapter;
import kr.co.wintercoding.wintercodingcalendar.dao.AppDatabase;
import kr.co.wintercoding.wintercodingcalendar.dao.ScheduleDao;
import kr.co.wintercoding.wintercodingcalendar.model.Schedule;
import kr.co.wintercoding.wintercodingcalendar.view.CalendarView;

public class CalendarActivity extends AppCompatActivity {
    private static final String PREF_SETTINGS = "settings";
    private static final String PREF_TAB_INDEX = "tab_index";
    public static final int MANAGE_SCHEDULE_REQ_CODE = 0;

    private ScheduleDao scheduleDao;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        scheduleDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "schedule")
                .build().scheduleDao();

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        // 최근 앱 실행 시 선택했던 탭으로 이동
        SharedPreferences preferences = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        int tabIndex = preferences.getInt(PREF_TAB_INDEX, 0);
        viewPager.setCurrentItem(tabIndex);
    }

    @Override
    protected void onDestroy() {
        // 최근 선택한 탭 인덱스 저장
        SharedPreferences preferences = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_TAB_INDEX, viewPager.getCurrentItem());
        editor.apply();

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MANAGE_SCHEDULE_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                if (data == null)
                    return;

                String content = data.getStringExtra("content");
                if (content.equals(""))
                    return;

                int year = data.getIntExtra("year", 0);
                int month = data.getIntExtra("month", 0);
                int week = data.getIntExtra("week", 0);
                int date = data.getIntExtra("date", 0);

                SectionsPagerAdapter pagerAdapter = (SectionsPagerAdapter) viewPager.getAdapter();
                if (pagerAdapter == null)
                    return;

                int calendarIds[] = {R.id.monthly_calendar_view, R.id.weekly_calendar_view, R.id.daily_calendar_view};
                int listIds[] = {-1, R.id.weekly_todo_recycler_view, R.id.daily_todo_recycler_view};
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    PlaceholderFragment fragment = (PlaceholderFragment) pagerAdapter.getFragment(i);
                    if (fragment != null) {
                        View view = fragment.getView();
                        if (view != null) {
                            CalendarView calendarView = view.findViewById(calendarIds[i]);
                            Schedule schedule = new Schedule(content, year, month, week, date);
                            calendarView.addSchedule(schedule);
                            calendarView.invalidate();
                            if (listIds[i] != -1) {
                                RecyclerView recyclerView = view.findViewById(listIds[i]);
                                ScheduleAdapter scheduleAdapter = (ScheduleAdapter) recyclerView.getAdapter();
                                if (scheduleAdapter != null) {
                                    scheduleAdapter.add(schedule);
                                    scheduleAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "일정 작업이 취소되었습니다.", Toast.LENGTH_SHORT).show();

            } //resultCode

        } //requestCode
    }

    public ScheduleDao getScheduleDao() {
        return scheduleDao;
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {
        final Fragment[] fragment = { null, null, null };

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 뷰페이저 페이지번호(0,1,2)에 해당하는 프래그먼트 반환
            fragment[position] = PlaceholderFragment.newInstance(position);
            return fragment[position];
        }

        @Override
        public int getCount() {
            return 3;
        }

        Fragment getFragment(int position) {
            return fragment[position];
        }
    }

}
