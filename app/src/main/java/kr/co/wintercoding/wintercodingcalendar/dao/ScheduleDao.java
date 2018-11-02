package kr.co.wintercoding.wintercodingcalendar.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

@Dao
public interface ScheduleDao {

    @Query("SELECT * FROM schedule WHERE year=(:year) AND month=(:month)")
    List<Schedule> getMonthlySchedules(int year, int month);

    /**
     * @param year  년 2XXX
     * @param month 월 0-11
     * @param week  주 0-6
     * @return 스케줄 목록
     */
    @Query("SELECT * FROM schedule WHERE year=(:year) AND month=(:month) AND week=(:week)")
    List<Schedule> getWeeklySchedules(int year, int month, int week);

    /**
     * @param year  년 2XXX
     * @param month 월 0-11
     * @param date  일 1-31
     * @return 스케줄 목록
     */
    @Query("SELECT * FROM schedule WHERE year=(:year) AND month=(:month) AND date=(:date)")
    List<Schedule> getDailySchedules(int year, int month, int date);

    @Insert
    void insert(Schedule schedule);

    @Update
    void update(Schedule schedule);

    @Delete
    void delete(Schedule schedule);

}
