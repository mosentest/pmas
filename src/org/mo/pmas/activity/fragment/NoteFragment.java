package org.mo.pmas.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import org.mo.common.util.DateUtil;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.fragment.listview.XListView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by moziqi on 2015/1/5 0005.
 */
public class NoteFragment extends BaseFragment implements XListView.IXListViewListener {
    private static NoteFragment mNoteFragment;
    private Context mContext;
    private View mRootView;
    private XListView mXListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;
    private static Calendar mCalendar;

    public static NoteFragment getInstance(Context context) {
        if (mNoteFragment == null) {
            mNoteFragment = new NoteFragment(context);
        }
        return mNoteFragment;
    }

    private NoteFragment(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCalendar = Calendar.getInstance();
        mRootView = inflater.inflate(R.layout.framgment_note_list, null);
        mXListView = (XListView) mRootView.findViewById(R.id.note_list);
        mAdapter = new ArrayAdapter<String>(mContext, R.layout.list_item, items);
        mXListView.setAdapter(mAdapter);
        mXListView.setXListViewListener(this);
        mHandler = new Handler();
        return mRootView;
    }

    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            items.add("refresh cnt " + (++start));
        }
    }

    private void onLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
        mXListView.setRefreshTime(DateUtil.date2Str(mCalendar,"yyyy-MM-dd HH:mm:ss"));
        mCalendar = Calendar.getInstance();
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = ++refreshCnt;
                items.clear();
                geneItems();
                // mAdapter.notifyDataSetChanged();
                mAdapter = new ArrayAdapter<String>(mContext, R.layout.list_item, items);
                mXListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }
}