package kr.co.wintercoding.wintercodingcalendar.activity;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import kr.co.wintercoding.wintercodingcalendar.R;
import kr.co.wintercoding.wintercodingcalendar.dao.AppDatabase;
import kr.co.wintercoding.wintercodingcalendar.dao.ScheduleDao;
import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

public class ManageScheduleActivity extends AppCompatActivity {
    private ScheduleDao scheduleDao;
    private EditText etContent;
    private DatePicker datePicker;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        // 데이터 초기화
        Calendar cal = Calendar.getInstance();

        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        if (content == null)
            content = "";
        int year = intent.getIntExtra("year", cal.get(Calendar.YEAR));
        int month = intent.getIntExtra("month", cal.get(Calendar.MONTH));
        int date = intent.getIntExtra("date", cal.get(Calendar.DATE));

        scheduleDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "schedule")
                .build().scheduleDao();

        // 뷰 초기화
        Toolbar toolbar = findViewById(R.id.manage_schedule_toolbar);
        setSupportActionBar(toolbar);

        etContent = findViewById(R.id.schedule_content);
        datePicker = findViewById(R.id.manage_schedule_date_picker);
        progressBar = findViewById(R.id.manage_schedule_progress);

        etContent.setText(content);
        datePicker.updateDate(year, month, date);

        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    attemptUpdateSchedule();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manage_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cancel:
                finish();
                return true;
            case R.id.action_done:
                attemptUpdateSchedule();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getWeekOfMonth(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    private void attemptUpdateSchedule() {
        String content = etContent.getText().toString();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int date = datePicker.getDayOfMonth();
        int week = getWeekOfMonth(year, month, date);

        if (!content.equals(""))
            new UpdateScheduleTask(new Schedule(content, year, month, week, date)).execute();
        else
            Toast.makeText(this, "일정을 입력해주세요.", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateScheduleTask extends AsyncTask<Void, Void, Void> {
        private Schedule schedule;

        private UpdateScheduleTask(Schedule schedule) {
            this.schedule = schedule;
        }

        @Override
        protected Void doInBackground(Void... avoids) {
            scheduleDao.insert(schedule);
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent()
                    .putExtra("id", schedule.getId())
                    .putExtra("content", schedule.getContent())
                    .putExtra("year", schedule.getYear())
                    .putExtra("month", schedule.getMonth())
                    .putExtra("date", schedule.getDate());
            setResult(RESULT_OK, intent);
            finish();
        }

        @Override
        protected void onCancelled() {
            progressBar.setVisibility(View.GONE);
        }
    }
}

