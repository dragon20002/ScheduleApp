package kr.co.wintercoding.wintercodingcalendar.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.wintercoding.wintercodingcalendar.R;

public class CalendarActivity extends AppCompatActivity {
    private static final String PREF_SETTINGS = "settings";
    private static final String PREF_TAB_INDEX = "tab_index";

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

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
            int sectionNumber = (getArguments() != null) ? getArguments().getInt(ARG_SECTION_NUMBER) : 0;
            switch (sectionNumber) {
                case 0: //월
                default:
                    rootView = inflater.inflate(R.layout.fragment_calendar_monthly, container, false);
                    break;
                case 1: //주
                    rootView = inflater.inflate(R.layout.fragment_calendar_weekly, container, false);
                    break;
                case 2: //일
                    rootView = inflater.inflate(R.layout.fragment_calendar_daily, container, false);
                    break;
            }
            return rootView;
        }
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
