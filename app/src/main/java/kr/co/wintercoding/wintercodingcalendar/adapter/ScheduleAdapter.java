package kr.co.wintercoding.wintercodingcalendar.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import kr.co.wintercoding.wintercodingcalendar.R;
import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    public interface CardViewOnClickListener {
        void onClickCardView(Schedule schedule);
    }

    private final CardViewOnClickListener listener;
    private final List<Schedule> schedules = new ArrayList<>();

    public ScheduleAdapter(CardViewOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewGroup cardView = (ViewGroup) inflater.inflate(R.layout.view_schedule_item, parent, false);
        return new ScheduleViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        final Schedule schedule = schedules.get(position);
        String dateStr = String.format(Locale.KOREA, "%d-%d-%d", schedule.getYear(), schedule.getMonth() + 1, schedule.getDate());
        holder.dateView.setText(dateStr);
        holder.contentView.setText(schedules.get(position).getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCardView(schedule);
            }
        });
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public void add(Schedule schedule) {
        boolean insert = true;
        for (Schedule s : schedules) {
            if (s.getId() == schedule.getId()) {
                // 기존 일정 업데이트
                s.setContent(schedule.getContent());
                s.setYear(schedule.getYear());
                s.setMonth(schedule.getMonth());
                s.setWeek(schedule.getWeek());
                s.setDate(schedule.getDate());
                insert = false;
                break;
            }
        }
        if (insert) {
            // 새로운 일정 추가
            this.schedules.add(schedule);
        }
        Collections.sort(schedules);
    }

    public void addAll(List<Schedule> schedules) {
        this.schedules.clear();
        this.schedules.addAll(schedules);
    }

    public void remove(Schedule schedule) {
        for (Schedule s : schedules) {
            if (s.getId() == schedule.getId()) {
                // 일정 삭제
                schedules.remove(s);
                break;
            }
        }
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateView;
        private final TextView contentView;

        private ScheduleViewHolder(@NonNull ViewGroup cardView) {
            super(cardView);
            dateView = cardView.findViewById(R.id.schedule_item_date);
            contentView = cardView.findViewById(R.id.schedule_item_content);
        }
    }
}
