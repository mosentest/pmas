package org.mo.pmas.activity.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import org.mo.common.util.AlertDateDialogOnEditText;
import org.mo.pmas.activity.R;
import org.mo.pmas.ext.entity.Score;

import java.util.Calendar;

/**
 * Created by moziqi on 2015/2/5 0005.
 */
public class ScoreSearchFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_score_search_date_up;
    private EditText et_score_search_date_down;

    private DatePickerDialog.OnDateSetListener changerListener;
    private DatePickerDialog datePickerDialog;

    public interface OnScoreSearchListener {
        public void onScoreSearch(String beginDate, String endDate);
    }

    //接口回调
    public void getEditText(OnScoreSearchListener callBack) {
        String msg1 = et_score_search_date_up.getText().toString();
        String msg2 = et_score_search_date_down.getText().toString();
        callBack.onScoreSearch(msg1, msg2);
    }

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
        et_score_search_date_up = (EditText) findViewById(R.id.et_score_search_date_up);
        et_score_search_date_down = (EditText) findViewById(R.id.et_score_search_date_down);

        et_score_search_date_up.setOnClickListener(this);
        et_score_search_date_down.setOnClickListener(this);
        et_score_search_date_up.setFocusable(false);
        et_score_search_date_down.setFocusable(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_score_search_date_up:
                createDetePickerDialog(R.id.et_score_search_date_up);
                break;
            case R.id.et_score_search_date_down:
                createDetePickerDialog(R.id.et_score_search_date_down);
                break;
        }
    }


    private void createDetePickerDialog(final int id) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        changerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                System.out.println("year" + year + "monthOfYear" + monthOfYear + "monthOfYear" + dayOfMonth);
                int ccmonth = monthOfYear + 1;
                String monthString = String.valueOf(ccmonth);
                if (monthString.length() == 1) {
                    monthString = "0" + monthString;
                }
                String dayString = String.valueOf(dayOfMonth);
                if (dayString.length() == 1) {
                    dayString = "0" + dayString;
                }
                String selectDateString = year + "-" + monthString + "-" + dayString;
                if (id == R.id.et_score_search_date_up) {
                    et_score_search_date_up.setText(selectDateString);
                } else if (id == R.id.et_score_search_date_down) {
                    et_score_search_date_down.setText(selectDateString);
                }

            }
        };
        datePickerDialog = new DatePickerDialog(getActivity(), changerListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}