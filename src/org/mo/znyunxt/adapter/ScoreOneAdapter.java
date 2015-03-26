package org.mo.znyunxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mo.pmas.activity.R;
import org.mo.pmas.activity.adapter.IAdapter;
import org.mo.znyunxt.entity.Score;

import java.util.List;

/**
 * Created by moziqi on 2015/3/25 0025.
 */
public class ScoreOneAdapter extends BaseAdapter implements IAdapter<Score> {
    private List<Score> list;
    private Context mContext;

    public ScoreOneAdapter(Context context, List<Score> scores) {
        this.mContext = context;
        this.list = scores;
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

            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_score_one_list_item, null);
            mViewHolder.tv_score_departname = (TextView) convertView.findViewById(R.id.tv_score_departname);
            mViewHolder.tv_score_studentName = (TextView) convertView.findViewById(R.id.tv_score_studentName);
            mViewHolder.tv_score_wenli = (TextView) convertView.findViewById(R.id.tv_score_wenli);
            mViewHolder.tv_score_yuwen = (TextView) convertView.findViewById(R.id.tv_score_yuwen);
            mViewHolder.tv_score_shuxue = (TextView) convertView.findViewById(R.id.tv_score_shuxue);
            mViewHolder.tv_score_yingyu = (TextView) convertView.findViewById(R.id.tv_score_yingyu);
            mViewHolder.tv_score_kouyu = (TextView) convertView.findViewById(R.id.tv_score_kouyu);
            mViewHolder.tv_score_zhenzhi = (TextView) convertView.findViewById(R.id.tv_score_zhenzhi);
            mViewHolder.tv_score_lishi = (TextView) convertView.findViewById(R.id.tv_score_lishi);
            mViewHolder.tv_score_dili = (TextView) convertView.findViewById(R.id.tv_score_dili);
            mViewHolder.tv_score_wuli = (TextView) convertView.findViewById(R.id.tv_score_wuli);
            mViewHolder.tv_score_huaxue = (TextView) convertView.findViewById(R.id.tv_score_huaxue);
            mViewHolder.tv_score_shengwu = (TextView) convertView.findViewById(R.id.tv_score_shengwu);
            mViewHolder.tv_score_zonghe = (TextView) convertView.findViewById(R.id.tv_score_zonghe);
            mViewHolder.tv_score_zongfen = (TextView) convertView.findViewById(R.id.tv_score_zongfen);
            mViewHolder.tv_score_mingci = (TextView) convertView.findViewById(R.id.tv_score_mingci);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Score attendRecordDetail = list.get(position);
        mViewHolder.tv_score_departname.setText(attendRecordDetail.getDepart_departname());
        mViewHolder.tv_score_studentName.setText(attendRecordDetail.getStudentName());
        mViewHolder.tv_score_wenli.setText(attendRecordDetail.getWenli());
        mViewHolder.tv_score_yuwen.setText(attendRecordDetail.getYuwen());
        mViewHolder.tv_score_shuxue.setText(attendRecordDetail.getShuxue());
        mViewHolder.tv_score_yingyu.setText(attendRecordDetail.getYingyu());
        mViewHolder.tv_score_kouyu.setText(attendRecordDetail.getKouyu());
        mViewHolder.tv_score_zhenzhi.setText(attendRecordDetail.getZhenzhi());
        mViewHolder.tv_score_lishi.setText(attendRecordDetail.getLishi());
        mViewHolder.tv_score_dili.setText(attendRecordDetail.getDili());
        mViewHolder.tv_score_wuli.setText(attendRecordDetail.getWuli());
        mViewHolder.tv_score_huaxue.setText(attendRecordDetail.getHuaxu());
        mViewHolder.tv_score_shengwu.setText(attendRecordDetail.getShengwu());
        mViewHolder.tv_score_zonghe.setText(attendRecordDetail.getZonghe());
        mViewHolder.tv_score_zongfen.setText(attendRecordDetail.getTotal());
        mViewHolder.tv_score_mingci.setText(attendRecordDetail.getRanking());
        return convertView;
    }

    @Override
    public void update(List<Score> mList) {
        list = mList;
        notifyDataSetChanged();
    }


    final static class ViewHolder {
        TextView tv_score_departname;
        TextView tv_score_studentName;
        TextView tv_score_wenli;
        TextView tv_score_yuwen;
        TextView tv_score_shuxue;
        TextView tv_score_yingyu;
        TextView tv_score_kouyu;
        TextView tv_score_zhenzhi;
        TextView tv_score_lishi;
        TextView tv_score_dili;
        TextView tv_score_wuli;
        TextView tv_score_huaxue;
        TextView tv_score_shengwu;
        TextView tv_score_zonghe;
        TextView tv_score_zongfen;
        TextView tv_score_mingci;
    }
}
