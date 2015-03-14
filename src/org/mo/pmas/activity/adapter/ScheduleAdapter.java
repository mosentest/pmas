package org.mo.pmas.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.mo.pmas.activity.R;
import org.mo.pmas.entity.Schedule;

import java.util.List;

/**
 * Created by moziqi on 2015/1/13 0013.
 */
public class ScheduleAdapter extends BaseAdapter {
    private Context mContext;
    private List<Schedule> schedules;

    public ScheduleAdapter(Context mContext, List<Schedule> schedules) {
        this.mContext = mContext;
        this.schedules = schedules;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Schedule schedule = schedules.get(position);
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_schedule_list_item, null);
            viewHolder.tv_schedule_list_title = (TextView) convertView.findViewById(R.id.tv_schedule_list_title);
            viewHolder.tv_schedule_list_time = (TextView) convertView.findViewById(R.id.tv_schedule_list_time);
            viewHolder.tv_schedule_list_content = (TextView) convertView.findViewById(R.id.tv_schedule_list_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_schedule_list_title.setText(schedule.getTitle());
        viewHolder.tv_schedule_list_title.setText(schedule.getRemindDate());
        viewHolder.tv_schedule_list_content.setText(schedule.getContent());
        return convertView;
    }

    final static class ViewHolder {
        TextView tv_schedule_list_title;
        TextView tv_schedule_list_time;
        TextView tv_schedule_list_content;
    }
}
