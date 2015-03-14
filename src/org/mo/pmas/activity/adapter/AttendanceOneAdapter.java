package org.mo.pmas.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mo.pmas.activity.R;
import org.mo.znyunxt.entity.StudentAttendance;

import java.util.List;

/**
 * Created by moziqi on 2015/3/11 0011.
 */
public class AttendanceOneAdapter extends BaseAdapter implements IAdapter<StudentAttendance> {

    private Context mContext;
    private List<StudentAttendance> list;

    int[] colors = {Color.parseColor("#FFFFCC"), Color.parseColor("#FFFFFF")};

    public AttendanceOneAdapter(Context mContext, List<StudentAttendance> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_attendance_one_list_item, null);
//            convertView.setBackgroundColor(colors[position % 2]);
            mViewHolder.tv_student_departname = (TextView) convertView.findViewById(R.id.tv_student_departname);
            mViewHolder.tv_student_name = (TextView) convertView.findViewById(R.id.tv_student_name);
            mViewHolder.tv_student_part = (TextView) convertView.findViewById(R.id.tv_student_part);
            mViewHolder.tv_student_realAttend = (TextView) convertView.findViewById(R.id.tv_student_realAttend);
            mViewHolder.tv_student_io = (TextView) convertView.findViewById(R.id.tv_student_io);
            mViewHolder.tv_student_lastOccurtime = (TextView) convertView.findViewById(R.id.tv_student_lastOccurtime);
            mViewHolder.rl_aoli = (RelativeLayout) convertView.findViewById(R.id.rl_aoli);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        StudentAttendance studentAttendance = list.get(position);
        String depart_departname = studentAttendance.getDepart_departname();
        String student_name = studentAttendance.getStudent_name();
        String part = studentAttendance.getPart();
        String realAttend = studentAttendance.getRealAttend();
        String lastIo = studentAttendance.getLastIo();
        String lastOccurtime = studentAttendance.getLastOccurtime();
        mViewHolder.tv_student_departname.setText(depart_departname);
        mViewHolder.tv_student_name.setText(student_name);
        mViewHolder.tv_student_part.setText(part);
        mViewHolder.tv_student_realAttend.setText(realAttend);
        mViewHolder.tv_student_io.setText(lastIo);
        mViewHolder.tv_student_lastOccurtime.setText(lastOccurtime);
        //TODO 2015-3-12
        return convertView;
    }

    @Override
    public void update(List<StudentAttendance> mList) {
        list = mList;
        notifyDataSetChanged();
    }

    final static class ViewHolder {
        TextView tv_student_departname;
        TextView tv_student_name;
        TextView tv_student_part;
        TextView tv_student_realAttend;
        TextView tv_student_io;
        TextView tv_student_lastOccurtime;
        RelativeLayout rl_aoli;

    }
}
