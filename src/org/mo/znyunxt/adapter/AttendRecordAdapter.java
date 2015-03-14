package org.mo.znyunxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mo.common.util.DateUtil;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.adapter.IAdapter;
import org.mo.znyunxt.entity.AttendRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by moziqi on 2015/3/14 0014.
 */
public class AttendRecordAdapter extends BaseAdapter implements IAdapter<AttendRecord> {

    private List<AttendRecord> list;

    private Context mContext;

    public AttendRecordAdapter(List<AttendRecord> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_attend_record_list_item, null);
//            convertView.setBackgroundColor(colors[position % 2]);
            mViewHolder.tv_ar_depart_name = (TextView) convertView.findViewById(R.id.tv_ar_depart_name);
            mViewHolder.tv_ar_create_time = (TextView) convertView.findViewById(R.id.tv_ar_create_time);
            mViewHolder.tv_ar_part = (TextView) convertView.findViewById(R.id.tv_ar_part);
            mViewHolder.tv_cq = (TextView) convertView.findViewById(R.id.tv_cq);
            mViewHolder.tv_cd = (TextView) convertView.findViewById(R.id.tv_cd);
            mViewHolder.tv_qj = (TextView) convertView.findViewById(R.id.tv_qj);
            mViewHolder.tv_qq = (TextView) convertView.findViewById(R.id.tv_qq);
            mViewHolder.tv_dj = (TextView) convertView.findViewById(R.id.tv_dj);
            mViewHolder.tv_ts = (TextView) convertView.findViewById(R.id.tv_ts);
            mViewHolder.rl_arli = (LinearLayout) convertView.findViewById(R.id.rl_arli);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        AttendRecord attendRecord = list.get(position);

        String depart_departname = attendRecord.getDepart_departname();
        String id = attendRecord.getId();
        String part = attendRecord.getPart();
        String createdate = attendRecord.getCreatedate();
        String Cd = attendRecord.getCd();
        String cq = attendRecord.getCq();
        String dj = attendRecord.getDj();
        String qj = attendRecord.getQj();
        String qq = attendRecord.getQq();
        String ts = attendRecord.getTs();
        Date date = DateUtil.str2Date(createdate, "yyyy-MM-dd");
        String s = DateUtil.date2Str(date, "yyyy-MM-dd");
        mViewHolder.tv_ar_depart_name.setText(depart_departname);
        mViewHolder.tv_ar_create_time.setText(s);
        mViewHolder.tv_ar_part.setText(part);
        mViewHolder.tv_cq.setText(cq);
        mViewHolder.tv_cd.setText(Cd);
        mViewHolder.tv_qj.setText(qj);
        mViewHolder.tv_qq.setText(qq);
        mViewHolder.tv_dj.setText(dj);
        mViewHolder.tv_ts.setText(ts);
        //TODO 2015-3-12
        return convertView;

    }

    @Override
    public void update(List<AttendRecord> mList) {
        list = mList;
        notifyDataSetChanged();
    }

    final static class ViewHolder {
        TextView tv_ar_depart_name;
        TextView tv_ar_create_time;
        TextView tv_ar_part;
        TextView tv_cq;
        TextView tv_cd;
        TextView tv_qj;
        TextView tv_qq;
        TextView tv_dj;
        TextView tv_ts;
        LinearLayout rl_arli;

    }
}
