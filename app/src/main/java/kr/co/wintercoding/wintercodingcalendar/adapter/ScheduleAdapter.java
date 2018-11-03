package kr.co.wintercoding.wintercodingcalendar.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.co.wintercoding.wintercodingcalendar.model.Schedule;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private final List<Schedule> schedules = new ArrayList<>();

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        private ScheduleViewHolder(@NonNull TextView v) {
            super(v);
            textView = v;
        }
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = new TextView(parent.getContext());
        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.textView.setText(schedules.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public void add(Schedule schedule) {
        this.schedules.add(schedule);
    }

    public void addAll(List<Schedule> schedules) {
        this.schedules.clear();
        this.schedules.addAll(schedules);
    }

}
