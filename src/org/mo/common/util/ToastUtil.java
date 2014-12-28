package org.mo.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Uno on 2014/12/27.
 */
public class ToastUtil {
    public static void showShortToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}

