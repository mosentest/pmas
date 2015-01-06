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
public class CalenderFragment extends BaseFragment {
    private static CalenderFragment mCalenderFragment;
    private Context mContext;
    private View mRootView;

    public static CalenderFragment getInstance(Context context) {
        if (mCalenderFragment == null) {
            mCalenderFragment = new CalenderFragment(context);
        }
        return mCalenderFragment;
    }

    private CalenderFragment(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_calender, null);
        return mRootView;
    }
}
