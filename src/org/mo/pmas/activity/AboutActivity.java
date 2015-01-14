package org.mo.pmas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import org.mo.common.activity.BaseFramgmentActivity;

/**
 * Created by moziqi on 2015/1/14 0014.
 */
public class AboutActivity extends BaseFramgmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return true;
        }
    }
}