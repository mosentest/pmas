package org.mo.common.ui.ListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.mo.pmas.activity.R;

/**
 * Created by moziqi on 2015/2/3 0003.
 */
public class ContactListViewHeader extends LinearLayout {
    private LinearLayout mContainer;

    public ContactListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    public ContactListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ContactListViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.contact_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);
    }
}
