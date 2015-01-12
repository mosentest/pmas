package org.mo.pmas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.activity.adapter.ContactGroupAdapter;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.entity.ContactGroup;
import org.mo.pmas.resolver.ContactGroupResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by moziqi on 2015/1/11 0011.
 */
public class ContactGroupActivity extends BaseFramgmentActivity implements View.OnClickListener {

    public static final String TAG = "ContactGroupActivity";

    private SwipeListView mSwipeListView;

    private ContactGroupAdapter contactGroupAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_group_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mSwipeListView = (SwipeListView) findViewById(R.id.lv_contact_group);
        mSwipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {
        ContactGroupResolver contactGroupResolver = new ContactGroupResolver(ContactGroupActivity.this);
        List<ContactGroup> all = contactGroupResolver.findAll();
        contactGroupAdapter = new ContactGroupAdapter(ContactGroupActivity.this, all);
        mSwipeListView.setAdapter(contactGroupAdapter);
        contactGroupAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_group_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                PmasAppliaction.getInstance().exit();
                Intent intent2 = new Intent(ContactGroupActivity.this, EnterActivity.class);
                startActivity(intent2);
                return true;
            case R.id.item_add_contact_group:
                Intent intent = new Intent(ContactGroupActivity.this, ContactGroupAddActivtiy.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PmasAppliaction.getInstance().exit();
        Intent intent2 = new Intent(ContactGroupActivity.this, EnterActivity.class);
        startActivity(intent2);
    }
}