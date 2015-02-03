package org.mo.pmas.activity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mo.common.util.ToastUtil;
import org.mo.pmas.activity.NoteAddActivity;
import org.mo.pmas.activity.R;
import org.mo.pmas.entity.Note;
import org.mo.pmas.service.NoteService;
import org.mo.taskmanager.AddTaskActivity;

import java.util.List;

/**
 * Created by moziqi on 2015/1/6 0006.
 */
public class NoteAdapter extends BaseAdapter {

    private List<Note> mNoteLists;
    private Context mContext;

    public NoteAdapter(List<Note> mNoteLists, Context mContext) {
        this.mNoteLists = mNoteLists;
        this.mContext = mContext;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param mNoteLists
     */
    public void updateListView(List<Note> mNoteLists) {
        this.mNoteLists = mNoteLists;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mNoteLists != null)
            return mNoteLists.size();
        else return 0;
    }

    @Override
    public Note getItem(int position) {
        return mNoteLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        convertView = initViewUI(position, convertView, mViewHolder);
        return convertView;
    }

    private View initViewUI(final int position, View convertView, ViewHolder mViewHolder) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_note_list_item, null);
            mViewHolder.m_tv_note_list_title = (TextView) convertView.findViewById(R.id.tv_note_list_title);
            mViewHolder.m_tv_note_list_time = (TextView) convertView.findViewById(R.id.tv_note_list_time);
            mViewHolder.m_tv_note_list_content = (TextView) convertView.findViewById(R.id.tv_note_list_content);
            mViewHolder.mRelativeLayout = (LinearLayout) convertView.findViewById(R.id.ll_note);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //初始化数据
        mViewHolder.m_tv_note_list_title.setText(mNoteLists.get(position).getTitle());
//        Date date = DateUtil.str2Date(mNoteLists.get(position).getCreatedAt(), "yyyy-MM-dd HH:mm:ss");
//        String s = null;
//        if (DateUtil.getCurDayStr().equals(DateUtil.date2Str(date, "yyyy-MM-dd"))) {
//            Date date1 = DateUtil.str2Date(mNoteLists.get(position).getCreatedAt(), "HH:mm:ss");
//            s = DateUtil.date2Str(date1, "HH:mm:ss");
//        } else {
//            Date date1 = DateUtil.str2Date(mNoteLists.get(position).getCreatedAt(), "yyyy-MM-dd");
//            s = DateUtil.date2Str(date1, "yyyy-MM-dd");
//        }
        mViewHolder.m_tv_note_list_time.setText(mNoteLists.get(position).getUpdateDate());
        mViewHolder.m_tv_note_list_content.setText(mNoteLists.get(position).getContent());

        mViewHolder.mRelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
                builder2.setTitle("提示");
                final String[] arr = new String[]{"修改记事", "删除记事"};
                builder2.setItems(arr,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(mContext, NoteAddActivity.class);
                                    intent.putExtra("oper", "update");
                                    intent.putExtra("id", mNoteLists.get(position).getId());
//                                    startActivityForResult(intent, 5);
                                    mContext.startActivity(intent);
                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                    builder.setTitle("删除记事");
                                    builder.setMessage("是否删除该条记事");
                                    builder.setPositiveButton("删除",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    NoteService noteService = new NoteService(mContext);
                                                    noteService.deleteById(mNoteLists.get(position).getId());
                                                    mNoteLists.remove(position);
                                                    notifyDataSetChanged();
                                                    ToastUtil.showLongToast(mContext, "删除记事");
                                                }
                                            });
                                    builder.setNegativeButton("取消", null);
                                    builder.show();
                                }
                            }
                        });
                builder2.setNegativeButton("取消", null);
                builder2.create().show();
                return true;
            }
        });
        return convertView;
    }

    final static class ViewHolder {
        TextView m_tv_note_list_title;
        TextView m_tv_note_list_time;
        TextView m_tv_note_list_content;
        LinearLayout mRelativeLayout;
    }
}
