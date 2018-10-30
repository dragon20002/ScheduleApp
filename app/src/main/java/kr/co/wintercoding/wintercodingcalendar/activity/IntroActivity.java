package kr.co.wintercoding.wintercodingcalendar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kr.co.wintercoding.wintercodingcalendar.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 1.3초 이후 메인화면으로 이동
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(IntroActivity.this, CalendarActivity.class));
                finish();
            }
        }).start();
    }
}
