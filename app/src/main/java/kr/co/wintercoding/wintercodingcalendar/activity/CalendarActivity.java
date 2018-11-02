package kr.co.wintercoding.wintercodingcalendar.activity;

import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import kr.co.wintercoding.wintercodingcalendar.R;
import kr.co.wintercoding.wintercodingcalendar.dao.AppDatabase;
import kr.co.wintercoding.wintercodingcalendar.dao.ScheduleDao;

public class CalendarActivity extends AppCompatActivity {
    private static final String PREF_SETTINGS = "settings";
    private static final String PREF_TAB_INDEX = "tab_index";

    private ViewPager mViewPager;

    public static ScheduleDao scheduleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        scheduleDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "schedule")
                .build()
                .scheduleDao();

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        // 최근 앱 실행 시 선택했던 탭으로 이동
        SharedPreferences preferences = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        int tabIndex = preferences.getInt(PREF_TAB_INDEX, 0);
        mViewPager.setCurrentItem(tabIndex);
    }

    @Override
    protected void onDestroy() {
        // 최근 선택한 탭 인덱스 저장
        SharedPreferences preferences = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_TAB_INDEX, mViewPager.getCurrentItem());
        editor.apply();

        super.onDestroy();
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
