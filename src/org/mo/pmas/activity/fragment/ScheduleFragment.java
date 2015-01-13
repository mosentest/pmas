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
public class ScheduleFragment extends BaseFragment {
    private static ScheduleFragment mCalenderFragment;
    private Context mContext;
    private View mRootView;

    public static ScheduleFragment getInstance(Context context) {
        if (mCalenderFragment == null) {
            mCalenderFragment = new ScheduleFragment(context);
        }
        return mCalenderFragment;
    }

    private ScheduleFragment(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_schedule_list, null);
        return mRootView;
    }
}
