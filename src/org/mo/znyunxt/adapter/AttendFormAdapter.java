package org.mo.znyunxt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mo.common.util.DateUtil;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.adapter.IAdapter;
import org.mo.znyunxt.entity.AttendForm;
import org.mo.znyunxt.entity.AttendRecordDetail;
import org.mo.znyunxt.entity.CodeUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by moziqi on 2015/3/22 0022.
 */
public class AttendFormAdapter extends BaseAdapter implements IAdapter<AttendForm> {
    private Context mContext;
    private List<AttendForm> attendForms;

    public AttendFormAdapter(Context mContext, List<AttendForm> attendForms) {
        this.mContext = mContext;
        this.attendForms = attendForms;
    }

    @Override
    public int getCount() {
        if (attendForms == null)
            return 0;
        return attendForms.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_attend_form_list_item, null);
            mViewHolder.tv_student_part = (TextView) convertView.findViewById(R.id.tv_student_part);
            mViewHolder.tv_student_name = (TextView) convertView.findViewById(R.id.tv_student_name);
            mViewHolder.tv_lastOccurtime = (TextView) convertView.findViewById(R.id.tv_lastOccurtime);
            mViewHolder.tv_realAttend = (TextView) convertView.findViewById(R.id.tv_realAttend);
            mViewHolder.tv_lastIo = (TextView) convertView.findViewById(R.id.tv_lastIo);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        AttendForm attendRecordDetail = attendForms.get(position);
        mViewHolder.tv_student_part.setText(attendRecordDetail.getDepart_departname());
        mViewHolder.tv_student_name.setText(attendRecordDetail.getStudent_name());
        Date date = DateUtil.str2Date(attendRecordDetail.getLastOccurtime());
        String dateString = DateUtil.date2Str(date, "HH:mm:ss");
        mViewHolder.tv_lastOccurtime.setText(dateString);
        int realAttend = Integer.parseInt(attendRecordDetail.getRealAttend());
        switch (realAttend) {
            case 1:
                mViewHolder.tv_realAttend.setTextColor(Color.rgb(255, 140, 0));
                break;
            case 2:
                mViewHolder.tv_realAttend.setTextColor(Color.rgb(255, 0, 0));
                break;
            case 3:
                mViewHolder.tv_realAttend.setTextColor(Color.rgb(24, 116, 205));
                break;
            default:
                mViewHolder.tv_realAttend.setTextColor(Color.rgb(24, 188, 156));
        }
        mViewHolder.tv_realAttend.setText("实际考勤：" + CodeUtil.getAttendName(realAttend));
        if (attendRecordDetail.getLastIo() != null && attendRecordDetail.getLastIo().length() > 0) {
            mViewHolder.tv_lastIo.setText("进出口：" + attendRecordDetail.getLastIo());
        }
        return convertView;
    }

    @Override
    public void update(List<AttendForm> mList) {
        attendForms = mList;
        notifyDataSetChanged();
    }


    final static class ViewHolder {
        TextView tv_student_part;
        TextView tv_student_name;
        TextView tv_lastOccurtime;
        TextView tv_realAttend;
        TextView tv_lastIo;
    }
}
