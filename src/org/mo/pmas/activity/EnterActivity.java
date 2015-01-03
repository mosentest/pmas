package org.mo.pmas.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.view.ViewHelper;
import org.json.JSONObject;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.ui.JazzyViewPager;
import org.mo.common.util.ToastUtil;
import org.mo.pmas.activity.adapter.EnterFragmentPageAdapter;
import org.mo.pmas.bmob.entity.MyUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterActivity extends BaseFramgmentActivity implements View.OnClickListener {
    @ViewInject(R.id.jazzyPager)
    private JazzyViewPager jazzyPager;
    List<Map<String, View>> tabViews = new ArrayList<Map<String, View>>();
    Context context;
    public TabHost tabHost;
    private EnterFragmentPageAdapter enterFragmentPageAdapter;
    private Menu menu;
    private ListView mListView_left_drawer;

    public EnterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //TODO Bmob 测试获取成功
        BmobQuery<MyUser> bmobQuery = new BmobQuery<MyUser>();
        bmobQuery.findObjects(this, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> myUsers) {
                for (MyUser myUser : myUsers)
                    ToastUtil.showShortToast(EnterActivity.this, myUser.getUsername());
            }

            @Override
            public void onError(int i, String s) {
                ToastUtil.showLongToast(EnterActivity.this, "失败");
            }
        });
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
        initJazzyPager(JazzyViewPager.TransitionEffect.Standard);
        //TODO 抽屉实现
        mListView_left_drawer = (ListView) findViewById(R.id.listView_left_drawer);
        String[] stringArray = getResources().getStringArray(R.array.effects);
        mListView_left_drawer.setAdapter(new DrawableAdapter(this, stringArray));
        mListView_left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = LayoutInflater.from(contexts).inflate(R.layout.main_drawer_list_item, parent, false);
            TextView textView = (TextView) inflate.findViewById(R.id.draw_item);
//            ImageView imageView = (ImageView)inflate.findViewById(R.id.imageView);
            //TODO 这里没做好
//            Bitmap bitmap = BitmapFactory.decodeFile(R.drawable.ic_drow_1 + "");
//            imageView.setImageBitmap(bitmap);
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
        jazzyPager.setTransitionEffect(effect);
        //TODO --------------------------------
        enterFragmentPageAdapter = new EnterFragmentPageAdapter(getSupportFragmentManager(), this);
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
                        menu.findItem(R.id.code).setVisible(false);

                        break;
                    case 1:
                        menu.findItem(R.id.save_contact).setVisible(false);
                        menu.findItem(R.id.save_note).setVisible(true);
                        menu.findItem(R.id.save_schedule).setVisible(false);
                        menu.findItem(R.id.code).setVisible(false);

                        break;
                    case 2:
                        menu.findItem(R.id.save_contact).setVisible(false);
                        menu.findItem(R.id.save_note).setVisible(false);
                        menu.findItem(R.id.save_schedule).setVisible(true);
                        menu.findItem(R.id.code).setVisible(false);

                        break;
                    case 3:
                        menu.findItem(R.id.save_contact).setVisible(false);
                        menu.findItem(R.id.save_note).setVisible(false);
                        menu.findItem(R.id.save_schedule).setVisible(false);
                        menu.findItem(R.id.code).setVisible(true);
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
        this.menu.findItem(R.id.code).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onClick(View v) {

    }

}
