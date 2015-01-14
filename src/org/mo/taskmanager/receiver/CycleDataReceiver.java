package org.mo.taskmanager.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.mo.taskmanager.AlarmActivity;
import org.mo.taskmanager.bean.TaskDetails;
import org.mo.taskmanager.db.TaskDBOperator;
import org.mo.taskmanager.utils.AlarmUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CycleDataReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		TaskDBOperator operator = new TaskDBOperator(context);
		String date = dateFormat.format(new Date());
		if (operator.checkCycle(date) == false) {

			if (operator.cycleData(date)) {
				List<TaskDetails> tasks = operator.findAll(date);
				AlarmUtils alarmUtils = new AlarmUtils(context);
				for (TaskDetails taskDetails : tasks) {
					alarmUtils.setAlarm(taskDetails);
				}
				if (tasks.size() > 0) {
					operator.addCycle(date);
					
				}
			}
		}
	}

}
