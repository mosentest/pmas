package org.mo.taskmanager;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RelativeLayout.LayoutParams;
import org.mo.common.util.AppLogger;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.fragment.BaseFragment;
import org.mo.taskmanager.adapter.MySpnnerAdapter;
import org.mo.taskmanager.bean.TaskDetails;
import org.mo.taskmanager.db.TaskDBOperator;
import org.mo.taskmanager.utils.CalendarUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class QueryFragment extends BaseFragment implements OnClickListener {
    private Context context;
    private String[] condition = {"日","周","月"};
    private String[] cycle = {"不重复", "每天", "每周", "每月", "每年"};
    int[] colors = new int[7];
    private ListView lv;
    private TaskDBOperator dbOperator;
    private MyListAdapter dapter;
    List<TaskDetails> list = new ArrayList<TaskDetails>();
    private int offset;
    private int maxno;
    private String conditonDate;
    private Spinner spCondition;
    private String periodStartDate;
    private String periodEndDate;
    private MySpnnerAdapter spAdapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String condtion;
    private int flag = 0;
    private TextView tvCondtion;
    private static QueryFragment qf;


    public QueryFragment(Context context) {
        this.context = context;
    }

    public static QueryFragment getInstance(Context context){
        if(qf == null)
            qf= new QueryFragment(context);
        return qf;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.query_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spCondition = (Spinner) getActivity().findViewById(R.id.sp_condition);
        tvCondtion = (TextView) getActivity().findViewById(R.id.tv_conditon);
        ImageView ivleft = (ImageView) getActivity().findViewById(R.id.query_iv1);
        ImageView ivright = (ImageView) getActivity().findViewById(R.id.query_iv2);
        lv = (ListView) getActivity().findViewById(R.id.query_lv);
        String date = dateFormat.format(new Date());
        tvCondtion.setText(CalendarUtils.getDateFormate(date, true));
        lv.setDividerHeight(0);
        ivleft.setOnClickListener(this);
        ivright.setOnClickListener(this);
        offset = 0;
        maxno = 1000;
        spCondition.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                conditonDate = dateFormat.format(new Date());
                fillListViewData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spAdapter = new MySpnnerAdapter(getActivity(), condition);
        spCondition.setAdapter(spAdapter);
        dbOperator = new TaskDBOperator(getActivity());
        conditonDate = dateFormat.format(new Date());
        colors[0] = Color.parseColor(getActivity().getResources().getString(R.string.health));
        colors[1] = Color.parseColor(getActivity().getResources().getString(R.string.family));
        colors[2] = Color.parseColor(getActivity().getResources().getString(R.string.friend));
        colors[3] = Color.parseColor(getActivity().getResources().getString(R.string.love));
        colors[4] = Color.parseColor(getActivity().getResources().getString(R.string.work));
        colors[5] = Color.parseColor(getActivity().getResources().getString(R.string.study));
        colors[6] = Color.parseColor(getActivity().getResources().getString(R.string.interest));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_iv1:
                if (flag == 1) {
                    conditonDate = CalendarUtils.calculateEndDate(periodStartDate,-2);
                } else {
                    conditonDate = CalendarUtils.calculateEndDate(periodStartDate,-1);
                }
                fillListViewData();
                break;
            case R.id.query_iv2:
                if (flag == 1) {
                    conditonDate = CalendarUtils.calculateEndDate(periodEndDate, 2);
                } else {
                    conditonDate = CalendarUtils.calculateEndDate(periodEndDate, 1);
                }
                fillListViewData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        fillListViewData();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        fillListViewData();
        super.onResume();
    }

    private void fillListViewData() {
        flag = spCondition.getSelectedItemPosition();
        String conditonPeriod = CalendarUtils.getDatePeriod(conditonDate, flag);
        String[] period = conditonPeriod.split(";");
        periodStartDate = period[0];
        periodEndDate = period[1];
        if (flag == 0) {
            tvCondtion.setText(CalendarUtils.getDateFormate(periodStartDate,true));
        } else {
            tvCondtion.setText(CalendarUtils.getDateFormate(periodStartDate,
                    false)
                    + "-"
                    + CalendarUtils.getDateFormate(periodEndDate, false));
        }
        list.clear();
        list.addAll(dbOperator.findPart(offset, maxno, period[0], period[1]));
        dapter = new MyListAdapter(getActivity(), list);
        lv.setAdapter(dapter);
        dapter.notifyDataSetChanged();

    }

    private class MyListAdapter extends BaseAdapter {
        private Context context;
        private List<TaskDetails> list;

        public MyListAdapter(Context context, List<TaskDetails> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            if (list == null) {
                return null;
            }
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_time_line,null);
                holder.tl_tv_date_time = (TextView) convertView.findViewById(R.id.tl_tv_date_time);
                holder.tl_tv_content = (TextView) convertView.findViewById(R.id.tl_tv_content);
                holder.tl_task_time = (TextView) convertView.findViewById(R.id.tl_task_time);
                holder.tl_alarm_time = (TextView) convertView.findViewById(R.id.tl_alarm_time);
                holder.tl_ll_content = (LinearLayout) convertView.findViewById(R.id.tl_ll_content);
                holder.tl_iv_timedot = (ImageView) convertView.findViewById(R.id.tl_iv_timedot);
                holder.tl_iv_datedot = (ImageView) convertView.findViewById(R.id.tl_iv_datedot);
                holder.tl_title = (RelativeLayout) convertView.findViewById(R.id.tl_title);
                holder.tl_content = (RelativeLayout) convertView.findViewById(R.id.tl_content);
                holder.tl_iv_alarm_time = (ImageView) convertView.findViewById(R.id.tl_iv_alarm_time);
                holder.tl_iv_cycle = (ImageView) convertView.findViewById(R.id.tl_iv_cycle);
                holder.tl_cycle = (TextView) convertView.findViewById(R.id.tl_cycle);
                holder.tl_space = (TextView) convertView.findViewById(R.id.tl_space);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            LayoutParams params = (LayoutParams) holder.tl_iv_timedot
                    .getLayoutParams();
            if (position == 0) {
                params.addRule(RelativeLayout.ALIGN_TOP, R.id.tl_ll_contenter);
                params.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.tl_ll_contenter);
                holder.tl_tv_date_time.setText(list.get(position).getDate());
                holder.tl_title.setVisibility(View.VISIBLE);
            } else {
                if (list.get(position).getDate()
                        .equals(list.get(position - 1).getDate())) {
                    holder.tl_title.setVisibility(View.GONE);
                    params.addRule(RelativeLayout.ALIGN_TOP,R.id.tl_ll_contenter);
                    params.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.tl_ll_contenter);
                } else {
                    holder.tl_title.setVisibility(View.VISIBLE);
                    holder.tl_tv_date_time.setText(list.get(position).getDate());
                    params.addRule(RelativeLayout.ALIGN_TOP,R.id.tl_ll_contenter);
                    params.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.tl_ll_contenter);
                }

            }

            holder.tl_tv_content.setText(list.get(position).getContent());
            holder.tl_task_time.setText(list.get(position).getStartTime() + "-" + list.get(position).getEndTime());
            holder.tl_alarm_time.setText(list.get(position).getReminderDate());

            holder.tl_iv_timedot.setLayoutParams(params);
            if (TextUtils.isEmpty(list.get(position).getReminderDate())) {
                holder.tl_alarm_time.setVisibility(View.GONE);
                holder.tl_iv_alarm_time.setVisibility(View.GONE);
                holder.tl_space.setVisibility(View.GONE);
            } else {
                holder.tl_alarm_time.setVisibility(View.VISIBLE);
                holder.tl_iv_alarm_time.setVisibility(View.VISIBLE);
                holder.tl_space.setVisibility(View.VISIBLE);
            }
            if (list.get(position).getCycle() == 0) {
                holder.tl_cycle.setVisibility(View.GONE);
                holder.tl_iv_cycle.setVisibility(View.GONE);
            } else {
                holder.tl_cycle.setVisibility(View.VISIBLE);
                holder.tl_iv_cycle.setVisibility(View.VISIBLE);
                holder.tl_cycle.setText(cycle[list.get(position).getCycle()]);
            }
            holder.tl_tv_content.setTextColor(colors[list.get(position)
                    .getType()]);

            holder.tl_ll_content.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Builder builder2 = new Builder(context);
                    builder2.setTitle("日程管理");
                    final String[] arr = new String[]{"修改日程", "删除日程"};
                    builder2.setItems(arr,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,int which) {
                                    if (which == 0) {
                                        Intent intent = new Intent(context,AddTaskActivity.class);
                                        intent.putExtra("oper","update");
                                        intent.putExtra("id", list.get(position).get_id());
                                        startActivityForResult(intent, 5);
                                    } else {

                                        Builder builder = new Builder(context);
                                        builder.setTitle("删除日程");
                                        builder.setMessage("是否删除该条日程");
                                        builder.setPositiveButton("删除",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog,int which) {
                                                        dbOperator.delete(String.valueOf(list.get(position).get_id()));
                                                        list.remove(position);
                                                        dapter.notifyDataSetChanged();
                                                    }
                                                });
                                        builder.setNegativeButton("取消",null);
                                        builder.show();
                                    }
                                }
                            });
                    builder2.setNegativeButton("取消", null);
                    builder2.create().show();
                    return true;
                }
            });

            return convertView;
        }
    }

    static class ViewHolder {
        TextView tl_tv_date_time, tl_tv_content, tl_task_time, tl_alarm_time,tl_cycle, tl_space;
        View line;
        LinearLayout tl_ll_content;
        ImageView tl_iv_timedot, tl_iv_datedot, tl_iv_alarm_time, tl_iv_cycle;
        RelativeLayout tl_title, tl_content;
    }
}
