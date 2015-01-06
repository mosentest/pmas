package org.mo.pmas.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.Toast;
import org.mo.pmas.activity.application.PmasAppliaction;

/**
 * Created by moziqi on 2015/1/4 0004.
 */
public class BaseFragment extends Fragment {

    public LayoutInflater mInflater;
    private Toast mToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mInflater = LayoutInflater.from(getActivity());
    }

    public void ShowToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
