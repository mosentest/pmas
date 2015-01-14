package org.mo.taskmanager.receiver;

import org.mo.taskmanager.AlarmActivity;
import org.mo.taskmanager.utils.AlarmUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
	  private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
	  private final String ACTION_MOUNTED = "android.intent.action.MEDIA_MOUNTED";
	  private final String ACTION_UNMOUNTED = "android.intent.action.MEDIA_UNMOUNTED";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String value;
		if (intent.getAction().equals(ACTION_BOOT)) {
//		Toast.makeText(context, "0", Toast.LENGTH_LONG).show();
//		System.out.println("������");
//			setIntent(context,"������");
			setAlarm(context);
			//Intent intent2=new Intent(context,CycleDataActivity.class);
			//context.startActivity(intent2);
		}else if (intent.getAction().equals(ACTION_MOUNTED)) {
//			Toast.makeText(context, "SD������", 0).show();
//			System.out.println("SD������");
//			setIntent(context,"SD������");
		}else if (intent.getAction().equals(ACTION_UNMOUNTED)) {
//			Toast.makeText(context, "SDxie����", 0).show();
//			System.out.println("SDxie����");
//			setIntent(context,"SDxie����");
		}
		
		

	}
	
	private void setAlarm(Context context) {
		AlarmUtils alarmUtils=new AlarmUtils(context);
		alarmUtils.setCycleDataAlarm();
	//	alarmUtils.setUpdateWidgetAlarm();
	}
	

}
