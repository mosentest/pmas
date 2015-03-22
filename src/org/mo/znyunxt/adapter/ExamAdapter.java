package org.mo.znyunxt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.mo.pmas.activity.adapter.IAdapter;
import org.mo.znyunxt.entity.AttendRecord;
import org.mo.znyunxt.entity.Exam;

import java.util.List;

/**
 * Created by moziqi on 2015/3/18 0018.
 */
public class ExamAdapter extends BaseAdapter implements IAdapter<AttendRecord> {

    private List<Exam> list;

    private Context mContext;

    public ExamAdapter(List<Exam> list, Context mContext) {
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
        return null;
    }

    @Override
    public void update(List<AttendRecord> mList) {

    }
}
