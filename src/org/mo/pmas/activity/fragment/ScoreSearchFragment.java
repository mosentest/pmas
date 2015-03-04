package org.mo.pmas.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.mo.common.util.AlertDateDialogOnEditText;
import org.mo.pmas.activity.R;
import org.mo.pmas.ext.entity.Score;

/**
 * Created by moziqi on 2015/2/5 0005.
 */
public class ScoreSearchFragment extends BaseFragment {

    private OnScoreSearchListener onScoreSearchListener;
    private EditText et_score_search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score_search, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        et_score_search = (EditText) findViewById(R.id.et_score_search_date_up);
        AlertDateDialogOnEditText dateDialogOnEditText = new AlertDateDialogOnEditText(getActivity(), et_score_search, "开始日期");
        et_score_search.setOnTouchListener(dateDialogOnEditText);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onScoreSearchListener = (OnScoreSearchListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement OnScoreSearchListener");
        }
    }

    public interface OnScoreSearchListener {
        public void onScoreSearch(Score entity);
    }
}