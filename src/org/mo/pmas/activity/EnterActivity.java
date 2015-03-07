package org.mo.pmas.activity;

import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.view.ViewHelper;

import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.ui.JazzyViewPager;
import org.mo.common.util.HttpURLTools;
import org.mo.pmas.activity.adapter.EnterFragmentPageAdapter;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.activity.fragment.ScheduleFragment;
import org.mo.pmas.activity.fragment.ContactFragment;
import org.mo.pmas.activity.fragment.NoteFragment;
import org.mo.pmas.activity.fragment.SettingFragment;
import org.mo.pmas.service.NetService;
import org.mo.taskmanager.AddTaskActivity;
import org.mo.taskmanager.QueryFragment;
import org.mo.taskmanager.SecheduleActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterActivity extends BaseFramgmentActivity implements View.OnClickListener {
    @ViewInject(R.id.jazzyPager)
    private JazzyViewPager jazzyPager;
    List<Map<String, View>> tabViews = null;
    Context context;
    public TabHost tabHost;
    private EnterFragmentPageAdapter enterFragmentPageAdapter;
    private Menu menu;
    private ListView mListView_left_drawer;
    private static long firstTime;
    private static List<Fragment> list = null;
    //检测网络============================================================
    private boolean conncetState = true;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NetService.NetBind bind = (NetService.NetBind) service;
            NetService netService = bind.getNetService();
            //此处回调
            netService.setOnGetConnectState(new NetService.GetConnectState() {
                @Override
                public void GetState(boolean isConnected) {
                    if (conncetState != isConnected) {
                        conncetState = isConnected;
                    }
                    Message msg = handler.obtainMessage();
                    if (conncetState) {
                        msg.what = 1;
                    } else {
                        msg.what = 2;
                    }
                    handler.sendMessage(msg);
                }
            });

        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //Toast.makeText(EnterActivity.this, "connect", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(EnterActivity.this, "无网络连接,请检查您的手机网络", Toast.LENGTH_LONG).show();
                default:
                    break;
            }
        }
    };
    private SharedPreferences preferences;

    private void unBind() {
        if (conn != null) {
            unbindService(conn);
        }
    }

    public EnterActivity() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = new Intent(this, NetService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        //检测网络============================================================
        setContentView(R.layout.main);
        tabViews = new ArrayList<Map<String, View>>();
        ViewUtils.inject(this);
        context = this;
        // --------------------
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        //TODO 设置下面显示的图标按钮
        tabHost.addTab(tabHost.newTabSpec("0").setIndicator(createTab(getString(R.string.contact), 0)).setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("1").setIndicator(createTab(getString(R.string.note), 1)).setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("2").setIndicator(createTab(getString(R.string.schedule), 2)).setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("3").setIndicator(createTab(getString(R.string.setting), 3)).setContent(android.R.id.tabcontent));
        // 点击tabHost 来切换不同的消息
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int index = Integer.parseInt(tabId);
                setTabSelectedState(index, 4);
                tabHost.getTabContentView().setVisibility(View.GONE);// 隐藏content
            }
        });
        tabHost.setCurrentTab(0);
        //TODO 抽屉实现
