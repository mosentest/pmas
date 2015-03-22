package org.mo.znyunxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mo.pmas.activity.R;
import org.mo.pmas.activity.adapter.IAdapter;
import org.mo.znyunxt.entity.AttendCount;

import java.util.List;

/**
 * Created by moziqi on 2015/3/22 0022.
 */
public class AttendCountAdapter extends BaseAdapter implements IAdapter<AttendCount> {
    private Context mContext;
    private List<AttendCount> attendCountList;

    public AttendCountAdapter(Context mContext, List<AttendCount> attendCountList) {
        this.mContext = mContext;
        this.attendCountList = attendCountList;
    }

    @Override
    public int getCount() {
        if (attendCountList == null) {
            return 0;
        }
        return attendCountList.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_attend_count_list_item, null);
            mViewHolder.tv_student_name = (TextView) convertView.findViewById(R.id.tv_student_name);
            mViewHolder.tv_depart_name = (TextView) convertView.findViewById(R.id.tv_depart_name);
            mViewHolder.tv_cq = (TextView) convertView.findViewById(R.id.tv_cq);
            mViewHolder.tv_cd = (TextView) convertView.findViewById(R.id.tv_cd);
            mViewHolder.tv_qj = (TextView) convertView.findViewById(R.id.tv_qj);
            mViewHolder.tv_qq = (TextView) convertView.findViewById(R.id.tv_qq);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        AttendCount attendRecordDetail = attendCountList.get(position);
        mViewHolder.tv_student_name.setText(attendRecordDetail.getStuname());
        mViewHolder.tv_depart_name.setText(attendRecordDetail.getDepartname());
        mViewHolder.tv_cq.setText(attendRecordDetail.getCq());
        mViewHolder.tv_cd.setText(attendRecordDetail.getCd());
        mViewHolder.tv_qj.setText(attendRecordDetail.getQj());
        mViewHolder.tv_qq.setText(attendRecordDetail.getQq());
        return convertView;
    }

    @Override
    public void update(List<AttendCount> mList) {
        attendCountList = mList;
        notifyDataSetChanged();
    }


    final static class ViewHolder {
        TextView tv_depart_name;
        TextView tv_student_name;
        TextView tv_cq;
        TextView tv_cd;
        TextView tv_qj;
        TextView tv_qq;
    }
}
