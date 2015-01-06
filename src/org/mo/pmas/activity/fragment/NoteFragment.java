package org.mo.pmas.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.mo.pmas.activity.R;

/**
 * Created by moziqi on 2015/1/5 0005.
 */
public class NoteFragment extends BaseFragment {
    private static NoteFragment mNoteFragment;
    private Context mContext;
    private View mRootView;

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
        mRootView = inflater.inflate(R.layout.framgment_note_list, null);
        return mRootView;
    }
}