package org.mo.pmas.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by moziqi on 2015/1/7 0007.
 */
public class NetService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return netBind;
    }

    //上下文
    Context context;

    //网络连接状态
    boolean isConntect = true;

    //定时器
    Timer timer = new Timer();

    @Override
    public void onCreate() {
        // 注册广播   检查网络状态
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
        Log.i("tag", "service**" + Thread.currentThread().getId());
        super.onCreate();
    }

    //网络检查广播接受者
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 启动定时任务
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                //立即启动，每隔5秒执行一次Task
                Log.i("tag", "receiver**" + Thread.currentThread().getId());
                timer.schedule(new NetTask(context), 0, 5 * 1000);
            }
        }
    };

    //具体的TimerTask实现类
    class NetTask extends TimerTask {

        public NetTask(Context context1) {
            context = context1;
        }


        @Override
        public void run() {

            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isConnectNet()) {
                isConntect = true;
            } else {
                isConntect = false;
            }
            Log.i("tag", "run**" + Thread.currentThread().getId());
            if (onGetConnectState != null) {
                onGetConnectState.GetState(isConntect); // 通知网络状态改变
            }
        }

    }

    // 网络状态改变之后，通过此接口的实例通知当前网络的状态，此接口在Activity中注入实例对象
    public interface GetConnectState {
        public void GetState(boolean isConnected);
    }

    private GetConnectState onGetConnectState;


    public void setOnGetConnectState(GetConnectState onGetConnectState) {
        this.onGetConnectState = onGetConnectState;
    }


    /**
     * 判断是否连通网络
     *
     * @return
     */
    private boolean isConnectNet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo Mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        NetworkInfo Wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (Mobile.getState().equals(NetworkInfo.State.DISCONNECTED) && Wifi.getState().equals(NetworkInfo.State.DISCONNECTED)) {
            return false;
        }
        return true;
    }


    //定义一个Bind类
    private NetBind netBind = new NetBind();

    public class NetBind extends Binder {
        public NetService getNetService() {
            return NetService.this;
        }
    }


    //销毁广播、停止定时轮询
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        timer.cancel();
        unregisterReceiver(receiver);
    }
}
