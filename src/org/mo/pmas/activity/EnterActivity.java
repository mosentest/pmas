package org.mo.pmas.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.*;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.view.ViewHelper;
import org.mo.common.ui.JazzyViewPager;
import org.mo.common.ui.OutlineContainer;
import org.mo.pmas.activity.adapter.EnterFragmentPageAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.jazzyPager)
    private JazzyViewPager jazzyPager;
    List<Map<String, View>> tabViews = new ArrayList<Map<String, View>>();
    Context context;
    public TabHost tabHost;
    private EnterFragmentPageAdapter enterFragmentPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
        enterFragmentPageAdapter = new EnterFragmentPageAdapter(getSupportFragmentManager());
        jazzyPager.setAdapter(enterFragmentPageAdapter);
        //TODO 修改适配器
//        jazzyPager.setAdapter(new MainAdapter());
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
//        String[] effects = this.getResources().getStringArray(R.array.jazzy_effects);
//        for (String effect : effects)
//            menu.add(effect);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        JazzyViewPager.TransitionEffect effect = JazzyViewPager.TransitionEffect.valueOf(item.getTitle().toString());
//        initJazzyPager(effect);
        return true;
    }

    @Override
    public void onClick(View v) {

    }




    private class MainAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            TextView text = new TextView(EnterActivity.this);
            text.setGravity(Gravity.CENTER);
            text.setTextSize(30);
            text.setTextColor(Color.WHITE);
            //TODO 显示数据
            text.setText("郑潇乾你是个傻逼 " + position);
            text.setPadding(30, 30, 30, 30);
            ListView listView  = new ListView(EnterActivity.this);
            container.addView(listView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            jazzyPager.setObjectForPosition(text, position);
            return text;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(jazzyPager.findViewFromObject(position));
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == obj;
            } else {
                return view == obj;
            }
        }
    }

}
