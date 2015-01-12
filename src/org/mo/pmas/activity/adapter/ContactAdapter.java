package org.mo.pmas.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.mo.pmas.activity.ContactEidtActivity;
import org.mo.pmas.activity.ContactShowOneInfoActivity;
import org.mo.pmas.activity.R;
import org.mo.pmas.entity.Contact;

import java.util.List;

/**
 * Created by moziqi on 2014/12/27 0027.
 */
public class ContactAdapter extends BaseAdapter implements SectionIndexer {
    private List<Contact> list = null;
    private Context mContext;
    ViewHolder viewHolder = null;

    public ContactAdapter(Context mContext, List<Contact> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<Contact> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {

        final Contact mContact = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_phone_contacts_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.icon);
            viewHolder.m_ll_constact = (LinearLayout) view.findViewById(R.id.ll_constact);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContact.getSortLetters());
            viewHolder.tvLetter.setClickable(false);
            viewHolder.tvLetter.setEnabled(false);
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
            viewHolder.tvLetter.setClickable(false);
            viewHolder.tvLetter.setEnabled(false);
        }

        //初始化数据
        viewHolder.tvTitle.setText(mContact.getName());
        //TODO 设置图片
        viewHolder.imageView.setImageBitmap(mContact.getContactPhoto());

        //TODO 跳转页面
        viewHolder.m_ll_constact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOneContactInThis(mContact);
            }
        });
        return view;

    }

    private void showOneContactInThis(Contact mContact) {
        Intent intent = new Intent(mContext, ContactShowOneInfoActivity.class);
        Bundle mbundle = new Bundle();
        mbundle.putInt("id", mContact.getId());
        intent.putExtras(mbundle);
        mContext.startActivity(intent);
    }


    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageView imageView;
        LinearLayout m_ll_constact;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}