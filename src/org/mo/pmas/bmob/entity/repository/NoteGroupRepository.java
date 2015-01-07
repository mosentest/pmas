package org.mo.pmas.bmob.entity.repository;

import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import org.mo.common.util.ToastUtil;
import org.mo.pmas.bmob.entity.NoteGroup;
import org.mo.pmas.util.ErrorEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moziqi on 2015/1/7 0007.
 */
public class NoteGroupRepository implements BaseRepository<NoteGroup> {
    private Context mContext;
    private int count;

    public NoteGroupRepository(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void save(NoteGroup entity) {

    }

    @Override
    public void update(NoteGroup entity) {

    }

    @Override
    public void delete(NoteGroup entity) {

    }

    @Override
    public NoteGroup findOne(String objectId) {
        return null;
    }

    @Override
    public int countAll() {
        BmobQuery<NoteGroup> query = new BmobQuery<NoteGroup>();
        query.count(mContext, NoteGroup.class, new CountListener() {
            @Override
            public void onSuccess(int i) {
                count = i;
            }

            @Override
            public void onFailure(int i, String s) {
                showErrorIms(i);
            }
        });
        return count;
    }

    /**
     * @param curPage 从1开始算起
     * @param mLimit  显示多少行
     * @return
     */
    @Override
    public List<NoteGroup> findAllByLimit(int curPage, int mLimit) {
        final List<NoteGroup> groups;
        groups = new ArrayList<NoteGroup>();
        BmobQuery<NoteGroup> query = new BmobQuery<NoteGroup>();
        //分页查询
        if (mLimit <= 15) {
            query.setLimit(limit);
            query.setSkip((curPage - 1) * limit);
        } else {
            query.setLimit(mLimit);
            query.setSkip((curPage - 1) * mLimit);
        }
        query.order("createdAt");
        query.findObjects(mContext, new FindListener<NoteGroup>() {
            @Override
            public void onSuccess(List<NoteGroup> noteGroups) {
                Log.e("noteGroups:", noteGroups.size() + "");
                groups.addAll(noteGroups);
            }

            @Override
            public void onError(int i, String s) {
                showErrorIms(i);
            }
        });
        Log.e("groupsize:", groups.size() + "");
        return groups;
    }


    private void showErrorIms(int i) {
        ErrorEnum ident = ErrorEnum.ident(i);
        ToastUtil.showShortToast(mContext, ident.getMessage());
    }
}
