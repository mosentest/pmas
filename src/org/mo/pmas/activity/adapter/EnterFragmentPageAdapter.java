package org.mo.pmas.activity.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moziqi on 2014/12/27 0027.
 */
public class EnterFragmentPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> list = new ArrayList<Fragment>();

    public EnterFragmentPageAdapter(FragmentManager fm, Context context, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment page = null;
        if (list.size() > position) {
            page = list.get(position);
            if (page != null) {
                return page;
            }
        }
        while (position >= list.size()) {
            list.add(null);
        }
        page = list.get(position);
        list.set(position, page);
        return page;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
