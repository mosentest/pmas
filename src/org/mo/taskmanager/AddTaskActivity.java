package org.mo.taskmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.mo.pmas.activity.R;
import org.mo.taskmanager.bean.TaskDetails;
import org.mo.taskmanager.db.TaskDBOperator;
import org.mo.taskmanager.utils.CalendarUtils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddTaskActivity extends Activity implements OnClickListener {

    private Spinner sp_cycle, sp_reminder, sp_type;
    private EditText et_content, et_date, et_startTimer, et_endTimer;
    private Button btnAdd;
    private static String[] cycle = {"不重复", "每天", "本周", "本月", "本年"};
    private static String[] reminder = {"不提醒", "按时提醒", "提前5分钟", "提前10分钟", "提前15分钟", "提前30分钟", "提前1小时"};
    private static String[] type = {"健康", "家庭", "友谊", "爱情", "工作", "学习", "兴趣"};
    private String oper;
    TaskDBOperator dbOperator;
    private int taskId;
    private OnDateSetListener changerListener;
    DatePickerDialog datePickerDialog;
    String timeString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        sp_cycle = (Spinner) findViewById(R.id.sp_cycle);
        sp_reminder = (Spinner) findViewById(R.id.sp_reminder);
        sp_type = (Spinner) findViewById(R.id.sp_stype);
        et_startTimer = (EditText) findViewById(R.id.et_startTimer);
        et_endTimer = (EditText) findViewById(R.id.et_endTimer);
        et_content = (EditText) findViewById(R.id.et_taskcontent);
        et_date = (EditText) findViewById(R.id.et_tastDate);
        ArrayAdapter adpt = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cycle);
        adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cycle.setAdapter(adpt);

        ArrayAdapter adpt2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
        adpt2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_type.setAdapter(adpt2);

        ArrayAdapter adpt1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, reminder);
        adpt1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_reminder.setAdapter(adpt1);

        et_date.setOnClickListener(this);
        et_startTimer.setOnClickListener(this);
        et_endTimer.setOnClickListener(this);
        initDateAndTime();
        dbOperator = new TaskDBOperator(getApplicationContext());
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        Intent it = getIntent();
        oper = it.getStringExtra("oper");
        if ("update".equals(oper)) {
            taskId = it.getIntExtra("id", 0);
            initTaskDetails(taskId);
            btnAdd.setText("添加");
        }

    }

    private void initTaskDetails(int id) {
        TaskDetails taskDetails = dbOperator.findTaskById(id);
        et_content.setText(taskDetails.getContent());
        et_date.setText(taskDetails.getDate());
        et_startTimer.setText(taskDetails.getStartTime());
        et_endTimer.setText(taskDetails.getEndTime());
        sp_cycle.setSelection(taskDetails.getCycle());
        sp_reminder.setSelection(taskDetails.getReminder());
        sp_type.setSelection(taskDetails.getType());
    }

    /**
     * ��ʼ����ǰ������ʱ���ؼ���Ĭ��ֵ
     */
    private void initDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int cyear = calendar.get(Calendar.YEAR);
        int cmonth = calendar.get(Calendar.MONTH);
        int cday = calendar.get(Calendar.DAY_OF_MONTH);
        int month = cmonth + 1;
        String monthString = String.valueOf(month);
        if (monthString.length() == 1) {
            monthString = "0" + monthString;
        }
        String dayString = String.valueOf(cday);
        if (dayString.length() == 1) {
            dayString = "0" + dayString;
        }
        et_date.setText(cyear + "-" + monthString + "-" + dayString);

        String hourString = String.valueOf(hour);
        if (hourString.length() == 1) {
            hourString = "0" + hourString;
        }
        String minuteString = String.valueOf(minutes);
        if (minuteString.length() == 1) {
            minuteString = "0" + minuteString;
        }

        et_startTimer.setText(hourString + ":" + minuteString);
        et_endTimer.setText(hourString + ":" + minuteString);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.et_tastDate:
                createDetePickerDialog();
                break;
            case R.id.et_startTimer:
                creatTimerPickerDialog(R.id.et_startTimer);
                break;
            case R.id.et_endTimer:
                creatTimerPickerDialog(R.id.et_endTimer);
                et_endTimer.setText(timeString);
                break;
            case R.id.btn_add:
                if (TextUtils.isEmpty(et_content.getText())) {
                    Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    et_content.startAnimation(shake);
                } else {
                    try {
                        TaskDetails task = getMyTask();
                        if ("update".equals(oper)) {
                            dbOperator.update(task);
                            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            dbOperator.add(task);
                            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                        }
                        if (sp_reminder.getSelectedItemPosition() > 0) {
                            setAlarm(task);
                        } else if ("update".equals(oper)) {
                            cancleAlarm(taskId);
                        }
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void creatTimerPickerDialog(final int id) {
        OnTimeSetListener oslListener = new OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hourString = String.valueOf(hourOfDay);
                timeString = formateDateOrTime(String.valueOf(hourOfDay)) + ":" + formateDateOrTime(String.valueOf(minute));
                if (id == R.id.et_startTimer) {
                    et_startTimer.setText(timeString);
                } else if (id == R.id.et_endTimer) {
                    et_endTimer.setText(timeString);
                }
            }
        };
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(this, oslListener, hours, minute, true).show();
        timeString = formateDateOrTime(hours + "") + ":" + formateDateOrTime(minute + "");

    }

    private String formateDateOrTime(String str) {
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }

    private void createDetePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        changerListener = new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                System.out.println("year" + year + "monthOfYear" + monthOfYear + "monthOfYear" + dayOfMonth);
                int ccmonth = monthOfYear + 1;
                String monthString = String.valueOf(ccmonth);
                if (monthString.length() == 1) {
                    monthString = "0" + monthString;
                }
                String dayString = String.valueOf(dayOfMonth);
                if (dayString.length() == 1) {
                    dayString = "0" + dayString;
                }
                String selectDateString = year + "-" + monthString + "-" + dayString;
                et_date.setText(selectDateString);

            }
        };
        datePickerDialog = new DatePickerDialog(this, changerListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private TaskDetails getMyTask() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setContent(et_content.getText().toString());
        taskDetails.setCycle(sp_cycle.getSelectedItemPosition());
        taskDetails.setDate(et_date.getText().toString());
        taskDetails.setEndTime(et_endTimer.getText().toString());
        taskDetails.setReminder(sp_reminder.getSelectedItemPosition());
        taskDetails.setType(sp_type.getSelectedItemPosition());
        taskDetails.setStartTime(et_startTimer.getText().toString());
        if (sp_reminder.getSelectedItemPosition() > 0) {

            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(dateFormat.parse(et_date.getText() + " " + et_startTimer.getText().toString()));
                switch (sp_reminder.getSelectedItemPosition()) {
                    case 2:// ��ǰ���������
                        calendar.add(Calendar.MINUTE, -5);
                        break;
                    case 3:// ��ǰ10��������
                        calendar.add(Calendar.MINUTE, -10);
                        break;
                    case 4:// ��ǰ15��������
                        calendar.add(Calendar.MINUTE, -15);
                        break;
                    case 5:// ��ǰ30��������
                        calendar.add(Calendar.MINUTE, -30);
                        break;
                    case 6:// ��ǰ1Сʱ����
                        calendar.add(Calendar.HOUR, -1);
                        break;
                    default:
                        break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            taskDetails.setReminderDate(dateFormat1.format(calendar.getTime()));
        }

        if ("update".equals(oper)) {
            taskDetails.set_id(taskId);

        }
        try {
            Date dateFrom = dateFormat.parse(taskDetails.getDate() + " " + taskDetails.getStartTime());
            Date dateTo = dateFormat.parse(taskDetails.getDate() + " " + taskDetails.getEndTime());
            long diff = dateTo.getTime() - dateFrom.getTime();
            int minute = (int) (diff / (1000 * 60));
            taskDetails.setTime(minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (sp_cycle.getSelectedItemPosition() > 0) {
            if (sp_cycle.getSelectedItemPosition() == 1) {
                taskDetails.setExpireDate("2099-12-31");
            } else {
                String conditonPeriod = CalendarUtils.getDatePeriod(taskDetails.getDate(), sp_cycle.getSelectedItemPosition() - 1);
                String[] period = conditonPeriod.split(";");
                taskDetails.setExpireDate(period[1]);
            }
        }

        return taskDetails;

    }

    private boolean setAlarm(TaskDetails taskDetails) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int id = 0;
        if ("update".equals(oper)) {
            id = taskId;
        } else {
            id = dbOperator.findMaxid();
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(taskDetails.getDate() + " "
                    + taskDetails.getReminderDate()));
            if (System.currentTimeMillis() < calendar.getTimeInMillis()) {
                Intent intent = new Intent(AddTaskActivity.this,
                        AlarmActivity.class);
                intent.putExtra("content", taskDetails.getContent());
                PendingIntent pi = PendingIntent.getActivity(AddTaskActivity.this, id, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
                AlarmManager aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                aManager.set(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(), pi);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void cancleAlarm(int id) {
        Intent intent = new Intent(AddTaskActivity.this, AlarmActivity.class);
        PendingIntent pi = PendingIntent.getActivity(AddTaskActivity.this, id,
                intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmManager aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        aManager.cancel(pi);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        switch (resultCode) {
            case 0:
                String date = data.getStringExtra("date");
                et_date.setText(date);
                break;
            case 1:
                String time = data.getStringExtra("time");
                if (requestCode == 1) {
                    et_startTimer.setText(time);
                } else {
                    et_endTimer.setText(time);
                }
                break;

            default:
                break;
        }
    }
}
