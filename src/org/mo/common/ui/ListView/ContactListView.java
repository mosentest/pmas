package org.mo.common.ui.ListView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.mo.pmas.activity.ContactGroupActivity;
import org.mo.pmas.activity.R;

/**
 * Created by moziqi on 2015/2/3 0003.
 */
public class ContactListView extends ListView {
    private ContactListViewHeader mContactListViewHeader;

    public ContactListView(Context context) {
        super(context);
        headUI(context);
    }

    public ContactListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        headUI(context);
    }

    public ContactListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        headUI(context);
    }

    public void headUI(final Context context) {
        mContactListViewHeader = new ContactListViewHeader(context);
        LinearLayout linearLayout = (LinearLayout) mContactListViewHeader.findViewById(R.id.ll_contact_group);
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactGroupActivity.class);
                context.startActivity(intent);
            }
        });
        addHeaderView(mContactListViewHeader);
    }

}
