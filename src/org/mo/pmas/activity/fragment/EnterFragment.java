package org.mo.pmas.activity.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.mo.common.ui.sideBar.ClearEditText;
import org.mo.common.ui.sideBar.LoadingView;
import org.mo.common.ui.sideBar.SideBar;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.adapter.ContactAdapter;
import org.mo.pmas.comm.Constant;
import org.mo.pmas.entity.Contact;
import org.mo.pmas.util.AsyncTaskBase;
import org.mo.pmas.util.CharacterParser;
import org.mo.pmas.util.ConstactUtil;
import org.mo.pmas.util.PinyinComparator;

import java.util.*;

/**
 * Created by moziqi on 2014/12/27 0027.
 */
public class EnterFragment extends Fragment {
    private static EnterFragment mEnterFragment;
    private Context mContext;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private ContactAdapter adapter;
    private ClearEditText mClearEditText;
    private Map<String, String> callRecords;
    private LoadingView mLoadingView;
    View rootView;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<Contact> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private int mNum; //页号

    public int getmNum() {
        return mNum;
    }

    public void setmNum(int mNum) {
        this.mNum = mNum;
    }

//
    public static EnterFragment newInstance(Context context) {
        if (mEnterFragment == null)
            mEnterFragment = new EnterFragment(context);
        return mEnterFragment;
    }


    public EnterFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里我只是简单的用num区别标签，其实具体应用中可以使用真实的fragment对象来作为叶片
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    /**
     * 为Fragment加载布局时调用*
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_phone_contacts_list, null);
        mLoadingView = (LoadingView) rootView.findViewById(R.id.loading);
        sideBar = (SideBar) rootView.findViewById(R.id.sidrbar);
        dialog = (TextView) rootView.findViewById(R.id.dialog);
        sortListView = (ListView) rootView.findViewById(R.id.country_lvcountry);
        initData();
        return rootView;
    }


    private void initData() {

        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @SuppressLint("NewApi")
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getActivity(),
                        ((Contact) adapter.getItem(position)).getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        new AsyncTaskConstact(mLoadingView).execute(0);
    }

    private class AsyncTaskConstact extends AsyncTaskBase {

        public AsyncTaskConstact(LoadingView loadingView) {
            super(loadingView);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int result = -1;
            //TODO 权限没有....
            callRecords = ConstactUtil.getAllCallRecords(mContext);
            result = 1;
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {

            super.onPostExecute(result);
            if (result == 1) {
                List<String> constact = new ArrayList<String>();
                for (Iterator<String> keys = callRecords.keySet().iterator(); keys
                        .hasNext(); ) {
                    String key = keys.next();
                    constact.add(key);
                }
                String[] names = new String[]{};
                names = constact.toArray(names);
                SourceDateList = filledData(names);

                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                adapter = new ContactAdapter(mContext, SourceDateList);
                sortListView.setAdapter(adapter);
                mClearEditText = (ClearEditText) rootView.findViewById(R.id.filter_edit);

                // 根据输入框输入值的改变来过滤搜索
                mClearEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                        filterData(s.toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }

        }

    }

    public List<Contact> filledData(String[] date) {
        List<Contact> mSortList = new ArrayList<Contact>();

        for (int i = 0; i < date.length; i++) {
            Contact sortModel = new Contact();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }


    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    public void filterData(String filterStr) {
        List<Contact> filterDateList = new ArrayList<Contact>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (Contact sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
}