//        mListView_left_drawer = (ListView) findViewById(R.id.listView_left_drawer);
//        String[] stringArray = getResources().getStringArray(R.array.effects);
//        mListView_left_drawer.setAdapter(new DrawableAdapter(this, stringArray));
//        mListView_left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        Intent intent1 = new Intent(EnterActivity.this, ContactGroupActivity.class);
//                        startActivity(intent1);
//                        break;
//                    case 1:
//                        Log.e("TAG", "stringArray[1]");
//                        break;
//                    case 2:
//                        Log.e("TAG", "stringArray[2]");
//                        break;
//                }
//            }
//        });
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {
        initJazzyPager(JazzyViewPager.TransitionEffect.Standard);
    }

    /**
     * 抽屉适配器
     */
    private static class DrawableAdapter extends BaseAdapter {

        private String[] strings;
        private Context contexts;

        public DrawableAdapter(Context contexts, String[] strings) {
            this.strings = strings;
            this.contexts = contexts;
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View inflate = LayoutInflater.from(contexts).inflate(R.layout.main_drawer_list_item, parent, false);
            TextView textView = (TextView) inflate.findViewById(R.id.draw_item);
            textView.setText(strings[position]);
            return inflate;
        }
    }

    /**
     * 动态创建 TabWidget 的Tab项,并设置normalLayout的alpha为1，selectedLayout的alpha为0[显示normal，隐藏selected]
     *
     * @param tabLabelText
     * @param tabIndex
     * @return
     */
    private View createTab(String tabLabelText, int tabIndex) {
        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.main_tabwidget_layout, null);
        TextView normalTV = (TextView) tabIndicator.findViewById(R.id.normalTV);
        TextView selectedTV = (TextView) tabIndicator.findViewById(R.id.selectedTV);
        normalTV.setText(tabLabelText);
        selectedTV.setText(tabLabelText);
        ImageView normalImg = (ImageView) tabIndicator.findViewById(R.id.normalImg);
        ImageView selectedImg = (ImageView) tabIndicator.findViewById(R.id.selectedImage);
        switch (tabIndex) {
            case 0:
                normalImg.setImageResource(R.drawable.scan_book);
                selectedImg.setImageResource(R.drawable.scan_book_hl);
                break;
            case 1:
                normalImg.setImageResource(R.drawable.scan_qr);
                selectedImg.setImageResource(R.drawable.scan_qr_hl);
                break;
            case 2:
                normalImg.setImageResource(R.drawable.scan_street);
                selectedImg.setImageResource(R.drawable.scan_street_hl);
                break;
            case 3:
                normalImg.setImageResource(R.drawable.scan_word);
                selectedImg.setImageResource(R.drawable.scan_word_hl);
                break;
        }
        View normalLayout = tabIndicator.findViewById(R.id.normalLayout);
        normalLayout.setAlpha(1f);// 透明度显示
        View selectedLayout = tabIndicator.findViewById(R.id.selectedLayout);
        selectedLayout.setAlpha(0f);// 透明的隐藏
        Map<String, View> map = new HashMap<String, View>();
        map.put("normal", normalLayout);
        map.put("selected", selectedLayout);
        tabViews.add(map);
        return tabIndicator;
    }

    /**
     * 设置tab状态选中
     *
     * @param index
     */
    private void setTabSelectedState(int index, int tabCount) {
        for (int i = 0; i < tabCount; i++) {
            if (i == index) {
                tabViews.get(i).get("normal").setAlpha(0f);
                tabViews.get(i).get("selected").setAlpha(1f);
            } else {
                tabViews.get(i).get("normal").setAlpha(1f);
                tabViews.get(i).get("selected").setAlpha(0f);
            }
        }
        jazzyPager.setCurrentItem(index, false);// false表示 代码切换 pager 的时候不带scroll动画
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTabSelectedState(tabHost.getCurrentTab(), 4);
    }

    private void initJazzyPager(JazzyViewPager.TransitionEffect effect) {
        list = new ArrayList<Fragment>();
        list.add(ContactFragment.newInstance(this));
        list.add(NoteFragment.getInstance(this));
        list.add(QueryFragment.getInstance(this));
        list.add(SettingFragment.getInstance(this));
        jazzyPager.setTransitionEffect(effect);
        //TODO -------------卡顿
        jazzyPager.setOffscreenPageLimit(list.size());
        enterFragmentPageAdapter = new EnterFragmentPageAdapter(getSupportFragmentManager(), this, list);
        jazzyPager.setAdapter(enterFragmentPageAdapter);
        //TODO 修改适配器
        jazzyPager.setPageMargin(30);
        jazzyPager.setFadeEnabled(true);
        jazzyPager.setSlideCallBack(new JazzyViewPager.SlideCallback() {
            @Override
            public void callBack(int position, float positionOffset) {
                Map<String, View> map = tabViews.get(position);
                ViewHelper.setAlpha(map.get("selected"), positionOffset);
                ViewHelper.setAlpha(map.get("normal"), 1 - positionOffset);
            }
        });
        jazzyPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        menu.findItem(R.id.save_contact).setVisible(true);
                        menu.findItem(R.id.save_note).setVisible(false);
                        menu.findItem(R.id.save_schedule).setVisible(false);
//                        menu.findItem(R.id.code).setVisible(false);
//                        menu.findItem(R.id.synchronous_contact).setVisible(true);
//                        menu.findItem(R.id.synchronous_note).setVisible(false);
//                        menu.findItem(R.id.synchronous_schedule).setVisible(false);
                        break;
                    case 1:
                        menu.findItem(R.id.save_contact).setVisible(false);
                        menu.findItem(R.id.save_note).setVisible(true);
                        menu.findItem(R.id.save_schedule).setVisible(false);
//                        menu.findItem(R.id.code).setVisible(false);
//                        menu.findItem(R.id.synchronous_contact).setVisible(false);
//                        menu.findItem(R.id.synchronous_note).setVisible(true);
//                        menu.findItem(R.id.synchronous_schedule).setVisible(false);
                        break;
                    case 2:
                        menu.findItem(R.id.save_contact).setVisible(false);
                        menu.findItem(R.id.save_note).setVisible(false);
                        menu.findItem(R.id.save_schedule).setVisible(true);
//                        menu.findItem(R.id.code).setVisible(false);
//                        menu.findItem(R.id.synchronous_contact).setVisible(false);
//                        menu.findItem(R.id.synchronous_note).setVisible(false);
//                        menu.findItem(R.id.synchronous_schedule).setVisible(true);

                        break;
                    case 3:
                        menu.findItem(R.id.save_contact).setVisible(false);
                        menu.findItem(R.id.save_note).setVisible(false);
                        menu.findItem(R.id.save_schedule).setVisible(false);
//                        menu.findItem(R.id.code).setVisible(true);
//                        menu.findItem(R.id.synchronous_contact).setVisible(false);
//                        menu.findItem(R.id.synchronous_note).setVisible(false);
//                        menu.findItem(R.id.synchronous_schedule).setVisible(false);
                        break;
                }
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
            }

            @Override
            public void onPageScrollStateChanged(int paramInt) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO 动态菜单
        getMenuInflater().inflate(R.menu.activity_enter_setting_actions, menu);
        this.menu = menu;
        this.menu.findItem(R.id.save_contact).setVisible(true);
        this.menu.findItem(R.id.save_note).setVisible(false);
        this.menu.findItem(R.id.save_schedule).setVisible(false);
