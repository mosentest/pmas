package org.mo.pmas.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import org.mo.common.util.ToastUtil;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.util.ErrorEnum;

/**
 * Created by moziqi on 2015/1/4 0004.
 */
public class BaseFragment extends Fragment {
    private final static String TAG = "BaseFragment";
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

    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }

    public void showErrorIms(int i) {
        ErrorEnum ident = ErrorEnum.ident(i);
        ToastUtil.showShortToast(getActivity(), ident.getMessage());
        Log.e(TAG, ident.getMessage());
    }
}
