package org.mo.pmas.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import org.mo.common.util.DateUtil;
import org.mo.common.util.ToastUtil;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.fragment.listview.XListView;
import org.mo.pmas.bmob.entity.MyUser;
import org.mo.pmas.bmob.entity.Note;
import org.mo.pmas.activity.adapter.NoteAdapter;

import java.util.Calendar;
import java.util.List;

/**
 * Created by moziqi on 2015/1/5 0005.
 */
public class NoteFragment extends BaseFragment implements XListView.IXListViewListener {
    private static NoteFragment mNoteFragment;
    private Context mContext;
    private XListView mXListView;
    private NoteAdapter mNoteAdapter;
    private List<Note> mNoteLists;
    //    private LoadingView mLoadingView;
    private Handler mHandler;
    private int currentPage = 1;
    private int size = 10;
    private static Calendar mCalendar;
    private MyUser myUser;

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
        myUser = MyUser.getCurrentUser(getActivity(), MyUser.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.framgment_note_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mXListView = (XListView) findViewById(R.id.note_list);
//        mLoadingView = (LoadingView) findViewById(R.id.note_loading);
        findAll();
        mXListView.setXListViewListener(this);
        mXListView.setPullLoadEnable(true);
    }

    private void findAllByLoadMore() {
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);// 先从缓存获取数据，如果没有，再从网络获取。
        query.setLimit(size * currentPage);
        if (myUser == null) {
            return;
        }
        query.addWhereRelatedTo("notes", new BmobPointer(myUser));
        query.order("-createdAt");
        query.findObjects(mContext, new FindListener<Note>() {
            @Override
            public void onSuccess(List<Note> notes) {
                mNoteLists = notes;
                mNoteAdapter = new NoteAdapter(mNoteLists, mContext);
                mXListView.setAdapter(mNoteAdapter);
                mNoteAdapter.updateListView(mNoteLists);
                currentPage++;
            }

            @Override
            public void onError(int i, String s) {
                showErrorIms(i);
//                ToastUtil.showLongToast(mContext, i + s);
            }
        });
    }

    private void findAll() {
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);// 先从缓存获取数据，如果没有，再从网络获取。
        query.setLimit(size);
        if (myUser == null) {
            return;
        }
        query.addWhereRelatedTo("notes", new BmobPointer(myUser));
        query.order("-createdAt");
        query.findObjects(mContext, new FindListener<Note>() {
            @Override
            public void onSuccess(List<Note> notes) {
                mNoteLists = notes;
                mNoteAdapter = new NoteAdapter(mNoteLists, mContext);
                mXListView.setAdapter(mNoteAdapter);
                mNoteAdapter.updateListView(mNoteLists);
                currentPage++;
            }

            @Override
            public void onError(int i, String s) {
                showErrorIms(i);
            }
        });
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
                findAllByLoadMore();
                onLoad();
            }
        }, 2000);
    }
}