//        this.menu.findItem(R.id.code).setVisible(false);
//        menu.findItem(R.id.synchronous_contact).setVisible(true);
//        menu.findItem(R.id.synchronous_note).setVisible(false);
//        menu.findItem(R.id.synchronous_schedule).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contact:
                Intent intent3 = new Intent(EnterActivity.this, ContactAddActivity.class);
                startActivity(intent3);
                return true;
            case R.id.save_note:
//                String currentUserName = PmasAppliaction.getInstance().getCurrentUserName();
//                if ("请登录".equals(currentUserName)) {
//                    ShowToast("请登录");
//                } else {
                Intent intent = new Intent(EnterActivity.this, NoteAddActivity.class);
                startActivity(intent);
//                }
                return true;
            case R.id.save_schedule:
                Intent intent2 = new Intent(EnterActivity.this, AddTaskActivity.class);
                startActivity(intent2);
                return true;
//            case R.id.code:
//                ShowToast("二维码");
//                return true;
//            case R.id.synchronous_schedule:
//                Intent intent4 = new Intent(EnterActivity.this, AddTaskActivity.class);
//                startActivityForResult(intent4,0);
            default:
                return true;
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 连续按两次返回键就退出
     */
    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            HttpURLTools.closeClient();
            PmasAppliaction.getInstance().exit();
        } else {
            ShowToast("再按一次退出程序");
        }
        firstTime = System.currentTimeMillis();
    }

}
