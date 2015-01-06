package org.mo.pmas.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.mo.common.activity.BaseFramgmentActivity;

/**
 * Created by moziqi on 2015/1/6 0006.
 */
public class NoteAddActivity extends BaseFramgmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}