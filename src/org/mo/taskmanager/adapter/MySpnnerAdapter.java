package org.mo.taskmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MySpnnerAdapter extends BaseAdapter {
	private Context context;
	private String[] list;

	public MySpnnerAdapter(Context context, String[] list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.length;
	}

	@Override
	public Object getItem(int position) {
		if (list == null) {
			return null;
		}
		return list[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//View view = View.inflate(context, R.layout.sp_item, null);
		TextView tView = new TextView(context);
		tView.setTextColor(Color.parseColor("#000000"));
		tView.setGravity(Gravity.CENTER);
		tView.setTextSize(20);
		tView.setText(list[position]);
		return tView;
	}

}
