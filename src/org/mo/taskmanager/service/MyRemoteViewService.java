package org.mo.taskmanager.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import org.mo.pmas.activity.R;
import org.mo.taskmanager.bean.TaskDetails;
import org.mo.taskmanager.db.TaskDBOperator;

import java.util.List;

public class MyRemoteViewService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new MyRemoteViewsFactory(this, intent);
	}

	private class MyRemoteViewsFactory implements
			RemoteViewsFactory {
		private Context mContext;
		private int mAppWidgetId;
		private String date;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		private List<TaskDetails> data;

		public MyRemoteViewsFactory(Context context, Intent intent) {
			mContext = context;
			 mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
		                AppWidgetManager.INVALID_APPWIDGET_ID);
			date = intent.getStringExtra("date");

		}


		private void initViewData() {
			TaskDBOperator helper = new TaskDBOperator(mContext);
			data = helper.findPart(0, 100, date, date);
		}

		@Override
		public void onCreate() {

		}


		@Override
		public RemoteViews getViewAt(int position) {
			RemoteViews rv = new RemoteViews(mContext.getPackageName(),R.layout.widget_lv_item);
			rv.setTextViewText(R.id.wg_content, data.get(position).getContent());
			rv.setTextViewText(R.id.wg_datetime, data.get(position).getStartTime() + "-" + data.get(position).getEndTime());
			Intent fillInIntent = new Intent();
			fillInIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, position);
			rv.setOnClickFillInIntent(R.id.itemLayout, fillInIntent);

			return rv;
		}

		@Override
		public void onDataSetChanged() {
			initViewData();
		}

		@Override
		public void onDestroy() {
			data.clear();
		}

		@Override
		public int getCount() {
			if (data != null) {
				//System.out.println( "data size:"+data.size());
				return data.size();
			}
			return 0;
		}

		@Override
		public RemoteViews getLoadingView() {
			return null;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}


	}
}
