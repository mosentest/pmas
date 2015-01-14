package org.mo.taskmanager.receiver;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import org.mo.pmas.activity.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerUpdateWidgetReceiver extends BroadcastReceiver {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	@Override
	public void onReceive(Context context, Intent intent) {
		AppWidgetManager awm = AppWidgetManager.getInstance(context);
		String conditonDate = dateFormat.format(new Date());
		ComponentName provider = new ComponentName(context, DailyWidget.class);
		int[] appids = awm.getAppWidgetIds(provider);
		RemoteViews rv = new RemoteViews(context.getPackageName(),
				R.layout.process_widget);
		rv.setTextViewText(R.id.tv_month, conditonDate + " �ճ�");

		awm.notifyAppWidgetViewDataChanged(appids, R.id.wd_lv);
		awm.updateAppWidget(provider, rv);

		System.out.println("�Զ������widget��.....");
	}

}
