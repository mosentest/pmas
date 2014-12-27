package org.mo.pmas.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import org.mo.pmas.activity.fragment.EnterFragment;

/**
 * Created by moziqi on 2014/12/27 0027.
 */
public class EnterFragmentPageAdapter extends FragmentPagerAdapter {


    public EnterFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EnterFragment.newInstance(position);
            case 1:
                return EnterFragment.newInstance(position);
            case 2:
                return EnterFragment.newInstance(position);
            case 3:
                return EnterFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


}
