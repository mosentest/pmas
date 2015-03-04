package org.mo.pmas.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.activity.fragment.ScoreSearchFragment;
import org.mo.pmas.activity.fragment.ScoreShowFragment;
import org.mo.pmas.ext.entity.Score;

/**
 * Created by moziqi on 2015/2/4 0004.
 */
public class ScoreActivity extends BaseFramgmentActivity implements ScoreSearchFragment.OnScoreSearchListener {
    private ScoreShowFragment scoreShowFragment;
    private ScoreSearchFragment scoreSearchFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        scoreShowFragment = (ScoreShowFragment) supportFragmentManager.findFragmentById(R.id.fl_score_content);
        if (scoreShowFragment == null) {
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();

            scoreShowFragment = new ScoreShowFragment();
            scoreSearchFragment = new ScoreSearchFragment();

            transaction.add(R.id.fl_score_content, scoreShowFragment);
            transaction.add(R.id.fl_score_search, scoreSearchFragment);

            transaction.commit();
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void toInitUI() {
    }

    @Override
    protected void toUIOper() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_score_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScoreSearch(Score entity) {

    }
}