package org.mo.taskmanager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.mo.taskmanager.AddTaskActivity;
import org.mo.taskmanager.AlarmActivity;
import org.mo.taskmanager.bean.TaskDetails;
import org.mo.taskmanager.receiver.CustomerUpdateWidgetReceiver;
import org.mo.taskmanager.receiver.CycleDataReceiver;

import android.R.integer;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmUtils {

	private Context context;
	AlarmManager aManager;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public AlarmUtils(Context context) {
		this.context = context;
		aManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
	}

	public void setCycleDataAlarm() {
		Intent intent = new Intent(context, CycleDataReceiver.class); 
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		aManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 1000 * 60 * 60 * 4, pi);

	}

	public void setUpdateWidgetAlarm() {
		Intent intents = new Intent(context, CustomerUpdateWidgetReceiver.class); 
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intents, 0);
		aManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 1000 * 60 * 60, pi);
	}

	public boolean setAlarm(TaskDetails taskDetails) {

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(taskDetails.getDate() + " "
					+ taskDetails.getReminderDate()));
			if (System.currentTimeMillis() < calendar.getTimeInMillis()) {

				Intent intent = new Intent(context, AlarmActivity.class); 
				intent.putExtra("content", taskDetails.getContent());
				PendingIntent pi = PendingIntent.getActivity(context,
						taskDetails.get_id(), intent,
						Intent.FLAG_ACTIVITY_NEW_TASK);
				aManager.set(AlarmManager.RTC_WAKEUP,
						calendar.getTimeInMillis(), pi);
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void cancleAlarm() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Intent intent = new Intent(context, CycleDataReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		aManager.cancel(pi);
	}

}
