package org.mo.pmas.activity.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ListView;
import org.mo.pmas.activity.fragment.EnterFragment;
import org.mo.pmas.activity.fragment.SettingFragment;
import org.mo.pmas.entity.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moziqi on 2014/12/27 0027.
 */
public class EnterFragmentPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> list = new ArrayList<Fragment>();

    private Context mContext;

    public EnterFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return EnterFragment.newInstance(mContext);
            case 1:
                return new Fragment();
            case 2:
                return new Fragment();
            case 3:
                return SettingFragment.getInstance(mContext);
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return 4;
    }


}
