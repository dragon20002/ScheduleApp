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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import kr.co.wintercoding.wintercodingcalendar.R;
import kr.co.wintercoding.wintercodingcalendar.dao.AppDatabase;
import kr.co.wintercoding.wintercodingcalendar.dao.ScheduleDao;
import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

public class ManageScheduleActivity extends AppCompatActivity {
    private static final int INSERT = 0;
    private static final int UPDATE = 1;
    private static final int DELETE = 2;

    private ScheduleDao scheduleDao;
    private EditText etContent;
    private DatePicker datePicker;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        // 데이터 초기화
        Calendar cal = Calendar.getInstance();

        Intent intent = getIntent();
        id = intent.getLongExtra("id", -1);
        String content = intent.getStringExtra("content");
        if (content == null)
            content = "";
        final int year = intent.getIntExtra("year", cal.get(Calendar.YEAR));
        final int month = intent.getIntExtra("month", cal.get(Calendar.MONTH));
        final int date = intent.getIntExtra("date", cal.get(Calendar.DATE));

        scheduleDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "schedule")
                .build().scheduleDao();

        // 뷰 초기화
        Toolbar toolbar = findViewById(R.id.manage_schedule_toolbar);
        setSupportActionBar(toolbar);

        etContent = findViewById(R.id.schedule_content);
        datePicker = findViewById(R.id.manage_schedule_date_picker);
        Button btnDelete = findViewById(R.id.manage_schedule_delete);

        etContent.setText(content);
        datePicker.updateDate(year, month, date);
        if (id == -1) {
            btnDelete.setVisibility(View.GONE);
        }

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

        final String finalContent = content;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int week = getWeekOfMonth(year, month, date);
                new UpdateScheduleTask(new Schedule(id, finalContent, year, month, week, date), DELETE).execute();
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

        if (!content.equals("")) {
            if (id == -1) {
                Schedule schedule = new Schedule(content, year, month, week, date);
                new UpdateScheduleTask(schedule, INSERT).execute();
            } else {
                Schedule schedule = new Schedule(id, content, year, month, week, date);
                new UpdateScheduleTask(schedule, UPDATE).execute();
            }
        } else {
            Toast.makeText(this, "일정을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateScheduleTask extends AsyncTask<Void, Void, Boolean> {
        private final Schedule schedule;
        private int tag;

        private UpdateScheduleTask(Schedule schedule, int tag) {
            this.schedule = schedule;
            this.tag = tag;
        }

        @Override
        protected Boolean doInBackground(Void... aVoids) {
            switch (tag) {
                case INSERT:
                    id = scheduleDao.insert(schedule);
                    schedule.setId(id);
                    return true;
                case UPDATE:
                    int updateCnt = scheduleDao.update(schedule);
                    return updateCnt >= 1; //업데이트된 행 개수가 1보다 적으면 실패
                case DELETE:
                    scheduleDao.delete(schedule);
                    return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Intent intent = new Intent()
                        .putExtra("tag", tag)
                        .putExtra("id", schedule.getId())
                        .putExtra("content", schedule.getContent())
                        .putExtra("year", schedule.getYear())
                        .putExtra("month", schedule.getMonth())
                        .putExtra("week", schedule.getWeek())
                        .putExtra("date", schedule.getDate());
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(ManageScheduleActivity.this, "일정 추가에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

