package org.mo.znyunxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mo.common.ui.MarqueeTextView;
import org.mo.common.util.DateUtil;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.adapter.IAdapter;
import org.mo.znyunxt.entity.Exam;

import java.util.Date;
import java.util.List;

/**
 * Created by moziqi on 2015/3/18 0018.
 */
public class ExamAdapter extends BaseAdapter implements IAdapter<Exam> {

    private List<Exam> list;

    private Context mContext;

    public ExamAdapter(Context mContext, List<Exam> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_score_list_item, null);
            mViewHolder.tv_exam_name = (MarqueeTextView) convertView.findViewById(R.id.tv_exam_name);
            mViewHolder.tv_exam_time = (TextView) convertView.findViewById(R.id.tv_exam_time);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Exam attendRecordDetail = list.get(position);
        mViewHolder.tv_exam_name.setText(attendRecordDetail.getName());
        Date date = DateUtil.str2Date(attendRecordDetail.getCreateDatetime());
        String dateString = DateUtil.date2Str(date);
        mViewHolder.tv_exam_time.setText(dateString);
        return convertView;
    }

    @Override
    public void update(List<Exam> mList) {
        list = mList;
        notifyDataSetChanged();
    }

    final static class ViewHolder {
        MarqueeTextView tv_exam_name;
        TextView tv_exam_time;
    }
}
