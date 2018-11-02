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
import android.widget.Toast;

import kr.co.wintercoding.wintercodingcalendar.R;
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

                long id = data.getLongExtra("id", -1);
                if (id == -1)
                    return;

                String content = data.getStringExtra("content");
                int year = data.getIntExtra("year", 0);
                int month = data.getIntExtra("month", 0);
                int week = data.getIntExtra("week", 0);
                int date = data.getIntExtra("date", 0);

                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    if (tab != null) {
                        CalendarView view = (CalendarView) tab.getCustomView();
                        if (view != null) {
                            view.addSchedule(new Schedule(id, content, year, month, week, date));
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 뷰페이저 페이지번호(0,1,2)에 해당하는 프래그먼트 반환
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
