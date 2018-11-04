package kr.co.wintercoding.wintercodingcalendar.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Schedule implements Comparable<Schedule> {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo
    private String content;
    @ColumnInfo
    private int year; //2XXX
    @ColumnInfo
    private int month; //0-11
    @ColumnInfo
    private int week; //1-6
    @ColumnInfo
    private int date; //1-31

    public Schedule(String content, int year, int month, int week, int date) {
        this.content = content;
        this.year = year;
        this.month = month;
        this.week = week;
        this.date = date;
    }

    @Ignore
    public Schedule(long id, String content, int year, int month, int week, int date) {
        this(content, year, month, week, date);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", week=" + week +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Schedule another) {
        long comp = this.year * 10000 + this.month * 100 + this.date;
        long ancomp = another.year * 10000 + this.month * 100 + this.date;
        if (comp == ancomp) {
            if (this.id < another.id) {
                return -1;
            } else
                return 1;
        } else if (comp < ancomp) {
            return -1;
        } else {
            return 1;
        }
    }
}
