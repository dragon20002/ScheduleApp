package kr.co.wintercoding.wintercodingcalendar.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

@Database(entities = {Schedule.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ScheduleDao scheduleDao();
}
