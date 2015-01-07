package org.mo.pmas.bmob.entity.repository;

import android.content.Context;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import org.mo.common.util.ToastUtil;
import org.mo.pmas.activity.R;
import org.mo.pmas.bmob.entity.Note;
import org.mo.pmas.util.ErrorEnum;

import java.util.List;

/**
 * Created by moziqi on 2015/1/6 0006.
 */
public class NoteRepository implements BaseRepository<Note> {
    private Context mContext;

    public NoteRepository(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void save(Note entity) {
        entity.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showShortToast(mContext, mContext.getString(R.string.save_success));
            }

            @Override
            public void onFailure(int i, String s) {
                showErrorIms(i);
            }
        });
    }


    @Override
    public void update(Note entity) {
        entity.update(mContext, new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showShortToast(mContext, mContext.getString(R.string.update_success));
            }

            @Override
            public void onFailure(int i, String s) {
                showErrorIms(i);
            }
        });
    }

    @Override
    public void delete(Note entity) {
        entity.delete(mContext, new DeleteListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showShortToast(mContext, mContext.getString(R.string.delete_success));
            }

            @Override
            public void onFailure(int i, String s) {
                showErrorIms(i);
            }
        });
    }

    @Override
    public Note findOne(String objectId) {

        return null;
    }

    @Override
    public int countAll() {
        return 0;
    }

    @Override
    public List<Note> findAllByLimit(int curPage, int limit) {
        return null;
    }

    private void showErrorIms(int i) {
        ErrorEnum ident = ErrorEnum.ident(i);
        ToastUtil.showShortToast(mContext, ident.getMessage());
    }

}
