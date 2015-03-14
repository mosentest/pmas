package org.mo.pmas.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.mo.common.util.DateUtil;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.adapter.NoteAdapter;
import org.mo.pmas.activity.fragment.listview.XListView;
//import org.mo.pmas.bmob.entity.Note;
import org.mo.pmas.service.NoteService;

import java.util.Calendar;
import java.util.List;

/**
 * Created by moziqi on 2015/1/5 0005.
 */
public class NoteFragment extends BaseFragment implements XListView.IXListViewListener {
    private static NoteFragment mNoteFragment;
    private Context mContext;
    private XListView mXListView;
    private GridView mGridView;
    private NoteAdapter mNoteAdapter;
//    private List<Note> mNoteLists;
    private Handler mHandler;
    private int currentPage = 1;
    private int size = 10;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCalendar = Calendar.getInstance();
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView = (GridView) findViewById(R.id.gv_note_list);
        findAll();
    }



    private void findAll() {
        NoteService noteService = new NoteService(mContext);
        List<org.mo.pmas.entity.Note> noteList = noteService.getAll();
//        Log.e("d",noteList.toString());
        mNoteAdapter = new NoteAdapter(noteList, mContext);
        mGridView.setAdapter(mNoteAdapter);
    }

    private void onLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
        mXListView.setRefreshTime(DateUtil.date2Str(mCalendar, "yyyy-MM-dd HH:mm:ss"));
        mCalendar = Calendar.getInstance();
    }

    /**
     * 下拉更新
     */
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findAll();
                onLoad();
            }
        }, 2000);
    }

    /**
     * 查找更多
     */
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                findAllByLoadMore();
                onLoad();
            }
        }, 2000);
    }
}