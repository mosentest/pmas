package org.mo.taskmanager.service;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import org.mo.pmas.activity.R;
import org.mo.taskmanager.receiver.DailyWidget;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateWidgetService extends Service {
    private ScreenOffReceiver offreceiver;
    private ScreenOnReceiver onreceiver;
    private Timer timer;
    private TimerTask task;
    private AppWidgetManager awm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ScreenOffReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopTimer();
        }
    }

    private class ScreenOnReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            startTimer();
        }
    }

    @Override
    public void onCreate() {
        onreceiver = new ScreenOnReceiver();
        offreceiver = new ScreenOffReceiver();
        registerReceiver(onreceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(offreceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        awm = AppWidgetManager.getInstance(this);
        startTimer();
        Log.i("ZENG", "onCreate==");
        super.onCreate();
    }

    private void startTimer() {
        if (timer == null && task == null) {
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    Log.i("ZENG", "����widget");
                    ComponentName provider = new ComponentName(UpdateWidgetService.this, DailyWidget.class);
                    RemoteViews views = new RemoteViews(getPackageName(), R.layout.process_widget);
                    views.setTextViewText(R.id.tv_month, "2015-02");
                    Intent intent = new Intent();
                    intent.setAction("org.mo.taskmanager.addtask");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    views.setOnClickPendingIntent(R.id.query_iv3, pendingIntent);
                    awm.updateAppWidget(provider, views);
                }
            };
            timer.schedule(task, 0, 3000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(offreceiver);
        unregisterReceiver(onreceiver);
        offreceiver = null;
        onreceiver = null;
        stopTimer();
    }

    private void stopTimer() {
        if (timer != null && task != null) {
            timer.cancel();
            task.cancel();
            timer = null;
            task = null;
        }
    }
}




















